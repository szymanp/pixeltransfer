package com.metapx.pixeltransfer.repository.local;

import static com.metapx.pixeltransfer.database.generated.Tables.FOLDER;
import static com.metapx.pixeltransfer.database.generated.Tables.IMAGE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.metapx.pixeltransfer.common.Opt;
import com.metapx.pixeltransfer.database.generated.Tables;
import com.metapx.pixeltransfer.database.generated.tables.records.FolderRecord;
import com.metapx.pixeltransfer.database.generated.tables.records.ImageFolderRecord;
import com.metapx.pixeltransfer.database.generated.tables.records.ImageRecord;

/**
 * Perform operations on the local image repository.
 */
public final class Repository {

	private final DSLContext db;
	
	private final Map<Path, TrackedFolder> folders = new HashMap<Path, TrackedFolder>();
	
	private final TrackedFolder root = new TrackedFolder(this);
	
	public Repository(Connection databaseConnection) {
		this.db = DSL.using(databaseConnection, SQLDialect.H2);
	}
	
	/**
	 * Mark a file for adding to the repository.
	 * @param file
	 * @throws IOException
	 */
	public void addFile(File file) throws IOException {
		if (!file.exists()) {
			throw new IOException("File does not exist");
		}
		
		String digest;
		try {
			digest = HashCalculator.calculateStringDigest(file);
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("Could not calculate hash of file", e);
		}
		
		final int imageId = this.createImage(file, digest);
		final int folderId = this.getFolder(file.getParentFile().toPath()).getId();
		addImageToFolder(imageId, folderId);
	}
	
	/**
	 * Creates an image if it does not exist yet.
	 * @param file
	 * @param digest
	 * @return the ID of the image
	 */
	int createImage(File file, String digest) {
		ImageRecord record = db.selectFrom(IMAGE)
				.where(IMAGE.HASH.eq(digest))
				.fetchOne();
		
		if (record == null) {
			ImageRecord result = db.insertInto(IMAGE)
					.set(IMAGE.NAME, file.getName())
					.set(IMAGE.HASH, digest)
					.set(IMAGE.STATE, ObjectState.ADD.toByte())
					.returning(IMAGE.ID)
					.fetchOne();
			
			return result.getId();
		} else {
			ObjectState state = ObjectState.valueOf(record.get(IMAGE.STATE));
			
			if (state == ObjectState.REMOVE) {
				// TODO undo a remove
			}
			
			return record.getId();
		}
	}
	
	public TrackedFolder getFolder(Path path) {
		final Path normalized = path.normalize().toAbsolutePath();

		// We initialize the search at the root.
		TrackedFolder targetFolder = root;
		
		// Find the root Path component, if it exists. On Windows this would be a drive letter.
		final Path root = path.getRoot();
		if (root != null) {
			if (folders.containsKey(root)) {
				// Fetch the drive from the cache
				targetFolder = folders.get(root);
			} else {
				// Resolve the drive
				String rootElement = root.toString();
				
				// Strip the trailing 
				if (rootElement.endsWith(File.separator)) {
					rootElement = rootElement.substring(0, rootElement.length() - 1);
				}
				
				targetFolder = targetFolder.findOrCreateFolderElement(rootElement);
			}
		}
		
		for(Path element : normalized) {
			targetFolder = targetFolder.findOrCreateFolderElement(element.toString());
		}
		
		return targetFolder;
	}
	
	void addImageToFolder(int imageId, int folderId) {
		ImageFolderRecord record = db.selectFrom(Tables.IMAGE_FOLDER)
				.where(Tables.IMAGE_FOLDER.IMAGE_ID.eq(imageId)
						.and(Tables.IMAGE_FOLDER.FOLDER_ID.eq(folderId)))
				.fetchOne();

		if (record == null) {
			db.insertInto(Tables.IMAGE_FOLDER)
				.set(Tables.IMAGE_FOLDER.IMAGE_ID, imageId)
				.set(Tables.IMAGE_FOLDER.FOLDER_ID, folderId)
				.set(Tables.IMAGE_FOLDER.STATE, ObjectState.ADD.toByte())
				.execute();
		} else if (ObjectState.valueOf(record.getState()) == ObjectState.REMOVE) {
			db.update(Tables.IMAGE_FOLDER)
				.set(Tables.IMAGE_FOLDER.STATE, ObjectState.ADD.toByte())
				.where(Tables.IMAGE_FOLDER.IMAGE_ID.eq(imageId)
						.and(Tables.IMAGE_FOLDER.FOLDER_ID.eq(folderId)))
				.execute();
		}
	}
	
	public static class TrackedFolder {
		final Repository repo;
		final List<String> path;
		final Integer parentId;
		final Integer id;
		final String name;
		final ObjectState state;
		
		private TrackedFolder(TrackedFolder parent, String name, Integer id, ObjectState state) {
			ArrayList<String> newPath = new ArrayList<String>(parent.path);
			newPath.add(name);
			
			this.repo = parent.repo;
			this.path = Collections.unmodifiableList(newPath);
			this.parentId = parent.id;
			this.id = id;
			this.name = name;
			this.state = state;
		}
		
		private TrackedFolder(TrackedFolder parent, FolderRecord record) {
			this(parent, record.getName(), record.getId(), ObjectState.valueOf(record.getState()));
		}
		
		/**
		 * Root element constructor.
		 * @param repo
		 */
		private TrackedFolder(Repository repo) {
			this.repo = repo;
			this.path = Collections.unmodifiableList(new ArrayList<String>());
			this.name = "";
			this.id = 0;
			this.parentId = 0;
			this.state = ObjectState.COMMITTED;
		}
		
		TrackedFolder findOrCreateFolderElement(String name) {
			final Opt<TrackedFolder> result = findOrMaybeCreateSubfolder(name, true);
			if (result.hasValue()) {
				return result.getValue();
			} else {
				throw new RuntimeException();
			}
		}
		Opt<TrackedFolder> findOrMaybeCreateSubfolder(String name, boolean create) {
			final FolderRecord record = repo.db
					.selectFrom(FOLDER)
					.where(FOLDER.NAME.eq(name).and(FOLDER.PARENT_ID.eq(parentId)))
					.fetchOne();
			
			if (record == null && create) {
				final FolderRecord result = repo.db.insertInto(FOLDER)
					   .set(FOLDER.NAME, name)
					   .set(FOLDER.PARENT_ID, parentId)
					   .set(FOLDER.STATE, ObjectState.ADD.toByte())
					   .returning(FOLDER.ID)
					   .fetchOne();
				
				return Opt.of(new TrackedFolder(this, name, result.getId(), ObjectState.ADD));
			} else if (record == null) {
				return Opt.empty();
				
			} else {
				return Opt.of(new TrackedFolder(this, record));
			}
		}
		
		public List<String> getPathElements() {
			return path;
		}
		
		public Path getPath() {
			return Paths.get(this.getPathAsString());
		}
		
		public String getPathAsString() {
			return String.join(File.separator, path);
		}

		public Integer getParentId() {
			return parentId;
		}

		public Integer getId() {
			return id;
		}

		public ObjectState getState() {
			return state;
		}
		
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return TrackedFolder.class.getSimpleName() + ": " + getPathAsString() + "(" + parentId + ", " + id + ")";
		}
	}
	
	public class TrackedFile {
		
	}
}
