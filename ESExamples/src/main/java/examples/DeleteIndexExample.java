package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

public class DeleteIndexExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        DeleteIndexRequest request = new DeleteIndexRequest("posts");

        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        //Synchronous
        //DeleteIndexResponse deleteIndexResponse = client.indices().delete(request);

        //Asynchronous
//        ActionListener<DeleteIndexResponse> listener = new ActionListener<DeleteIndexResponse>() {
//            @Override
//            public void onResponse(DeleteIndexResponse deleteIndexResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.indices().deleteAsync(request, listener);

        try {
            client.indices().delete(request);
        } catch (ElasticsearchException | IOException exception) {
            exception.printStackTrace();
        }

    }
}
