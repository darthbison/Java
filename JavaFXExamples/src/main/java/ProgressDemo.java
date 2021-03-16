import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ProgressDemo extends Application {

   
    
    public static Future<Integer> portIsOpen(final ExecutorService es,
            final String ip, final int port, final int timeout) {
        return es.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return port;
                } catch (Exception ex) {
                    return 0;
                }
            }
        });
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        @SuppressWarnings("rawtypes")
        Task task = new Task<Void>() {
            @Override public Void call() throws Exception {

                //String localHost = InetAddress.getLocalHost().getHostName();
                String localAddress = InetAddress.getLocalHost().getHostAddress();
                ExecutorService es = Executors.newFixedThreadPool(20);
                
                String subnet = localAddress.replace(localAddress.substring(
                        localAddress.lastIndexOf("."), localAddress.length()), "");

                int timeout = 1000;
                for (int i = 1; i < 255; i++) {
                    String host = subnet + "." + i;
                    updateProgress(i, 255);
                    if (InetAddress.getByName(host).isReachable(timeout)) {
                        if (host.equals("10.1.10.216")) {

                            List<Future<Integer>> futures = new ArrayList<>();
                            for (int port = 1; port <= 65535; port++) {
                                futures.add(portIsOpen(es, host, port, timeout));
                            }
                            es.shutdown();

                            for (final Future<Integer> f : futures) {

                                if (f.get() == 22)
                                {
                                    System.out.println("SSH is enabled on " + host);
                                    updateProgress(255, 255);
                                }
                                  

                            }
                            break;

                        }
                    }
                }
               
                return null;
            }
        };
        
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.progressProperty().bind(task.progressProperty());
        
        ProgressBar progressBar = new ProgressBar();
        progressBar.progressProperty().bind(task.progressProperty());
        
        
        Button button = new Button("Start");
        button.setOnAction((e) -> Executors.newSingleThreadExecutor().execute(task));
        
        VBox box = new VBox(progressIndicator, progressBar, button);
        box.setSpacing(12);
        box.setPadding(new Insets(12));
        box.setAlignment(Pos.CENTER);
        
        Scene myScene = new Scene(box);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Progress");
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
