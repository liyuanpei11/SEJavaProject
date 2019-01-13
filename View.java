package lagerverwaltung;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Die Class View ist nur für die visuelle Darstellung des Programms zuständig.
 * <br> Um alle nötigen Funktionen von JavaFX nutzen zu können, muss man die Class View um die JavaFx Class Application erweitern.
 * <br>Hier werden alle Fenster, Textfelder und Knöpfe im vorhinein festgelegt. 
 * <br>Die Daten für die Tabellen oder Textfelder bezieht die View Class direkt von der Model Class.
 * <br>Alle anderen aktiven Bedienungen des Benutzers werden an die Controller Class übergeben und dort weiter verarbeitet.
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


public class View extends Application {
	
	//Hauptfenster
	Stage window;
	static TableView<Model> table;
	static TextField SearchInput;
	public static ChoiceBox<String> mainChoiceBox = new ChoiceBox<>();
	
	//"Neues Element hinzufügen" - Fenster
	public static Stage addwindow = new Stage();
	public static ChoiceBox<String> addChoiceBox = new ChoiceBox<>();
	static TextField nameInput;
	static TextField platzInput;
	static TextField preisInput;
	static TextField anzahlInput;
	static TextField gewichtInput;
	static TextField kategorieInput;
	static TextField eigenschaftenInput;
	
	//"Element bearbeiten" - Fenster
	public static Stage editwindow = new Stage();
	static TextField editnameInput;
	static TextField editplatzInput;
	static TextField editpreisInput;
	static TextField editanzahlInput;
	static TextField editgewichtInput;
	static TextField editkategorieInput;
	static TextField editeigenschaftenInput;
	public static ChoiceBox<String> editkatChoiceBox = new ChoiceBox<>();
	
	//"Kategorien bearbeiten" Fenster
	public static Stage editkatwindow = new Stage();
	static ListView<String> listView;
	static TextField editkatInput;
	static TableView<String> editkattable;

	
	/**
	 * In dieser Methode wird das Hauptfenster initialisert. 
	 * <br> Das Hauptfenster verbindet alle grafischen Fenster miteinander, welche über Buttons aufgerufen werden können.
	 * <br>Hier werden die Daten aus der Datenbank mithilfe der Tabelle dargestellt.
	 * <br>Desweiteren kann man eine Suche nach beliebiger Eingabe starten.
	 * <br> (at)SuppressWarnings("unchecked") dient dazu dar, um eine Warnung von Java zu unterdrücken, welche jedoch keinen Einfluss auf das Programm hat.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void start(Stage primaryStage) {
		Model.readcsv("penfactory.csv");
		try {
			window = primaryStage;
			window.setTitle("Penfactory - Datenbank");
			window.resizableProperty().setValue(Boolean.FALSE);
			window.setOnCloseRequest(e -> {
				e.consume();
				if (Controller.checkopenwindow() && Controller.confirmclosewindow()) {
					window.close();
				}
			});
			
			// Tabellenspalten
			
			//name column
			TableColumn<Model, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setMinWidth(200);
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

			//platz column
			TableColumn<Model, Integer> platzColumn = new TableColumn<>("Platz");
			platzColumn.setMinWidth(100);
			platzColumn.setCellValueFactory(new PropertyValueFactory<>("platz"));

			//preis column
			TableColumn<Model, BigDecimal> preisColumn = new TableColumn<>("Preis (€)");
			preisColumn.setMinWidth(100);
			preisColumn.setCellValueFactory(new PropertyValueFactory<>("preis"));

			//anzahl column
			TableColumn<Model, Integer> anzahlColumn = new TableColumn<>("Anzahl");
			anzahlColumn.setMinWidth(100);
			anzahlColumn.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
			
			//gewicht column
			TableColumn<Model, Integer> gewichtColumn = new TableColumn<>("Gewicht (g)");
			gewichtColumn.setMinWidth(100);
			gewichtColumn.setCellValueFactory(new PropertyValueFactory<>("gewicht"));
			
			//gesamtgewicht column
			TableColumn<Model, Integer> gesamtgewichtColumn = new TableColumn<>("Gesamtgewicht (g)");
			gesamtgewichtColumn.setMinWidth(150);
			gesamtgewichtColumn.setCellValueFactory(new PropertyValueFactory<>("gesamtgewicht"));

			//kategorie column
			TableColumn<Model, String> kategorieColumn = new TableColumn<>("Kategorie");
			kategorieColumn.setMinWidth(150);
			kategorieColumn.setCellValueFactory(new PropertyValueFactory<>("kategorie"));

			//eigenschaften column
			TableColumn<Model, String> eigenschaftenColumn = new TableColumn<>("Eigenschaften");
			eigenschaftenColumn.setMinWidth(200);
			eigenschaftenColumn.setCellValueFactory(new PropertyValueFactory<>("eigenschaften"));
			
			// Hinzufügen - Knopf
			Button addButton = new Button("Hinzufügen");
			addButton.setOnAction(e -> addwindow());
			
			// Bearbeiten - Knopf
			Button editButton = new Button("Bearbeiten");
			editButton.setOnAction(e -> {
				
				ObservableList<Model> checkSelected;
				checkSelected = table.getSelectionModel().getSelectedItems();
				
				if (!Controller.checkselect(checkSelected)) {	
					Controller.warnungFenster("Sie haben kein Element ausgewählt!");
				} else {
					editwindow();
				}
			});
			
			// Kategorie bearbeiten - Knopf
			Button editkatButton = new Button("Kategorien bearbeiten");
			editkatButton.setOnAction(e -> editkatwindow());
			
			// Löschen - Knopf
			Button deleteButton = new Button("Löschen");
			deleteButton.setOnAction(e -> {
				
				ObservableList<Model> checkSelected;
				checkSelected = table.getSelectionModel().getSelectedItems();
				
				if (!Controller.checkselect(checkSelected)) {
					Controller.warnungFenster("Sie haben kein Element ausgewählt!");
				} else if (Controller.confirmdeletewindow()) {
					ObservableList<Model> productSelected, allProducts;
					allProducts = table.getItems();
					productSelected = table.getSelectionModel().getSelectedItems();
					Controller.deleteButtonClicked(allProducts, productSelected);
				}
			});
			
			// Beenden - Knopf
			Button closeButton = new Button("Beenden");
			closeButton.setOnAction(e -> {
				if (Controller.checkopenwindow() && Controller.confirmclosewindow()) {
					window.close();
				}
			});
			
			//set up table
			table = new TableView<>();
			table.setItems(Model.objektliste);
			table.getColumns().addAll(						
									nameColumn,
									platzColumn,
									preisColumn,
									anzahlColumn,
									gewichtColumn,
									gesamtgewichtColumn,
									kategorieColumn,
									eigenschaftenColumn); 	
			
			mainChoiceBox.getItems().addAll("Name", "Platz", "Preis", "Anzahl", "Gewicht", "Kategorie", "Eigenschaften");
			mainChoiceBox.setValue("Name");
			
			// Suchleiste
			SearchInput = new TextField();
			SearchInput.setPromptText("Suchleiste");
			SearchInput.setPrefWidth(200);
			
			// Suchen - Knopf
			Button searchButton = new Button("Suchen");
			searchButton.setOnAction(e -> Controller.searchButtonClicked(mainChoiceBox, SearchInput.getText().toLowerCase()));
			
			// Beenden - Knopf
			Button resetButton = new Button("Zurücksetzen");
			resetButton.setOnAction(e -> Controller.resetButtonClicked());
			
			// Virtuelle Box wo alle Elemente horizontal nebeneinander dargestellt werden
			HBox hBox2 = new HBox(addButton,editButton,editkatButton,deleteButton,closeButton);
			hBox2.setSpacing(10); //Platz zwischen den Elementen
			hBox2.setAlignment(Pos. CENTER_RIGHT);
			HBox.setHgrow(hBox2, Priority.ALWAYS);
			
			// Virtuelle Box wo alle Elemente horizontal nebeneinander dargestellt werden
			HBox hBox3 = new HBox(mainChoiceBox, SearchInput, searchButton, resetButton);
			hBox3.setSpacing(10); //Platz zwischen den Elementen
			hBox3.setAlignment(Pos. CENTER_LEFT);
			HBox.setHgrow(hBox3, Priority.ALWAYS);
			
			// Virtuelle Box um eine geordnet Ansicht zu erreichen
			HBox hBox1 = new HBox();
			hBox1.setPadding(new Insets(10,10,0,10)); //rand um die HBox
			hBox1.setSpacing(10); //Platz zwischen den Elementen
			hBox1.getChildren().addAll(hBox3, hBox2);
			
			// Virtuelle Box wo die Tabelle über die HBox 1 platziert wird
			VBox vBox = new VBox();
			vBox.getChildren().addAll(table,hBox1);
			
			Scene scene = new Scene(vBox);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Diese Methode initiert das "Eintrag hinzufügen" Fenster. 
	 * <br> Der Benutzer kann hier die Produkteigenschaften eingeben und in die Datenbank abspeichern.
	 */
	public static void addwindow() {
		
		// Eintrag hinzufügen - Fenster
		if (!addwindow.isShowing()) {
			addwindow.setTitle("Penfactory - Dateneintrag erstellen");
			addwindow.setWidth(400);
			addwindow.resizableProperty().setValue(Boolean.FALSE);
			addwindow.setOnCloseRequest(e -> {
				e.consume();
				if (Controller.confirmabortwindow()) {
					addwindow.close();
				}
			});
			
			Text addscenetitle = new Text("Neuer Eintrag");
			addscenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			
			Label addlabel1 = new Label();
			addlabel1.setText("Produktname:");
			//name input
			nameInput = new TextField();
			nameInput.setPromptText("Produktname");
			nameInput.setPrefWidth(100);
	
			
			Label addlabel2 = new Label();
			addlabel2.setText("Produktplatz:");	
			//platz input
			platzInput = new TextField();
			platzInput.setPromptText("Produktplatz");
			platzInput.setPrefWidth(100);
			
			
			Label addlabel3 = new Label();
			addlabel3.setText("Produktpreis (€):");
			//preis input
			preisInput = new TextField();
			preisInput.setPromptText("Produktpreis (€) z.B. 2.00 ");
			preisInput.setPrefWidth(100);
			
			Label addlabel4 = new Label();
			addlabel4.setText("Produktanzahl:");
			//anzahl input
			anzahlInput = new TextField();
			anzahlInput.setPromptText("Produktanzahl z.B. 2");
			anzahlInput.setPrefWidth(100);
			
			
			Label addlabel5 = new Label();
			addlabel5.setText("Produktgewicht (g):");
			//Gewicht input
			gewichtInput = new TextField();
			gewichtInput.setPromptText("Produktgewicht (g) z.B. 2");
			gewichtInput.setPrefWidth(120);
			
			
			Label addlabel6 = new Label();
			addlabel6.setText("Produktkategorie:");
			//kategorie input
			//get items from the kategorielist
			System.out.println(Model.kategorieliste);
			addChoiceBox.getItems().clear();
			addChoiceBox.getItems().add("Kategorie wählen...");
			addChoiceBox.getItems().addAll(Model.kategorieliste);
			addChoiceBox.setValue("Kategorie wählen...");
			addChoiceBox.setMinWidth(200);
	
			
			Label addlabel7 = new Label();
			addlabel7.setText("Produkteigenschaften:");
			//eigenschaften input
			eigenschaftenInput = new TextField();
			eigenschaftenInput.setPromptText("Produkteigenschaften");
			eigenschaftenInput.setPrefWidth(150);
			
			// Speichern Knopf
			Button saveButton = new Button("Hinzufügen");
			saveButton.setOnAction(e -> {
				if (Controller.checkInput(nameInput, platzInput, preisInput, anzahlInput, gewichtInput, eigenschaftenInput)) {
						Controller.saveButtonClicked(
								addChoiceBox,
								nameInput.getText(),
								Integer.parseInt(platzInput.getText()),
								new BigDecimal(preisInput.getText()),
								Integer.parseInt(anzahlInput.getText()),
								Integer.parseInt(gewichtInput.getText()),
								eigenschaftenInput.getText()
								);
				}
			});
			
			// Abbrechen Knopf
			Button abortButton = new Button("Abbrechen");
			abortButton.setOnAction(e -> {
				if (Controller.confirmabortwindow()) {
					addwindow.close();
				}
			});
			
			// Virtuelle Box für die Knöpfe
			HBox addhBox = new HBox();
			addhBox.setPadding(new Insets(10,10,10,10)); //rand um die HBox
			addhBox.setSpacing(10); //Platz zwischen den Elementen
			addhBox.setAlignment(Pos.BOTTOM_RIGHT);
			addhBox.getChildren().addAll(saveButton, abortButton);
			
			// Ein Gitternetz um die Labels und Texteingabefelder zu sortieren und darzustellen
			GridPane addgrid = new GridPane();
			addgrid.setAlignment(Pos.CENTER);
			addgrid.setHgap(10);
			addgrid.setVgap(10);
			addgrid.setPadding(new Insets(10,10,10,10));
			addgrid.add(addscenetitle, 0, 0, 2, 1);
			addgrid.add(addlabel1, 0, 1);
			addgrid.add(nameInput, 1, 1);
			addgrid.add(addlabel2, 0, 2);
			addgrid.add(platzInput, 1, 2);
			addgrid.add(addlabel3, 0, 3);
			addgrid.add(preisInput, 1, 3);
			addgrid.add(addlabel4, 0, 4);
			addgrid.add(anzahlInput, 1, 4);
			addgrid.add(addlabel5, 0, 5);
			addgrid.add(gewichtInput, 1, 5);
			addgrid.add(addlabel6, 0, 6);
			addgrid.add(addChoiceBox, 1, 6);
			addgrid.add(addlabel7, 0, 7);
			addgrid.add(eigenschaftenInput, 1, 7);
			addgrid.add(addhBox, 1, 8);
			
			BorderPane addBorderPane = new BorderPane();
			addBorderPane.setCenter(addgrid);
			Scene addscene = new Scene(addBorderPane, 500, 400);
			addwindow.setScene(addscene);
			addwindow.show();
		} else {
			Controller.warnungFenster("Bitte schließen Sie erst das erste 'Eintrag hinzufügen' - Fenster!");
		}
	}
	
	/**
	 * Mit dieser Methode wird das "Eintrag bearbeiten" Fenster erstellt. 
	 * <br> Das Fenster zeigt die Daten des ausgewählten Produktes an. 
	 * <br>Hier kann der Benutzer beliebige Änderungen durchführen.
	 */
	public static void editwindow() {
		
		// Eine Liste 
		if (!editwindow.isShowing()) {
			ObservableList<Model> productSelected;
			productSelected = View.table.getSelectionModel().getSelectedItems();
			String editgetselectedName = productSelected.get(0).getName();
			int editgetselectedPlatz = productSelected.get(0).getPlatz();
	
			editwindow.setTitle("Penfactory - Dateneintrag bearbeiten");
			editwindow.setWidth(400);
			editwindow.resizableProperty().setValue(Boolean.FALSE);
			editwindow.setOnCloseRequest(e -> {
				e.consume();
				if (Controller.confirmabortwindow()) {
					editwindow.close();
				}
			});
			
			Text editscenetitle = new Text("Eintrag bearbeiten");
			editscenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	
			
			Label editlabel1 = new Label();
			editlabel1.setText("Produktname:");
			//name input
			editnameInput = new TextField();
			editnameInput.setText(productSelected.get(0).getName());
			editnameInput.setPrefWidth(100);
	
			
			Label editlabel2 = new Label();
			editlabel2.setText("Produktplatz:");	
			//platz input
			editplatzInput = new TextField();
			editplatzInput.setText(String.valueOf(productSelected.get(0).getPlatz()));
			editplatzInput.setPrefWidth(100);
			
			
			Label editlabel3 = new Label();
			editlabel3.setText("Produktpreis (€):");
			//preis input
			editpreisInput = new TextField();
			editpreisInput.setText(String.valueOf(productSelected.get(0).getPreis()));
			editpreisInput.setPrefWidth(100);
			
			Label editlabel4 = new Label();
			editlabel4.setText("Produktanzahländerung:");
			//anzahl input
			editanzahlInput = new TextField();
			editanzahlInput.setText("0");
			editanzahlInput.setPrefWidth(100);
			int alteAnzahl = productSelected.get(0).getAnzahl();
			
			
			Label editlabel5 = new Label();
			editlabel5.setText("Produktgewicht (g):");
			//Gewicht input
			editgewichtInput = new TextField();
			editgewichtInput.setText(String.valueOf(productSelected.get(0).getGewicht()));
			editgewichtInput.setPrefWidth(120);
			
			
			Label editlabel6 = new Label();
			editlabel6.setText("Produktkategorie:");
			//kategorie input
			ChoiceBox<String> editaddChoiceBox = new ChoiceBox<>();
			//get items from the kategorielist
			editaddChoiceBox.getItems().addAll(Model.kategorieliste);
			editaddChoiceBox.setValue(productSelected.get(0).getKategorie());
			editaddChoiceBox.setMinWidth(200);
	
			
			Label editlabel7 = new Label();
			editlabel7.setText("Produkteigenschaften:");
			//eigenschaften input
			editeigenschaftenInput = new TextField();
			editeigenschaftenInput.setText(productSelected.get(0).getEigenschaften());
			editeigenschaftenInput.setPrefWidth(150);
			
			//Buttons
			Button editsaveButton = new Button("Speichern");
			editsaveButton.setOnAction(e -> {
				if (Controller.checkInput(editnameInput, editplatzInput, editpreisInput, editanzahlInput, editgewichtInput, editeigenschaftenInput)) {
						Controller.editsaveButtonClicked(
								productSelected, 
								alteAnzahl, 
								editaddChoiceBox, 
								editgetselectedName, 
								editgetselectedPlatz,
								editnameInput.getText(),
								Integer.parseInt(editplatzInput.getText()),
								new BigDecimal(editpreisInput.getText()),
								Integer.parseInt(editanzahlInput.getText()),
								Integer.parseInt(editgewichtInput.getText()),
								editeigenschaftenInput.getText()
								);
				}	
			});
			
			Button editabortButton = new Button("Abbrechen");
			editabortButton.setOnAction(e -> {
				if (Controller.confirmabortwindow()) {
					editwindow.close();
				}
			});
			
			HBox edithBox = new HBox();
			edithBox.setPadding(new Insets(10,10,10,10)); //rand um die HBox
			edithBox.setSpacing(10); //Platz zwischen den Elementen
			edithBox.setAlignment(Pos.BOTTOM_RIGHT);
			edithBox.getChildren().addAll(editsaveButton, editabortButton);
			
			GridPane editgrid = new GridPane();
			editgrid.setAlignment(Pos.CENTER);
			editgrid.setHgap(10);
			editgrid.setVgap(10);
			editgrid.setPadding(new Insets(10,10,10,10));
			editgrid.add(editscenetitle, 0, 0, 2, 1);
			editgrid.add(editlabel1, 0, 1);
			editgrid.add(editnameInput, 1, 1);
			editgrid.add(editlabel2, 0, 2);
			editgrid.add(editplatzInput, 1, 2);
			editgrid.add(editlabel3, 0, 3);
			editgrid.add(editpreisInput, 1, 3);
			editgrid.add(editlabel4, 0, 4);
			editgrid.add(editanzahlInput, 1, 4);
			editgrid.add(editlabel5, 0, 5);
			editgrid.add(editgewichtInput, 1, 5);
			editgrid.add(editlabel6, 0, 6);
			editgrid.add(editaddChoiceBox, 1, 6);
			editgrid.add(editlabel7, 0, 7);
			editgrid.add(editeigenschaftenInput, 1, 7);
			editgrid.add(edithBox, 1, 8);
			
			BorderPane editBorderPane = new BorderPane();
			editBorderPane.setCenter(editgrid);
			
			Scene editscene = new Scene(editBorderPane, 600, 400);
			editwindow.setScene(editscene);
			editwindow.show();
		} else {
			Controller.warnungFenster("Bitte schließen Sie das erste 'Eintrag bearbeiten' - Fenster!");
		}
	}
	
	/**
	 * Mit dieser Methode wird das "Kategorie bearbeiten" Fenster gestartet.
	 * <br> Der Benutzer kann hier alle Kategorien einsehen, entfernen und neue hinzufügen.
	 */
	public static void editkatwindow() {
		
		//Kategorien Bearbeiten - Fenster
		
		editkatwindow.setTitle("Penfactory - Kategorien bearbeiten");
		editkatwindow.setWidth(400);
		editkatwindow.resizableProperty().setValue(Boolean.FALSE);
		editkatwindow.setOnCloseRequest(e -> {
			e.consume();
			if (Controller.confirmabortwindow()) {
				editkatwindow.close();
			}
		});
		
		listView = new ListView<>();
		listView.getItems().addAll(Model.kategorieliste);
		listView.setPrefHeight(150);
		listView.getItems().sort(null);
		
		// Fenster Überschrift
		Text editkatscenetitle = new Text("Kategorien bearbeiten");
		editkatscenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		// Schließen Knopf
		Button editkatabortButton = new Button("Schließen");
		editkatabortButton.setOnAction(e -> Controller.editkatabortButtonClicked(editkatInput.getText()));
		
		// Hinzufügen Knopf
		Button editkataddButton = new Button("Hinzufügen");
		editkataddButton.setOnAction(e -> Controller.editkataddButtonClicked(editkatInput.getText()));
		editkataddButton.setMinWidth(150);
		
		Button editkatdelButton = new Button("Löschen");
		editkatdelButton.setOnAction(e -> {
			String selectedKat;
			selectedKat = listView.getSelectionModel().getSelectedItem();
			if (Controller.checkselectkat(selectedKat)) {
				Controller.editkatdelButtonClicked(selectedKat);
			}
		});
		editkatdelButton.setMinWidth(150);

		// Eingabefeld für neue Kategorie
		editkatInput = new TextField();
		editkatInput.setPromptText("Neue Kategorie");
		editkatInput.setMinWidth(150);
		editkatInput.setAlignment(Pos.CENTER);
		
		HBox editkatBox = new HBox();
		editkatBox.setPadding(new Insets(10,10,10,10)); //rand um die HBox
		editkatBox.setSpacing(10); //Platz zwischen den Elementen
		editkatBox.setAlignment(Pos.CENTER);
		editkatBox.getChildren().addAll(editkatabortButton);
		
		GridPane editkatgrid = new GridPane();
		editkatgrid.setAlignment(Pos.CENTER);
		editkatgrid.setHgap(10);
		editkatgrid.setVgap(10);
		editkatgrid.setPadding(new Insets(10,10,10,10));
		
		GridPane editkatgrid2 = new GridPane();
		editkatgrid2.setAlignment(Pos.CENTER);
		editkatgrid2.setHgap(10);
		editkatgrid2.setVgap(10);
		
		editkatgrid.add(editkatscenetitle, 0, 0, 2, 1);
		editkatgrid.add(listView, 0, 1);
		editkatgrid.add(editkatgrid2, 1, 1);
		editkatgrid2.add(editkatInput, 0, 1);
		editkatgrid2.add(editkataddButton, 0, 2);
		editkatgrid2.add(editkatdelButton, 0, 3);
		editkatgrid2.add(editkatBox, 0, 4);
		
		BorderPane editkatBorderPane = new BorderPane();
		editkatBorderPane.setCenter(editkatgrid);
		Scene editkatscene = new Scene(editkatBorderPane, 300, 250);
		editkatwindow.setScene(editkatscene);
		editkatwindow.show();
	}
	
	/**
	 * Mit der main Methode wird das Hauptfenster gestartet und nach Beendigung des Programms die Model.writecsv() Methode aufgerufen.
	 * @param args - command - line Argumente die an das Programm übergeben werden.
	 */
	public static void main(String[] args) {
		
		launch(args);
		Model.writecsv("penfactory.csv");
	}
	
}














