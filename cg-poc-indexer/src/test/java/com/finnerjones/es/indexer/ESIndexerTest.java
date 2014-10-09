package com.finnerjones.es.indexer;

import com.finnerjones.indexer.ESIndexer;
import com.finnerjones.search.es.ElasticsearchConnection;
import junit.framework.Assert;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.junit.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by finner on 9/18/14.
 */
public class ESIndexerTest {

    private ElasticsearchConnection esConn;
    private Client client;
    private ESIndexer esIndexer;


    @Before
    public void setUp() {
        esConn = new ElasticsearchConnection();
        client = esConn.getClient();
    }



    @After
    public void cleanUp() {
        esConn.closeClient();
    }

    @Test
    public void addDocument() {
        esIndexer = new ESIndexer(client);
        IndexResponse indexResponse = indexAJsonDocument();
        Assert.assertEquals("1", indexResponse.getId());
        Assert.assertEquals("twitter", indexResponse.getIndex());
        Assert.assertEquals("tweet", indexResponse.getType());
    }


    @Test
    public void thereIsAnIndex() {
        SearchResponse response = esConn.getClient().prepareSearch("twitter").setTypes("tweet").execute().actionGet();
        Assert.assertEquals("Expecting 1+ hits on test-ubuntu cluster ", true, response.getHits().totalHits() > 0);
    }

    @Test
    public void cleanIndexes() {
        Client client = esConn.getClient();
        SearchRequestBuilder request = client.prepareSearch("twitter");
        request.setTypes("tweet");
        ListenableActionFuture<SearchResponse> laf = request.execute();
        SearchResponse response = laf.actionGet();

        if(response.getHits().totalHits() > 0) {
            System.out.println("** Found indices. Preparing to delete ... ");
            DeleteResponse deleteResponse = esConn.getClient().prepareDelete().setIndex("twitter").setType("tweet").setId("1").execute().actionGet();
            System.out.println("deleteResponse ID " + deleteResponse.getId());
            System.out.println("deleteResponse Index " + deleteResponse.getIndex());
            System.out.println("deleteResponse Type " + deleteResponse.getType());
            System.out.println("deleteResponse Version " + deleteResponse.getVersion());
            SearchResponse searchResponseAfterDelete = esConn.getClient().prepareSearch("twitter").setTypes("tweet").execute().actionGet();
            System.out.println("Hits found  " + searchResponseAfterDelete.getHits().getTotalHits());
            for (SearchHit hit : searchResponseAfterDelete.getHits().getHits()) {
                System.out.println("ID " + hit.getId());
                System.out.println("Index " + hit.getIndex());
                System.out.println("Type " + hit.getType());
                System.out.println("Version " + hit.getVersion());
            }
            Assert.assertEquals("There are 0 hits for twitter/tweet ", true, searchResponseAfterDelete.getHits().getTotalHits() == 0);
        }
    }


    private IndexResponse indexAJsonDocument()  {
        String source = createJSONDocument();
        IndexResponse indexResponse = esIndexer.addDocumentAsJSON(source, "twitter", "tweet", 1L);
        return indexResponse;
    }


    private String createJSONDocument() {
        return "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
    }

    private Map<String, Object> createMapDocument() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        return jsonMap;
    }

}
