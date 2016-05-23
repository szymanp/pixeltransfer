package com.metapx.pixeltransfer.database;

import java.sql.Connection;
import java.sql.Statement;

public final class DatabaseBuilder {
	
	private final String filename;
	
	public DatabaseBuilder(String filename) {
		this.filename = filename;
	}
	
	public void build() throws Exception {
		Connection connection = ConnectionFactory.newConnection(this.filename);
		
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            
            stmt.execute("CREATE TABLE SYSTEM(id int primary key, schema_version int)");
            stmt.execute("CREATE TABLE IMAGE(id int primary key, name varchar(255))");
            stmt.execute("CREATE TABLE FOLDER(id int primary key, name varchar(255), parent_id int)");
            stmt.execute("CREATE TABLE IMAGE_FOLDER(image_id int, folder_id int)");
            stmt.execute("CREATE UNIQUE INDEX IMAGE_FOLDER_01 ON IMAGE_FOLDER (image_id, folder_id)");
			stmt.execute("INSERT INTO SYSTEM(id, schema_version) VALUES(1, 1)");

            connection.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            connection.close();
        }
	}
}
