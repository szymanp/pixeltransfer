package com.metapx.pixeltransfer.repository.local;

import static com.metapx.pixeltransfer.database.generated.Tables.FOLDER;
import static com.metapx.pixeltransfer.database.generated.Tables.IMAGE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.metapx.pixeltransfer.database.generated.Tables;
import com.metapx.pixeltransfer.database.generated.tables.records.FolderRecord;
import com.metapx.pixeltransfer.database.generated.tables.records.ImageFolderRecord;
import com.metapx.pixeltransfer.database.generated.tables.records.ImageRecord;

/**
 * Perform operations on the local image repository.
 */
public final class Repository {

	private final DSLContext db;
	
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
		
		int imageId = this.createImage(file, digest);
		int folderId = this.createFolder(file.getParentFile().toPath());
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
	
	int createFolder(Path folder) {
		Path normalized = folder.normalize().toAbsolutePath();
		int parentId = this.findOrCreateFolderElement(normalized.getRoot().toString(), 0);
		
		for(Path element : folder.normalize().toAbsolutePath()) {
			parentId = this.findOrCreateFolderElement(element.toString(), parentId);
		}
		
		return parentId;
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
	
	private int findOrCreateFolderElement(String name, int parentId) {
		Record1<Integer> record = this.db
				.select(FOLDER.ID)
				.from(FOLDER)
				.where(FOLDER.NAME.eq(name).and(FOLDER.PARENT_ID.eq(parentId)))
				.fetchOne();
		
		if (record == null) {
			FolderRecord result = this.db.insertInto(FOLDER)
				   .set(FOLDER.NAME, name)
				   .set(FOLDER.PARENT_ID, parentId)
				   .set(FOLDER.STATE, ObjectState.ADD.toByte())
				   .returning(FOLDER.ID)
				   .fetchOne();
			return result.getId();
		} else {
			return record.get(FOLDER.ID);
		}
		
	}
}
