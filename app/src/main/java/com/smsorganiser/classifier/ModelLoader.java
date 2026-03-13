package com.smsorganiser.classifier;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ModelLoader {
    /*
    * This class provides a static funtion for loading the model with uri.
    * */
    public static byte[] loadModel(Context context, String modelName) throws Exception{
        InputStream is = context.getAssets().open(modelName);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[4096];

        int n;
        while((n=is.read(data))!=-1){
            buffer.write(data,0,n);
        }
        return buffer.toByteArray();
    }
}
