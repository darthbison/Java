import com.google.inject.Guice;
import com.google.inject.Injector;
import io.alzuma.EntrypointType;

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(EntrypointType.REST, args);
    }
}
