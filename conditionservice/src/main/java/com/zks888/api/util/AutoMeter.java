package com.zks888.api.util;

import com.zks888.api.entity.ApiCasedata;
import com.zks888.api.service.ApiCasedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AutoMeter {
    @Autowired
    private  ApiCasedataService apiCasedataService1;
    private static ApiCasedataService apiCasedataService;

    public static Long caseid;

    @PostConstruct
    public  void init()
    {
        apiCasedataService=apiCasedataService1;
    }

    public static String GetRequestValue(String Param,String Property)
    {
        String Value="";
        ApiCasedata apiCasedata= apiCasedataService.GetCaseDatasByCaseIDAndApiparamAndType(caseid,Param,Property);
        if(apiCasedata!=null)
        {
            Value=apiCasedata.getApiparamvalue();
        }
        return Value;
    }

    public static void SetRequestValue(String Param,String Property,String Value)
    {
        apiCasedataService.UpdateByCaseIDAndApiparam(caseid,Param,Property,Value);
    }
}
