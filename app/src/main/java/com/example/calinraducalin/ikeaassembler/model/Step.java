package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 26/06/15.
 */
public class Step implements Serializable{
    private ArrayList<StepComponent> components;
    private ArrayList<Warning> warnings;
    private ArrayList<Substep> substeps;
    private Integer number;
    private Integer time;
    private Tool tool;

    public Step(Integer number, Tool tool, Integer time) {
        this.components = new ArrayList<>();
        this.substeps = new ArrayList<>();
        this.number = number;
        this.tool = tool;
        this.time = time;
    }

    public List getSubsteps() {
        return this.substeps;
    }

    public void addSubStep(Substep substep) {
        this.substeps.add(substep);
    }

    public void addComponent(StepComponent component) {
        this.components.add(component);
    }

    public void addWarning(Warning warning) {
        if (this.warnings == null) {
            this.warnings = new ArrayList<Warning>();
        }
        this.warnings.add(warning);
    }

    public Integer getNumber() {
        return number;
    }

    public List getToolsAndComponents(){
        List toolAndComponents = new ArrayList();
        if (tool != null) {
            toolAndComponents.add(tool);
        }
        for (StepComponent component : this.components) {
            toolAndComponents.add(component);
            String extraImage = component.getExtraImage();
            if (extraImage != null) {
                toolAndComponents.add(extraImage);
            }
        }

        return toolAndComponents;
    }

    public Integer getTime() {
        return time;
    }

    public ArrayList<Warning> getWarnings() {
        return warnings;
    }
}
