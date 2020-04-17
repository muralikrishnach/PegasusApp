package com.pegasus.pegasus.model;

import android.os.Bundle;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class TitleParentData extends ExpandableGroup {

    public TitleParentData(String title, List items) {
        super(title, items);
    }
}
