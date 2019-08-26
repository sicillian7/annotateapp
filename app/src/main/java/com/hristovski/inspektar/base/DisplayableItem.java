package com.hristovski.inspektar.base;

public interface DisplayableItem {

    String getMainItem();

    String getSubItem();

    String getImageResourceName();

    int getImageResourceId();

    boolean isSelected();

    int getId();

}