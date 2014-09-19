package com.finnerjones.search.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Arrays;

/**
 * Created by U0142036 on 19/09/2014.
 */
public class ESQuickSearch {


    private Client client;

    public ESQuickSearch(Client client) {
        this.client = client;
    }


    public SearchResponse doQuickSearch() {
        SearchResponse response = client.prepareSearch().execute().actionGet();
        return response;
    }

}
