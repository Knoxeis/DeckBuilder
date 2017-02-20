package esiee.hearthstone_deckbuilder;

	import android.graphics.Bitmap;
	import android.support.v4.util.LruCache;
	import android.util.Log;

	import com.android.volley.RequestQueue;
	import com.android.volley.toolbox.ImageLoader;
	import com.android.volley.Cache;
	import com.android.volley.Network;
	import com.android.volley.toolbox.BasicNetwork;
	import com.android.volley.toolbox.DiskBasedCache;
	import com.android.volley.toolbox.HurlStack;


/**
 * Created by VM on 20/02/2017.
 */

public class VolleyHandler {
	private static VolleyHandler mInstance = null;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private VolleyHandler(){
		Log.d("VolleyHandler", "Init");
		mRequestQueue = getRequestQueue();
		mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				Log.d("VolleyHandler", "getBit");
				mCache.put(url, bitmap);
			}
			@Override
			public Bitmap getBitmap(String url) {
				Log.d("VolleyHandler", "getBit");
				return mCache.get(url);
			}
		});
	}

	public static VolleyHandler getInstance(){

		if(mInstance == null){
			mInstance = new VolleyHandler();
		}
		Log.d("VolleyHandler", "VolleyHandler.mImageLoader" + mInstance.mImageLoader.toString() );
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			Cache cache = new DiskBasedCache(Data_singleton.getInstance().appContext.getCacheDir(), 10 * 1024 * 1024);
			Network network = new BasicNetwork(new HurlStack());
			mRequestQueue = new RequestQueue(cache, network);
			// Don't forget to start the volley request queue
			mRequestQueue.start();
		}
		return mRequestQueue;
	}

	public ImageLoader getImageLoader(){
		Log.d("VolleyHandler", "this.mImageLoader: " + this.mImageLoader.toString());
		return this.mImageLoader;
	}

}