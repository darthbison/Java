package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;

import java.io.IOException;

public class InfoExample {

    public static void main(String[] args) {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));


        try {
            MainResponse response = client.info();

            ClusterName clusterName = response.getClusterName();
            String clusterUuid = response.getClusterUuid();
            String nodeName = response.getNodeName();
            Version version = response.getVersion();
            Build build = response.getBuild();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
