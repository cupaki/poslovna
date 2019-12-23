//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.29 at 02:09:03 PM CEST 
//


package com.poslovna.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="stavka">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="id_proizvoda" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="jedinica_mere" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="naziv_proizvoda" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="id_stavke_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="id_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="cena_bez_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="skracenica_jedinice_mere" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ukupan_iznos_sa_pdvom" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="kolicina" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="rabat" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="stopa_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="osnovica" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="iznos_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="stavka_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="datum" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="id_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="id_partnera" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="naziv_partnera" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="naziv_preduzeca" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id_preduzeca" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="id_godine" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="godina" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="vrsta_fakture" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="broj_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="ukupan_rabat" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="ukupan_iznos_bez_pdv" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="ukupan_pdv" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="ukupno_za_placanje" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stavka"
})
@XmlRootElement(name = "Faktura")
public class Faktura {

    protected List<Faktura.Stavka> stavka;
    @XmlAttribute
    @XmlSchemaType(name = "date")
    protected Date datum;
    @XmlAttribute(name = "id_fakture")
    protected Integer idFakture;
    @XmlAttribute(name = "id_partnera")
    protected Integer idPartnera;
    @XmlAttribute(name = "naziv_partnera")
    protected String nazivPartnera;
    @XmlAttribute(name = "naziv_preduzeca")
    protected String nazivPreduzeca;
    @XmlAttribute(name = "id_preduzeca")
    protected Integer idPreduzeca;
    @XmlAttribute(name = "id_godine")
    protected Integer idGodine;
    @XmlAttribute
    protected Integer godina;
    @XmlAttribute(name = "vrsta_fakture")
    protected String vrstaFakture;
    @XmlAttribute(name = "broj_fakture")
    protected Integer brojFakture;
    @XmlAttribute(name = "datum_valute")
    @XmlSchemaType(name = "date")
    protected Date datumValute;
    @XmlAttribute(name = "ukupan_rabat")
    protected Float ukupanRabat;
    @XmlAttribute(name = "ukupan_iznos_bez_pdv")
    protected Float ukupanIznosBezPdv;
    @XmlAttribute(name = "ukupan_pdv")
    protected Float ukupanPdv;
    @XmlAttribute(name = "ukupno_za_placanje")
    protected Float ukupnoZaPlacanje;

    /**
     * Gets the value of the stavka property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavka property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavka().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Faktura.Stavka }
     * 
     * 
     */
    public List<Faktura.Stavka> getStavka() {
        if (stavka == null) {
            stavka = new ArrayList<Faktura.Stavka>();
        }
        return this.stavka;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param datum2
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(Date datum2) {
        this.datum = datum2;
    }

    /**
     * Gets the value of the idFakture property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdFakture() {
        return idFakture;
    }

    /**
     * Sets the value of the idFakture property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdFakture(Integer value) {
        this.idFakture = value;
    }

    /**
     * Gets the value of the idPartnera property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPartnera() {
        return idPartnera;
    }

    /**
     * Sets the value of the idPartnera property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPartnera(Integer value) {
        this.idPartnera = value;
    }

    /**
     * Gets the value of the nazivPartnera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivPartnera() {
        return nazivPartnera;
    }

    /**
     * Sets the value of the nazivPartnera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivPartnera(String value) {
        this.nazivPartnera = value;
    }

    /**
     * Gets the value of the nazivPreduzeca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivPreduzeca() {
        return nazivPreduzeca;
    }

    /**
     * Sets the value of the nazivPreduzeca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivPreduzeca(String value) {
        this.nazivPreduzeca = value;
    }

    /**
     * Gets the value of the idPreduzeca property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPreduzeca() {
        return idPreduzeca;
    }

    /**
     * Sets the value of the idPreduzeca property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPreduzeca(Integer value) {
        this.idPreduzeca = value;
    }

    /**
     * Gets the value of the idGodine property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdGodine() {
        return idGodine;
    }

    /**
     * Sets the value of the idGodine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdGodine(Integer value) {
        this.idGodine = value;
    }

    /**
     * Gets the value of the godina property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGodina() {
        return godina;
    }

    /**
     * Sets the value of the godina property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGodina(Integer value) {
        this.godina = value;
    }

    /**
     * Gets the value of the vrstaFakture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVrstaFakture() {
        return vrstaFakture;
    }

    /**
     * Sets the value of the vrstaFakture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVrstaFakture(String value) {
        this.vrstaFakture = value;
    }

    /**
     * Gets the value of the brojFakture property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBrojFakture() {
        return brojFakture;
    }

    /**
     * Sets the value of the brojFakture property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBrojFakture(Integer value) {
        this.brojFakture = value;
    }

    /**
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDatumValute() {
        return datumValute;
    }

    /**
     * Sets the value of the datumValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumValute(Date value) {
        this.datumValute = value;
    }

    /**
     * Gets the value of the ukupanRabat property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUkupanRabat() {
        return ukupanRabat;
    }

    /**
     * Sets the value of the ukupanRabat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUkupanRabat(Float value) {
        this.ukupanRabat = value;
    }

    /**
     * Gets the value of the ukupanIznosBezPdv property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUkupanIznosBezPdv() {
        return ukupanIznosBezPdv;
    }

    /**
     * Sets the value of the ukupanIznosBezPdv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUkupanIznosBezPdv(Float value) {
        this.ukupanIznosBezPdv = value;
    }

    /**
     * Gets the value of the ukupanPdv property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUkupanPdv() {
        return ukupanPdv;
    }

    /**
     * Sets the value of the ukupanPdv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUkupanPdv(Float value) {
        this.ukupanPdv = value;
    }

    /**
     * Gets the value of the ukupnoZaPlacanje property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUkupnoZaPlacanje() {
        return ukupnoZaPlacanje;
    }

    /**
     * Sets the value of the ukupnoZaPlacanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUkupnoZaPlacanje(Float value) {
        this.ukupnoZaPlacanje = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="id_proizvoda" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="jedinica_mere" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="naziv_proizvoda" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="id_stavke_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="id_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="cena_bez_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="skracenica_jedinice_mere" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ukupan_iznos_sa_pdvom" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="kolicina" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="rabat" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="stopa_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="osnovica" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="iznos_pdva" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="stavka_fakture" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Stavka {

        @XmlAttribute(name = "id_proizvoda")
        protected Integer idProizvoda;
        @XmlAttribute(name = "jedinica_mere")
        protected Integer jedinicaMere;
        @XmlAttribute(name = "naziv_proizvoda")
        protected String nazivProizvoda;
        @XmlAttribute(name = "id_stavke_fakture")
        protected Integer idStavkeFakture;
        @XmlAttribute(name = "id_fakture")
        protected Integer idFakture;
        @XmlAttribute(name = "cena_bez_pdva")
        protected Float cenaBezPdva;
        @XmlAttribute(name = "skracenica_jedinice_mere")
        protected String skracenicaJediniceMere;
        @XmlAttribute(name = "ukupan_iznos_sa_pdvom")
        protected Float ukupanIznosSaPdvom;
        @XmlAttribute
        protected Float kolicina;
        @XmlAttribute
        protected Float rabat;
        @XmlAttribute(name = "stopa_pdva")
        protected Float stopaPdva;
        @XmlAttribute
        protected Float osnovica;
        @XmlAttribute(name = "iznos_pdva")
        protected Float iznosPdva;
        @XmlAttribute(name = "stavka_fakture")
        protected Integer stavkaFakture;

        /**
         * Gets the value of the idProizvoda property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getIdProizvoda() {
            return idProizvoda;
        }

        /**
         * Sets the value of the idProizvoda property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setIdProizvoda(Integer value) {
            this.idProizvoda = value;
        }

        /**
         * Gets the value of the jedinicaMere property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getJedinicaMere() {
            return jedinicaMere;
        }

        /**
         * Sets the value of the jedinicaMere property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setJedinicaMere(Integer value) {
            this.jedinicaMere = value;
        }

        /**
         * Gets the value of the nazivProizvoda property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNazivProizvoda() {
            return nazivProizvoda;
        }

        /**
         * Sets the value of the nazivProizvoda property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNazivProizvoda(String value) {
            this.nazivProizvoda = value;
        }

        /**
         * Gets the value of the idStavkeFakture property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getIdStavkeFakture() {
            return idStavkeFakture;
        }

        /**
         * Sets the value of the idStavkeFakture property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setIdStavkeFakture(Integer value) {
            this.idStavkeFakture = value;
        }

        /**
         * Gets the value of the idFakture property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getIdFakture() {
            return idFakture;
        }

        /**
         * Sets the value of the idFakture property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setIdFakture(Integer value) {
            this.idFakture = value;
        }

        /**
         * Gets the value of the cenaBezPdva property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getCenaBezPdva() {
            return cenaBezPdva;
        }

        /**
         * Sets the value of the cenaBezPdva property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setCenaBezPdva(Float value) {
            this.cenaBezPdva = value;
        }

        /**
         * Gets the value of the skracenicaJediniceMere property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSkracenicaJediniceMere() {
            return skracenicaJediniceMere;
        }

        /**
         * Sets the value of the skracenicaJediniceMere property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSkracenicaJediniceMere(String value) {
            this.skracenicaJediniceMere = value;
        }

        /**
         * Gets the value of the ukupanIznosSaPdvom property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getUkupanIznosSaPdvom() {
            return ukupanIznosSaPdvom;
        }

        /**
         * Sets the value of the ukupanIznosSaPdvom property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setUkupanIznosSaPdvom(Float value) {
            this.ukupanIznosSaPdvom = value;
        }

        /**
         * Gets the value of the kolicina property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Float getKolicina() {
            return kolicina;
        }

        /**
         * Sets the value of the kolicina property.
         * 
         * @param kolicina2
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setKolicina(Float kolicina2) {
            this.kolicina = kolicina2;
        }

        /**
         * Gets the value of the rabat property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getRabat() {
            return rabat;
        }

        /**
         * Sets the value of the rabat property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setRabat(Float value) {
            this.rabat = value;
        }

        /**
         * Gets the value of the stopaPdva property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getStopaPdva() {
            return stopaPdva;
        }

        /**
         * Sets the value of the stopaPdva property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setStopaPdva(Float value) {
            this.stopaPdva = value;
        }

        /**
         * Gets the value of the osnovica property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getOsnovica() {
            return osnovica;
        }

        /**
         * Sets the value of the osnovica property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setOsnovica(Float value) {
            this.osnovica = value;
        }

        /**
         * Gets the value of the iznosPdva property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getIznosPdva() {
            return iznosPdva;
        }

        /**
         * Sets the value of the iznosPdva property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setIznosPdva(Float value) {
            this.iznosPdva = value;
        }

        /**
         * Gets the value of the stavkaFakture property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getStavkaFakture() {
            return stavkaFakture;
        }

        /**
         * Sets the value of the stavkaFakture property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setStavkaFakture(Integer value) {
            this.stavkaFakture = value;
        }

    }

}