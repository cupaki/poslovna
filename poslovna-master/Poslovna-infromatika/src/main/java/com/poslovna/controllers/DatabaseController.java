package com.poslovna.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.db.Database;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

	@Autowired
	private Database db;
	
	@RequestMapping(value="/tables", method=RequestMethod.GET)
	public @ResponseBody List<String> getTables() throws SQLException {
		List<String> tables = db.getDatabseTables();
		List<String> realTables = new ArrayList<String>();
		
		for (int i = 0; i<tables.size()-2; i++) {
			String tableName = tables.get(i).replace("_", " " ).toLowerCase();
			String capital = tableName.substring(0, 1).toUpperCase();
			String capitalized = capital + tableName.substring(1);
			realTables.add(capitalized);
		}
		return realTables;
	}
}
