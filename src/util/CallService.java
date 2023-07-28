package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import org.json.JSONObject;

public class CallService {
    
    public static JSONObject getServicioJson(String url, Connection connection){
        try {
            URL twitter = new
            URL(url); 
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new
            InputStreamReader( tc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                in.close();
                return new JSONObject(line);
            }
        } catch (Exception e) {}
        return null;
    }
}
