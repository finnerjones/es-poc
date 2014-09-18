package com.finnerjones.es.indexer;

import com.finnerjones.indexer.ESIndexer;
import com.finnerjones.search.es.ElasticsearchConnection;
import junit.framework.Assert;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
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




    @Test
    public void addDocument() {
        esConn = new ElasticsearchConnection();
        client = esConn.getClient();
        esIndexer = new ESIndexer(client);
        IndexResponse indexResponse = indexAJsonDocument();
        Assert.assertEquals("1",indexResponse.getId());
        Assert.assertEquals("twitter", indexResponse.getIndex());
        Assert.assertEquals("tweet", indexResponse.getType());
        esConn.closeClient();
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
