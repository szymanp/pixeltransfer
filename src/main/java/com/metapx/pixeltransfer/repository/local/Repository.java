package com.metapx.pixeltransfer.repository.local;

import static com.metapx.pixeltransfer.database.generated.Tables.FOLDER;

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

/**
 * Perform operations on the local image repository.
 */
public final class Repository {

	private Connection databaseConnection;
	private DSLContext db;
	
	public Repository(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
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
		
		
	}
	
	int createFolder(Path folder) {
		Path normalized = folder.normalize().toAbsolutePath();
		int parentId = this.findOrCreateFolderElement(normalized.getRoot().toString(), 0);
		
		for(Path element : folder.normalize().toAbsolutePath()) {
			parentId = this.findOrCreateFolderElement(element.toString(), parentId);
		}
		
		return parentId;
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
				   .set(FOLDER.PARENT_ID, 0)
				   .returning(FOLDER.ID)
				   .fetchOne();
			return result.getId();
		} else {
			return record.get(FOLDER.ID);
		}
		
	}
}
