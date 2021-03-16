package oopandfp;

import java.util.stream.IntStream;

public class Foo {

  public void runExample() {
    IntStream.rangeClosed(2, 6)
        .forEach(n -> System.out.println("Hello " + n + " bottles of beer"));
  }

}
