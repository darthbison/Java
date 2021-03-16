package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;

public class CloseIndexExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        CloseIndexRequest request = new CloseIndexRequest("index");

        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        //Asynchronous
//        ActionListener<CloseIndexResponse> listener = new ActionListener<CloseIndexResponse>() {
//            @Override
//            public void onResponse(CloseIndexResponse closeIndexResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.indices().closeAsync(request, listener);

        try {
            CloseIndexResponse closeIndexResponse = client.indices().close(request);
            boolean acknowledged = closeIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
