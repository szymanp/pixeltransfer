package com.metapx.pixeltransfer.repository.local;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.metapx.pixeltransfer.database.ConnectionFactory;
import com.metapx.pixeltransfer.database.generated.Tables;
import com.metapx.pixeltransfer.repository.local.Repository;
import com.metapx.pixeltransfer.repository.local.Repository.TrackedFolder;

import junit.framework.TestCase;

public class RepositoryTest extends TestCase {

	Repository repo;
	DSLContext db;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Connection conn = ConnectionFactory.newInMemoryConnection();
		repo = new Repository(conn);
		db = DSL.using(conn, SQLDialect.H2);
	}
	
	public void testCreateFolder() {
		String userDir = System.getProperty("user.dir");
		Path folder = Paths.get(userDir);
		
		TrackedFolder trackedFolder = repo.getFolder(folder);
		
		assertEquals(folder.getFileName().toString(), trackedFolder.getName());
		assertEquals(ObjectState.ADD, trackedFolder.getState());
		assertTrue(trackedFolder.id != 0);
		assertTrue(trackedFolder.parentId != 0);
		
		assertEquals(folder, trackedFolder.getPath());
		assertEquals(userDir, trackedFolder.getPathAsString());
	}
	
	public void testAddFile() throws IOException {
		Path image = Paths.get(System.getProperty("user.dir"), "target", "test-classes", "IMG_1883_r.jpg");
		
		repo.addFile(image.toFile());
		
		System.out.println(db.selectFrom(Tables.IMAGE).fetch());
		System.out.println(db.selectFrom(Tables.FOLDER).fetch());
		System.out.println(db.selectFrom(Tables.IMAGE_FOLDER).fetch());
	}
}
