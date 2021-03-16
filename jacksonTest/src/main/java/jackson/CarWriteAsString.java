package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CarWriteAsString {

    public static void main(String[] args) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        Car car = new Car();

        CarSerializer carSerializer = new CarSerializer(Car.class);


        SimpleModule module =
                new SimpleModule("CarSerializer");
        module.addSerializer(Car.class, carSerializer);

        objectMapper.registerModule(module);


        car.setBrand("BMW");
        car.setDoors(4);
        car.setColor("blue");

        String json = objectMapper.writeValueAsString(car);
        System.out.println(json);
    }
}
