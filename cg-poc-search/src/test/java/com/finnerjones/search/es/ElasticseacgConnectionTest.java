package com.finnerjones.search.es;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by finner on 9/17/14.
 */
public class ElasticseacgConnectionTest {

    private ElasticsearchConnection conn = new ElasticsearchConnection();

    @Test
    public void putAnIndex() {
        conn.createClient();
        conn.createSettings();
        conn.doGet(conn.getClusterName());
        try {
            putIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.closeClient();

    }


    private void putIndex() throws IOException {
        String source = createJSONDocument();
        conn.addDocumentAsJSON(source, "twitter", "tweet", 1L);
        conn.addDocumentWithJsonBuilder(createMapDocument(), "twitter", "tweet", "1");
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
