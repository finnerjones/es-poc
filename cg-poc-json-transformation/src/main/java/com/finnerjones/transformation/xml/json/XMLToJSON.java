package com.finnerjones.transformation.xml.json;

import com.finnerjones.search.es.ElasticsearchConnection;
import org.elasticsearch.client.Client;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by U0142036 on 17/09/2014.
 */
public class XMLToJSON {

    //private static final String DATA_PATH = "D:\\ClinicalGenomics\\test-data\\cortellis-fast\\si";
    private static final String DATA_PATH = "cg-poc-json-transformation\\target\\classes\\contacts";
    private static final String INDEX_NAME = "contacts";
    private static File dataFolder = new File (DATA_PATH);
    private static ElasticsearchConnection esConn;


    public static void main(String[] args) {
        esConn = new ElasticsearchConnection();
        XMLToJSON xmltojson = new XMLToJSON();
        xmltojson.findDataFiles(dataFolder);
    }


    public void convertXMLFileToJSON(String fullFilename) {
        try  {
            InputStream inNull = this.getClass().getClassLoader().getResourceAsStream(fullFilename);
            InputStream in = new FileInputStream(new File(fullFilename));

            StringBuilder builder =  new StringBuilder();
            int ptr = 0;
            while ((ptr = in.read()) != -1 )
                {
                    builder.append((char) ptr);
                }

            String xml  = builder.toString();
            JSONObject jsonObj = XML.toJSONObject(xml);
            Iterator keyIter = jsonObj.keys();
            String type = null;
            while (keyIter.hasNext()) {
                if (type == null) {
                    type = (String)keyIter.next();
                }
            }
            System.out.println("type :" + type);
            System.out.println(jsonObj);

            Long id = jsonObj.getJSONObject(type).getLong("id");
            esConn.createClient();
            esConn.addDocumentAsJSON(jsonObj.toString(), INDEX_NAME, type, id);

         }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void findDataFiles(File fileOrFolder) {
        for (File aFile: fileOrFolder.listFiles()) {
            if (aFile.isDirectory()) {
                findDataFiles(aFile);
            } else {
                convertXMLFileToJSON(aFile.getAbsolutePath());
            }
        }

    }
}
