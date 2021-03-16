import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

class Test extends StackPane {
  
  private double pressedX, pressedY;
  private LongProperty frame = new SimpleLongProperty();

  public Test() {
    setMinSize(600, 600);
    setStyle("-fx-border-color: blue;");
    Label count = new Label();
    count.textProperty().bind(Bindings.convert(frame));
    getChildren().add(count);
    count.setMouseTransparent(true);

    setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        pressedX = event.getX();
        pressedY = event.getY();
      }
    });

    setOnMouseDragged(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        setTranslateX(getTranslateX() + event.getX() - pressedX);
        setTranslateY(getTranslateY() + event.getY() - pressedY);

        event.consume();
      }
    });

  }
}

public class PanningTest extends Application {
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) {
    stage.setScene(new Scene(new Test()));
    stage.show();
  }
}