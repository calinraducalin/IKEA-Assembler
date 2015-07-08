package com.example.calinraducalin.ikeaassembler.utils;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.view.download.IDownloadView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * Created by calinraducalin on 22/06/15.
 */
public class ItemsService implements ItemsServiceDelegate{
    private static final String componentsForStepTable = "ComponentsForStep";
    private static final String warningsForStepsKey = "phases.steps.warnings";
    private static final String substepsForStepsKey = "phases.steps.substeps";
    private static final String toolsForStepsKey = "phases.steps.tid";
    private static final String componentsKey = "components";
    private static final String stepsKey = "phases.steps";
    private static final String warningsKey = "warnings";
    private static final String statusKey = "status";
    private static final String phasesKey = "phases";
    private static final String componentId = "cid";
    private static final String itemTable = "Item";
    private static final String stepId = "sid";
    private static final String itemId = "iid";

//    private ItemsManagerDelegate delegate;
    private IDownloadView downloadView;
    private ItemsService self;
    private boolean isProcessCanceled;


    public ItemsService(IDownloadView delegate) {
        this.downloadView = delegate;
        self = this;
    }

    @Override
    public void updateProgressActivity(String key, String value) {
        if (this.downloadView != null) {
            this.downloadView.updateProgressActivity(key, value);
        }
    }

    public void getItemByID(Integer iid) {
        isProcessCanceled = false;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(itemTable);
        query.whereEqualTo(itemId, iid);
        query.include(phasesKey);
        query.include(warningsKey);
        query.include(stepsKey);
        query.include(toolsForStepsKey);
        query.include(warningsForStepsKey);
        query.include(substepsForStepsKey);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (isProcessCanceled) {
                    Log.wtf("PROCESS ITEM", "CANCELED");
                    return;
                }
                if (e == null) {
                    downloadView.processingItemStarted();
                    Item item = Parser.parseItem(object, self);
                    getComponentsForItem(item);
                } else {
                    handleParseException(e);
                }
            }
        });
    }

    public void cancelProcessItem() {
        isProcessCanceled = true;
    }


    private void getComponentsForItem(final Item item) {
        updateProgressActivity(statusKey, componentsKey);

        ParseQuery<ParseObject> innerQuery = new ParseQuery<ParseObject>(itemTable);
        innerQuery.whereEqualTo(itemId, item.getCode());
        ParseQuery query = new ParseQuery(componentsForStepTable);
        query.whereMatchesQuery(itemId, innerQuery);
        query.include(componentId);
        query.include(stepId);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> components, ParseException e) {
                if (e == null) {
                    Parser.parseComponentsForItem(item, components);
                    downloadView.succesfullyLoadItem(item);
                } else {
                    handleParseException(e);
                }
            }
        });
    }

    private void handleParseException(ParseException e) {
        Log.d("Parse Error", "" + e.getCode());

        switch (e.getCode()) {
            case ParseException.OBJECT_NOT_FOUND:
                downloadView.itemNotFound();
                break;
            case ParseException.CONNECTION_FAILED:
                downloadView.networkError();
                break;
            default:
                downloadView.unknownError();
                break;
        }
    }

}
