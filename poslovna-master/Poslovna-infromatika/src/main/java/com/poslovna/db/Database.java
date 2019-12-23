package com.poslovna.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class Database {

	private Connection conn;

	public  Connection getConnection() {
		if (conn == null)
			try {
				open();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return conn;
	}
	

	public void open() throws ClassNotFoundException, SQLException {
		if (conn != null)
			return;
		ResourceBundle bundle =
				PropertyResourceBundle.getBundle("DBConnection"); //ime fajla
		String driver = bundle.getString("driver"); //Ime parametara
		String url = bundle.getString("url");
		String username = bundle.getString("username");  
		String password = bundle.getString("password");
		Class.forName(driver); //Registrovanje drajvera
		conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
	}

	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Vraca nazive tabela u bazi podataka
	 * @return
	 * @throws SQLException
	 */
	public List<String> getDatabseTables() throws SQLException {
		DatabaseMetaData md = getConnection().getMetaData();
		String types[] = {"TABLE"};
		ResultSet rs = md.getTables(null, null, "%", types);
		List<String> tables = new ArrayList<String>();
		
		while (rs.next()) {
		  tables.add(rs.getString("TABLE_NAME"));
		}
		
		return tables;
	}
	
	public List<String> getTablePrimaryKeys(String tableName) throws SQLException {
		tableName = tableName.toUpperCase().replace(" ", "_");
		ResultSet rs = null;
		DatabaseMetaData meta = getConnection().getMetaData();
		rs = meta.getPrimaryKeys(null, null, tableName);
		List<String> primaryKeys = new ArrayList<String>();

		while (rs.next()) {
			String columnName = rs.getString("COLUMN_NAME");
			primaryKeys.add(columnName);
		}
		
		return primaryKeys;
	}
	public ArrayList<String> getForeignKeys(String databaseTableName) {
		ArrayList<String> straniKljucevi = new ArrayList<String>();

		try {
			ResultSet fKeys = getConnection().getMetaData().getImportedKeys(getConnection().getCatalog(), null, databaseTableName);
			while (fKeys.next()) {
				String fkColumnName = fKeys.getString("FKCOLUMN_NAME");

				if (!straniKljucevi.contains(fkColumnName)) {
					straniKljucevi.add(fkColumnName);
				}
			}

		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		return straniKljucevi;
	}
}
