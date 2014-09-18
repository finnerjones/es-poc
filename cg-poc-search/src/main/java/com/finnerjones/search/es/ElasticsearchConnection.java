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

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;


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


    public void addDocumentAsJSON(String document, String indexName, String typeName, Long id) {
        IndexResponse response = client.prepareIndex(indexName, typeName, id.toString())
                .setSource(document)
                .execute()
                .actionGet();
        printResponse(response);
    }

    private void printResponse(IndexResponse response) {
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