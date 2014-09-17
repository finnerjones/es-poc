package com.finnerjones.search.es;

/**
 * Created by U0142036 on 15/09/2014.
 */

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.node.NodeBuilder.*;
import static org.elasticsearch.common.xcontent.XContentFactory.*;


public class ElasticsearchConnection {

    private Client client;
    private Settings settings;


    public ElasticsearchConnection() {

    }

    public void createClient() {
        Node node = nodeBuilder().node();
        client = node.client();
    }

    public void createSettings() {
        settings = client.settings();
    }

    public String getClusterName() {
        return settings.get("cluster.name");
    }

    public ActionFuture<GetResponse> doGet(String clusterName) {
        GetRequest req = new GetRequest(clusterName);
        return client.get(req);
    }


    public void putIndex() throws IOException {

        String source = createJSONDocument();
        addDocumentAsJSON(source, "twitter", "tweet", 1L);
        addDocumentWithJsonBuilder(createMapDocument(), "twitter", "tweet", "1");
    }


    public String createJSONDocument() {
        return "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
    }

    public Map<String, Object> createMapDocument() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("user","kimchy");
        jsonMap.put("postDate",new Date());
        jsonMap.put("message","trying out Elasticsearch");
        return jsonMap;
    }

    public void addDocumentAsJSON(String document, String indexName, String typeName, Long id) {
        IndexResponse response = client.prepareIndex(indexName, typeName, id.toString())
                .setSource(document)
                .execute()
                .actionGet();
        printResponse(response);


    }


    public void addDocumentWithJsonBuilder(Object document, String indexName, String typeName, String version) throws IOException {
        IndexResponse response = client.prepareIndex(indexName, typeName, version)
                .setSource(jsonBuilder().
                                startObject()
                                    .field("user", "kimchy")
                                    .field("postDate", new Date())
                                    .field("message","trying out elasticsearch")
                                .endObject()
                )
                .execute()
                .actionGet();
        printResponse(response);


    }





    public void printResponse(IndexResponse response) {
        // Index name
        String index = response.getIndex();
        // Type name
        String type = response.getType();
        // Document ID
        String id = response.getId();
        // Version
        long version = response.getVersion();

        System.out.println("Index: " + index + "\n" + "Type: " + type + "\n" + "ID: " + id + "\n" + "Version: " + version);
    }


    public void closeClient() {
        client.close();
    }

}