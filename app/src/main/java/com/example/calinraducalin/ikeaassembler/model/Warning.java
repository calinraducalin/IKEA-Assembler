package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 29/04/15.
 */
public class Warning implements Serializable{
    private String image;
    private String text;

    public Warning(String image, String text) {
        this.image = image;
        this.text = text;
    }

    public String  getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
