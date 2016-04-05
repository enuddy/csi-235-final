import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 * @author Rebekah
 */
public class URLConnection {
    public static String fetchData(String setCode) {
        //int date = year;
        String name = setCode;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String movieJsonStr = null;
        try {
            // Construct a URL for the query
            String sUrl = "https://api.deckbrew.com/mtg/cards?name=" + name;
            URL url = new URL(sUrl);
            //Setup connection to moviedb
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream inputStream = conn.getInputStream(); //Read the input stream
            //Place input stream into a buffered reader
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            movieJsonStr = buffer.toString();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (conn != null)
                conn.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(movieJsonStr);
        }
        return movieJsonStr;
    }

    public static Card[] parseData(String cardStr)
    {
        Card[] cardArr = new Card[1];

        JSONArray jsonArr;
        //JSONObject cardsJson;

        try{

            jsonArr = new JSONArray(cardStr);

            JSONObject obj = jsonArr.getJSONObject(0);
            String name = obj.getString("name");
            String color = "";
            for(int i = 0; i < obj.getJSONArray("colors").length(); i++)
                color += obj.getJSONArray("colors").getString(i) + " ";
            int cmc = obj.getInt("cmc");


            cardArr[0] = new Card(name, color, cmc);
        }
        catch(JSONException ex)
        {
            System.out.println(ex.getMessage());
        }

        return cardArr;
    }

    /*
    public static Movie[] parseData(String movieStr, int numMovies)
    {
        Movie[] movieArr = new Movie[numMovies];
        JSONArray jsonArr;
        JSONObject moviesJson;

        try {
            moviesJson = new JSONObject(movieStr);
            jsonArr = moviesJson.getJSONArray("results");

            for(int i = 0; i < numMovies; i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                String title = obj.getString("title");
                String overview = obj.getString("overview");
                String release = obj.getString("release");
                movieArr[i] = new Movie(title, release, overview);
            }
        }
        catch(JSONException ex){
            System.out.println(ex.getMessage());
        }

        return movieArr;
    }
    */
}