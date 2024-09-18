package com.svw.avatar.models;

import com.svw.avatar.customize.views.Item;


public class WayData2 extends WayData{
    public Item item;

    public WayData2(Item item, int count, boolean isBlock) {
        super(count, isBlock, 0, 0);
        this.item = item;
    }

    @Override
    public String toString() {
        return "isBlock: " + isBlock + "\tcount: " + count;
    }
}
