package esiee.hearthstone_deckbuilder;

import android.content.Context;

import java.util.ArrayList;

public class DataSingleton {

	private static DataSingleton ourInstance = new DataSingleton();
	public static DataSingleton getInstance() {
		return ourInstance;
	}

	public Context				appContext;
	public ScrollingActivity	mainActivity;
	public ArrayList<Card>		cards;

	public void CallMainActivity_downloadFinish(ArrayList<Card> C) {
		if (mainActivity != null) {
			mainActivity.LoadingDone(C);
		}
	}
}
