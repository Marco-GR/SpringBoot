package com.example.myFirstSpringApp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.helpers.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.concurrent.RecursiveTask;


public class LoggerHelper {

    private static Logger logger;

    public  static Logger writeLog(String context, Exception exception){
        logger = LoggerFactory.getLogger(Util.getCallingClass());

        StringWriter trace = new StringWriter();

        if(exception!=null){

            exception.printStackTrace(new PrintWriter(trace));
            MDC.put("trace",trace.toString());
            try {
                trace.flush();
                trace.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(MDC.get("code")==null)  MDC.put("code",Integer.toHexString(new Random().nextInt()).toUpperCase());
        MDC.put("context",context);

        return logger;
    }
}
