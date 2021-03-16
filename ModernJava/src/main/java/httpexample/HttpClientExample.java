package httpexample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {


    public void runExample() throws IOException, InterruptedException {
        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://www.google.com")).build();
        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }



}
