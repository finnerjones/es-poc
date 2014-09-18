package com.finnerjones.transformation.xml.json;

import com.finnerjones.search.es.ElasticsearchConnection;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.Iterator;

/**
 * Created by U0142036 on 17/09/2014.
 */
public class XMLToJSON {

    //private static final String DATA_PATH = "D:\\ClinicalGenomics\\test-data\\cortellis-fast\\si";
    private static final String DATA_PATH = "cg-poc-json-transformation\\src\\main\\resources\\contacts";
    private static final String OUTPUT_PATH = "data\\json\\";
    private static final String INDEX_NAME = "contacts";
    private static File dataFolder = new File(DATA_PATH);
    private static ElasticsearchConnection esConn;
    private static String ID_KEY = "id";
    private static String JSON_EXTENSION = ".json";

    public static void main(String[] args) {
        XMLToJSON xmlToJson = new XMLToJSON();
        xmlToJson.process();
    }


    public void process() {
        esConn = new ElasticsearchConnection();
        XMLToJSON xmltojson = new XMLToJSON();
        xmltojson.findDataFiles(dataFolder);
    }


    public void findDataFiles(File fileOrFolder) {
        for (File aFile : fileOrFolder.listFiles()) {
            if (aFile.isDirectory()) {
                findDataFiles(aFile);
            } else {
                convertXmlToJsonAndWriteToDisk(aFile.getAbsolutePath());
            }
        }
    }

    public void convertXmlToJsonAndWriteToDisk(String fullFilename) {
        try {
            JSONObject jsonObj = translateXmlToJson(fullFilename);
            String type = extractFirstKeyAsTypeFromJson(jsonObj);
            Long documentId = jsonObj.getJSONObject(type).getLong(ID_KEY);
            writeToDisk(jsonObj.toString().getBytes(), documentId + JSON_EXTENSION);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject translateXmlToJson(String fullFilename) throws IOException {
        String xml = getXmlAsString(fullFilename);
        return XML.toJSONObject(xml);
    }

    private String extractFirstKeyAsTypeFromJson(JSONObject jsonObj) {
        Iterator keyIter = jsonObj.keys();
        String type = null;
        while (keyIter.hasNext()) {
            if (type == null) {
                type = (String) keyIter.next();
            }
        }
        return type;
    }

    private String getXmlAsString(String fullFilename) throws IOException {
        InputStream in = new FileInputStream(new File(fullFilename));

        StringBuilder builder = new StringBuilder();
        int ptr = 0;
        while ((ptr = in.read()) != -1) {
            builder.append((char) ptr);
        }

        return builder.toString();
    }


    public void writeToDisk(byte[] data, String filename) throws IOException {
        FileOutputStream out = new FileOutputStream(OUTPUT_PATH + filename);
        out.write(data);
        out.close();
    }
}
