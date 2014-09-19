package com.finnerjones.search.es;

import junit.framework.Assert;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

/**
 * Created by U0142036 on 19/09/2014.
 */
public class ESQuickSearchTest {

    @Test
    public void searchAllIndexes() {
        ElasticsearchConnection esConn = new ElasticsearchConnection();
        Client client = esConn.getClient();
        ESQuickSearch esQuickSearch= new ESQuickSearch(client);
        SearchResponse searchResponse = esQuickSearch.doQuickSearch();

        Assert.assertNotNull(searchResponse);
        SearchHits searchHits = searchResponse.getHits();
        Assert.assertTrue(searchHits.getHits().length > 1);
        System.out.println("********************************************** : " + searchResponse.toString());
    }
}
