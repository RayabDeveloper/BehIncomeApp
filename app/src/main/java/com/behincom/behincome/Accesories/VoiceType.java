package com.behincom.behincome.Accesories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;

public class VoiceType {

    public VoiceType(Context context, int IntentNumber){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());// identifying your application to the Google service
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "نام فروشگاه را بگویید");// hint in the dialog
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);// hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);// number of results
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR");// recognition language
        ((Activity)context).startActivityForResult(intent, IntentNumber);
    }

}
