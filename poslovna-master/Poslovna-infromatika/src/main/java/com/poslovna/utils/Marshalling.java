package com.poslovna.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import com.poslovna.xml.Faktura;
import com.poslovna.xml.Faktura.Stavka;
import com.poslovna.xml.ObjectFactory;

@Component
public class Marshalling {

	public void test() throws Exception {
		try {

			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");

			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("com.poslovna.xml");

			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni
			// model
		/*	Unmarshaller unmarshaller = context.createUnmarshaller();

			Akt akt = (Akt) unmarshaller.unmarshal(new File("src/data/instance1.xml"));

			akt.setAmandman(createAmandman(new BigInteger("350"), "Novi Sad", "Stefan Halapa",
					"Obrazlozenje je da je hitno", null, 150, "Resenje broj jedan"));
			// Marshaller je objekat zadužen za konverziju iz objektnog u XML
			// model
			Marshaller marshaller = context.createMarshaller();

			// Podešavanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Umesto System.out-a, može se koristiti FileOutputStream
			marshaller.marshal(akt, System.out);*/

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	

	private Faktura createFaktura(Date datum, Integer idFakture, Integer idPartnera, String nazivPartnera, Integer idPreduzeca,
			Integer godina, Integer idGodina, String vrstaFakture, Integer brojFakture, Date datumValute, Float ukupanRabat, Float ukupanIznosBezPdv,
			Float ukupanPdv, Float ukupnoZaPlacanje) {
		
		ObjectFactory factory = new ObjectFactory();
		Faktura faktura = factory.createFaktura();
		
		faktura.setBrojFakture(brojFakture);
		faktura.setDatum(datum);
		faktura.setDatumValute(datumValute);
		faktura.setGodina(godina);
		faktura.setIdFakture(idFakture);
		faktura.setIdGodine(idGodina);
		faktura.setIdPartnera(idPartnera);
		faktura.setIdPreduzeca(idPreduzeca);
		faktura.setNazivPartnera(nazivPartnera);
		faktura.setUkupanIznosBezPdv(ukupanIznosBezPdv);
		faktura.setUkupanPdv(ukupanPdv);
		faktura.setUkupanRabat(ukupanRabat);
		faktura.setUkupnoZaPlacanje(ukupnoZaPlacanje);
		faktura.setVrstaFakture(vrstaFakture);
		
		return faktura;

	}
	
	private Stavka createStavka(Integer idProizvoda, Integer jedinicaMere, String nazivProzivoda, Integer idStavkeFakture, Integer idFakture, Float cenaBezPdva, String skracenicaJediniceMere,
			Float ukupanIznosSaPdvom, Float kolicina, Float rabat, Float stopaPdva, Float osnovica, Float iznosPdva, Integer stavkaFakture) {

		ObjectFactory factory = new ObjectFactory();
		Stavka stavka = factory.createFakturaStavka();
		
		stavka.setCenaBezPdva(cenaBezPdva);
		stavka.setIdFakture(idFakture);
		stavka.setIdProizvoda(idProizvoda);
		stavka.setIdStavkeFakture(idStavkeFakture);
		stavka.setIznosPdva(iznosPdva);
		stavka.setJedinicaMere(jedinicaMere);
		stavka.setKolicina(kolicina);
		stavka.setNazivProizvoda(nazivProzivoda);
		stavka.setOsnovica(osnovica);
		stavka.setRabat(rabat);
		stavka.setSkracenicaJediniceMere(skracenicaJediniceMere);
		stavka.setStavkaFakture(stavkaFakture);
		stavka.setStopaPdva(stopaPdva);
		stavka.setUkupanIznosSaPdvom(ukupanIznosSaPdvom);
		
		
		return stavka;
	}

	
}
