package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;

public class OpenIndexExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        OpenIndexRequest request = new OpenIndexRequest("index");

        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.indicesOptions(IndicesOptions.strictExpandOpen());

        //Asynchronous
//        ActionListener<OpenIndexResponse> listener = new ActionListener<OpenIndexResponse>() {
//            @Override
//            public void onResponse(OpenIndexResponse openIndexResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.indices().openAsync(request, listener);


        try {
            OpenIndexResponse openIndexResponse = client.indices().open(request);
            boolean acknowledged = openIndexResponse.isAcknowledged();
            boolean shardsAcked = openIndexResponse.isShardsAcknowledged();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
