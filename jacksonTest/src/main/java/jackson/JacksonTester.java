package jackson;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

public class JacksonTester {
    public static void main(String args[]) {

        //ObjectMapper objectMapper = new ObjectMapper();

        String carJson =
                "{ \"brand\" : \"Mercedes\", " +
                        "\"doors\" : 5, " +
                        "\"color\" : \"black\" }";

        Reader reader = new StringReader(carJson);

        byte[] bytes = null;

        try {
            bytes = carJson.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {


            SimpleModule module =
                    new SimpleModule("CarDeserializer");
            module.addDeserializer(Car.class, new CarDeserializer(Car.class));

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(module);

            Car car = mapper.readValue(carJson, Car.class);

            StringBuilder sb = new StringBuilder();

            sb.append("car brand = " + car.getBrand() + "\n");
            sb.append("car doors = " + car.getDoors() + "\n");
            sb.append("car color = " + car.getColor());


            System.out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


