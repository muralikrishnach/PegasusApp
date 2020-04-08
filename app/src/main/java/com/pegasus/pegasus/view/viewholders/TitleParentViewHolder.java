package com.pegasus.pegasus.view.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;


import com.pegasus.pegasus.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class TitleParentViewHolder extends GroupViewHolder {

    private AppCompatTextView textHeader;

    public TitleParentViewHolder(@NonNull View itemView) {
        super(itemView);
        textHeader = itemView.findViewById(R.id.idHeader);
    }

    @Override
    public void expand() {
        super.expand();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
    }

    @Override
    public void collapse() {
        super.collapse();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
    }

    public void setGroupName(ExpandableGroup groupName){
        textHeader.setText(groupName.getTitle());
    }

}
