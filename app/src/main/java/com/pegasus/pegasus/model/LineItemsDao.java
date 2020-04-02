package com.pegasus.pegasus.model;

import java.io.Serializable;

public class LineItemsDao implements Serializable {

    public String getPieces() {
        return Pieces;
    }

    public void setPieces(String pieces) {
        Pieces = pieces;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    private String Pieces = "";
    private String Description = "";
    private String Weight = "";

}
