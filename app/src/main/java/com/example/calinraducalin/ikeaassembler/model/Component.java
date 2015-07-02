package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 23/06/15.
 */
public class Component implements Serializable{
    private Integer code;
    private String image;

    public Component(Integer code, String image) {
        this.code = code;
        this.image = image;
    }

    public Integer getCode() {
        return code;
    }

    public String getImage() {
        return image;
    }

}
