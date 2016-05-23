package com.metapx.pixeltransfer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION_PREFIX = "jdbc:h2:";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static Connection newConnection(String filename) throws ClassNotFoundException, SQLException {
		Class.forName(DB_DRIVER);

		return DriverManager.getConnection(DB_CONNECTION_PREFIX + filename, DB_USER, DB_PASSWORD);
	}
}