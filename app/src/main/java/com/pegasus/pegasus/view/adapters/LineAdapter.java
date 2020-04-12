package com.pegasus.pegasus.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.LineItemsDao;
import com.pegasus.pegasus.view.viewholders.TitleParentViewHolder;

import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineViewHolder> {

    private Context context;
    private List<LineItemsDao> alLineItemList;

    public LineAdapter(Context context,List<LineItemsDao> lineItemsDaoList){
        this.context = context;
        this.alLineItemList = lineItemsDaoList;
    }

    @NonNull
    @Override
    public LineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.linecard,parent,false);

        return new LineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineViewHolder holder, int position) {
        LineItemsDao lineItemsDao = alLineItemList.get(position);

        holder.pieces.setText(lineItemsDao.getPieces());
        holder.description.setText(lineItemsDao.getDescription());
        holder.weight.setText(lineItemsDao.getWeight());

        if(position%2==0){
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.whitee));
        }else {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.darkgrey));
        }
    }

    @Override
    public int getItemCount() {
        if(alLineItemList!=null) {
            return alLineItemList.size();
        }else{
            return 0;
        }
    }

    class LineViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private AppCompatTextView pieces,description,weight;

        public LineViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            pieces = itemView.findViewById(R.id.tvPieces);
            description = itemView.findViewById(R.id.tvDescription);
            weight = itemView.findViewById(R.id.tvWeight);

        }
    }
}
