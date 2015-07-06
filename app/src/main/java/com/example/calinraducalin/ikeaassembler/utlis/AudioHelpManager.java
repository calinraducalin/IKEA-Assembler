package com.example.calinraducalin.ikeaassembler.utlis;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;

/**
 * Created by calinraducalin on 18/06/15.
 */
public class AudioHelpManager {

    private static AudioHelpManager sharedInstance;
    private TextToSpeech textToSpeech;
    private HashMap<String, String> map;
    private String leftToRead;

    public AudioHelpManager(){
        map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
    }

    public static AudioHelpManager getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new AudioHelpManager();
        }

        return sharedInstance;
    }

    public void setContext(Context context) {
        this.setupTextToSpeech(context);
    }

    public void disable(){
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public void speakTheText(String str){
//        Log.e("SPEAK TEXT!!!!", "SPEAK TEXT");
        // If not yet initialized, queue up the text.
        if (textToSpeech == null) {
//            queuedText = text;
            return;
        }
//        queuedText = null;
        // Before speaking the current text, stop any ongoing speech.
        textToSpeech.stop();
        // Speak the text.
        if (textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, map) == TextToSpeech.ERROR) {
            leftToRead = str;
        }
    }

    private void setupTextToSpeech(Context context) {

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
//                Log.e("INIT TTS", "INIT");
                if (status == TextToSpeech.SUCCESS) {
                    if (leftToRead != null) {
                        speakTheText(leftToRead);
                        leftToRead = null;
                    }
//                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
//                        @Override
//                        public void onStart(String s) {
//                        }
//
//                        @Override
//                        public void onDone(String s) {
//                            Log.d("text to speech", "end");
//                        }
//
//                        @Override
//                        public void onError(String s) {
//                            Log.d("text to speech", "error");
//
//                        }
//                    });
                }
            }

        });
    }

}
