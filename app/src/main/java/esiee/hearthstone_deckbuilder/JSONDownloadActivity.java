package esiee.hearthstone_deckbuilder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONDownloadActivity extends FragmentActivity implements DownloadCallback {

	// Keep a reference to the NetworkFragment, which owns the AsyncTask object
	// that is used to execute network ops.
	private NetworkFragment mNetworkFragment;

	// Boolean telling us whether a download is in progress, so we don't trigger overlapping
	// downloads with consecutive button clicks.
	private boolean mDownloading = false;

	private ArrayList<Card>	_cards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Button click = (Button)findViewById(R.id.button);
		click.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				startDownload();
			}
		});
		mNetworkFragment = NetworkFragment.getInstance(getFragmentManager(), "https://api.hearthstonejson.com/v1/15590/enUS/cards.json");
	}

	private void startDownload() {
		if (!mDownloading && mNetworkFragment != null) {
			// Execute the async download.
			mNetworkFragment.startDownload();
			mDownloading = true;
		}
	}
	@Override
	public void updateFromDownload(Object result) {
		Log.d("result", "Start");
		if (result != null) {
			Log.d("result", result.toString());
			UpdateJSONObject(result.toString());
			Log.d("result", _cards.toString());
		}
	}

	@Override
	public NetworkInfo getActiveNetworkInfo() {
		ConnectivityManager connectivityManager =
			(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo;
	}

	@Override
	public void onProgressUpdate(int progressCode, int percentComplete) {
		switch(progressCode) {
			// You can add UI behavior for progress updates here.
			case Progress.ERROR:
				Log.d("e","ERROR");
				break;
			case Progress.CONNECT_SUCCESS:
				Log.d("e","CONNECT_SUCCESS");
				break;
			case Progress.GET_INPUT_STREAM_SUCCESS:
				Log.d("e","GET_INPUT_STREAM_SUCCESS");
				break;
			case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
				Log.d("e","PROCESS_INPUT_STREAM_IN_PROGRESS");
				break;
			case Progress.PROCESS_INPUT_STREAM_SUCCESS:
				Log.d("e","PROCESS_INPUT_STREAM_SUCCESS");
				break;
		}
	}

	@Override
	public void finishDownloading() {
		mDownloading = false;
		if (mNetworkFragment != null) {
			mNetworkFragment.cancelDownload();
		}
		Log.d("e","END FUCKER");
		Log.d("e","END FUCKER");
	}

	public boolean UpdateJSONObject (String json)
	{
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			_cards = mapper.readValue(json, new TypeReference<ArrayList<Card>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
