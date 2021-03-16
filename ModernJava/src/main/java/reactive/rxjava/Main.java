package reactive.rxjava;

import io.reactivex.Observable;
import reactive.TempInfo;

import static reactive.rxjava.TempObservable.getTemperature;

public class Main {

  public void runExample() {
    Observable<TempInfo> observable = getTemperature("New York");
    observable.subscribe(new TempObserver());

    try {
      Thread.sleep(10000L);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
