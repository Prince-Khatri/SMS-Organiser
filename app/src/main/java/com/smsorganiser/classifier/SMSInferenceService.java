package com.smsorganiser.classifier;

import android.content.Context;

import com.smsorganiser.model.SMSMessage;

import java.util.ArrayList;
import java.util.List;

public class SMSInferenceService {
    /*
     * Singletone class - single instance throughout the app.
    */
    private SMSClassifier classifier;
    private static SMSInferenceService instance;

    public static SMSInferenceService getInstance(Context context) {
        if(instance==null){
            instance = new SMSInferenceService(context);
        }
        return instance;
    }

    public SMSInferenceService(Context context){
        try {
            byte[] modelBytes = ModelLoader.loadModel(context, "sms_model.onnx");
            classifier = new SMSClassifier(modelBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void classifySMS(ArrayList<SMSMessage> listOfMessages) throws Exception{
        int n= listOfMessages.size();

        String [] []  bodies=  new String[n][1];
        String [] []  addresses =  new String[n][1];

        String[] predictions = classifier.classify(listOfMessages);

        for(int i=0;i<n;i++){
            listOfMessages.get(i).setSmsCategory(predictions[i]);
        }
    }


}
