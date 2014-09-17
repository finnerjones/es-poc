package com.finnerjones.search.es;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by finner on 9/17/14.
 */
public class ElasticseacgConnectionTest {


    @Test
    public void putAnIndex() {
        ElasticsearchConnection conn = new ElasticsearchConnection();
        conn.createClient();
        conn.createSettings();
        conn.doGet(conn.getClusterName());
        try {
            conn.putIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.closeClient();

    }


}
