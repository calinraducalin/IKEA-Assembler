package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 23/06/15.
 */
public class ItemComponent implements Serializable{
    private Component component;
    private Integer count;

    public ItemComponent(Component component, Integer count) {
        this.component = component;
        this.count = count;
    }

    public Component getComponent() {
        return this.component;
    }

    public Integer getCode() {
        return this.component.getCode();
    }

    public Integer getCount() {
        return this.count;
    }

    public String getImage() {
        return this.component.getImage();
    }

    public void updateCount(Integer count) {
        this.count += count;
    }
}
