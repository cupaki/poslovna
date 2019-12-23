package com.poslovna.models;

import org.json.JSONObject;

public class Column {

	private String name;
	private String type;
	private String fkTable;
	private String tableColumnId;
	private boolean primaryKey;
	private boolean forgienKey;
	private Column lookupField;
	private boolean lookUp;

	public Column(String name, String type, String fkTable) {
		super();
		this.name = name;
		this.type = type;
		this.fkTable = fkTable;	
		this.primaryKey = false;
		this.forgienKey = false;
		this.lookUp = false;
	}
	public Column(JSONObject o) {
		this.name = o.getString("columnName");
		this.type = o.getString("columnType");
		this.fkTable = o.getString("fkTable");
		this.tableColumnId = "";
		this.primaryKey = o.getBoolean("priamryKey");
		this.forgienKey = o.getBoolean("forgienKey");
		this.lookUp = o.getBoolean("lookUp");
	}

	public Column() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFkTable() {
		return fkTable;
	}

	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}

	public String getTableColumnId() {
		return tableColumnId;
	}

	public void setTableColumnId(String tableColumnId) {
		this.tableColumnId = tableColumnId;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isForgienKey() {
		return forgienKey;
	}

	public void setForgienKey(boolean forgienKey) {
		this.forgienKey = forgienKey;
	}

	public Column getLookupField() {
		return lookupField;
	}

	public void setLookupField(Column lookupField) {
		this.lookupField = lookupField;
	}

	public boolean isLookUp() {
		return lookUp;
	}

	public void setLookUp(boolean lookUp) {
		this.lookUp = lookUp;
	}

	

}
