package esiee.hearthstone_deckbuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements Filterable {

    private ArrayList<Card> cards;
    private Context context;
    private ArrayList<Card> orig;

    public CardAdapter(Context context, ArrayList<Card> cards) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageUrl(cards.get(i).getImgURL(), VolleyHandler.getInstance().getImageLoader());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private NetworkImageView img;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            img = (NetworkImageView) view.findViewById(R.id.img);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Card> results = new ArrayList<Card>();
                if (orig == null)
                    orig  = cards;
                if (constraint != null){
                    if(orig !=null & orig.size()>0 ){
                        for ( final Card g :orig) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cards = (ArrayList<Card>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}