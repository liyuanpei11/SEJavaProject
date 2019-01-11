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
 * Die Class Controller ist für die Kommunikation zwischen View und Model zuständig.
 * <br> Die Class Controller überprüft die Eingabe des Benutzers auf Korrektheit und übergibt diese an die Model Class.
 * <br> Bei falscher oder nicht kompatibler Eingabe wird der Benutzer daraufhingewiesen und bittet ihn um eine Änderung der Eingabe.
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
public class Controller {
	
	public static ObservableList<Model> searchliste = FXCollections.observableArrayList();
	
	/**
	 * Die Eingabe des Benutzers wird an die Model Class übergeben und in die objektliste eingefügt.
	 * <br> Um die Korrektheit der Eingabe zu gewähren, übergibt die Methode alle Eingaben an die Methode checkInput().
	 * Erst nach der Prüfung werden die Daten an die Model Class übergeben.
	 * @param katChoiceBox ist die Auswahl der Kategorie des Nutzers.
	 */
	public static void saveButtonClicked(ChoiceBox<String> katChoiceBox) {
		String kategorie = katChoiceBox.getValue();
			// Überprüfung der Eingabe, ob die Eingabe leer ist bzw. nicht erlaubte Zeichen enthält
		if (checkInput(katChoiceBox, View.nameInput, View.platzInput, View.preisInput, View.anzahlInput, View.gewichtInput, View.eigenschaftenInput)) {
			try {
				Model.objektliste.add(new Model(
						View.nameInput.getText(), 
						Integer.parseInt(View.platzInput.getText()), 
						new BigDecimal(View.preisInput.getText()), 
						Integer.parseInt(View.anzahlInput.getText()),
						Integer.parseInt(View.gewichtInput.getText()),
						Integer.parseInt("0"),
						kategorie, 
						View.eigenschaftenInput.getText()));
				View.addwindow.close();
			} catch (Exception e2) {
				warnungFenster("Bitte überprüfen Sie Ihre Eingabe!");
			}
			}
		} 
	
	/**
	 * Hier wird die Eingabe des Benutzers auf Korrektheit und Kompatibilität geprüft. 
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
	public static boolean checkInput(ChoiceBox<String> katChoiceBox, TextField checknameInput, TextField checkplatzInput, TextField checkpreisInput, TextField checkanzahlInput, TextField checkgewichtInput, TextField checkeigenschaftenInput ) {
		String kategorie = katChoiceBox.getValue();
		if(checknameInput.getText().isEmpty()){ 
			warnungFenster("Bitte geben Sie einen Produktnamen ein!");
			return false;
		} else 
			if (checkalreadyexist(checknameInput.getText(), 0)) {
				warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
				return false;
		} else
			if (!checknameInput.getText().matches("[A-Za-z0-9\\s]+$")) { 
				warnungFenster("Ein Produktname darf nur Buchstaben und Zahlen enthalten!");
				return false;
		} else
			if(checkplatzInput.getText().isEmpty() || !checkplatzInput.getText().matches("\\d+")){
				warnungFenster("Bitte überprüfen Sie die Eingabe des Produktplatzes!");
				return false;
		} else 
			if (checkplatzexist(Integer.parseInt(checkplatzInput.getText()))) {
				warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
				return false;
		} else 
			if (Integer.parseInt(checkplatzInput.getText()) <= 0) {
				warnungFenster("Der Produktplatz muss größer als 0 sein!");
				return false;
		} else 
			if(checkpreisInput.getText().isEmpty()){
				warnungFenster("Bitte geben Sie einen Produktpreis ein!");
				return false;
		} else 
			if (!checkpreisInput.getText().matches("(\\d+).(\\d{2,})")) {
				warnungFenster("Bitte übeprüfen Sie Ihre Eingabe bezüglich des Preises! z.B. 2.00");
				return false;
		} else 
			if(checkanzahlInput.getText().isEmpty() ||!checkanzahlInput.getText().matches("\\d+") || Integer.parseInt(checkanzahlInput.getText()) <= 0){
				warnungFenster("Bitte überprüfen Sie Ihre Eingabe bezüglich der Anzahl! z.B. 2");
				return false;
		} else 
			if(checkgewichtInput.getText().isEmpty() ||!checkgewichtInput.getText().matches("\\d+")){
				warnungFenster("Bitte überprüfen Sie die Eingabe des Produkgewichtes! z.B. 2");
				return false;
		} else 
			if (kategorie == "Kategorie wählen...") {
				warnungFenster("Bitte wählen Sie eine Produktkategorie aus!");
				return false;
		} else 
			if(checkeigenschaftenInput.getText().isEmpty() || checkeigenschaftenInput.getText().contains(";")){
				warnungFenster("Bitte überprüfen Sie die Eingabe der Produkteigenschaft! Es darf kein Semikolon (;) verwendet werden!");
				return false;
		} else  {
			return true;
		}
	}
	
	/**
	 * Die Änderung der Produktdaten werden an die Model Class übergeben.
	 * <br> Um die Korrektheit der Eingabe zu gewährleisten, wird die Eingabe an die Methode checkInput() übergeben und überprüft.
	 * Erst nach der Überprüfung werden die neuen Daten an die Model Class übergeben.
	 * 
	 * @param productSelected - das ausgewählte Produkt aus der Tabelle
	 * @param alteAnzahl - die alte Produktanzahl
	 * @param editkatChoiceBox - die ausgewählte Kategorie 
	 * @param editgetName - der alte Name
	 * @param editgetPlatz - der alte Platz
	 */
	public static void editsaveButtonClicked(ObservableList<Model> productSelected, int alteAnzahl, ChoiceBox<String> editkatChoiceBox, String editgetName, int editgetPlatz) {
		int neueAnzahl = alteAnzahl + Integer.parseInt(View.editanzahlInput.getText());
		String neuerName = View.editnameInput.getText();
		int neuerPlatz = Integer.parseInt(View.editplatzInput.getText());
		
		if (checkalreadyexist(neuerName, 0) && !neuerName.equals(editgetName)) {
				warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
		} else 
			if (checkplatzexist(neuerPlatz) && neuerPlatz != editgetPlatz) {
				warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
		} else 
			if (neueAnzahl < 0) {
				warnungFenster("Bitte übeprüfen Sie Ihre Eingabe bezüglich der geänderten Anzahl!");
		} else 
			if (checkInput(editkatChoiceBox, View.editnameInput, View.editplatzInput, View.editpreisInput, 1, View.editgewichtInput, View.editeigenschaftenInput)) {
				try {
					String kategorie = editkatChoiceBox.getValue();
					productSelected.get(0).setName(View.editnameInput.getText());
					productSelected.get(0).setPlatz(Integer.parseInt(View.editplatzInput.getText()));
					productSelected.get(0).setPreis(new BigDecimal(View.editpreisInput.getText()));
					if (neueAnzahl < 10) {
						warnungFenster("Die Produktanzahl beträgt nur noch " + neueAnzahl +"!");
					}
					productSelected.get(0).setAnzahl(neueAnzahl);
					productSelected.get(0).setGewicht(Integer.parseInt(View.editgewichtInput.getText()));
					productSelected.get(0).setKategorie(kategorie);
					productSelected.get(0).setEigenschaften(View.editeigenschaftenInput.getText());
					View.editwindow.close();
					View.table.refresh();
				} catch (Exception e) {
					warnungFenster("Bitte überprüfen Sie Ihre Eingabe!");
				}
			}
		}
	
	/**
	 *  Das ausgewählte Produkt wird komplett aus dem Datensatz entfernt.
	 */
	public static void deleteButtonClicked() {
		ObservableList<Model> productSelected, allProducts;
		allProducts = View.table.getItems();
		productSelected = View.table.getSelectionModel().getSelectedItems();
		productSelected.forEach(allProducts::remove);
	}
	

//	public static int checkdecimaldigits(BigDecimal bdToCheck) {
//		final String s = bdToCheck.toPlainString();
//	    final int index = s.indexOf('.');
//	    if (index < 0) {
//	        return 0;
//	    } else {
//	    	return s.length() - 1 - index;
//	    }
//	}
	
	/**
	 * Mit dieser Methode wird überprüft, ob der Benutzer ein Element aus der Tabelle ausgewählt hat.
	 * @return true - wenn eine Element ausgewählt wurde.
	 */
	public static boolean checkselect() {
		ObservableList<Model> checkSelected;
		checkSelected = View.table.getSelectionModel().getSelectedItems();
		
		if (checkSelected.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Diese Methode überprüft, ob eine Element aus der Listenansicht der Kategorie ausgewählt wurde.
	 * <br> Falls kein Element ausgewählt wurde, erscheint eine Fehlermeldung.
	 * <br> Falls ein Element ausgewählt wurde, wird die Methode editkatdelButtonClicked() aufgerufen.
	 */
	public static void checkselectkat() {
		ObservableList<String> checkSelected;
		checkSelected = View.listView.getSelectionModel().getSelectedItems();
		
		if (checkSelected.isEmpty()) {
			warnungFenster("Sie haben kein Element ausgewählt!");
//			return true;
		} else {
			editkatdelButtonClicked();
//			return false;
		}
	}
	
	/**
	 * Diese Methode löscht die ausgewählte Kategorie aus der kategorieliste.
	 * <br> Hier wird überprüft, ob die ausgewählte Kategorie noch Produkte enthält. 
	 * Falls ja, kann diese Kategorie nicht gelöscht werden.
	 */
	public static void editkatdelButtonClicked() {
		String delkat = View.listView.getSelectionModel().getSelectedItem();
		
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
	 * Mit dieser Abfrage wird überprüft, ob der Name oder der Platz bereits belegt ist.
	 * @param check - die zu prüfende Eingabe
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
	 * Mit dieser Abfrage wird überprüft, ob der Platz bereits belegt ist.
	 * @param check - der zu prüfende Platz
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
	 * Die eingegebene Kategorie wird in die kategorieliste hinzugefügt, wenn diese nicht bereits vorhanden ist.
	 */
	public static void editkataddButtonClicked() {
		String katadd = View.editkatInput.getText();
		if (katadd.isEmpty()) {
			warnungFenster("Bitte überprüfen Sie Ihre Eingabe!");
		} else if(Model.checkkategorieliste(katadd)){
			warnungFenster("Diese Kategorie existiert bereits!");
		}else {
			Model.kategorieliste.add(katadd);
			View.editkatInput.clear();
			View.listView.getItems().clear();
			View.listView.getItems().addAll(Model.kategorieliste);
		}
	}
	
	/**
	 * Es wird bei dieser Methode das "Kategorie bearbeiten" Fenster geschlossen.
	 * <br> Der Benutzer wird darauf hingewiesen, wenn noch eine Eingabe nicht abgeschlossen ist und gefragt, ob das Fenster geschlossen werden soll.
	 */
	public static void editkatabortButtonClicked() {
		if (View.editkatInput.getText().isEmpty()) {
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
	 * Der Benutzer wird gefragt, ob er sich sicher ist, dass er das Programm schließen will.
	 * @return true - wenn der Benutzer das Fenster schließen will.
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
	 * Der Benutzer wird gefragt, ob er das ausgewählte Element löschen will.
	 * @return true - der Benutzer hat das Löschen bestätigt
	 */
	public static boolean confirmdeletewindow() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sind Sie sich sicher?");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie das ausgewählte Element entfernen?");
		
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
	 * Der Benutzer wird gefragt, ob er das Fenster ohne Speichern Schließen will.
	 * @return true - der Benutzer hat das schließen ohne Speichern bestätigt
	 */
	public static boolean confirmabortwindow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sind Sie sich sicher?");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie das Fenster ohne Speichern schließen?");
		
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
	public static void searchButtonClicked(ChoiceBox<String> mainChoiceBox) {
		searchliste.clear();
//		System.out.println(searchliste);
//		System.out.println("hallo");
		String searchtext = View.SearchInput.getText().toLowerCase();
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
	 * Diese Methode zeigt wieder alle Daten in der Tabelle an.
	 */
	public static void resetButtonClicked() {
		View.SearchInput.clear();
		View.table.setItems(Model.objektliste);
	}
	
	/**
	 * Hier überprüft das Programm, ob noch Fenster offen sind.
	 * @return true - wenn andere Fenster außer dem Hauptfenster noch offen sind
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
