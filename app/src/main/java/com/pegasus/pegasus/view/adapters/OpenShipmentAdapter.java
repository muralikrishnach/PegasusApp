package com.pegasus.pegasus.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.PODShipmentsDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.view.MapsActivity;
import com.pegasus.pegasus.view.ShipmentDetails;
import com.pegasus.pegasus.view.viewholders.ChildParentViewHolder;
import com.pegasus.pegasus.view.viewholders.TitleParentViewHolder;
import com.pegasus.pegasus.viewmodel.TrackingViewModel;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;
import java.util.Objects;

public class OpenShipmentAdapter extends ExpandableRecyclerViewAdapter<TitleParentViewHolder, ChildParentViewHolder> {

    private Context context;
    private TrackingViewModel trackingViewModel;
    private TrackingDetailsDao trackingDetailsDaodata;

    public OpenShipmentAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }


    @Override
    public TitleParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = Objects.requireNonNull(inflater).inflate(R.layout.header_layout, parent, false);

        return new TitleParentViewHolder(view);
    }

    @Override
    public ChildParentViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = Objects.requireNonNull(inflater).inflate(R.layout.list_items, parent, false);

        return new ChildParentViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildParentViewHolder holder, int flatPosition, final ExpandableGroup group, final int childIndex) {

        final Object childData = group.getItems().get(childIndex);

        if (group.getTitle().equalsIgnoreCase(JsonParsing.Head1)) {
            holder.setOpenShipment(childData);
        } else {
            holder.setPODShipment(childData);
        }

        holder.imglocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String waybillno;
                if (group.getTitle().equalsIgnoreCase(JsonParsing.Head1)) {
                    OpenShipmentsDao openShipmentsDao = (OpenShipmentsDao) childData;
                    waybillno = openShipmentsDao.getWaybillNumber();
                } else {
                    OpenShipmentsDao openShipmentsDao = (OpenShipmentsDao) childData;
                    waybillno = openShipmentsDao.getWaybillNumber();
                }

                trackingViewModel = ViewModelProviders.of((FragmentActivity) context).get(TrackingViewModel.class);

                trackingViewModel.getTrackingLiveData(waybillno).observe((FragmentActivity) context, new Observer<TrackingDetailsDao>() {
                    @Override
                    public void onChanged(TrackingDetailsDao trackingDetailsDao) {
                        trackingDetailsDaodata = trackingDetailsDao;
                        if (trackingDetailsDaodata != null) {
                            Intent i = new Intent(context, MapsActivity.class);
                            i.putExtra("Tracking", trackingDetailsDaodata);
                            context.startActivity(i);
                        }
                    }
                });
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String waybillno;
                if (group.getTitle().equalsIgnoreCase(JsonParsing.Head1)) {
                    OpenShipmentsDao openShipmentsDao = (OpenShipmentsDao) childData;
                    waybillno = openShipmentsDao.getWaybillNumber();
                } else {
                    PODShipmentsDao podShipmentsDao = (PODShipmentsDao) childData;
                    waybillno = podShipmentsDao.getWaybillNumber();
                }
                Intent i = new Intent(context, ShipmentDetails.class);
                i.putExtra("WayBillNo", waybillno);
                context.startActivity(i);
            }
        });

    }

    @Override
    public void onBindGroupViewHolder(TitleParentViewHolder holder, final int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }

}