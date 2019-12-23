package com.poslovna.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.ChangedCharSetException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.db.Database;
import com.poslovna.models.Column;
import com.poslovna.xml.Faktura;
import com.poslovna.xml.Faktura.Stavka;

@Service
public class TablesService {

	private static final int CUSTOM_ERROR_CODE = 50000;
	private static final String ERROR_RECORD_WAS_CHANGED = "Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost";
	private static final String ERROR_RECORD_WAS_DELETED = "Slog je obrisan od strane drugog korisnika";

	@Autowired
	private Database db;

	/**
	 * Pronalazi sve podatke iz date tabele
	 * 
	 * @param tableName
	 * @return Lista redova i kolona
	 * @throws SQLException
	 */
	public List<List<String>> getDataForTable(String tableName) throws SQLException {
		System.out.println(tableName + " TNAME");
		Statement stmt = db.getConnection().createStatement();

		String sql = generateBasicSelectForTable(tableName);
		List<Column> columns = getColumnsForTable(tableName);

		ResultSet rset = stmt.executeQuery(sql);
		List<List<String>> resultData = new ArrayList<List<String>>();

		while (rset.next()) {
			List<String> row = new ArrayList<String>();
			for (Column c : columns) {
				row.add(rset.getString(c.getName()));
				if (c.getLookupField() != null) {
					row.add(rset.getString(c.getLookupField().getName()));
				}
			}

			resultData.add(row);
		}
		return resultData;
	}

	/**
	 * Vraca kolone za trazenu tabelu u za imenom kolone i tipom kolone
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<Column> getColumnsForTable(String tableName) throws SQLException {
		ResultSet rsColumns = null;
		DatabaseMetaData meta = db.getConnection().getMetaData();
		rsColumns = meta.getColumns(null, null, tableName, null);

		ResultSet keys = meta.getImportedKeys(null, null, tableName);

		List<Column> columns = new ArrayList<Column>();

		Map<String, String> fk = new HashMap<String, String>();

		List<String> primaryKeys = db.getTablePrimaryKeys(tableName);
		List<String> forgienKeys = db.getForeignKeys(tableName);

		while (keys.next()) {

			String fkColumnName = keys.getString("FKCOLUMN_NAME");
			String fkTableName = keys.getString("PKTABLE_NAME");

			fk.put(fkColumnName, fkTableName);
		}

		while (rsColumns.next()) {
			String columnName = rsColumns.getString("COLUMN_NAME");
			if (primaryKeys.contains(columnName)) {
				Column c = new Column(columnName, rsColumns.getString("TYPE_NAME"), fk.get(columnName));
				c.setTableColumnId(primaryKeys.get(0));
				c.setPrimaryKey(true);
				c.setForgienKey(false);
				columns.add(c);

			} else {
				if (fk.containsKey(columnName)) {
					Column c = new Column(columnName, rsColumns.getString("TYPE_NAME"), fk.get(columnName));
					c.setTableColumnId(primaryKeys.get(0));
					c.setForgienKey(true);
					c.setPrimaryKey(false);

					if (columnName.equals("ID_PREDUZECA")) {
						Column c2 = new Column("NAZIV_PREDUZECA", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_PARTNERA")) {
						Column c2 = new Column("NAZIV_PARTNERA", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_GODINE")) {
						Column c2 = new Column("GODINA", "numeric", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_PDV")) {
						Column c2 = new Column("NAZIV_PDV_A", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_JEDINICE_MERE")) {
						Column c2 = new Column("NAZIV_JEDINICE_MERE", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_GRUPE")) {
						Column c2 = new Column("NAZIV_GRUPE", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}
					if (columnName.equals("ID_PROIZVODA")) {
						Column c2 = new Column("NAZIV_PROIZVODA", "varchar", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}
					if (columnName.equals("ID_CENOVNIKA")) {
						Column c2 = new Column("DATUM_VAZENA", "datetime", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}

					if (columnName.equals("ID_FAKTURE")) {
						Column c2 = new Column("BROJ_FAKTURE", "int", "");
						c2.setLookUp(true);
						c.setLookupField(c2);

					}
					columns.add(c);

				} else {
					Column c = new Column(columnName, rsColumns.getString("TYPE_NAME"), "");
					c.setTableColumnId(primaryKeys.get(0));
					c.setForgienKey(false);
					c.setPrimaryKey(false);
					columns.add(c);
				}
			}
		}
		
		return columns;
	}

	/**
	 * Formira osnovni upit za selekciju svega iz tabele
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String generateBasicSelectForTable(String tableName) throws SQLException {

		/*
		 * List<Column> columns = getColumnsForTable(tableName);
		 * 
		 * String sqlSelectQuery = "SELECT ";
		 * 
		 * for (Column c : columns) { sqlSelectQuery += c.getName() + ", "; }
		 * 
		 * sqlSelectQuery = sqlSelectQuery.substring(0,
		 * sqlSelectQuery.length()-2); sqlSelectQuery += " FROM " +tableName;
		 * return sqlSelectQuery;
		 */

		DatabaseMetaData meta = db.getConnection().getMetaData();
		ResultSet keys = meta.getImportedKeys(null, null, tableName);
		ResultSet keys1 = meta.getImportedKeys(null, null, tableName);

		List<Column> columns = getColumnsForTable(tableName);
		String sqlSelectQuery1 = "SELECT ";

		for (Column c : columns) {
			sqlSelectQuery1 += c.getName() + ", ";
			if (c.getLookupField() != null) {
				sqlSelectQuery1 += c.getLookupField().getName() + ", ";
			}
		}

		while (keys1.next()) {

			if (sqlSelectQuery1.contains(keys1.getString("FKCOLUMN_NAME"))) {
				sqlSelectQuery1 = sqlSelectQuery1.replace(keys1.getString("FKCOLUMN_NAME"),
						tableName + "." + keys1.getString("FKCOLUMN_NAME"));
			}
		}

		sqlSelectQuery1 = sqlSelectQuery1.substring(0, sqlSelectQuery1.length() - 2);
		String sqlSelectQuery2 = " FROM " + tableName;

		while (keys.next()) {
			sqlSelectQuery2 += " JOIN " + keys.getString("PKTABLE_NAME");
			sqlSelectQuery2 += " ON ";
			sqlSelectQuery2 += tableName + "." + keys.getString("FKCOLUMN_NAME") + " = ";
			sqlSelectQuery2 += keys.getString("PKTABLE_NAME") + "." + keys.getString("FKCOLUMN_NAME");
		}

		System.out.println("UPIT: ");
		System.out.println(sqlSelectQuery1 + sqlSelectQuery2);
		return sqlSelectQuery1 + sqlSelectQuery2;

	}

	public void insertIntoTable(String tableName, String data) throws SQLException, ParseException {

		String sql = generateInsertSql(tableName, data);

		JSONArray jsonData = new JSONArray(data);

		PreparedStatement stmt = db.getConnection().prepareStatement(sql);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int k = 1;
		for (int i = 0; i < jsonData.length(); i++) {
			
			JSONObject o = jsonData.getJSONObject(i);
			String columnValue = "";
			try {
				columnValue = o.getString("columnValue");
			} catch (JSONException e) {
				Double value  = o.getDouble("columnValue");
				columnValue = value.toString();
			}
			String type = o.getString("columnType");
			
			Boolean primaryKey = o.getBoolean("primaryKey");
			Boolean lookUp = o.getBoolean("lookUp");
			Boolean forgien = o.getBoolean("forgienKey");
			System.out.println(o.getString("columnName"));
			if (!primaryKey && !lookUp) {
				if (type.equals("datetime")) {
					columnValue = columnValue.substring(0, columnValue.length() - 14);

					java.util.Date dateParsed = (java.util.Date) formatter.parse(columnValue);
					Date date = new Date(dateParsed.getTime());
					System.out.println(date + " DATUM");
					stmt.setDate(k, date);
				} else if (type.equals("numeric") || type.equals("decimal")) {
					BigDecimal value = new BigDecimal(columnValue);
					System.out.println(value);
					
					stmt.setBigDecimal(k, value);
				} else if (type.equals("float")) {
					Float f = Float.parseFloat(columnValue);
					System.out.println(f);
					stmt.setFloat(k, f);
					
				} else if (type.equals("int")) {
					Integer value = Integer.parseInt(columnValue);
					System.out.println(value);
					stmt.setInt(k, value);
				} else if (type.equals("varchar") || type.equals("char")) {
					stmt.setString(k, columnValue);
				} else if (type.equals("bit")) {
					if (columnValue.equals("1")) {
						stmt.setBoolean(k, true);
					} else {
						stmt.setBoolean(k, false);
					}

				}
				k++;
			}
		}

		stmt.execute();

		db.getConnection().commit();

		stmt.close();

		tableName = tableName.toUpperCase().replace(" ", "_");
		if (tableName.equals("STAVKE_FAKTURE")) {
			Integer IdFakture = null;
			Integer StavkaFakture = null;
			for (int i = 0; i < jsonData.length(); i++) {
				JSONObject o = jsonData.getJSONObject(i);
				Boolean forgien = o.getBoolean("forgienKey");
				
				if (forgien) {
					if (o.get("columnName").equals("ID_FAKTURE")) {
						IdFakture = o.getInt("columnValue");
						
						//break;
					}
				}
				if(o.get("columnName").equals("STAVKA_FAKTURE")){
						StavkaFakture = o.getInt("columnValue");
				}
				
				if(IdFakture != null && StavkaFakture != null){
					uradiFakturisanje(IdFakture, StavkaFakture);
				}
				
			}
		}

	}

	public String updateElementInTable(String tableName, String data, String idValue)
			throws SQLException, ParseException {
		JSONObject obj = new JSONObject(data);
		JSONArray selectedRow = obj.getJSONArray("tableRow");
		JSONArray changedRow = obj.getJSONArray("changedRow");
		String selectedR = selectedRow.toString();
		String changed = changedRow.toString();

		String errorMsg = checkRow(tableName, selectedR, idValue);
		if (errorMsg != "") {
			return errorMsg;
		} else {
			String sql = generateUpdateSql(tableName, changed, idValue);

			JSONArray jsonData = new JSONArray(changed);

			PreparedStatement stmt = db.getConnection().prepareStatement(sql);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			int k = 1;
			for (int i = 0; i < jsonData.length(); i++) {
				JSONObject o = jsonData.getJSONObject(i);
				String columnValue = o.getString("columnValue");
				String columnName = o.getString("columnName");
				Boolean primaryKey = o.getBoolean("primaryKey");
				Boolean forgienKey = o.getBoolean("forgienKey");
				String type = o.getString("columnType");

				if (!primaryKey && !forgienKey) {

					if (type.equals("datetime")) {
						columnValue = columnValue.substring(0, columnValue.length() - 14);

						java.util.Date dateParsed = (java.util.Date) formatter.parse(columnValue);
						Date date = new Date(dateParsed.getTime());
						stmt.setDate(k, date);
					} else if (type.equals("numeric") || type.equals("decimal")) {
						BigDecimal value = new BigDecimal(columnValue);
						stmt.setBigDecimal(k, value);
					} else if (type.equals("float")) {
						Float f = Float.parseFloat(columnValue);
						stmt.setFloat(k, f);
						
					} else if (type.equals("int")) {
						Integer value = Integer.parseInt(columnValue);
						stmt.setInt(k, value);
					} else if (type.equals("varchar") || type.equals("char")) {
						stmt.setString(k, columnValue);
					} else if (type.equals("bit")) {
						if (columnValue.equals("1")) {
							stmt.setBoolean(k, true);
						} else {
							stmt.setBoolean(k, false);
						}

					}
					k++;

				}

			}
			for (int i = 0; i < jsonData.length(); i++) {
				JSONObject o = jsonData.getJSONObject(i);
				String columnValue = o.getString("columnValue");
				Boolean primaryKey = o.getBoolean("primaryKey");
				String type = o.getString("columnType");

				if (primaryKey) {
					if (type.equals("numeric") || type.equals("numeric() identity") || type.equals("decimal")
							|| type.equals("float")) {
						BigDecimal value = new BigDecimal(columnValue);
						stmt.setBigDecimal(k, value);
					} else if (type.equals("int")) {
						Integer value = Integer.parseInt(columnValue);
						stmt.setInt(k, value);
					} else if (type.equals("varchar") || type.equals("char")) {
						stmt.setString(k, columnValue);
					}
				}

			}

			stmt.executeUpdate();

			db.getConnection().commit();

			stmt.close();
			return "";
		}
	}

	public List<List<String>> searchTable(String tableName, String json) throws SQLException {
		String sql = generateSearchSql(tableName, json);

		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		List<Column> columns = getColumnsForTable(tableName);
		List<List<String>> resultData = new ArrayList<List<String>>();

		JSONArray jsonData = new JSONArray(json);
		while (rset.next()) {
			List<String> row = new ArrayList<String>();
			for (int i = 0; i < jsonData.length(); i++) {
				row.add(rset.getString(jsonData.getJSONObject(i).getString("columnName")));
			}
			resultData.add(row);
		}

		return resultData;
	}

	public String deleteFromTable(String tableName, String data, String elementId) throws SQLException {

		String errMsg = checkRow(tableName, data, elementId);
		checkRow(tableName, data, elementId);

		if (errMsg.equals("")) {
			String sql = generateDeleteSql(tableName);

			PreparedStatement stmt = db.getConnection().prepareStatement(sql);

			Integer id = Integer.parseInt(elementId);

			try {
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
				db.getConnection().commit();
			} catch (SQLException e) {
				System.out.println("Ne moze");
				return "Element u referenci sa drugim elementom";
			}

			return errMsg;
		} else {
			return errMsg;
		}

	}

	private String generateInsertSql(String tableName, String data) {
		JSONArray jsonData = new JSONArray(data);

		String sql = "INSERT INTO " + tableName.toUpperCase().replace(" ", "_");
		sql += " (";
		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = jsonData.getJSONObject(i);
			Boolean primaryKey = o.getBoolean("primaryKey");
			Boolean lookUp = o.getBoolean("lookUp");
			if (!primaryKey && !lookUp) {
				String columnName = o.getString("columnName");
				sql += columnName + ", ";
			}
		}

		sql = sql.substring(0, sql.length() - 2);
		sql += ") VALUES(";

		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = jsonData.getJSONObject(i);
			Boolean primaryKey = o.getBoolean("primaryKey");
			Boolean lookUp = o.getBoolean("lookUp");
			if (!primaryKey && !lookUp) {
				sql += "?,";
			}
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		return sql;
	}

	public String generateUpdateSql(String tableName, String data, String idValue) throws SQLException {
		JSONArray jsonData = new JSONArray(data);

		String sql = "UPDATE " + tableName.toUpperCase().replace(" ", "_") + " SET ";
		List<String> keys = db.getTablePrimaryKeys(tableName);

		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = jsonData.getJSONObject(i);
			String columnName = o.getString("columnName").toUpperCase().replace(" ", "_");
			boolean primaryKey = o.getBoolean("primaryKey");
			boolean forgienKey = o.getBoolean("forgienKey");
			if (!primaryKey && !forgienKey) {
				sql += columnName + " = ?,";
			}

		}

		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE " + keys.get(0) + " = ?";
		return sql;
	}

	public String generateSearchSql(String tableName, String data) throws SQLException {
		JSONArray jsonData = new JSONArray(data);

		String sql = generateBasicSelectForTable(tableName);
		/*
		 * String primaryKey = ""; for (int i = 0; i < jsonData.length(); i++) {
		 * if (!jsonData.get(i).toString().equals("null")) { JSONObject o =
		 * jsonData.getJSONObject(i); String columnName =
		 * o.getString("columnName"); Boolean primary =
		 * o.getBoolean("primaryKey"); Boolean lookUp = o.getBoolean("lookUp");
		 * if (!lookUp) { columnName = columnName.toUpperCase().replace(" ",
		 * "_"); sql += columnName + ", "; } }
		 * 
		 * }
		 * 
		 * // Nalazenje primarnog kljuca for (int i = 0; i < jsonData.length();
		 * i++) { JSONObject o = jsonData.getJSONObject(i); String columnName =
		 * o.getString("columnName"); Boolean primary =
		 * o.getBoolean("primaryKey");
		 * 
		 * if (primary) { primaryKey = columnName; } } sql = sql.substring(0,
		 * sql.length() - 2); sql += " FROM " + tableName.toUpperCase().replace(
		 * " ", "_") + " WHERE ";
		 */
		sql += " WHERE ";
		String primaryKey = "";
		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = jsonData.getJSONObject(i);
			String columnName = o.getString("columnName");
			Boolean primary = o.getBoolean("primaryKey");
			Boolean forgien = o.getBoolean("forgienKey");
			String columnValue = o.getString("columnValue");
			String columnType = o.getString("columnType");
			Boolean lookUp = o.getBoolean("lookUp");

			if (!primary && !forgien && !lookUp) {
				columnName = columnName.toUpperCase().replace(" ", "_");
				sql += columnName + " LIKE '%" + columnValue + "%' AND ";
			}
			if (primary) {
				primaryKey = columnName;
			}

		}

		sql = sql.substring(0, sql.length() - 4);
		sql += " ORDER BY " + primaryKey;

		System.out.println("SEARCH QUERY");
		System.out.println(sql);

		return sql;
	}

	public String generateDeleteSql(String tableName) throws SQLException {
		tableName = tableName.toUpperCase().replace(" ", "_");
		String sql = "DELETE FROM " + tableName + " WHERE ";

		List<Column> columns = getColumnsForTable(tableName);

		for (Column c : columns) {

			String columnName = c.getName();
			Boolean primaryKey = c.isPrimaryKey();
			if (primaryKey) {
				sql += columnName.toUpperCase().replace(" ", "_") + " = ?";
			}
		}
		System.out.println(sql);
		return sql;
	}

	public ArrayList<String> getLinkedTable(String tableName) throws SQLException {
		ResultSet rsColumns = null;
		DatabaseMetaData meta = db.getConnection().getMetaData();
		rsColumns = meta.getColumns(null, null, tableName, null);

		ResultSet keys = meta.getImportedKeys(null, null, tableName);
		List<Column> columns = new ArrayList<Column>();

		Map<String, String> fk = new HashMap<String, String>();
		ArrayList<String> listLinkedTable = new ArrayList<String>();

		while (keys.next()) {
			String fkColumnName = keys.getString("FKCOLUMN_NAME");
			String fkTableName = keys.getString("PKTABLE_NAME");

			String tableName2 = fkTableName.replace("_", " ").toLowerCase();
			String capital = tableName2.substring(0, 1).toUpperCase();
			String capitalized = capital + tableName2.substring(1);
			listLinkedTable.add(capitalized);
		}

		return listLinkedTable;
	}

	public List<String> getPrimaryKeyName(String tableName) throws SQLException {
		System.out.println("U funkciji 222");
		ResultSet rsColumns = null;
		DatabaseMetaData meta = db.getConnection().getMetaData();
		rsColumns = meta.getColumns(null, null, tableName, null);

		ResultSet keys = meta.getExportedKeys(null, null, tableName);
		// ResultSet key = meta.getPrimaryKeys(null, null, tableName);

		List<String> pkColumnName = new ArrayList<String>();
		while (keys.next()) {
			pkColumnName.add(keys.getString("PKCOLUMN_NAME"));
			System.out.println(pkColumnName + " PRIMARY");
			// break;
		}

		return pkColumnName;
	}

	public String selectElementFromTable(String tableName, String data) throws SQLException {
		String selectSql = "SELECT * FROM " + tableName.toUpperCase().replace(" ", "_");

		selectSql += " WHERE ";

		JSONArray jsonData = new JSONArray(data);

		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = (JSONObject) jsonData.getJSONObject(i);
			Boolean primary = o.getBoolean("primaryKey");
			if (primary) {
				String columnName = o.getString("columnName");
				selectSql += columnName + " =?";
			}
		}

		return selectSql;
	}

	public String checkRow(String tableName, String data, String elementId) throws SQLException {
		String sql = selectElementFromTable(tableName, data);

		db.getConnection().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

		PreparedStatement stmt = db.getConnection().prepareStatement(sql);

		Integer primaryKeyVal = Integer.parseInt(elementId);
		stmt.setInt(1, primaryKeyVal);

		ResultSet rset = stmt.executeQuery();
		ArrayList<String> columns = new ArrayList<String>();

		JSONArray jsonData = new JSONArray(data);
		for (int i = 0; i < jsonData.length(); i++) {
			JSONObject o = jsonData.getJSONObject(i);
			String columnName = o.getString("columnName");
			Boolean lookUp = o.getBoolean("lookUp");
			if (!lookUp) {
				columns.add(columnName);
			}
		}
		HashMap<String, String> row = new HashMap<String, String>();
		Boolean exist = false;
		String errorMsg = "";
		while (rset.next()) {
			for (String s : columns) {
				String value = rset.getString(s);
				row.put(s, value);
			}
			exist = true;
		}
		if (!exist) {
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else {
			Boolean changed = false;
			for (int i = 0; i < jsonData.length(); i++) {
				JSONObject o = jsonData.getJSONObject(i);
				String columnName = o.getString("columnName");
				String columnValue = o.getString("columnValue");

				Boolean lookUp = o.getBoolean("lookUp");
				if (!lookUp) {
					if (!row.get(columnName).equals(columnValue)) {
						changed = true;
						errorMsg = ERROR_RECORD_WAS_CHANGED;
						break;
					}

				}
			}
		}
		rset.close();
		db.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

		return errorMsg;

	}

	public void createXML(int idFakture, int idPartnera, String nazivPartnera, int idPreduzeca, String nazivPreduzeca,
			int idGodine, int godina, String vrstaFakture, int brojFakture, Date datumFakture, Date datumValute,
			Float ukupanRabat, Float ukupanIznosBezPdva, Float ukupanPdv, Float ukupnoZaPlacane)
			throws JAXBException, IOException, SQLException {

		Faktura faktura = new Faktura();

		faktura.setIdFakture(idFakture);
		faktura.setBrojFakture(brojFakture);
		faktura.setDatum(datumFakture);
		faktura.setDatumValute(datumValute);
		faktura.setGodina(godina);
		faktura.setIdGodine(idGodine);
		faktura.setIdPartnera(idPartnera);
		faktura.setIdPreduzeca(idPreduzeca);
		faktura.setNazivPartnera(nazivPreduzeca);
		faktura.setUkupanIznosBezPdv(ukupanIznosBezPdva);
		faktura.setUkupanPdv(ukupanPdv);
		faktura.setUkupanRabat(ukupanRabat);
		faktura.setUkupnoZaPlacanje(ukupnoZaPlacane);
		faktura.setVrstaFakture(vrstaFakture);
		faktura.setNazivPartnera(nazivPartnera);

		// marshall(faktura);

		addStavkeNaFakturu(faktura);

	}

	private void addStavkeNaFakturu(Faktura faktura) throws SQLException, JAXBException, IOException {

		String sqlUpitZaStavkeFaktura = "SELECT ID_STAVKE_FAKTURE, STAVKE_FAKTURE.ID_PROIZVODA, NAZIV_PROIZVODA, STAVKE_FAKTURE.ID_FAKTURE, STAVKE_FAKTURE.ID_FAKTURE, CENA_BEZ_PDV_A, SKRACENICA_JEDINICE_MERE, UKUPAN_IZNOS_SA_PDV_OM, KOLICINA, JEDINICA_MERE, RABAT, STOPA_PDV_A, OSNOVICA, IZNOS_PDV_A, STAVKA_FAKTURE FROM STAVKE_FAKTURE JOIN FAKTURA ON STAVKE_FAKTURE.ID_FAKTURE = FAKTURA.ID_FAKTURE JOIN PROIZVOD ON STAVKE_FAKTURE.ID_PROIZVODA = PROIZVOD.ID_PROIZVODA WHERE STAVKE_FAKTURE.ID_FAKTURE = "
				+ faktura.getIdFakture();

		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sqlUpitZaStavkeFaktura);

		System.out.println(" PRE");
		while (rset.next()) {

			int IdStavkeFakture = rset.getInt(1);
			int IdProizvoda = rset.getInt(2);
			String nazivProizvoda = rset.getString(3);
			int IdFakture = rset.getInt(4);
			Float cenaBezPdva = rset.getFloat(6);
			String skracenicaJediniceMere = rset.getString(7);
			Float ukupanIznosSaPdvom = rset.getFloat(8);
			Float kolicina = rset.getFloat(9);
			int jedinicaMere = rset.getInt(10);
			Float rabat = rset.getFloat(11);
			Float stopaPdva = rset.getFloat(12);
			Float osnovica = rset.getFloat(13);
			Float iznosPdva = rset.getFloat(14);
			int stavkaFakture = rset.getInt(15);

			Stavka stavka = new Stavka();
			stavka.setCenaBezPdva(cenaBezPdva);
			stavka.setIdFakture(IdFakture);
			stavka.setIdProizvoda(IdProizvoda);
			stavka.setIdStavkeFakture(IdStavkeFakture);
			stavka.setIznosPdva(iznosPdva);
			stavka.setJedinicaMere(jedinicaMere);
			stavka.setKolicina(kolicina);
			stavka.setNazivProizvoda(nazivProizvoda);
			stavka.setOsnovica(osnovica);
			stavka.setRabat(rabat);
			stavka.setSkracenicaJediniceMere(skracenicaJediniceMere);
			stavka.setStavkaFakture(stavkaFakture);
			stavka.setStopaPdva(stopaPdva);
			stavka.setUkupanIznosSaPdvom(ukupanIznosSaPdvom);

			faktura.getStavka().add(stavka);

		}

		marshall(faktura);

	}

	private void marshall(Faktura faktura) throws JAXBException, IOException {

		File fakturaFile = new File("src/data/Faktura_" + faktura.getIdFakture() + ".xml");

		if (!fakturaFile.exists()) {
			try {
				fakturaFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance(Faktura.class);
		// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
		Marshaller marshaller = context.createMarshaller();

		// Podešavanje marshaller-a
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		FileOutputStream fos = new FileOutputStream(fakturaFile);

		// Umesto System.out-a, može se koristiti FileOutputStream
		marshaller.marshal(faktura, fos);

		fos.flush();
		fos.close();

	}

	public void uradiFakturisanje(Integer IdFakture, Integer StavkaFakture) throws SQLException {

	
		//NALAZIM UBACENU STAVKU FAKTURE
		String query = "SELECT ID_STAVKE_FAKTURE, STAVKE_FAKTURE.ID_PROIZVODA, NAZIV_PROIZVODA, STAVKE_FAKTURE.ID_FAKTURE, STAVKE_FAKTURE.ID_FAKTURE, CENA_BEZ_PDV_A, SKRACENICA_JEDINICE_MERE, UKUPAN_IZNOS_SA_PDV_OM, KOLICINA, JEDINICA_MERE, RABAT, STOPA_PDV_A, OSNOVICA, IZNOS_PDV_A, STAVKA_FAKTURE FROM STAVKE_FAKTURE JOIN FAKTURA ON STAVKE_FAKTURE.ID_FAKTURE = FAKTURA.ID_FAKTURE JOIN PROIZVOD ON STAVKE_FAKTURE.ID_PROIZVODA = PROIZVOD.ID_PROIZVODA WHERE STAVKE_FAKTURE.STAVKA_FAKTURE = "
				+ StavkaFakture;

		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(query);

		Float cenaBezPdva = 0.0f;
		Float ukupanIznosSaPdvom = 0.0f;
		Float rabat = 0.0f;
		Float iznosPdva = 0.0f;
		BigDecimal kolicina = new BigDecimal(0);
		
		Float ukupnaCenaBezPdva = 0.0f;
		Float ukupanUkupanIznosSaPdvom = 0.0f;
		Float ukupanRabat = 0.0f;
		Float ukupanIznosPdva = 0.0f;
		
		while (rset.next()) {

			cenaBezPdva = rset.getFloat(6);
			ukupanIznosSaPdvom = rset.getFloat(8);
			rabat = rset.getFloat(11);
			iznosPdva = rset.getFloat(14);
			kolicina = rset.getBigDecimal(9);
			
		/*	ukupnaCenaBezPdva += kolicina.floatValue()*cenaBezPdva;
			ukupanUkupanIznosSaPdvom += ukupanIznosSaPdvom;
			ukupanRabat += rabat;
			ukupanIznosPdva += iznosPdva;*/
		}
		
		cenaBezPdva = (cenaBezPdva * kolicina.floatValue()) - rabat;
		stmt.close();

		
		//NALAZIM FAKTURU
		String query2 = "SELECT ID_FAKTURE, FAKTURA.ID_PARTNERA, NAZIV_PARTNERA, FAKTURA.ID_PREDUZECA, NAZIV_PREDUZECA, FAKTURA.ID_GODINE, GODINA, VRSTA_FAKTURE, BROJ_FAKTURE, DATUM_FAKTURE, DATUM_VALUTE, UKUPAN_RABAT, UKUPAN_IZNOS_BEZ_PDV_A, UKUPAN_PDV, UKUPNO_ZA_PLACANJE FROM FAKTURA JOIN POSLOVNA_GODINA ON FAKTURA.ID_GODINE = POSLOVNA_GODINA.ID_GODINE JOIN POSLOVNI_PARTNER ON FAKTURA.ID_PARTNERA = POSLOVNI_PARTNER.ID_PARTNERA JOIN PREDUZECE ON FAKTURA.ID_PREDUZECA = PREDUZECE.ID_PREDUZECA WHERE FAKTURA.ID_FAKTURE = "
				+ IdFakture;
		Statement stmt2 = db.getConnection().createStatement();
		ResultSet rset2 = stmt2.executeQuery(query2);
		while(rset2.next()){
			ukupanRabat = rset2.getFloat(12);
			ukupanUkupanIznosSaPdvom = rset2.getFloat(15);
			ukupnaCenaBezPdva = rset2.getFloat(13);
			ukupanIznosPdva = rset2.getFloat(14);
		}
		
		ukupanRabat += rabat;
		ukupanUkupanIznosSaPdvom += ukupanIznosSaPdvom;
		ukupnaCenaBezPdva += (cenaBezPdva * kolicina.floatValue() - rabat);
		ukupanIznosPdva += iznosPdva;
		stmt2.close();
		
		
		//RADIM UPDATE FAKTURE
		String query3 = "UPDATE FAKTURA SET UKUPAN_RABAT = " + ukupanRabat + ", UKUPAN_IZNOS_BEZ_PDV_A = "
				+ ukupnaCenaBezPdva + ", UKUPAN_PDV = " + ukupanIznosPdva + ", UKUPNO_ZA_PLACANJE = "
				+ ukupanUkupanIznosSaPdvom + " WHERE ID_FAKTURE = " + IdFakture;
		Statement stmt3 = db.getConnection().createStatement();
		stmt3.executeUpdate(query3);
		
		db.getConnection().commit();
		stmt3.close();

		System.out.println(" SVE OK");
	}

	public ArrayList<Object> getJosOpisnihPolja(String nazivPolja, String vrednostPolja) throws SQLException {

		String upit = "SELECT JEDINICA_MERE.SKRACENICA, JEDINICA_MERE.ID_JEDINICE_MERE FROM PROIZVOD JOIN JEDINICA_MERE ON JEDINICA_MERE.ID_JEDINICE_MERE = PROIZVOD.ID_JEDINICE_MERE WHERE PROIZVOD." + nazivPolja + " = " + vrednostPolja;
		
		
		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(upit);

		System.out.println(" PRE UPITA");
		ArrayList<Object> skracenica = new ArrayList<Object>();
		while (rset.next()) {
			System.out.println(rset.getString(1));
			System.out.println(rset.getInt(2));
			skracenica.add(rset.getString(1));
			skracenica.add(rset.getInt(2));
			
		}
		System.out.println(" POSLE");
		
		return skracenica;
	}

}
