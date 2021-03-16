package reactive;

import java.util.concurrent.Flow.Publisher;

public class Main {

  public void runExample() {
    getTemperatures("New York").subscribe(new TempSubscriber());
  }

  private static Publisher<TempInfo> getTemperatures(String town) {
    return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
  }

}
