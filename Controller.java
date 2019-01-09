package lagerverwaltung;

import java.math.BigDecimal;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class Controller {
	
	public static ObservableList<Model> searchliste = FXCollections.observableArrayList();
	
	public static void saveButtonClicked(ChoiceBox<String> katChoiceBox) {
		String kategorie = katChoiceBox.getValue();
		
			if(View.nameInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie einen Produktnamen ein!");
			} else 
				if (checkalreadyexist(View.nameInput.getText(), 0)) {
					warnungFenster("Ein Produkt mit diesem Namen existiert bereits!");
			} else
				if (!View.nameInput.getText().matches("[A-Za-z0-9\\s]+$")) { // Hier weiter arbeiten!
					warnungFenster("Ein Produktname darf nur Buchstaben und Zahlen enthalten!");
			} else
				if(View.platzInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie einen Produktplatz ein!");
			} else 
				if (checkplatzexist(Integer.parseInt(View.platzInput.getText()))) {
					warnungFenster("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
			} else 
				if(View.preisInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie einen Produktpreis ein!");
			} else 
				if (checkdecimaldigits(new BigDecimal(View.preisInput.getText())) != 2) {
					warnungFenster("Bitte übeprüfen Sie Ihre Eingabe bezüglich des Preises!");
			} else 
				if (!View.preisInput.getText().contains("[0-9]" + ".")) {
				 	warnungFenster("Bitte übeprüfen Sie Ihre Eingabe bezüglich des Preises!");
			} else 
				if(View.anzahlInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie eine Produktanzahl ein!");
			} else 
				if(View.gewichtInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie ein Produkgewicht ein!");
			} else 
				if (kategorie == "Kategorie wählen...") {
					warnungFenster("Bitte wählen Sie eine Produktkategorie aus!");
			} else 
				if(View.eigenschaftenInput.getText().isEmpty()){
					warnungFenster("Bitte geben Sie eine Produkteigenschaft ein!");				
			} else  {
				try {
					Model.objektliste.add(new Model(View.nameInput.getText(), 
					Integer.parseInt(View.platzInput.getText()), 
					new BigDecimal(View.preisInput.getText()), 
					Integer.parseInt(View.anzahlInput.getText()),
					Integer.parseInt(View.gewichtInput.getText()),
					Integer.parseInt("0"),
					kategorie, 
					View.eigenschaftenInput.getText()));
					View.nameInput.clear();
					View.platzInput.clear();
					View.preisInput.clear();
					View.anzahlInput.clear();
					View.gewichtInput.clear();
					View.eigenschaftenInput.clear();
					View.addwindow.close();
				} catch (Exception e2) {
					warnungFenster("Bitte überprüfen Sie Ihre Eingabe!");
				}
			}
		} 
	
	public static void editsaveButtonClicked(ObservableList<Model> productSelected, int alteAnzahl,ChoiceBox<String> editaddChoiceBox, String editgetName, int editgetPlatz) {
		int neueAnzahl = alteAnzahl + Integer.parseInt(View.editanzahlInput.getText());
		String neuerName = View.editnameInput.getText();
		int neuerPlatz = Integer.parseInt(View.editplatzInput.getText());
		if (checkalreadyexist(neuerName, 0) && !neuerName.equals(editgetName)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche oder fehlende Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Ein Produkt mit diesem Namen existiert bereits!");
			alert.showAndWait();
		} else if (checkplatzexist(neuerPlatz) && neuerPlatz != editgetPlatz) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche oder fehlende Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Dieser Produktplatz ist bereits von einem anderen Produkt belegt!");
			alert.showAndWait();
		} else if (neueAnzahl < 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche oder fehlende Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Bitte übeprüfen Sie Ihre Eingabe bezüglich der geänderten Anzahl!");
			alert.showAndWait();
		} else if (checkdecimaldigits(new BigDecimal(View.editpreisInput.getText())) != 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche oder fehlende Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Bitte übeprüfen Sie Ihre Eingabe bezüglich des geänderten Preises!");
			alert.showAndWait();
		} else {
			try {
				String kategorie = editaddChoiceBox.getValue();
				productSelected.get(0).setName(View.editnameInput.getText());
				productSelected.get(0).setPlatz(Integer.parseInt(View.editplatzInput.getText()));
				productSelected.get(0).setPreis(new BigDecimal(View.editpreisInput.getText()));
				if (neueAnzahl < 10) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Geringe Menge");
					alert.setHeaderText(null);
					alert.setContentText("Die Produktanzahl beträgt nur noch " + neueAnzahl +"!");
					alert.showAndWait();
				}
				productSelected.get(0).setAnzahl(neueAnzahl);
				productSelected.get(0).setGewicht(Integer.parseInt(View.editgewichtInput.getText()));
				productSelected.get(0).setKategorie(kategorie);
				productSelected.get(0).setEigenschaften(View.editeigenschaftenInput.getText());
				View.editwindow.close();
				View.table.refresh();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Falsche Eingabe");
				alert.setHeaderText(null);
				alert.setContentText("Bitte überprüfen Sie Ihre Eingabe!");
				alert.showAndWait();
			}
		}
	}
	
	public static void deleteButtonClicked() {
		ObservableList<Model> productSelected, allProducts;
		allProducts = View.table.getItems();
		productSelected = View.table.getSelectionModel().getSelectedItems();
		productSelected.forEach(allProducts::remove);
	}
	
	public static int checkdecimaldigits(BigDecimal bdToCheck) {
		final String s = bdToCheck.toPlainString();
	    final int index = s.indexOf('.');
	    if (index < 0) {
	        return 0;
	    } else {
	    	return s.length() - 1 - index;
	    }
	}
	
	public static boolean checkselect() {
		ObservableList<Model> checkSelected;
		checkSelected = View.table.getSelectionModel().getSelectedItems();
		
		if (checkSelected.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
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
	
	public static void editkatdelButtonClicked() {
		String delkat = View.listView.getSelectionModel().getSelectedItem();
		if (checkalreadyexist(delkat, 1)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Achtung");
			alert.setHeaderText(null);
			alert.setContentText("Mindestens ein Produkt ist noch in dieser Kategorie!");
			alert.showAndWait();
		} else {
			Model.kategorieliste.remove(delkat);
			View.listView.getItems().remove(0,Model.kategorieliste.size());
			View.listView.getItems().addAll(Model.kategorieliste);
			View.listView.getItems().remove(0);
		}
	}
	
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
			
	public static void editkataddButtonClicked() {
		String katadd = View.editkatInput.getText();
		if (katadd.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Bitte überprüfen Sie Ihre Eingabe!");
			alert.showAndWait();
		} else if(Model.checkkategorieliste(katadd)){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falsche Eingabe");
			alert.setHeaderText(null);
			alert.setContentText("Diese Kategorie existiert bereits!");
			alert.showAndWait();
		}else {
			Model.kategorieliste.add(katadd);
			View.editkatInput.clear();
			System.out.println(Model.kategorieliste.size());
			View.listView.getItems().remove(0,Model.kategorieliste.size()-2);
			View.listView.getItems().addAll(Model.kategorieliste);
			View.listView.getItems().remove(0);
		}
	}
	
	public static void editkatabortButtonClicked() {
		if (View.editkatInput.getText().isEmpty()) {
			View.editkatwindow.close();
		} else {
			if (confirmabortwindow()) {
				View.editkatwindow.close();
			}
		}
	}
		
	public static void warnungFenster(String warntext) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warnung!");
		alert.setHeaderText(null);
		alert.setContentText(warntext);
		alert.showAndWait();
	}
	
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

	public static void searchButtonClicked(ChoiceBox<String> mainChoiceBox) {
		searchliste.clear();
		System.out.println(searchliste);
		System.out.println("hallo");
		String searchtext = View.SearchInput.getText().toLowerCase();
		String auswahltext = mainChoiceBox.getValue(); //"Name", "Platz", "Kategorie", "Eigenschaften"
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
			System.out.println(searchliste);
			View.table.setItems(searchliste);
		}
	}
	
	public static void resetButtonClicked() {
		View.SearchInput.clear();
		View.table.setItems(Model.objektliste);
	}
	
	public static boolean checkopenwindow() {
		if (!View.addwindow.isShowing() && !View.editwindow.isShowing() && !View.editkatwindow.isShowing()) {
			return true;
		} else {
			warnungFenster("Achtung! Sie haben noch offene Fenster!");
			return false;
		}
	}
	
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
