package rover;

import java.io.IOException;

import com.leapmotion.leap.Controller;

public class photonRover {

    public static void main(String[] args) 
    {
        MotionListener listener = new MotionListener();
        Controller controller = new Controller();
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Remove the sample listener when done
        controller.removeListener(listener);
    }

}
