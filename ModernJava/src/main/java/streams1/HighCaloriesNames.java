package streams1;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static streams1.Dish.menu;

public class HighCaloriesNames {

  public void runExample() {
    List<String> names = menu.stream()
        .filter(dish -> {
          System.out.println("filtering " + dish.getName());
          return dish.getCalories() > 300;
        })
        .map(dish -> {
          System.out.println("mapping " + dish.getName());
          return dish.getName();
        })
        .limit(3)
        .collect(toList());
    System.out.println(names);
  }

}
