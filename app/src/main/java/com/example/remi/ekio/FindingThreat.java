package com.example.remi.ekio;

import android.content.Context;

/**
 * Created by Hoang Nam on 15/06/2016.
 */
public class FindingThreat extends Thread {
    private ObjectDetection ODectect;
    private Context context;

    public FindingThreat(Context c, String p){
        context = c;
        ODectect = new ObjectDetection(p, c);
    }

    public ObjectDetection getObjectDetection(){
        return ODectect;
    }

    public void prepare(){

    }

    @Override
    public void run() {
        synchronized (this){
            /*
            try {
                sleep(6000,1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            ODectect.match2(context);
            notify();
        }

    }


}
