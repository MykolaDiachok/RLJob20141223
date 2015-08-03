package com.radioline.master.soapconnector;

/**
 * Created by master on 16.11.2014.
 */
public class TaskProgress {
    static int percentage1;
    static int percentage2;
    static String message;
    static Boolean isCancelled;

    TaskProgress(int percentage1, int percentage2, String message, Boolean isCancelled) {
        this.percentage1 = percentage1;
        this.percentage2 = percentage2;
        this.message = message;
        this.isCancelled = isCancelled;
    }
}
