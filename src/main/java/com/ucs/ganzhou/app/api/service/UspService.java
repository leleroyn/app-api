package com.ucs.ganzhou.app.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucsmy.usp.api.ApiBaseService;
import ucsmy.usp.api.EarlyWarn;
import ucsmy.usp.api.Log;


public class UspService {
    private  static Logger logger = LoggerFactory.getLogger(UspService.class);
    private static volatile boolean inited = false;

    public UspService(String url,String systemId) throws Throwable{
        if(!inited) {
            ApiBaseService.initialize(url, systemId);
            inited = true;
        }
    }

    public  void warn(String title, String message) {
        try {
            EarlyWarn.AlarmLog(title, message);
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }
    public  void  log(String title,String message) {
        try {
            Log.LogText(title,message,"");
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }
}
