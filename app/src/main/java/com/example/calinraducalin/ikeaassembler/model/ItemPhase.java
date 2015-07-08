package com.example.calinraducalin.ikeaassembler.model;

/**
 * Created by calinraducalin on 08/07/15.
 */
public class ItemPhase {
    private int type;
    private int count;
    private String text;

    public ItemPhase(int type, int count, String text) {
        this.type = type;
        this.count = count;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getText() {
        return text;
    }
}
