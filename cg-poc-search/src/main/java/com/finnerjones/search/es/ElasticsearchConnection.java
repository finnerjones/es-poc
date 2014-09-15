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

import static org.elasticsearch.node.NodeBuilder.*;

public class ElasticsearchConnection {

    public static void main(String[] args) {
        Node node = nodeBuilder().node();
        Client client = node.client();
        Settings settings = client.settings();
        String clustername = settings.get("cluster.name");
        System.out.println("cluster.name: " + clustername);
        GetRequest req = new GetRequest(clustername);
        ActionFuture<GetResponse> resp =  client.get(req);
        System.out.println(resp);
        client.close();
    }

}