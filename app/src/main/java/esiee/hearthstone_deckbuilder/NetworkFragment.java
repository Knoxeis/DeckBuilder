package esiee.hearthstone_deckbuilder;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by VM on 16/01/2017.
 */

public class NetworkFragment extends Fragment {
	/**
	 * Implementation of headless Fragment that runs an AsyncTask to fetch data from the network.
	 */
	public static final String TAG = "NetworkFragment";
	private static final String URL_KEY = "UrlKey";
	private DownloadCallback mCallback;
	private DownloadTask mDownloadTask;
	private String mUrlString;

	/**
	 * Static initializer for NetworkFragment that sets the URL of the host it will be downloading
	 * from.
	 */
	public static NetworkFragment getInstance(FragmentManager fragmentManager, String url) {
		NetworkFragment networkFragment = new NetworkFragment();
		Bundle args = new Bundle();
		args.putString(URL_KEY, url);
		networkFragment.setArguments(args);
		fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
		return networkFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUrlString = getArguments().getString(URL_KEY);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Host Activity will handle callbacks from task.
		mCallback = (DownloadCallback) activity;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		// Host Activity will handle callbacks from task.
		mCallback = (DownloadCallback) context;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// Clear reference to host Activity to avoid memory leak.
		mCallback = null;
	}

	@Override
	public void onDestroy() {
		// Cancel task when Fragment is destroyed.
		cancelDownload();
		super.onDestroy();
	}

	/**
	 * Start non-blocking execution of DownloadTask.
	 */
	public void startDownload() {
		cancelDownload();
		mDownloadTask = new DownloadTask(mCallback);
		mDownloadTask.execute(mUrlString);
	}

	/**
	 * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
	 */
	public void cancelDownload() {
		if (mDownloadTask != null) {
			mDownloadTask.cancel(true);
		}
	}
}
