package esiee.hearthstone_deckbuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ArrayList<Card> cards;
    private Context context;

    public CardAdapter(Context context, ArrayList<Card> cards) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageUrl(cards.get(i).getImgURL(), VolleyHandler.getInstance().getImageLoader());
        Picasso.with(context).load(viewHolder.img.getId()).resize(240, 120).into(viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private NetworkImageView img;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            img = (NetworkImageView) view.findViewById(R.id.img);
        }
    }
}
