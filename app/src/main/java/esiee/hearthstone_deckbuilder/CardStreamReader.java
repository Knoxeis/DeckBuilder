package esiee.hearthstone_deckbuilder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class CardStreamReader extends AsyncTask<AppCompatActivity, Void, Boolean> {

    private static final String URLRequest = "https://api.hearthstonejson.com/v1/15590/enUS/cards.json";

    private static HttpURLConnection connection;
    private static InputStream inputStream;
    private static JSONObject result;
    private static URL url;

    private ScrollingActivity activity;

    /* Tente de mettre à jour le flux de données depuis le site fournissant les données */
    public boolean UpdateStream ()
    {
        try {
            url = new URL(URLRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setConnectTimeout(500);
            inputStream = connection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* Tente de mettre à jour l'object JSON qui contient les données des cartes */
    public boolean UpdateJSONObject ()
    {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            result =  new JSONObject(responseStrBuilder.toString());

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* Renvoie un tableau d'objet carte (tout type confondu) */
    public ArrayList<Card> GetCardsList ()
    {
        ArrayList<Card> cards = new ArrayList<>();
        Iterator keys = result.keys();

        while (keys.hasNext())
        {
            JSONObject value = null;
            Card card = null;

            try {
                value = result.getJSONObject((String) keys.next());
                card = new Card();

                card.setId(value.getString("id"));
                card.setName(value.getString("name"));
                card.setText(value.getString("text"));

                card.setText(value.getString("cost"));
                card.setText(value.getString("attack"));
                card.setText(value.getString("health"));
                card.setText(value.getString("mechanics"));

                card.setText(value.getString("rarity"));
                card.setText(value.getString("type"));
                card.setText(value.getString("dust"));

                card.setText(value.getString("set"));
                card.setText(value.getString("faction"));
                card.setText(value.getString("artist"));
                card.setText(value.getString("flavor"));

                cards.add(card);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cards;
    }

    @Override
    protected Boolean doInBackground(AppCompatActivity... activity)
    {
        this.activity = (ScrollingActivity)activity[0];
        if (UpdateStream()) {
            return true;
        }
        return false;
    }
    @Override
    protected void onPostExecute (Boolean result)
    {
        if (result) {
            UpdateJSONObject();
            this.activity.cards = GetCardsList();
            this.activity.LoadingDone();
        }
    }
}
