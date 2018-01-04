package com.ucs.ganzhou.app.api.Config;

import com.ucs.ganzhou.app.api.service.UspService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UspConfig {

    @Bean
    public UspService getUspService(@Value("${ucs.usp.system-id}")  String systemId , @Value("${ucs.usp.url}") String url) throws Throwable{
        return  new UspService(url,systemId);
    }
}
