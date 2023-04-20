package lib;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


public class WebService {
    private String url;
    private URL conn;
    private HttpURLConnection con;
    private BufferedReader in;
    private StringBuffer response;
    private JSONObject obj;

    public WebService(String url){
        this.url = url;
    }

    public JSONObject getInfo(){
        try{
            // Create a URL Object for web service
            conn = new URL(url+"info/");
            // Open a connection to the web service
            con = (HttpURLConnection) conn.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Read the response from the web service
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convert the response to a JSON object
            obj = new JSONObject(response.toString());
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getNew(String strategy){
        try{
            // Create a URL Object for web service
            conn = new URL(url+"new/?strategy="+strategy);
            // Open a connection to the web service
            con = (HttpURLConnection) conn.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Read the response from the web service
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convert the response to a JSON object
            obj = new JSONObject(response.toString());
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getPlay(String pid, int x, int y){
        try{
            // Create a URL Object for web service
            conn = new URL(url+"play/?pid="+ pid + "&x=" + x + "&y=" + y);
            // Open a connection to the web service
            con = (HttpURLConnection) conn.openConnection();

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Read the response from the web service
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convert the response to a JSON object
            obj = new JSONObject(response.toString());
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}