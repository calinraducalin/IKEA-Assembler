package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 26/06/15.
 */
public class Substep implements Serializable{
    private Integer number;
    private String image;

    public Substep(Integer number, String image) {
        this.number = number;
        this.image = image;
    }

    public Integer getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }
}
