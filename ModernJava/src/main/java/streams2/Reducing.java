package streams2;

import streams1.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static streams1.Dish.menu;

public class Reducing {

  public void runExample() {
    List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
    int sum = numbers.stream().reduce(0, (a, b) -> a + b);
    System.out.println(sum);

    int sum2 = numbers.stream().reduce(0, Integer::sum);
    System.out.println(sum2);

    int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
    System.out.println(max);

    Optional<Integer> min = numbers.stream().reduce(Integer::min);
    min.ifPresent(System.out::println);

    int calories = menu.stream()
        .map(Dish::getCalories)
        .reduce(0, Integer::sum);
    System.out.println("Number of calories:" + calories);
  }

}
