package com.poslovna.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.db.Database;
import com.poslovna.models.Column;
import com.poslovna.services.TablesService;
import com.poslovna.utils.Marshalling;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

@RestController
@RequestMapping("/api/table")
public class TablesController {

	@Autowired
	private TablesService tableService;
	
	@Autowired
	private Database db;
	
	@Autowired
	Marshalling marshaller;
	
	@RequestMapping(value="/{tableName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<List<String>> getTableData(@PathVariable("tableName") String tableName ) throws SQLException {
		
		if(tableName.contains(" ")){
			tableName = tableName.toUpperCase().replace(" ", "_");
		}
		else{
			tableName = tableName.toUpperCase();
		}
		
		
		System.out.println(tableName + " TABLE NAME");
		List<List<String>> resultData =  tableService.getDataForTable(tableName);
		
		return resultData;
	}
	
	@RequestMapping(value="/columns/{tableName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Column> getTableColumnsName(@PathVariable("tableName") String tableName) throws SQLException {
		
		tableName = tableName.toUpperCase().replace(" ", "_");
		List<Column> columnsName = tableService.getColumnsForTable(tableName);
		System.out.println("nadjeno kolona " + columnsName.size());
		return columnsName;
	}
	
	@RequestMapping(value="/{tableName}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void insertIntoTable(@PathVariable("tableName") String tableName, @RequestBody String jsonData) throws SQLException, ParseException {
		
		System.out.println("----------------");
		System.out.println(jsonData);
		System.out.println("----------------");

		tableService.insertIntoTable(tableName, jsonData);
	}
	
	@RequestMapping(value="/linkedTable/{tableName}", method=RequestMethod.GET)
	public @ResponseBody ArrayList<String> getLinkedTable(@PathVariable("tableName") String tableName) throws SQLException, ParseException {
		
		return tableService.getLinkedTable(tableName);
	}
	
	@RequestMapping(value="/{tableName}/element/{id}", method=RequestMethod.PUT)
	public @ResponseBody String updateElementInTable(@PathVariable("tableName") String tableName, @PathVariable("id") String elementId, @RequestBody String jsonData) throws SQLException, ParseException {

		String errorMsg = tableService.updateElementInTable(tableName, jsonData, elementId);
		
		return errorMsg;
	}
	
	@RequestMapping(value="/{tableName}/search", method=RequestMethod.POST)
	public @ResponseBody List<List<String>> searchTable(@PathVariable("tableName") String tableName, @RequestBody String jsonData) throws SQLException{
		tableName = tableName.toUpperCase().replace(" ", "_");
		
		List<List<String>> result = tableService.searchTable(tableName, jsonData);
		
		return result;
	}
	
	@RequestMapping(value="/{tableName}/element/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteElementFromTable(@PathVariable("tableName") String tableName, @PathVariable("id") String elementId, @RequestBody String json) throws SQLException, ParseException {
		System.out.println(json);
		String err = tableService.deleteFromTable(tableName, json, elementId);
		
		return err;
		
	}
	
	
	@RequestMapping(value="/primaryKeyName/{tableName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getPrimaryKeyName(@PathVariable("tableName") String tableName) throws SQLException, ParseException {
		
		return tableService.getPrimaryKeyName(tableName);
	}
	
	@RequestMapping(value="/jasperReport/{value}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getReport(@PathVariable("value") Integer value) throws JRException, IOException, SQLException {

		String sqlUpitZaFakturuPreduzecePartnera = "SELECT ID_FAKTURE, FAKTURA.ID_PARTNERA, NAZIV_PARTNERA, FAKTURA.ID_PREDUZECA, NAZIV_PREDUZECA, FAKTURA.ID_GODINE, GODINA, VRSTA_FAKTURE, BROJ_FAKTURE, DATUM_FAKTURE, DATUM_VALUTE, UKUPAN_RABAT, UKUPAN_IZNOS_BEZ_PDV_A, UKUPAN_PDV, UKUPNO_ZA_PLACANJE FROM FAKTURA JOIN POSLOVNA_GODINA ON FAKTURA.ID_GODINE = POSLOVNA_GODINA.ID_GODINE JOIN POSLOVNI_PARTNER ON FAKTURA.ID_PARTNERA = POSLOVNI_PARTNER.ID_PARTNERA JOIN PREDUZECE ON FAKTURA.ID_PREDUZECA = PREDUZECE.ID_PREDUZECA WHERE ID_FAKTURE = " + value;
		
		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sqlUpitZaFakturuPreduzecePartnera);
		
		int IdPreduzeca = 0;
		int IdPartnera = 0;
		
		while(rset.next()){
			
			IdPartnera = rset.getInt(2);
			IdPreduzeca = rset.getInt(4);
		}
		
		System.out.println(IdPreduzeca + " PREDUZECE");
		System.out.println(IdPartnera + " PARTNER");
		
		
		
		System.setProperty("java.awt.headless", "false");
		try {
			String path = "E:\\GitLab\\poslovna\\Poslovna-infromatika\\src\\main\\resources\\public\\jasper\\Faktura4.jrxml";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("IdStavkeParam", value);
			map.put("nesto", IdPartnera);
			map.put("svasta", IdPreduzeca);
			JasperReport jp = JasperCompileManager.compileReport(path);
			JasperPrint pr = JasperFillManager.fillReport(jp,map,db.getConnection());
			JasperViewer.viewReport(pr);
		}
		catch(Exception e){
			System.err.println(e);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/jasperReport/{datumFrom}/{datumUntil}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getReport(@PathVariable("datumFrom") String datumFrom, @PathVariable("datumUntil") String datumUntil) throws JRException, IOException, ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMM dd yyyy HH:mm:ss");
		datumFrom = datumFrom.substring(0, datumFrom.length() - 40);
		datumUntil = datumUntil.substring(0, datumUntil.length() - 40);

		java.util.Date dateParsed = (java.util.Date) formatter.parse(datumFrom);
		java.util.Date dateParsed2 = (java.util.Date) formatter.parse(datumUntil);
		Date date1 = new Date(dateParsed.getTime());
		Date date2 = new Date(dateParsed2.getTime());
	
		System.setProperty("java.awt.headless", "false");
		try {
			String path = "E:\\GitLab\\poslovna\\Poslovna-infromatika\\src\\main\\resources\\public\\jasper\\SpisakFakturaDatum.jrxml";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("datumFromParam", date1);
			map.put("datumUntilParam", date2);
			JasperReport jp = JasperCompileManager.compileReport(path);
			JasperPrint pr = JasperFillManager.fillReport(jp,map,db.getConnection());
			JasperViewer.viewReport(pr);
		}
		catch(Exception e){
			System.err.println(e);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	
	}
	
/*	@RequestMapping(value="/uradiFakturisanje/{IdFakture}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> uradiFakturisanje(@PathVariable("IdFakture") String IdFakture) throws JRException, IOException, ParseException {
		
		TablesService.uradiFakturisanje(Integer.parseInt(IdFakture));
		
		return new ResponseEntity<String>(HttpStatus.OK);
	
	}
	*/
	@RequestMapping(value="/izveziXml/{idFakture}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> izveziXml(@PathVariable("idFakture") Integer idFakture) throws JRException, IOException, ParseException, SQLException, JAXBException {
		
		
		String sqlUpitZaFakturu = "SELECT ID_FAKTURE, FAKTURA.ID_PARTNERA, NAZIV_PARTNERA, FAKTURA.ID_PREDUZECA, NAZIV_PREDUZECA, FAKTURA.ID_GODINE, GODINA, VRSTA_FAKTURE, BROJ_FAKTURE, DATUM_FAKTURE, DATUM_VALUTE, UKUPAN_RABAT, UKUPAN_IZNOS_BEZ_PDV_A, UKUPAN_PDV, UKUPNO_ZA_PLACANJE FROM FAKTURA JOIN POSLOVNA_GODINA ON FAKTURA.ID_GODINE = POSLOVNA_GODINA.ID_GODINE JOIN POSLOVNI_PARTNER ON FAKTURA.ID_PARTNERA = POSLOVNI_PARTNER.ID_PARTNERA JOIN PREDUZECE ON FAKTURA.ID_PREDUZECA = PREDUZECE.ID_PREDUZECA WHERE ID_FAKTURE = " + idFakture;
		
		Statement stmt = db.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sqlUpitZaFakturu);

		System.out.println(" PRE");
		while (rset.next()) {
			System.out.println(rset.toString());
			int IdFakture = rset.getInt(1);
			int IdPartnera = rset.getInt(2);
			String nazivPartnera = rset.getString(3);
			int IdPreduzeca = rset.getInt(4);
			String nazivPreduzeca = rset.getString(5);
			int IdGodine = rset.getInt(6);
			int godina = rset.getInt(7);
			String vrstaFakture = rset.getString(8);
			int brojFakture = rset.getInt(9);
			Date datumFakture = rset.getDate(10);
			Date datumValute = rset.getDate(11);
			Float ukupanRabat = rset.getFloat(12);
			Float ukupanIznosBezPdva = rset.getFloat(13);
			Float ukupanPdv = rset.getFloat(14);
			Float ukupnoZaPlacane = rset.getFloat(15);
			
			tableService.createXML(IdFakture, IdPartnera, nazivPartnera, IdPreduzeca, nazivPreduzeca, IdGodine, godina,
					vrstaFakture, brojFakture, datumFakture, datumValute, ukupanRabat, ukupanIznosBezPdva, ukupanPdv, ukupnoZaPlacane);
			
		}
		System.out.println(" POSLE");
		
		return new ResponseEntity<String>(HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/josOpisnihPolja/{nazivPolja}/{vrednostPolja}", method=RequestMethod.GET)
	public @ResponseBody ArrayList<Object> josOpisnihPoljaStavkaFakture(@PathVariable("nazivPolja") String nazivPolja, @PathVariable("vrednostPolja") String vrednostPolja) throws JRException, IOException, ParseException, SQLException, JAXBException {
		
		return tableService.getJosOpisnihPolja(nazivPolja, vrednostPolja);
	
	}
}
