package com.smsorganiser.classifier;

import android.util.Log;

import com.smsorganiser.model.SMSMessage;

import ai.onnxruntime.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SMSClassifier {
    private OrtEnvironment env;
    private OrtSession session;

    public SMSClassifier(byte[] modelBytes) throws OrtException{
        env = OrtEnvironment.getEnvironment();
        session = env.createSession(modelBytes);
    }

    public void classify(ArrayList<SMSMessage> listOfSMS) throws Exception{
        int noOfSMS = listOfSMS.size();
        String[][] bodies = new String[listOfSMS.size()][1];;
        String[][] addresses = new String[listOfSMS.size()][1];
        for(int i=0;i<noOfSMS;i++){
            bodies[i][0] = listOfSMS.get(i).getSmsBody();
            addresses[i][0] = listOfSMS.get(i).getSmsAddress();
        }

        OnnxTensor bodyTensor = OnnxTensor.createTensor(env, bodies);
        OnnxTensor addressTensor = OnnxTensor.createTensor(env, addresses);

        HashMap<String,OnnxTensor> input = new HashMap<>();

        input.put("body", bodyTensor);
        input.put("address", addressTensor);

        OrtSession.Result results = session.run(input);

        String[] predictions = (String[]) results.get(0).getValue();

        for(int i=0;i<noOfSMS; i++){
            listOfSMS.get(i).setSmsCategory(predictions[i]);
        }
        Log.i("Prediction done for:", String.valueOf(noOfSMS));
    }

}
