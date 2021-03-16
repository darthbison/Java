
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeViewExample extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
        
    @Override
    public void start(Stage stage) 
    {
        // Create the TreeViewHelper
        TreeViewHelper helper = new TreeViewHelper();
        // Get the Products
        ArrayList<TreeItem> products = helper.getProducts();
        
        // Create the TreeView
        TreeView treeView = new TreeView();
        // Create the Root TreeItem
        TreeItem rootItem = new TreeItem("Vehicles");
        // Add children to the root
        rootItem.getChildren().addAll(products);
        // Set the Root Node
        treeView.setRoot(rootItem);
        
        // Create the VBox
        VBox root = new VBox();
        // Add the TreeView to the VBox
        root.getChildren().add(treeView);       
        
        // Create the Scene
        Scene scene = new Scene(root,400,400);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title for the Scene
        stage.setTitle("TreeView Example");
        // Display the stage
        stage.show();       
    }
}
