package com.finnerjones.search.es;

import junit.framework.Assert;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by finner on 9/17/14.
 */
public class ElasticseachConnectionTest {

    private ElasticsearchConnection conn = new ElasticsearchConnection();

    @Test
    public void createClientIsNotNull() {
        conn.createClient();
        Assert.assertNotNull(conn.getClient());

    }


}
