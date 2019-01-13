package lagerverwaltung;

import java.math.BigDecimal;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * 
 * Die Class Controller ist f�r die Kommunikation zwischen View und Model zust�ndig.
 * <br> Die Class Controller �berpr�ft die Eingabe des Benutzers auf Korrektheit und �bergibt diese an die Model Class.
 * <br> Bei falscher oder nicht kompatibler Eingabe wird der Benutzer daraufhingewiesen und bittet ihn um eine �nderung der Eingabe.
 * 
 *
 * @author Didem G�ng�r 	(Mat-Nr. 772703)
 * @author Wisal Elzakzouk	(Mat-Nr. 782102)
 * @author Florian Schubart	(Mat-Nr. 772093)    
 * @author Mohamad Doulabi 	(Mat-Nr. 775713)    
 * @author Richard Li		(Mat-Nr. 795238)
 * @version Java 8
 * @since 1.0
 *
 */
public class Controller {
	
	public static ObservableList<Model> searchliste = FXCollections.observableArrayList();
	
	/**
	 * Die Eingabe des Benutzers wird an die Model Class �bergeben und in die objektliste eingef�gt.
	 * <br> Um die Korrektheit der Eingabe zu gew�hren, �bergibt die Methode alle Eingaben an die Methode checkInput().
	 * Erst nach der Pr�fung werden die Daten an die Model Class �bergeben.
	 * @param katChoiceBox ist die Auswahl der Kategorie des Nutzers.
	 */
	public static void saveButtonClicked(ChoiceBox<String> katChoiceBox, String neuName, int neuPlatz, BigDecimal neuPreis, int neuAnzahl, int neuGewicht, String neuEigenschaft) {
		
		String kategorie = katChoiceBox.getValue();
		
		if (checkalreadyexist(neuName, 0)) {
			warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
		} else if (checkplatzexist(neuPlatz)) {
			warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
		} else {
			try {
				Model.objektliste.add(new Model(
						neuName, 
						neuPlatz, 
						neuPreis, 
						neuAnzahl,
						neuGewicht,
						0,
						kategorie, 
						neuEigenschaft));
				if(neuAnzahl < 10) {
					warnungFenster("Achtung, die Anzahl betr�gt weniger als 10!");
				}
				View.addwindow.close();
			} catch (Exception e2) {
				warnungFenster("Bitte �berpr�fen Sie Ihre Eingabe!");
			}
		}	
	} 
	
	/**
	 * Hier wird die Eingabe des Benutzers auf Korrektheit und Kompatibilit�t gepr�ft. 
	 * 
	 * @param katChoiceBox - Die Auswahl der Kategorie des Benutzers.
	 * @param checknameInput - Der eingegebene Produktname
	 * @param checkplatzInput - Der eingegebene Produktplatz
	 * @param checkpreisInput - Der eingegebene Produktpreis
	 * @param checkanzahlInput - Die eingegebene Produktanzahl
	 * @param checkgewichtInput - Das eingegebene Produktgewicht
	 * @param checkeigenschaftenInput - Die eingegebene Produkteigenschaft
	 * @return true - wenn die Eingabe Korrekt war
	 */
	public static boolean checkInput(TextField checknameInput, TextField checkplatzInput, TextField checkpreisInput, TextField checkanzahlInput, TextField checkgewichtInput, TextField checkeigenschaftenInput ) {
		if(checknameInput.getText().isEmpty()){ 
			warnungFenster("Bitte geben Sie einen Produktnamen ein!");
			return false;
		} else 
			if (!checknameInput.getText().matches("[A-Za-z0-9\\s]+$")) { 
				warnungFenster("Ein Produktname darf nur Buchstaben und Zahlen enthalten!");
				return false;
		} else
			if(checkplatzInput.getText().isEmpty() || !checkplatzInput.getText().matches("\\d+")){
				warnungFenster("Bitte �berpr�fen Sie die Eingabe des Produktplatzes! z.B. 2");
				return false;
		} else 
			if (Integer.parseInt(checkplatzInput.getText()) <= 0) {
				warnungFenster("Der Produktplatz muss gr��er als 0 sein!");
				return false;
		} else 
			if(checkpreisInput.getText().isEmpty()){
				warnungFenster("Bitte geben Sie einen Produktpreis ein!");
				return false;
		} else 
			if (!checkpreisInput.getText().matches("(\\d+).(\\d{2,})")) {
				warnungFenster("Bitte �bepr�fen Sie Ihre Eingabe bez�glich des Preises! z.B. 2.00");
				return false;
		} else 
			if(checkanzahlInput.getText().isEmpty() || !checkanzahlInput.getText().matches("\\d+") || Integer.parseInt(checkanzahlInput.getText()) <= 0){
				warnungFenster("Bitte �berpr�fen Sie Ihre Eingabe bez�glich der Anzahl! z.B. 2");
				return false;
		} else 
			if(checkgewichtInput.getText().isEmpty() || !checkgewichtInput.getText().matches("\\d+") || Integer.parseInt(checkgewichtInput.getText()) <= 0){
				warnungFenster("Bitte �berpr�fen Sie die Eingabe des Produkgewichtes! z.B. 2");
				return false;
		} else 
			if(checkeigenschaftenInput.getText().isEmpty() || checkeigenschaftenInput.getText().contains(";")){
				warnungFenster("Bitte �berpr�fen Sie die Eingabe der Produkteigenschaft! Es darf kein Semikolon (;) verwendet werden!");
				return false;
		} else  {
			return true;
		}
	}
	
//	public static boolean checkInput(ChoiceBox<String> katChoiceBox, TextField checknameInput, TextField checkplatzInput, TextField checkpreisInput, TextField checkanzahlInput, TextField checkgewichtInput, TextField checkeigenschaftenInput ) {
//		String kategorie = katChoiceBox.getValue();
//		if(checknameInput.getText().isEmpty()){ 
//			warnungFenster("Bitte geben Sie einen Produktnamen ein!");
//			return false;
//		} else 
//			if (checkalreadyexist(checknameInput.getText(), 0)) {
//				warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
//				return false;
//		} else
//			if (!checknameInput.getText().matches("[A-Za-z0-9\\s]+$")) { 
//				warnungFenster("Ein Produktname darf nur Buchstaben und Zahlen enthalten!");
//				return false;
//		} else
//			if(checkplatzInput.getText().isEmpty() || !checkplatzInput.getText().matches("\\d+")){
//				warnungFenster("Bitte �berpr�fen Sie die Eingabe des Produktplatzes!");
//				return false;
//		} else 
//			if (checkplatzexist(Integer.parseInt(checkplatzInput.getText()))) {
//				warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
//				return false;
//		} else 
//			if (Integer.parseInt(checkplatzInput.getText()) <= 0) {
//				warnungFenster("Der Produktplatz muss gr��er als 0 sein!");
//				return false;
//		} else 
//			if(checkpreisInput.getText().isEmpty()){
//				warnungFenster("Bitte geben Sie einen Produktpreis ein!");
//				return false;
//		} else 
//			if (!checkpreisInput.getText().matches("(\\d+).(\\d{2,})")) {
//				warnungFenster("Bitte �bepr�fen Sie Ihre Eingabe bez�glich des Preises! z.B. 2.00");
//				return false;
//		} else 
//			if(checkanzahlInput.getText().isEmpty() ||!checkanzahlInput.getText().matches("\\d+") || Integer.parseInt(checkanzahlInput.getText()) <= 0){
//				warnungFenster("Bitte �berpr�fen Sie Ihre Eingabe bez�glich der Anzahl! z.B. 2");
//				return false;
//		} else 
//			if(checkgewichtInput.getText().isEmpty() ||!checkgewichtInput.getText().matches("\\d+")){
//				warnungFenster("Bitte �berpr�fen Sie die Eingabe des Produkgewichtes! z.B. 2");
//				return false;
//		} else 
//			if (kategorie == "Kategorie w�hlen...") {
//				warnungFenster("Bitte w�hlen Sie eine Produktkategorie aus!");
//				return false;
//		} else 
//			if(checkeigenschaftenInput.getText().isEmpty() || checkeigenschaftenInput.getText().contains(";")){
//				warnungFenster("Bitte �berpr�fen Sie die Eingabe der Produkteigenschaft! Es darf kein Semikolon (;) verwendet werden!");
//				return false;
//		} else  {
//			return true;
//		}
//	}
	
	/**
	 * Die �nderung der Produktdaten werden an die Model Class �bergeben.
	 * <br> Um die Korrektheit der Eingabe zu gew�hrleisten, wird die Eingabe an die Methode checkInput() �bergeben und �berpr�ft.
	 * Erst nach der �berpr�fung werden die neuen Daten an die Model Class �bergeben.
	 * 
	 * @param productSelected - das ausgew�hlte Produkt aus der Tabelle
	 * @param alteAnzahl - die alte Produktanzahl
	 * @param editkatChoiceBox - die ausgew�hlte Kategorie 
	 * @param editgetName - der alte Name
	 * @param editgetPlatz - der alte Platz
	 */
	public static void editsaveButtonClicked(ObservableList<Model> productSelected, int alteAnzahl, ChoiceBox<String> editkatChoiceBox, String editgetselectedName, int editgetselectedPlatz, String editName, int editPlatz, BigDecimal editPreis, int editAnzahl,  int editGewicht, String editEigenschaft) {
		int neueAnzahl = alteAnzahl + editAnzahl;
//		String neuerName = editName;
//		int neuerPlatz = Integer.parseInt(View.editplatzInput.getText());
		
		if (checkalreadyexist(editName, 0) && !editName.equals(editgetselectedName)) {
				warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
		} else 
			if (checkplatzexist(editPlatz) && editPlatz != editgetselectedPlatz) {
				warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
		} else 
			if (neueAnzahl < 0) {
				warnungFenster("Bitte �bepr�fen Sie Ihre Eingabe bez�glich der ge�nderten Anzahl!");
		} else {
				try {
					String kategorie = editkatChoiceBox.getValue();
					productSelected.get(0).setName(editName);
					productSelected.get(0).setPlatz(editPlatz);
					productSelected.get(0).setPreis(editPreis);
					if (neueAnzahl < 10) {
						warnungFenster("Die Produktanzahl betr�gt nur noch " + neueAnzahl +"!");
					}
					productSelected.get(0).setAnzahl(neueAnzahl);
					productSelected.get(0).setGewicht(editGewicht);
					productSelected.get(0).setKategorie(kategorie);
					productSelected.get(0).setEigenschaften(editEigenschaft);
					View.editwindow.close();
					View.table.refresh();
				} catch (Exception e) {
					warnungFenster("Bitte �berpr�fen Sie Ihre Eingabe!");
				}
			}
		}
	
	/**
	 *  Das ausgew�hlte Produkt wird komplett aus dem Datensatz entfernt.
	 */
	public static void deleteButtonClicked(ObservableList<Model> allProducts, ObservableList<Model> productSelected) {
		productSelected.forEach(allProducts::remove);
	}
	
	/**
	 * Mit dieser Methode wird �berpr�ft, ob der Benutzer ein Element aus der Tabelle ausgew�hlt hat.
	 * @return true - wenn eine Element ausgew�hlt wurde.
	 */
	public static boolean checkselect(ObservableList<Model> checkSelected) {
		if (checkSelected.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Diese Methode �berpr�ft, ob eine Element aus der Listenansicht der Kategorie ausgew�hlt wurde.
	 * <br> Falls kein Element ausgew�hlt wurde, erscheint eine Fehlermeldung.
	 * <br> Falls ein Element ausgew�hlt wurde, wird die Methode editkatdelButtonClicked() aufgerufen.
	 */
	public static boolean checkselectkat(String checkSelected) {
		if (!checkSelected.isEmpty()) {
			return true;
		} else {
			warnungFenster("Sie haben kein Element ausgew�hlt!");
			return false;
		}
	}
	
	/**
	 * Diese Methode l�scht die ausgew�hlte Kategorie aus der kategorieliste.
	 * <br> Hier wird �berpr�ft, ob die ausgew�hlte Kategorie noch Produkte enth�lt. 
	 * Falls ja, kann diese Kategorie nicht gel�scht werden.
	 */
	public static void editkatdelButtonClicked(String delkat) {
//		String delkat = View.listView.getSelectionModel().getSelectedItem();
//		
		if (checkalreadyexist(delkat, 1)) {
			warnungFenster("Mindestens ein Produkt ist noch in dieser Kategorie!");
		} else {
			Model.kategorieliste.remove(delkat);
			View.listView.getItems().remove(0,Model.kategorieliste.size());
			View.listView.getItems().addAll(Model.kategorieliste);
			View.listView.getItems().remove(0);
		}
	}
	
	/**
	 * Mit dieser Abfrage wird �berpr�ft, ob der Name oder der Platz bereits belegt ist.
	 * @param check - die zu pr�fende Eingabe
	 * @param objekt_id_id - die ID der Eigenschaft des Produktes
	 * @return true - wenn der Name oder Platz bereits vergeben ist
	 */
	public static boolean checkalreadyexist(String check, int objekt_id_id) {
		ObservableList<String> checkliste = FXCollections.observableArrayList();
		if (objekt_id_id == 0){
			for (int i = 0 ; i < Model.objektliste.size(); i++) {
				checkliste.add(Model.objektliste.get(i).getName());
			}
		} else {
			for (int i = 0 ; i < Model.objektliste.size(); i++) {
				checkliste.add(Model.objektliste.get(i).getKategorie());
			}
		}
			
		for(String str: checkliste) {
		    if(str.trim().contains(check)) {
		    	return true;
		    } 
		}
		return false;
	}	
	
	/**
	 * Mit dieser Abfrage wird �berpr�ft, ob der Platz bereits belegt ist.
	 * @param check - der zu pr�fende Platz
	 * @return true - wenn der Platz bereits belegt ist
	 */
	public static boolean checkplatzexist(int check) {
		ObservableList<Integer> checkliste = FXCollections.observableArrayList();
		for (int i = 0 ; i < Model.objektliste.size(); i++) {
			checkliste.add(Model.objektliste.get(i).getPlatz());
		}			
		if(checkliste.contains(check)) {
		    return true;
		} else {
			return false;
		}
	}	
			
	/**
	 * Die eingegebene Kategorie wird in die kategorieliste hinzugef�gt, wenn diese nicht bereits vorhanden ist.
	 */
	public static void editkataddButtonClicked(String katadd) {
//		String katadd = View.editkatInput.getText();
		if (katadd.isEmpty()) {
			warnungFenster("Bitte �berpr�fen Sie Ihre Eingabe!");
		} else if(Model.checkkategorieliste(katadd)){
			warnungFenster("Diese Kategorie existiert bereits!");
		}else {
			Model.kategorieliste.add(katadd);
			View.editkatInput.clear(); //Textfeld zum Ausgangspunkt zur�cksetzen
			View.listView.getItems().clear();
			View.listView.getItems().addAll(Model.kategorieliste);
		}
	}
	
	/**
	 * Es wird bei dieser Methode das "Kategorie bearbeiten" Fenster geschlossen.
	 * <br> Der Benutzer wird darauf hingewiesen, wenn noch eine Eingabe nicht abgeschlossen ist und gefragt, ob das Fenster geschlossen werden soll.
	 */
	public static void editkatabortButtonClicked(String editkattext) {
		if (editkattext.isEmpty()) {
			View.editkatwindow.close();
		} else {
			if (confirmabortwindow()) {
				View.editkatwindow.close();
			}
		}
	}
	
	/**
	 * Mit dieser Funktion wird eine Warnung erzeugt und der Benutzer auf ein Problem hingewiesen.
	 * @param warntext - Der Hinweis, was die Fehlerursache ist
	 */
	public static void warnungFenster(String warntext) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warnung!");
		alert.setHeaderText(null);
		alert.setContentText(warntext);
		alert.showAndWait();
	}
	
	/**
	 * Der Benutzer wird gefragt, ob er sich sicher ist, dass er das Programm schlie�en will.
	 * @return true - wenn der Benutzer das Fenster schlie�en will.
	 */
	public static boolean confirmclosewindow() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sind Sie sich sicher?");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie das Programm beenden?");
		
		ButtonType yesButton = new ButtonType("Ja");
		ButtonType noButton = new ButtonType("Nein", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(yesButton, noButton);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButton) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Der Benutzer wird gefragt, ob er das ausgew�hlte Element l�schen will.
	 * @return true - der Benutzer hat das L�schen best�tigt
	 */
	public static boolean confirmdeletewindow() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sind Sie sich sicher?");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie das ausgew�hlte Element entfernen?");
		
		ButtonType yesButton = new ButtonType("Ja");
		ButtonType noButton = new ButtonType("Nein", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(yesButton, noButton);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButton) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Der Benutzer wird gefragt, ob er das Fenster ohne Speichern Schlie�en will.
	 * @return true - der Benutzer hat das schlie�en ohne Speichern best�tigt
	 */
	public static boolean confirmabortwindow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sind Sie sich sicher?");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie das Fenster ohne Speichern schlie�en?");
		
		ButtonType yesButton = new ButtonType("Ja");
		ButtonType noButton = new ButtonType("Nein", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(yesButton, noButton);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButton) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * In der Methode werden die Daten nach der Eingabe des Benutzers in der Sucheliste durchsucht und dargestellt.
	 * @param mainChoiceBox - die Auswahl des Benutzers, in welcher Spalte er die Tabelle durchsuchen will
	 */
	public static void searchButtonClicked(ChoiceBox<String> mainChoiceBox, String searchtext) {
		searchliste.clear();
//		String searchtext = View.SearchInput.getText().toLowerCase();
		String auswahltext = mainChoiceBox.getValue(); //"Name", "Platz", "Preis", "Anzahl", "Gewicht", "Kategorie", "Eigenschaften"
		if (searchtext.isEmpty()) {
			warnungFenster("Bitte geben Sie etwas in die Suchleiste ein!");
		} else if (auswahltext == "Name") {
			for (Model o: Model.objektliste) {
				if (o.getName().toLowerCase().contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Platz") {
			for (Model o: Model.objektliste) {
				if (Integer.toString(o.getPlatz()).contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Preis") {
			for (Model o: Model.objektliste) {
				if (o.getPreis().toString().contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Anzahl") {
			for (Model o: Model.objektliste) {
				if (Integer.toString(o.getAnzahl()).contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Gewicht") {
			for (Model o: Model.objektliste) {
				if (Integer.toString(o.getGewicht()).contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Kategorie") {
			for (Model o: Model.objektliste) {
				if (o.getKategorie().toLowerCase().contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} else if (auswahltext == "Eigenschaften") {
			for (Model o: Model.objektliste) {
				if (o.getEigenschaften().toLowerCase().contains(searchtext)) {
					searchliste.add(new Model(o.getName(), 
							o.getPlatz(), 
							o.getPreis(), 
							o.getAnzahl(),
							o.getGewicht(),
							o.getGesamtgewicht(),
							o.getKategorie(), 
							o.getKategorie()));
				}
			}
		} 
		if (searchliste.isEmpty() && !searchtext.isEmpty()) {
			warnungFenster("Es konnte kein Ergebnis zu dieser Suche gefunden werden!");
		} else if (!searchliste.isEmpty()){
//			System.out.println(searchliste);
			View.table.setItems(searchliste);
		}
	}
	
	/**
	 * Diese Methode setzt die Suchleiste zur�ck und zeigt wieder alle Daten in der Tabelle an.
	 */
	public static void resetButtonClicked() {
		View.SearchInput.clear(); //Suchleiste zur�cksetzen
		View.table.setItems(Model.objektliste);
	}
	
	/**
	 * Hier �berpr�ft das Programm, ob noch Fenster offen sind.
	 * @return true - wenn andere Fenster au�er dem Hauptfenster noch offen sind
	 */
	public static boolean checkopenwindow() {
		if (!View.addwindow.isShowing() && !View.editwindow.isShowing() && !View.editkatwindow.isShowing()) {
			return true;
		} else {
			warnungFenster("Achtung! Sie haben noch offene Fenster!");
			return false;
		}
	}
	
	/**
	 * Diese Methode dient zur zwischenzeitigen Konsolen ausgebe der aktuellen objekteliste.
	 */
	public static void listePrinten() {
		for (Model o: Model.objektliste) {
			System.out.println("Name: "+o.getName()+
								" Platz: "+o.getPlatz()+
								" Preis: "+o.getPreis()+
								" Anzahl: "+o.getAnzahl()+
								" Gewicht: "+o.getGewicht()+
								" Gesamtgewicht: "+o.getGesamtgewicht()+
								" Kategorie: "+o.getKategorie()+
								" Eigenschaften: " +o.getEigenschaften()+ "\n");
		}
	}


}
