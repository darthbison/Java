package examples;

import javafx.scene.Parent;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;

import java.io.IOException;

public class DeleteExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        DeleteRequest request = new DeleteRequest(
                "posts",
                "doc",
                "1");

        request.routing("routing");
        request.parent("parent");
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        request.setRefreshPolicy("wait_for");
        request.version(2);
        request.versionType(VersionType.EXTERNAL);

        try {
            DeleteResponse deleteResponse = client.delete(request);
            String index = deleteResponse.getIndex();
            String type = deleteResponse.getType();
            String id = deleteResponse.getId();
            long version = deleteResponse.getVersion();
            ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();

            if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {

            }

            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
