package rover;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

public class MotionListener extends Listener {

    private static final String accessToken = "";
    private static final String deviceID = "";

    private static final int totalHands = 1;
    private static final int forward = 5;
    private static final int left = 1;
    private static final int right = 2;
    private static final int back = 4;
   
    private static String controlURL;

    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    @Override
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        int hands = frame.hands().count();
        int fingers = frame.fingers().count();

        determineMovement(hands, fingers);
    }

    private static void determineMovement(int hands, int fingers) {
        StringBuilder url = new StringBuilder();
        url.append("https://api.spark.io/v1/devices/");
        url.append(deviceID);
        url.append("/control");

        controlURL = url.toString();

        if (hands == totalHands) {
            switch (fingers) {
            case forward:
                System.out.println("Forward");
                executeCommand("F-100");
                break;
            case right:
                System.out.println("Right");
                executeCommand("R-50");
                break;
            case left:
                System.out.println("Left");
                executeCommand("L-50");
                break;
            case back:
                System.out.println("Reverse");
                executeCommand("B-75");
                break;
            default:
                System.out.println("Stop");
                executeCommand("S");
                break;

            }
        }
        else {
            executeCommand("S");
        }
    }

    private static void executeCommand(String command) {
        URL url;
        try {
            url = new URL(controlURL);

            HttpURLConnection hConnection = (HttpURLConnection) url
                    .openConnection();
            HttpURLConnection.setFollowRedirects(true);

            hConnection.setDoOutput(true);
            hConnection.setRequestMethod("POST");

            PrintStream ps = new PrintStream(hConnection.getOutputStream());
            ps.print("params=" + command + "&access_token=" + accessToken);
            ps.close();

            hConnection.connect();
            
            hConnection.getResponseCode();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
