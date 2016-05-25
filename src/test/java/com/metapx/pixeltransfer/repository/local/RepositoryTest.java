package com.metapx.pixeltransfer.repository.local;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.metapx.pixeltransfer.database.ConnectionFactory;
import com.metapx.pixeltransfer.repository.local.Repository;

import junit.framework.TestCase;

public class RepositoryTest extends TestCase {

	Repository repo;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		repo = new Repository(ConnectionFactory.newInMemoryConnection());
	}
	
	public void testCreateFolder() {
		Path folder = Paths.get(System.getProperty("user.dir"));
		
		repo.createFolder(folder);
	}
}
