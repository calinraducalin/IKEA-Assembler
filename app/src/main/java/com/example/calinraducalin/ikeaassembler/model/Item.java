package com.example.calinraducalin.ikeaassembler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 15/05/15.
 */
public class Item implements Serializable {
    private ArrayList<ItemComponent> components;
    private ArrayList<AssemblyPhase> phases;
    private ArrayList<Warning> warnings;
    private ArrayList<Tool> tools;
    private Integer code;
    private Integer time;
    private String name;
    private String image;

    public Item(String name, Integer code, String image) {
        this.components = new ArrayList<>();
        this.phases = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.tools = new ArrayList<>();
        this.image = image;
        this.code = code;
        this.name = name;
        this.time = 0;
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

    public List getWarnings() {
        return this.warnings;
    }

    public List getToolsAndComponents() {
        List toolsAndComponents = new ArrayList(tools);
        toolsAndComponents.addAll(this.components);

        return toolsAndComponents;
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

    public boolean isLastPhase(int phase) {
        return this.phases.size() == phase + 1;
    }

    public boolean shouldRepeatPhase(int phase) {
        return (this.phases.get(phase)).getRepeat() > 1;
    }

    public int getPhasesCount() {
        return this.phases.size();
    }

    public int getStepsCountForPhase(int phaseIndex) {
        return (this.phases.get(phaseIndex)).getStepsCount();
    }

    public int computeTimeLeft(int phaseIndex, int stepIndex) {
        int timeLeft = 0;

        int totalPhases = this.phases.size();
        for (int i = phaseIndex; i < totalPhases; i++) {
            int startStepIndex = i == phaseIndex ? stepIndex : 0;
            AssemblyPhase currentPhase = this.phases.get(i);
            int totalSteps = currentPhase.getStepsCount();
            int repeat = currentPhase.getRepeat();
            for (int j = startStepIndex; j < totalSteps; j++) {
                timeLeft += repeat * currentPhase.getStep(j).getTime();
            }
        }

        return timeLeft;
    }

    public ArrayList<AssemblyPhase> getPhases() {
        return phases;
    }

    public void updateTime(int value) {
        time += value;
    }

    public Integer getTime() {
        return time;
    }
}
