import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimpleControls extends Application {

    private CheckBox button = new CheckBox("Confirmed");
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label userLabel = new Label("User:");
        Label passwordLabel = new Label("Password:");
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
       

        passwordField.disableProperty().bind(
                userNameField.textProperty().isEmpty());
        button.disableProperty().bind(
                userNameField.textProperty().isEmpty()
                        .or(passwordField.textProperty().isEmpty()));

//        button.setOnAction((e) -> System.out.println(userNameField.getText()
//                + "\n" + passwordField.getText()));
        
        button.setOnMouseClicked((e) -> test()

       
        
        );

        TitledPane gridTitlePane = new TitledPane();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Email: "), 0, 2);
        grid.add(new TextField(), 1, 2);        
        gridTitlePane.setText("Title Grid");
        gridTitlePane.setContent(grid);
        gridTitlePane.setExpanded(false);
        
        
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(6);
        gridPane.setVgap(6);
        gridPane.setPadding(new Insets(6));
        gridPane.add(userLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(button, 0, 2);
        gridPane.add(gridTitlePane, 1, 2);
        GridPane.setHalignment(button, HPos.CENTER);

        
        
        
        Scene myScene = new Scene(gridPane);
        primaryStage.setHeight(220.0);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Login");
        primaryStage.show();

    }

    private void test()
    {
        
        // Lambda Runnable
        Runnable task2 = () -> { System.out.println("Task #2 is running"); };
         
        // start the thread
        new Thread(task2).start();  
        
      if (button.isSelected())
      {
          System.out.println("Clicked");
      }
      else {
          System.out.println("Not Clicked");
      }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
