package tablecell;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * View-Controller for the person table.
 * 
 * @author Marco Jakob
 */
public class PersonTableController {
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private TableColumn<Person, LocalDate> birthdayColumn;
	
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	/**
	 * The constructor (is called before the initialize()-method).
	 */
	public PersonTableController() {
		// Add some sample data.
		personData.add(new Person("Hans", "Muster", LocalDate.of(2012, 3, 22)));
		personData.add(new Person("Ruth", "Mueller", LocalDate.of(2012, 4, 2)));
		personData.add(new Person("Heinz", "Kurz", LocalDate.of(2011, 3, 22)));
		personData.add(new Person("Cornelia", "Meier", LocalDate.of(2012, 6, 13)));
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
		
		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		
		// Custom rendering of the table cell.
//		birthdayColumn.setCellFactory(column -> {
//			return new TableCell<Person, LocalDate>() {
//				@Override
//				protected void updateItem(LocalDate item, boolean empty) {
//					super.updateItem(item, empty);
//					
//					if (item == null || empty) {
//						setText(null);
//						setStyle("");
//					} else {
//						// Format date.
//					    
//					    TableRow currentRow = getTableRow();
//					    currentRow.setStyle("-fx-background-color: #00ffff");
//						
//					    setText(myDateFormatter.format(item));
//						//setStyle("-fx-background-color: #ffffff");
//						//setStyle("-fx-background-color: none");
//						// Style all dates in March with a different color.
//						if (item.getMonth() == Month.MARCH) {
//							setTextFill(Color.CHOCOLATE);
//							setStyle("-fx-background-color: yellow");
//						} else {
//							setTextFill(Color.BLACK);
//							setStyle("");
//						}
//					}
//				}
//			};
	//	});
		
		// Add data to the table
		personTable.setItems(personData);
		//getTableViewValues(personTable);
	}
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<String> getTableViewValues(TableView tableView) {
	    ArrayList<String> values = new ArrayList<>();
	   
        ObservableList<TableColumn> columns = tableView.getColumns();

        int i = 0;
	    for (Object row : tableView.getItems()) {
	     
	        
	        
	       for (TableColumn column : columns) {
	        
	           
//	          System.out.println(column.
//              getCellObservableValue(row).
//              getValue().toString());
	          
//	          values.add(
//	          (String) column.
//	          getCellObservableValue(row).
//	          getValue());
	      }
	       i++;
	    }

	    personTable.setRowFactory(tv -> new TableRow<Person>() {
            @Override
            public void updateItem(Person item, boolean empty) {
                super.updateItem(item, empty) ;
                
                //System.out.println("ID: " + tv.getId());
                
                setStyle("-fx-background-color: #00FF00");
//                try {
//                    Thread.sleep(5000);
//                    setStyle("-fx-background-color: none");
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                
//              if (item == null) {
//                  setStyle("");
//              } else if (item.getInstrumentId().equals("1070")) {
//                  setStyle("-fx-background-color: tomato;");
//              } else {
//                  setStyle("");
//              }
            }
        });
	    
	    
	    System.out.println("Rows: " + i);
	    return values;
	  }
	
	
}