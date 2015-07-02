package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;

/**
 * Created by calinraducalin on 23/06/15.
 */
public class Tool implements Serializable{
    private String name;
    private Integer toolId;
    private String image;

    public Tool(String name, String image, int toolId) {
        this.name = name;
        this.image = image;
        this.toolId = toolId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getToolId() {
        return toolId;
    }
}
