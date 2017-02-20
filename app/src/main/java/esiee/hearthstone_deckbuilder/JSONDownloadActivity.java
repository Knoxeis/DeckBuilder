package esiee.hearthstone_deckbuilder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	private ArrayList<Card> _cards;

	private boolean hasCachedData = false;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

		mNetworkFragment = NetworkFragment.getInstance(getFragmentManager(), "https://api.hearthstonejson.com/v1/15590/enUS/cards.json");
	}

	@Override
	protected void onStart() {
		super.onStart();


		String FILENAME = "cardData.json";
		try {
			Log.d("JSONDownloadActivity", "Opening Cache");
			String data = getStringFromFile(FILENAME);
			if (data != null) {
				Log.d("JSONDownloadActivity", "Load from Cache");
				UpdateJSONObject(data);
				//
				SendResult();
			}
		} catch (FileNotFoundException e) {
			client.connect();
			AppIndex.AppIndexApi.start(client, getIndexApiAction());
			Log.d("JSONDownloadActivity", "launching download");
			startDownload();
		} catch (IOException e) {
			this.finish();
		}
	}

	private void SendResult() {
		Data_singleton.getInstance().CallMainActivity_downloadFinish(_cards);
		finish();
	}

	public static String convertStreamToString(FileInputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		Boolean firstLine = true;
		while ((line = reader.readLine()) != null) {
			if (firstLine) {
				sb.append(line);
				firstLine = false;
			} else {
				sb.append("\n").append(line);
			}
		}
		reader.close();
		return sb.toString();
	}

	public String getStringFromFile(String fileName) throws IOException {
		File fl = new File(getApplicationContext().getFilesDir(), fileName);
		FileInputStream fin = new FileInputStream(fl);
		String ret = convertStreamToString(fin);
		//Make sure you close all streams.
		fin.close();
		return ret;
	}

	private void startDownload() {
		if (!mDownloading && mNetworkFragment != null) {
			Log.d("JSONDownloadActivity", "Download");
			// Execute the async download.
			mNetworkFragment.startDownload();
			mDownloading = true;
		}
	}

	@Override
	public void updateFromDownload(Object result) {
		Log.d("result", "Start");
		if (result != null) {
			String FILENAME = "cardData.json";
			try {
				FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(result.toString().getBytes());
				fos.close();
				Log.d("JSONDownloadActivity", "Saved Cache");
			} catch (IOException e) {
				e.printStackTrace();
			}
			UpdateJSONObject(result.toString());
			//
			SendResult();
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
		switch (progressCode) {
			// You can add UI behavior for progress updates here.
			case Progress.ERROR:
				Log.d("e", "ERROR");
				break;
			case Progress.CONNECT_SUCCESS:
				Log.d("e", "CONNECT_SUCCESS");
				break;
			case Progress.GET_INPUT_STREAM_SUCCESS:
				Log.d("e", "GET_INPUT_STREAM_SUCCESS");
				break;
			case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
				Log.d("e", "PROCESS_INPUT_STREAM_IN_PROGRESS");
				break;
			case Progress.PROCESS_INPUT_STREAM_SUCCESS:
				Log.d("e", "PROCESS_INPUT_STREAM_SUCCESS");
				break;
		}
	}

	@Override
	public void finishDownloading() {
		mDownloading = false;

		Log.d("JSONDownloadActivity", "End Download");
		if (mNetworkFragment != null) {
			mNetworkFragment.cancelDownload();
		}
	}

	public boolean UpdateJSONObject(String json) {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			_cards = mapper.readValue(json, new TypeReference<ArrayList<Card>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
			.setName("JSONDownload Page") // TODO: Define a title for the content shown.
			// TODO: Make sure this auto-generated URL is correct.
			.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
			.build();
		return new Action.Builder(Action.TYPE_VIEW)
			.setObject(object)
			.setActionStatus(Action.STATUS_TYPE_COMPLETED)
			.build();
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}
