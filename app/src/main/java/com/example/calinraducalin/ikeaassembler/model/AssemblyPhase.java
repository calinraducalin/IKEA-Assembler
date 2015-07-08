package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by calinraducalin on 26/06/15.
 */
public class AssemblyPhase implements Serializable{
    private ArrayList<Step> steps;
    private String name;
    private int repeat;
    private String image;

    public AssemblyPhase(String name, String imageFileName, int repeat) {
        this.steps = new ArrayList<Step>();
        this.repeat = repeat;
        this.image = imageFileName;
        this.name = name;
    }

    public int getStepsCount() {
        return this.steps.size();
    }

    public int getRepeat() {
        return repeat;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void addStep(Step step) {
        this.steps.add(step);
    }

    public Step getStep(int index) {
        return this.steps.get(index);
    }

    public Step getStepByNumber(Integer number) {
        for (Step step : steps) {
            if (step.getNumber() == number) {
                return step;
            }
        }
        return null;
    }

    public boolean isLastStep(int stepIndex) {
        return this.steps.size() == stepIndex + 1;
    }
}
