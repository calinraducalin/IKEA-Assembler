package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 26/06/15.
 */
public class StepComponent implements Serializable{
    private Component component;
    private Integer count;
    private String extraImage;

    public StepComponent(Component component, Integer count,  String extraImage) {
        this.component = component;
        this.count = count;
        this.extraImage = extraImage;
    }

    public void setExtraImage(String extraImage) {
        this.extraImage = extraImage;
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

    public String getExtraImage() {
        return extraImage;
    }
}
