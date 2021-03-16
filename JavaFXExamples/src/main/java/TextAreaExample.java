import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TextAreaExample extends Application {

   
        public static void main(String[] args) {
            Application.launch(args);
        }
        
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Borders");
            Group root = new Group();
            Scene scene = new Scene(root, 600, 330, Color.WHITE);
            
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            
            final TextArea cssEditorFld = new TextArea();
            cssEditorFld.setPrefRowCount(5);
            cssEditorFld.setPrefColumnCount(50);
            cssEditorFld.setWrapText(true);
            cssEditorFld.setEditable(false);
            cssEditorFld.setPrefWidth(100);
            GridPane.setHalignment(cssEditorFld, HPos.CENTER);
            gridpane.add(cssEditorFld, 0, 1);

            String cssDefault = "line1;\nline2;\n";
            
            cssEditorFld.setText(cssDefault);
            
            root.getChildren().add(gridpane);        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
        
        public void keyPressed(KeyEvent event) {
            System.out.println("Hello");
        }
    

}
