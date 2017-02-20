package esiee.hearthstone_deckbuilder;

import java.util.ArrayList;

class DataSingleton
{
	private static DataSingleton ourInstance = new DataSingleton();
	static DataSingleton getInstance() {
		return ourInstance;
	}

	ScrollingActivity mainActivity;

	private DataSingleton() {}

	void CallMainActivity_downloadFinish(ArrayList<Card> cards) {
		if (mainActivity != null) {
			mainActivity.LoadingDone(cards);
		}
	}
}
