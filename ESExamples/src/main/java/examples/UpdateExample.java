package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;

import java.io.IOException;
import java.util.Map;

public class UpdateExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));


        UpdateRequest request = new UpdateRequest(
                "posts",
                "doc",
                "1");

        //Update using scripts
//        Map<String, Object> parameters = singletonMap("count", 4);
//        Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.field += params.count", parameters);
//        request.script(inline);

        String jsonString = "{\"created\":\"2017-01-01\"}";
        request.upsert(jsonString, XContentType.JSON);

        try {
            UpdateResponse updateResponse = client.update(request);

            String index = updateResponse.getIndex();
            String type = updateResponse.getType();
            String id = updateResponse.getId();
            long version = updateResponse.getVersion();
            if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {

            } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {

            } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {

            } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {

            }

            GetResult result = updateResponse.getGetResult();
            if (result.isExists()) {
                String sourceAsString = result.sourceAsString();
                Map<String, Object> sourceAsMap = result.sourceAsMap();
                byte[] sourceAsBytes = result.source();
            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
