package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 15/05/15.
 */
public class Item implements Serializable {
    private ArrayList<Object> components;
    private ArrayList<AssemblyPhase> phases;
    private ArrayList<Object> warnings;
    private ArrayList<Tool> tools;
    private String objectId;
    private Integer code;
    private String name;
    private String image;

    public Item(String name, Integer code, String image) {
        this.components = new ArrayList<>();
        this.phases = new ArrayList<AssemblyPhase>();
        this.warnings = new ArrayList<>();
        this.tools = new ArrayList<Tool>();
        this.image = image;
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getObjectId() {
        return objectId;
    }

    public List<Object> getWarnings() {
        return this.warnings;
    }

    public List<Object> getComponents() {
        return this.components;
    }

    public Step getStep(int phase, int step) {
        return this.phases.get(phase).getStep(step);
    }

    public void addWarning(Warning warning) {
        this.warnings.add(warning);
    }

    public List<Tool> getTools() {
        return this.tools;
    }

    public AssemblyPhase getAssamblyPhase(int index) {
        return this.phases.get(index);
    }

    public void addTool(Tool tool) {
        this.tools.add(tool);
    }

    public void addComponent(ItemComponent component) {
        this.components.add(component);
    }

    public void addAssemblyPhase(AssemblyPhase phase) {
        this.phases.add(phase);
    }

    public Component updateComponent(Integer code, Integer count) {
        for (Object itemComponent : this.components) {
            if (((ItemComponent) itemComponent).getCode().intValue() == code.intValue()) {
                ((ItemComponent) itemComponent).updateCount(count);
                return ((ItemComponent) itemComponent).getComponent();
            }
        }

        return null;
    }

    public Tool getToolWithToolId(Integer toolId) {
        for (Tool tool : this.tools) {
            if (tool.getToolId().intValue() == toolId.intValue()) {
                return tool;
            }
        }
        return null;
    }

    public Step getStepByNumber(Integer number) {
        for (AssemblyPhase phase : phases) {
            Step step = phase.getStepByNumber(number);
            if (step != null) {
                return step;
            }
        }
        return null;
    }


}
