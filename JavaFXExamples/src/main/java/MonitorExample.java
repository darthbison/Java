import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonitorExample extends Application {

    private Button button = new Button("Add Thread");
    private static ThreadPoolExecutor executorPool;
    private static MyMonitorThread monitor;
    private int i = 1;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       

        button.setOnAction((e) -> addThread());
        
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(6);
        gridPane.setVgap(6);
        gridPane.setPadding(new Insets(6));
        gridPane.add(button, 0, 0);
        GridPane.setHalignment(button, HPos.CENTER);

        
        
        
        Scene myScene = new Scene(gridPane);
        primaryStage.setHeight(70.0);
        primaryStage.setScene(myScene);
        //primaryStage.setTitle("Login");
        primaryStage.show();

    }

    
    public static void main(String[] args) {
        
        
        MonitorExample me = new MonitorExample();
        
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = me.new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        executorPool = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //start the monitoring thread
        monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        
        launch(args);
    }
    
    private void addThread() 
    {
        
        
        executorPool.execute(new WorkerThread("Thread "+i));
        
        if (i == 20)
        {
            try {
                Thread.sleep(3000);
                //shut down the pool
                executorPool.shutdown();
                //shut down the monitor thread
                Thread.sleep(5000);
                monitor.shutdown();
                System.exit(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
       
        i++;
    }
    
    class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString() + " is rejected");
        }

    }
    
    class WorkerThread implements Runnable {

        
        private String command;
        
        public WorkerThread(String command)
        {
            this.command = command;
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + command + " has started.");
            
            processCommand();
            
            System.out.println("Name: " + Thread.currentThread().getName() + " End");
            
        }
        
        private void processCommand()
        {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
        @Override
        public String toString() {
            return this.command;
        }

    }
}

class MyMonitorThread implements Runnable {

    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run=true;

    public MyMonitorThread(ThreadPoolExecutor executor, int delay)
    {
        this.executor = executor;
        this.seconds=delay;
    }
    public void shutdown(){
        this.run=false;
    }
    @Override
    public void run()
    {
        while(run){
                System.out.println(
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                        this.executor.getPoolSize(),
                        this.executor.getMaximumPoolSize(),
                        this.executor.getActiveCount(),
                        this.executor.getCompletedTaskCount(),
                        this.executor.getTaskCount(),
                        this.executor.isShutdown(),
                        this.executor.isTerminated()));
                try {
                    Thread.sleep(seconds*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
            
    }
    
}



