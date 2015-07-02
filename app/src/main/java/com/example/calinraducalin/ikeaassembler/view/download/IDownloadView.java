package com.example.calinraducalin.ikeaassembler.view.download;

import com.example.calinraducalin.ikeaassembler.model.Item;

/**
 * Created by calinraducalin on 01/07/15.
 */
public interface IDownloadView {
    void updateProgressActivity(String key, String value);
    void succesfullyLoadItem(Item item);
    void processingItemStarted();
    void networkError();
    void itemNotFound();
    void unknownError();
}
