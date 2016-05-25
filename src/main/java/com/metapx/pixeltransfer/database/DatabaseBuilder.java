package com.metapx.pixeltransfer.database;

import java.sql.Connection;
import java.sql.Statement;

public final class DatabaseBuilder {
	
	final private Connection connection;
	
	public static void buildFile(String filename) throws Exception {
		Connection connection = ConnectionFactory.newConnection(filename);
		try {
			DatabaseBuilder b = new DatabaseBuilder(connection);
			b.build();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			connection.close();
		}
	}
	
	public DatabaseBuilder(Connection connection) {
		this.connection = connection;
	}
	
	public void build() throws Exception {
        Statement stmt = connection.createStatement();
            
        stmt.execute("CREATE TABLE SYSTEM(id int primary key, schema_version int)");
        stmt.execute("CREATE TABLE IMAGE(id int auto_increment primary key, name varchar(255))");
        stmt.execute("CREATE TABLE FOLDER(id int auto_increment primary key, name varchar(255), parent_id int)");
        stmt.execute("CREATE TABLE IMAGE_FOLDER(image_id int, folder_id int)");
        stmt.execute("CREATE UNIQUE INDEX IMAGE_FOLDER_01 ON IMAGE_FOLDER (image_id, folder_id)");
		stmt.execute("INSERT INTO SYSTEM(id, schema_version) VALUES(1, 1)");

        connection.commit();
	}
}
