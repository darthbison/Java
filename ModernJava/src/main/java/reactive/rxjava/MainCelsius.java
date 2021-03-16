package reactive.rxjava;

import io.reactivex.Observable;
import reactive.TempInfo;

import static reactive.rxjava.TempObservable.getCelsiusTemperatures;

public class MainCelsius {

  public void runExample() {
    Observable<TempInfo> observable = getCelsiusTemperatures("New York", "Chicago", "San Francisco");
    observable.subscribe(new TempObserver());

    try {
      Thread.sleep(10000L);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
