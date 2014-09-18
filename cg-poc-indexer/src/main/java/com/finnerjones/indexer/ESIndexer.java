package com.finnerjones.indexer;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

/**
 * Created by U0142036 on 17/09/2014.
 */
public class ESIndexer {

    private Client client;

    public ESIndexer(Client client) {
        this.client = client;
    }

    public IndexResponse addDocumentAsJSON(String document, String indexName, String typeName, Long id) {
        IndexResponse response = client.prepareIndex(indexName, typeName, id.toString())
                .setSource(document)
                .execute()
                .actionGet();
        printResponse(response);
        return response;
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

}
