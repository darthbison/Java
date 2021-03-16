import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ZoomCircles extends Application {

    
    public static void main(final String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 350, 300);
        primaryStage.setTitle("Dots");
        primaryStage.setScene(scene);

        Circle circle = new Circle(175, 150, 10, Color.BLUE);
        addMouseScrolling(circle);
        root.getChildren().add(circle);
        
        scene.setOnScroll(
                new EventHandler<ScrollEvent>() {
                  @Override
                  public void handle(ScrollEvent event) {
                    double zoomFactor = 1.05;
                    double deltaY = event.getDeltaY();
                    if (deltaY < 0){
                      zoomFactor = 2.0 - zoomFactor;
                    }
                    System.out.println(deltaY);
                    event.consume();
                  }
                });
        

        primaryStage.show();
    }

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            node.setScaleX(node.getScaleX() * zoomFactor);
            node.setScaleY(node.getScaleY() * zoomFactor);
        });
    }
}