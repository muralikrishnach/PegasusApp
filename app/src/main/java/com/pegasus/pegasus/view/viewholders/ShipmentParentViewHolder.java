package com.pegasus.pegasus.view.viewholders;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.pegasus.pegasus.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


public class ShipmentParentViewHolder extends GroupViewHolder {

    private AppCompatTextView textHeader;


    public ShipmentParentViewHolder(View itemView) {
        super(itemView);
        textHeader = itemView.findViewById(R.id.idHeader);
    }


    @Override
    public void collapse() {
        super.collapse();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
//        textHeader.setBackgroundResource(itemView.getContext().getResources().getColor(R.color.skyblue));
    }


    @Override
    public void expand() {
        super.expand();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
//        textHeader.setBackgroundResource(itemView.getContext().getResources().getColor(R.color.darkblue));
    }

    public void setGroupName(ExpandableGroup groupName){
        textHeader.setText(groupName.getTitle());
    }

}
