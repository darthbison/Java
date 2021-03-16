
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ColoredToggle extends Application {
 
  private boolean selected = false;  
    
  public static void main(String[] args) { Application.launch(ColoredToggle.class, args); }

  @Override public void start(Stage stage) {
    ToggleButton visibilityControl = new ToggleButton("Winterfell");

    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().setAll(visibilityControl);

    layout.getStylesheets().add(getClass().getResource("colored-toggle.css").toExternalForm());

    visibilityControl.setOnAction((e) -> toggleSelected());
    
    
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  private void toggleSelected()
  {
      if (selected)
          selected = false;
      else
          selected = true;
      
      System.out.println("Selected: " + selected);
  }
}