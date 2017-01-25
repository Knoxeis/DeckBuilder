package esiee.hearthstone_deckbuilder;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by VM on 25/01/2017.
 */
public class Data_singleton {
	private static Data_singleton ourInstance = new Data_singleton();
	public static Data_singleton getInstance() {
		return ourInstance;
	}

	public ScrollingActivity	mainActivity;

	private Data_singleton() {
	}

	public void CallMainActivity_downloadFinish(ArrayList<Card> cards) {
		if (mainActivity != null) {
			mainActivity.LoadingDone(cards);
		}
	}
}
