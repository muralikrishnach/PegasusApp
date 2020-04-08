package com.pegasus.pegasus.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.OpenPODShipmentDetailsDao;
import com.pegasus.pegasus.model.TitleParentData;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.view.ShipmentDetailsActivity;
import com.pegasus.pegasus.view.viewholders.ChildParentViewHolder;
import com.pegasus.pegasus.view.viewholders.TitleParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class OpenShipmentAdapter extends ExpandableRecyclerViewAdapter<TitleParentViewHolder, ChildParentViewHolder> {

    public Context context;
    private List<? extends ExpandableGroup> headerName;


    public OpenShipmentAdapter(Context context,List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        this.headerName = groups;
    }



    @Override
    public TitleParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.header_layout,parent,false);

        return new TitleParentViewHolder(view);
    }

    @Override
    public ChildParentViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_items,parent,false);

        return new ChildParentViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildParentViewHolder holder, int flatPosition, final ExpandableGroup group, final int childIndex) {

        final Object childData = ((TitleParentData)group).getItems().get(childIndex);

        if(group.getTitle().equalsIgnoreCase(JsonParsing.Head1)){
            holder.setOpenShipment(childData);
        }else {
            holder.setPODShipment(childData);
        }

        holder.imglocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShipmentDetailsActivity.class);
                i.putExtra("WayBillNo","22462370");
                context.startActivity(i);
            }
        });



    }

    @Override
    public void onBindGroupViewHolder(TitleParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }


}