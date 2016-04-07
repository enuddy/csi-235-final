import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnection {

    public static String fetchData(String name) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String cardJsonStr = null;
        try {
            // Construct a URL for the query
            String sUrl = "https://api.deckbrew.com/mtg/cards?name=" + name;
            URL url = new URL(sUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            cardJsonStr = buffer.toString();
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
        }
        return cardJsonStr;
    }

    public static Card[] parseData(String cardStr, final int NUM_CARDS)
    {
        // Create a card array of the requested size. Total cards may be <= NUM_CARDS
        Card[] cardArr = new Card[NUM_CARDS];

        // The JSON is an array of results that will be stored here.
        JSONArray jsonArr;

        try{
            jsonArr = new JSONArray(cardStr);

            // Create a card for every entry in the array.
            for (int i = 0; (i < jsonArr.length() && i < NUM_CARDS); i++) {
                // Use obj as a the current object in the array to grab data from (buffering for quicker access)
                JSONObject obj = jsonArr.getJSONObject(i);
                // An array of colors to access from the JSON object to be added to the card.
                JSONArray color_arr = null;

                // Obtain the details neccesary for a card from the object.
                String name = obj.getString("name");
                String color = "";

                // Collect the colors into a single string for the card object.
                try {
                    color_arr = obj.getJSONArray("colors");
                    for(int k = 0; k < color_arr.length(); k++) {
                        color += color_arr.getString(k) + " ";
                    }
                }
                catch(JSONException ex)
                {
                    color = "Colorless";
                }


                if(color_arr == null) {
                    color = "Colorless";
                }

                int cmc = obj.getInt("cmc");

                // Create the card for the json information at this index i.
                cardArr[i] = new Card(name, color, cmc);
            }
        }
        catch(JSONException ex)
        {
            System.out.println(ex.getMessage());
        }

        return cardArr;
    }
}