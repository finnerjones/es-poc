package com.finnerjones.search.es;

/**
 * Created by U0142036 on 15/09/2014.
 */

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

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
            createSettings();
            printClusterDetails();
        }
    }

    private void printClusterDetails() {
        System.out.println("Settings cluster.name : " + client.settings().get("cluster.name"));
        System.out.println("Settings name : " + client.settings().get("name"));
        System.out.println("getClusterName() : " + getClusterName());
        System.out.println("Settings node.name : " + client.settings().get("node.name"));
        System.out.println("Settings names() : " + client.settings().names());
        System.out.println("Settings path : " + client.settings().get("path"));
        System.out.println("Settings path.data : " + client.settings().get("path.data"));
        System.out.println("Settings network.host : " + client.settings().get("network.host"));
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