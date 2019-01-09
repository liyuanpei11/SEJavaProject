package lagerverwaltung;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	//Objekt Eigenschaften
	private String name;
	private int platz;
	private BigDecimal preis;
	private int anzahl;
	private int gewicht;
	private int gesamtgewicht;
	private String kategorie;
	private String eigenschaften;
	
	/**
	 * @param name
	 * @param platz
	 * @param preis
	 * @param anzahl
	 * @param gewicht
	 * @param gesamtgewicht
	 * @param kategorie
	 * @param eigenschaften
	 * @return 
	 */
	
	//Delimiter used in CSV file
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
	
	
	
	public Model(String name, int platz, BigDecimal preis, int anzahl, int gewicht, int gesamtgewicht, String kategorie, String eigenschaften) {
		super();
		this.name = name;
		this.platz = platz;
		this.preis = preis;
		this.anzahl = anzahl;
		this.setGewicht(gewicht);
		this.setGesamtgewicht(anzahl * gewicht);
		this.kategorie = kategorie;
		this.eigenschaften = eigenschaften;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPlatz() {
		return platz;
	}
	
	public void setPlatz(int platz) {
		this.platz = platz;
	}
	
	public BigDecimal getPreis() {
		return preis;
	}
	
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public int getGewicht() {
		return gewicht;
	}

	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}

	public int getGesamtgewicht() {
		return gesamtgewicht;
	}

	public void setGesamtgewicht(int gesamtgewicht) {
		this.gesamtgewicht = gesamtgewicht;
	}
	
	public String getKategorie() {
		return kategorie;
	}
	
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	
	public String getEigenschaften() {
		return eigenschaften;
	}
	
	public void setEigenschaften(String eigenschaften) {
		this.eigenschaften = eigenschaften;
	}

	
	public String toString() {
		return "Objekt [Name=" + name +", platz=" + platz + ", Preis=" + preis +", Anzahl=" + anzahl + ", Gewicht=" + gewicht + ", Gesamtgewicht=" + gesamtgewicht + ", Kategorie=" + kategorie + ", Eigenschaften=" + eigenschaften +"]";
	}
		
	public static void readcsv(String filename){
		kategorieliste.add("Kategorie wählen...");	
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
					
					if (tokens[objekt_name_id].equals("katdummy")) {
						if (checkkategorieliste(tokens[objekt_kategorie_id])) {
						} else {
							kategorieliste.add(tokens[objekt_kategorie_id]);
						}
					} else {
						objektliste.add(new Model(tokens[objekt_name_id], 
											Integer.parseInt(tokens[objekt_platz_id]), 
											new BigDecimal(tokens[objekt_preis_id]), 
											Integer.parseInt(tokens[objekt_anzahl_id]),
											Integer.parseInt(tokens[objekt_gewicht_id]),
											0,
											tokens[objekt_kategorie_id], 
											tokens[objekt_eigenschaften_id]));
					}
				}
			}
//			Controller.listePrinten();
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
		    	fileReader.close();
		    } catch(IOException ie) {
		    	System.out.println("Error occured while closing the BufferedReader");
		        ie.printStackTrace();
		    }
		}
		
	}
	
	public static boolean checkkategorieliste(String search) {
		for(String str: kategorieliste) {
		    if(str.trim().contains(search)) {
		    	return true;
		    }
		}
		return false;
	}
			
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
			for (int i=1; i < kategorieliste.size(); i++) {
				fileWriter.append("katdummy");
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
			
	public static void main(String args[]){
		Model.readcsv("penfactory.csv");
	}


	
	
	
	
}
