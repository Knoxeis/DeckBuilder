package esiee.hearthstone_deckbuilder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class ScrollingActivity extends AppCompatActivity {

    public static ArrayList<Card> cards;
	private ImageLoader _ImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        Data_singleton.getInstance().mainActivity = this;
        Data_singleton.getInstance().appContext = this.getApplicationContext();


        setContentView(R.layout.activity_scrolling);

        Intent intent = new Intent(this, JSONDownloadActivity.class);
        startActivity(intent);
    }

    public void LoadingDone(ArrayList<Card> c)
    {
        cards = c;
        Log.d("ScrollingActivity", cards.toString());
		Log.d("ScrollingActivity", "http://wow.zamimg.com/images/hearthstone/cards/enus/medium/" + cards.get(0).getId() + ".png");
		Log.d("ScrollingActivity", findViewById(R.id.NIV).toString());
		((NetworkImageView)findViewById(R.id.NIV))
			.setImageUrl("http://wow.zamimg.com/images/hearthstone/cards/enus/medium/" + cards.get(34).getId() + ".png",
			VolleyHandler.getInstance().getImageLoader());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
