package com.example.calinraducalin.ikeaassembler.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.model.Component;
import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.model.ItemComponent;
import com.example.calinraducalin.ikeaassembler.model.Step;
import com.example.calinraducalin.ikeaassembler.model.StepComponent;
import com.example.calinraducalin.ikeaassembler.model.Substep;
import com.example.calinraducalin.ikeaassembler.model.Tool;
import com.example.calinraducalin.ikeaassembler.model.Warning;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by calinraducalin on 22/06/15.
 */
public class Parser {
    private static final String repeatKey = "repeat";
    private static final String numberKey = "number";
    private static final String nameKey = "name";
    private static final String imageKey = "image";
    private static final String textKey = "text";
    private static final String itemIdKey = "iid";
    private static final String countKey = "count";
    private static final String componentIdKey = "cid";
    private static final String toolIdKey = "tid";
    private static final String stepId = "sid";
    private static final String phasesKey = "phases";
    private static final String warningsKey = "warnings";
    private static final String stepsKey = "steps";
    private static final String substepsKey = "substeps";
    private static final String statusKey = "status";
    private static final String totalStepsKey = "totalSteps";
    private static final String stepKey = "step";

    static Item parseItem(ParseObject object, final ItemsServiceDelegate delegate) {
        String name = ((String) object.get(nameKey));
        Integer itemId = ((Integer) object.get(itemIdKey));
        String folderName = "/" + itemId.toString();
        String imageFileName = storeImage(object, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "ITEM");
            return null;
        }

        Item item = new Item(name, itemId, imageFileName);
        delegate.updateProgressActivity(nameKey, name);

        int totalSteps = 0;
        //find phases
        List<ParseObject> objects = ((List) object.get(phasesKey));
        if (objects != null) {
            for (ParseObject phase : objects) {
                List<ParseObject> steps = ((List) phase.get(stepsKey));
                totalSteps += steps.size();
            }
        }
        delegate.updateProgressActivity(totalStepsKey, "" + totalSteps);

        //parse phases
        objects = ((List) object.get(phasesKey));
        if (objects != null) {
            for (ParseObject phase : objects) {
                if (phase.isDataAvailable()) {
                    AssemblyPhase assemblyPhase = parseAssemblyPhaseForItem(item, phase, folderName, delegate);
                    item.addAssemblyPhase(assemblyPhase);
                }
            }
        }

        //parse warnings
        objects = ((List) object.get(warningsKey));
        if (objects != null) {
            delegate.updateProgressActivity(statusKey, warningsKey);
            for (ParseObject warningObject : objects) {
                if (warningObject.isDataAvailable()) {
                    Warning warning = parseWarning(warningObject, folderName);
                    item.addWarning(warning);
                }
            }
        }

        return item;
    }

    static void parseComponentsForItem(Item item, List<ParseObject> components) {
        Log.d("PROCESS COMPONENTS", "");
        for (ParseObject object : components) {
            if (object.isDataAvailable()) {
                ParseObject stepObject = object.getParseObject(stepId);
                Integer stepNumber = ((Integer) stepObject.get(numberKey));
                if (stepNumber != null) {
                    Step step = item.getStepByNumber(stepNumber);
                    if (step != null) {
                        parseComponentForStep(item, step, object);
                    }
                }
            }
        }
    }

    private static AssemblyPhase parseAssemblyPhaseForItem(Item item, ParseObject object, String folderName, ItemsServiceDelegate delegate) {
        String name = ((String) object.get(nameKey));
        Integer repeat = ((Integer) object.get(repeatKey));
        String imageFileName = storeImage(object, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "PHASE");
            return null;
        }

        AssemblyPhase phase = new AssemblyPhase(name, imageFileName, repeat);

        List<ParseObject> steps = ((List) object.get(stepsKey));
        if (steps != null) {
            for (ParseObject stepObject : steps) {
                if (stepObject.isDataAvailable()) {
                    Step step = parseStep(item, stepObject, folderName, delegate);
                    phase.addStep(step);
                }
            }
        }

        return phase;
    }

    private static Step parseStep(Item item, ParseObject object, String folderName, ItemsServiceDelegate delegate) {
        Integer number = ((Integer) object.get(numberKey));
        delegate.updateProgressActivity(stepKey, number.toString());
        Tool tool = null;

        Log.d("PROCESS STEP", "" + number);

        //parse tool for step
        ParseObject toolObject = ((ParseObject) object.get(toolIdKey));
        if (toolObject != null) {
            if (toolObject.isDataAvailable()) {
                Integer toolId = ((Integer) toolObject.get(toolIdKey));
                tool = item.getToolWithToolId(toolId);
                if (tool == null) {
                   tool = parseTool(toolObject, folderName);
                   item.addTool(tool);
                }
            }
        }
        Step step = new Step(number, tool);

        //parse warnings for step
        List<ParseObject> objects = ((List) object.get(warningsKey));
        if (objects != null) {
            for (ParseObject warningObject : objects) {
                if (warningObject.isDataAvailable()) {
                    Warning warning = parseWarning(warningObject, folderName);
                    step.addWarning(warning);
                }
            }
        }

//        parse substeps for step
        objects = ((List) object.get(substepsKey));
        if (objects != null) {
            for (ParseObject substepObject : objects) {
                if (substepObject.isDataAvailable()) {
                    Substep substep = parseSubstepsForStep(substepObject, folderName);
                    step.addSubStep(substep);
                }
            }
        }

        return step;
    }

    private static void parseComponentForStep(Item item, Step step, ParseObject object) {
        String folderName = "/" + item.getCode();
        ParseObject componentObject = ((ParseObject) object.get(componentIdKey));
        if (componentObject != null) {
            Integer count = ((Integer) object.get(countKey));
            Integer code = ((Integer) componentObject.get(componentIdKey));

            Component component = item.updateComponent(code, count);
            if (component == null) {
                component = parseComponent(componentObject, folderName);
                ItemComponent itemComponent = new ItemComponent(component, count);
                item.addComponent(itemComponent);
            }

            String imageFileName = storeImage(object, imageKey, folderName);
            StepComponent stepComponent = new StepComponent(component, count, imageFileName);

            step.addComponent(stepComponent);
        }
    }

    private static Component parseComponent(ParseObject componentObject, String folderName) {
        Integer code = ((Integer) componentObject.get(componentIdKey));
        String imageFileName = storeImage(componentObject, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "COMPONENT");
            return null;
        }

        Component component = new Component(code, imageFileName);

        return component;
    }

    private static Tool parseTool(ParseObject object, String folderName) {
        String name = ((String) object.get(nameKey));
        Integer toolId = object.getInt(toolIdKey);
        String imageFileName = storeImage(object, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "TOOL");
            return null;
        }

        Tool tool = new Tool(name, imageFileName, toolId);

        return tool;
    }

    private static Warning parseWarning(ParseObject object, String folderName) {
        String text = ((String) object.get(textKey));
        String imageFileName = storeImage(object, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "WARNING");
            return null;
        }

        Warning warning = new Warning(imageFileName, text);

        return warning;
    }

    private static Substep parseSubstepsForStep(ParseObject object, String folderName) {
        Integer number = ((Integer) object.get(numberKey));
        String imageFileName = storeImage(object, imageKey, folderName);

        if (imageFileName == null) {
            Log.wtf("IMAGE STORE ERROR", "WARNING");
            return null;
        }

        Substep substep = new Substep(number, imageFileName);

        return substep;
    }

    private static String storeImage(ParseObject parseObject, final String key, String folderName) {
        ParseFile fileObject = ((ParseFile) parseObject.get(key));
        byte[] data;
        String fileName = null;

        if(fileObject!=null) {
            try {
                data = fileObject.getData();
                Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length);

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + folderName;
                File dir = new File(filePath);
                if(!dir.exists())
                    dir.mkdirs();
                File fileToStored = new File(dir, fileObject.getName());
                try {
                    FileOutputStream outputStream = new FileOutputStream(fileToStored);
                    bMap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    if (bMap != null) {
                        bMap.recycle();
                        bMap = null;
                    }

                    fileName = fileObject.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return fileName;
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }

        return fileName;
    }

}
