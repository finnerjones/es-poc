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
        createClient();
    }

    public void createClient() {
        if (client == null) {
            Node node = nodeBuilder().node();
            client = node.client();
        }
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

    public void closeClient() {
        client.close();
    }

    public Client getClient() {
        return client;
    }
}