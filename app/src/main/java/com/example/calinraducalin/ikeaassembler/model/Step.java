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
    private Tool tool;

    public Step(Integer number, Tool tool) {
        this.components = new ArrayList<StepComponent>();
        this.substeps = new ArrayList<Substep>();
        this.number = number;
        this.tool = tool;
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
}
