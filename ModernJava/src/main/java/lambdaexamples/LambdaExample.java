package lambdaexamples;

import java.awt.*;

public class LambdaExample {

    public void runExample() {
        Frame frame=new Frame("ActionListener java8");

        Button b=new Button("Click Here");
        b.setBounds(50,100,80,50);

        b.addActionListener(e -> System.out.println("Hello World!"));
        frame.add(b);

        frame.setSize(200,200);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
