package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ClearScrollExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));


        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId("");

        try {
            ClearScrollResponse response = client.clearScroll(request);
            boolean success = response.isSucceeded();
            int released = response.getNumFreed();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
