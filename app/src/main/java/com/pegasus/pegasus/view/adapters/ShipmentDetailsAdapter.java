package com.pegasus.pegasus.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.TitleParentData;
import com.pegasus.pegasus.view.viewholders.ScreenNames;
import com.pegasus.pegasus.view.viewholders.ShipmentDetailsViewHolder;
import com.pegasus.pegasus.view.viewholders.ShipmentParentViewHolder;
import com.pegasus.pegasus.view.viewholders.TitleParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ShipmentDetailsAdapter extends ExpandableRecyclerViewAdapter<ShipmentParentViewHolder, ShipmentDetailsViewHolder> {

    public Context context;
    private List<? extends ExpandableGroup> headerName;
    private ShipmentDataDao shipmentDataDao;

    public ShipmentDetailsAdapter(Context context,List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        this.headerName = groups;

    }

    @Override
    public ShipmentParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.header_layout,parent,false);

        return new ShipmentParentViewHolder(view);
    }

    @Override
    public ShipmentDetailsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.shipmentdetailscontainer,parent,false);

        return new ShipmentDetailsViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ShipmentDetailsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Object childData = ((TitleParentData)group).getItems().get(childIndex);

        Log.v("","Title Child"+group.getTitle());

        if(group.getTitle().equalsIgnoreCase(ScreenNames.Head1)){
            holder.shipmenentContent.setVisibility(View.VISIBLE);
            holder.shipperContent.setVisibility(View.GONE);
            holder.consigneeContent.setVisibility(View.GONE);
            holder.lineContent.setVisibility(View.GONE);
            holder.setShimentInfo(childData);
        }else if(group.getTitle().equalsIgnoreCase(ScreenNames.Head2)){
            holder.shipmenentContent.setVisibility(View.GONE);
            holder.shipperContent.setVisibility(View.VISIBLE);
            holder.consigneeContent.setVisibility(View.GONE);
            holder.lineContent.setVisibility(View.GONE);
            holder.setShipperInfo(childData);
        }else if(group.getTitle().equalsIgnoreCase(ScreenNames.Head3)){
            holder.shipmenentContent.setVisibility(View.GONE);
            holder.shipperContent.setVisibility(View.GONE);
            holder.consigneeContent.setVisibility(View.VISIBLE);
            holder.lineContent.setVisibility(View.GONE);
            holder.setConsigneeInfo(childData);
        }else if(group.getTitle().equalsIgnoreCase(ScreenNames.Head4)){
            holder.shipperContent.setVisibility(View.GONE);
            holder.consigneeContent.setVisibility(View.GONE);
            holder.shipmenentContent.setVisibility(View.GONE);
            holder.lineContent.setVisibility(View.VISIBLE);
            holder.setLineItemsInfo(childData);
        }

    }

    @Override
    public void onBindGroupViewHolder(ShipmentParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }

}
