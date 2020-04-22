package com.pegasus.pegasus.view.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;


import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class TitleParentViewHolder extends GroupViewHolder {

    public AppCompatTextView textHeader;
    public AppCompatButton lineseparator;

    public TitleParentViewHolder(@NonNull View itemView) {
        super(itemView);
        lineseparator = itemView.findViewById(R.id.lin1);
        textHeader = itemView.findViewById(R.id.idHeader);

    }

    @Override
    public void expand() {
        super.expand();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
        textHeader.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.header));
        lineseparator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cerulean));
    }

    @Override
    public void collapse() {
        super.collapse();
        textHeader.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
        textHeader.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.aegean));
        lineseparator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.yellow));
    }

    public void setGroupName(final ExpandableGroup groupName){
        textHeader.setText(groupName.getTitle());
    }

}
