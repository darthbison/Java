package streams1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVsCollection {

  public void runExample() {
    List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
    Stream<String> s = names.stream();
    s.forEach(System.out::println);
    // uncommenting this line will result in an IllegalStateException
    // because streams can be consumed only once
    //s.forEach(System.out::println);
  }

}
