package com.tjeason.mbz;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Get music artist information using public web services from musbrainz.org.
 * @author T.J. Eason <tjeason@gmail.com>
 * @version 1.0
 */
public class Artist {

    /**
     * Constructor.
     */
    Artist() {}

    /**
     * Search an artist's recorded tracks using musicbrainz.org's REST/XML service.
     * @param name
     * @return XML data.
     */
    public String searchTracksByArtistName(String name) {

        String result = "";

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        try {
            // Specify the host, protocol, and port
            HttpHost target = new HttpHost("musicbrainz.org", 80, "http");

            // Replace whitespace and quotes to artist's name.
            name = name.replaceAll(" ", "%20").toLowerCase();
            name = '"' + name + '"';

            // Specify the GET request.
            HttpGet getRequest = new HttpGet("/ws/2/recording/?query=artist:" + URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8.toString()) + "&limit=99");

            // Retrieve response.
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();

            // Check if a 200 XML response was received.
            if (entity != null)
                result = EntityUtils.toString(entity);
        }
        catch (Exception e) {
            System.out.println("ERROR >>> " + e.getMessage());
        }

        finally {
            // Shut down the connection manager to clean-up all system resources.
            if(httpclient != null) {
                try {
                    httpclient.close();
                }

                catch (IOException e) {
                    System.out.println("ERROR >>> " + e.getMessage());
                }
            }
        }

        return result;
    }
}
