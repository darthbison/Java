package examples;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;


import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class SearchScrollExample {

    public static void main(String[] args) {


        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));


        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("title", "Elasticsearch"));
        searchSourceBuilder.size(50);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = null;
        String scrollId = null;
        SearchHits hits = null;
        try {
            searchResponse = client.search(searchRequest);
            scrollId = searchResponse.getScrollId();
            hits = searchResponse.getHits();
        } catch (IOException e) {
            e.printStackTrace();
        }


        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueSeconds(30));
        SearchResponse searchScrollResponse = null;
        try {
            searchScrollResponse = client.searchScroll(scrollRequest);
            scrollId = searchScrollResponse.getScrollId();
            hits = searchScrollResponse.getHits();
//            assertEquals(3, hits.getTotalHits());
//            assertEquals(1, hits.getHits().length);
//            assertNotNull(scrollId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
