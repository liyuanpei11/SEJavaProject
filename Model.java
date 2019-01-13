package lagerverwaltung;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Die Class Model ist nur mit dem Auslesen und Abspeichern der Daten 
 * zuständig. Hier werden alle wichtigen Variablen definiert. 
 * Sowohl das Lesen, aber auch das Schreiben der Datei wird in dieser Class erledigt.
 * 
 *
 * @author Didem Güngör 	(Mat-Nr. 772703)
 * @author Wisal Elzakzouk	(Mat-Nr. 782102)
 * @author Florian Schubart	(Mat-Nr. 772093)    
 * @author Mohamad Doulabi 	(Mat-Nr. 775713)    
 * @author Richard Li		(Mat-Nr. 795238)
 * @version Java 8
 * @since 1.0
 *
 */

public class Model {

	//Objekt Eigenschaften
	/**
	 * @param name 
	 * @param platz
	 * @param preis
	 * @param anzahl
	 * @param gewicht
	 * @param gesamtgewicht
	 * @param kategorie
	 * @param eigenschaften 
	 */
	private String name;
	private int platz;
	private BigDecimal preis;
	private int anzahl;
	private int gewicht;
	private int gesamtgewicht;
	private String kategorie;
	private String eigenschaften;
	
	//Delimiter used in CSV file
	/**
	 * @param 
	 */
	private static final String CSV_Delimiter = ";";
	private static final String New_Line_Seperator = "\n";
	
	//CSV File header
	private static final String File_Header = "name;platz;preis;anzahl;gewicht;gesamtgewicht;kategorie;eigenschaften";
	
	//objekt attribute id
	public static final int objekt_name_id = 0;
	public static final int objekt_platz_id = 1;
	public static final int objekt_preis_id = 2;
	public static final int objekt_anzahl_id = 3;
	public static final int objekt_gewicht_id = 4;
	public static final int objekt_gesamtgewicht_id = 5;
	public static final int objekt_kategorie_id = 6;
	public static final int objekt_eigenschaften_id = 7;
	
	//create a new list of objekt to be filled by csv
	public static ObservableList<Model> objektliste = FXCollections.observableArrayList();
	public static ObservableList<String> kategorieliste = FXCollections.observableArrayList();
	
	/**
	 * Bei Model() kann ohne weitergabe von jeglichen Parametern ein Objekt erstellt werden. 
	 * <br>Diese Methode wird aber normalerweise nicht verwendet, da meist die Parameter bereits bekannt sind 
	 * und das Objekt mit diesen initiert wird.
	 */
	
	public Model() {
		this.name = "";
		this.platz = 0;
		this.preis = new BigDecimal ("0.00");
		this.anzahl = 0;
		this.gewicht = 0;
	    this.gesamtgewicht = anzahl * gewicht;
		this.kategorie = "";
		this.eigenschaften = "";
	}
	
	/**
	 * Ein neues Objekt mit dem Namen Model wird hier erstellt.
	 * 
	 * @param name Der Name das Produktes.
	 * @param platz Der Platz des Produktes.
	 * @param preis Der Preis des Produktes.
	 * @param anzahl Die Anzahl des Produktes.
	 * @param gewicht Das Gewicht des Produktes.
	 * @param gesamtgewicht Das Gesamtgewicht wird vom Programm (Anzahl * Gewicht) berechnet.
	 * @param kategorie Die Kategorie des Produktes.
	 * @param eigenschaften Die Eigenschaften des Produktes.
	 */
	
	public Model(String name, int platz, BigDecimal preis, int anzahl, int gewicht, int gesamtgewicht, String kategorie, String eigenschaften) {
//		super();
		this.name = name;
		this.platz = platz;
		this.preis = preis;
		this.anzahl = anzahl;
		this.setGewicht(gewicht);
		this.setGesamtgewicht(anzahl * gewicht);
		this.kategorie = kategorie;
		this.eigenschaften = eigenschaften;
	}
	
	/**
	 * 
	 * @return Gibt den Namen des Produktes wieder.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name Legt den Namen des Produktes fest.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return Gibt den Platz des Produktes wieder.
	 */
	public int getPlatz() {
		return platz;
	}
	
	/**
	 * 
	 * @param platz Legt den Platz des Produktes fest.
	 */
	public void setPlatz(int platz) {
		this.platz = platz;
	}
	
	/**
	 * 
	 * @return Gibt den Preis des Produktes wieder.
	 */
	public BigDecimal getPreis() {
		return preis;
	}
	
	/**
	 * 
	 * @param preis Legt den Preis des Produktes fest.
	 */
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	/**
	 * 
	 * @return Gibt den Preis des Produktes wieder.
	 */
	public int getAnzahl() {
		return anzahl;
	}
	
	/**
	 * 
	 * @param anzahl Legt die Anzahl des Produktes fest.
	 */
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	/**
	 * 
	 * @return Gibt das Gewicht des Produktes wieder.
	 */
	public int getGewicht() {
		return gewicht;
	}
	
	/**
	 * 
	 * @param gewicht Legt das Gewicht des Produktes fest.
	 */
	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}
	
	/**
	 * 
	 * @return Gibt das Gewicht des Produktes wieder.
	 */
	public int getGesamtgewicht() {
		return gesamtgewicht;
	}
	
	/**
	 * 
	 * @param gesamtgewicht Legt das Gesamtgewicht des Produktes fest.
	 */
	public void setGesamtgewicht(int gesamtgewicht) {
		this.gesamtgewicht = gesamtgewicht;
	}
	
	/**
	 * 
	 * @return Gibt die Kategorie wieder.
	 */
	public String getKategorie() {
		return kategorie;
	}
	
	/**
	 * 
	 * @param kategorie Legt die Kateogrie des Produktes fest.
	 */
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	
	/**
	 * 
	 * @return Gibt die Eigenschaften des Produktes wieder.
	 */
	public String getEigenschaften() {
		return eigenschaften;
	}
	
	/**
	 * 
	 * @param eigenschaften Legt die Eigenschaften des Produktes wieder.
	 */
	public void setEigenschaften(String eigenschaften) {
		this.eigenschaften = eigenschaften;
	}

	/**
	 *  @return Gibt alle Paramter eines Produktes in einer Zeile aus. 
	 */
	public String toString() {
		return "Objekt [Name=" + name +
				", platz=" + platz + 
				", Preis=" + preis +
				", Anzahl=" + anzahl + 
				", Gewicht=" + gewicht + 
				", Gesamtgewicht=" + gesamtgewicht + 
				", Kategorie=" + kategorie + 
				", Eigenschaften=" + eigenschaften +"]";
	}
		
	/**
	 * Hier werden die Daten aus der Datei ausgelesen und eingespeichert.
	 * <br> Die Methode ruft die Methode checkkategorieliste() und speichert die Daten in zwei Listen.
	 * 
	 * @param filename Der Dateiname der Datenbank.
	 */
	public static void readcsv(String filename){
		BufferedReader fileReader = null;
			
		try {
				
			//Creat the file reader
			fileReader = new BufferedReader(new FileReader(filename));
			
			//read the csv file header to skip it
			fileReader.readLine();
					
			String line = "";
					
			//read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				
				//Get all tokens available in line
				String[] tokens = line.split(CSV_Delimiter);
				
				if (tokens.length > 0) {
					//Create a new objekt object and fill his data
					
					if (tokens[objekt_name_id].equals("!?$katdummy!?$")) {
						if (checkkategorieliste(tokens[objekt_kategorie_id])) {
						} else {
							kategorieliste.add(tokens[objekt_kategorie_id]);
						}
					} else {
						objektliste.add(new Model(
											tokens[objekt_name_id], 
											Integer.parseInt(tokens[objekt_platz_id]), 
											new BigDecimal(tokens[objekt_preis_id]), 
											Integer.parseInt(tokens[objekt_anzahl_id]),
											Integer.parseInt(tokens[objekt_gewicht_id]),
											0, 												// Die 0 dient lediglich als Platzhalter in diesem Moment. 
											tokens[objekt_kategorie_id], 
											tokens[objekt_eigenschaften_id]));
					}
				}
			}
			fileReader.close();
			
		} catch (Exception e) {
			if (e.toString().contains("java.lang.ArrayIndexOutOfBoundsException")) {
				System.out.println("Array Error");
				Controller.warnungFenster("Fehlerhafte Datei! Bitte überprüfen Sie, ob in der Datenbank - Datei ein Semikolon (;) fehlt.");
			} else 
				if (e.toString().contains("java.io.FileNotFoundException")) {
					System.out.println("File Not Found Error");
					Controller.warnungFenster("Die Datei konnte nicht gefunden werden! Bitte legen Sie die Datenbankdatei in den selben Ordner wie das Programm oder Sie erstellen die Datenbank mit diesem Programm!");
				}
			System.out.println("Error in CsvFileReader !!!");
		}
	}
	
	/**
	 * Überprüft ob die Kategorie bereits in der Liste vorhanden ist.
	 * 
	 * @param search gibt die zu Überprüfende Kategorie an.
	 * 
	 * @return true - wenn die Kategorie bereits vorhanden ist.
	 */
	public static boolean checkkategorieliste(String search) {
		for(String str: kategorieliste) {
		    if(str.trim().toLowerCase().contains(search.toLowerCase())) {
		    	return true;
		    }
		}
		return false;
	}
	
	/**
	 * Sichert die Daten in einer CSV - Datei.
	 * <br> Alle Objekte aus der objekteliste in die Datei geschrieben.
	 * <br> Alle Kategorien werden mit einer besonderen Kennzeichnung in die Datei geschrieben.
	 * 
	 * @param filename gibt den Namen der CSV - Datei an.
	 */
	public static void writecsv(String filename){
		
		FileWriter fileWriter = null;
			
		try {
			fileWriter = new FileWriter(filename);
			
			//Write the CSV file_Header
			fileWriter.append(File_Header.toString());
					
			//Add a new line seperator after the header
			fileWriter.append(New_Line_Seperator);
					
			for (Model o: objektliste) {
				fileWriter.append(o.getName());
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(String.valueOf(o.getPlatz()));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(String.valueOf(o.getPreis()));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(String.valueOf(o.getAnzahl()));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(String.valueOf(o.getGewicht()));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(String.valueOf(o.getGesamtgewicht()));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(o.getKategorie());
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(o.getEigenschaften());
				fileWriter.append(New_Line_Seperator);					
			}
			for (int i=0; i < kategorieliste.size(); i++) {
				fileWriter.append("!?$katdummy!?$");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(CSV_Delimiter);
				fileWriter.append(kategorieliste.get(i));
				fileWriter.append(CSV_Delimiter);
				fileWriter.append("0");
				fileWriter.append(New_Line_Seperator);
			}
					
			System.out.println("Csv Datei wurde erfolgreich geschrieben!!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter!!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter!!!");
				e.printStackTrace();
			}
		}
	}
				
	
}
