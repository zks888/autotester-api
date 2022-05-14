package com.zks888.api.core.service;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION
 @create 2020/10/17
*/

import com.alibaba.fastjson.JSON;
import com.zks888.api.dto.RequestObject;
import com.zks888.api.dto.TestResponeData;
import com.zks888.api.entity.*;
import com.zks888.api.service.*;
import com.zks888.api.util.RadomVariables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




/**
 * 用例数据
 */
@Component
@Slf4j
public class TestCaseHelp  {

    public static TestCaseHelp tch;

    @PostConstruct
    public void init() {
        tch = this;
        tch.apicasesVariablesService = this.apicasesVariablesService;
        tch.testvariablesService = this.testvariablesService;
        tch.testvariablesValueService = this.testvariablesValueService;
        tch.variablesService = this.variablesService;
        tch.dbvariablesService = this.dbvariablesService;
    }

    @Autowired(required = false)
    private ApicasesVariablesService apicasesVariablesService;

    @Autowired(required = false)
    private TestvariablesService testvariablesService;

    @Autowired(required = false)
    private TestvariablesValueService testvariablesValueService;

    @Autowired(required = false)
    private VariablesService variablesService;

    @Autowired(required = false)
    private DbvariablesService dbvariablesService;



    public RequestObject GetCaseRequestDataForDebug(HashMap<String, String>  DBValueMap,HashMap<String, String>  InterfaceValueMap,List<ApiCasedata> apiCasedataList, Api api, Apicases apicases, Deployunit deployunit, Macdepunit macdepunit, Machine machine) throws Exception {
        RequestObject ro=new RequestObject();
        try
        {
            // url请求资源路径
            String path = api.getPath();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            String casetype=apicases.getCasetype();
            String expect=apicases.getExpect();

            //获取请求响应的数据格式
            String requestcontenttype =api.getRequestcontenttype();
            String responecontenttype =api.getResponecontenttype();
            // http请求方式 get，post
            String method = api.getVisittype();
            // api风格
            String apistyle =api.getApistyle();
            // 协议 http,https,rpc
            String protocal = deployunit.getProtocal().toLowerCase();
            // 发布单元端口
            String port = deployunit.getPort();
            // 获取发布单元访问方式，ip或者域名
            String deployunitvisittype =macdepunit.getVisittype();
            // 根据访问方式来确定ip还是域名
            String testserver = "";
            String resource = "";
            if(deployunitvisittype.equals(new String("ip")))
            {
                testserver =machine.getIp();
                resource = protocal + "://" + testserver + ":" + port + path;
            }
            else
            {
                testserver= macdepunit.getDomain();
                resource = protocal + "://" + testserver  + path;
            }

            HashMap<String, String> InterfaceMap = InterfaceValueMap;
            HashMap<String, String> DBMap = DBValueMap;
//            HashMap<String, String> ScriptMap = GetMap(scriptValueList);

            List<Variables> variablesList = tch.variablesService.listAll();
            HashMap<String, String> RadomMap = new HashMap<>();
            for (Variables va : variablesList) {
                RadomMap.put(va.getVariablesname(), va.getVariablestype());
            }

            //url中的变量替换
            //1.随机变量替换
            for (Variables variables : variablesList) {
                String VariableName = "[" + variables.getVariablesname() + "]";
                if (resource.contains(VariableName)) {
                    Object VariableValue = GetRadomValue(variables.getVariablesname());
                    resource = resource.replace(VariableName, VariableValue.toString());
                }
            }
            //2.接口变量替换
            for (String Interfacevariables : InterfaceMap.keySet()) {
                String UseInterfacevariables = "<" + Interfacevariables + ">";
                if (resource.contains(UseInterfacevariables)) {
                    Object VariableValue = InterfaceMap.get(Interfacevariables);// GetVariablesObjectValues("$" +Interfacevariables, ParamsValuesMap);
                    resource = resource.replace(UseInterfacevariables, VariableValue.toString());
                }
            }

            //3.数据库变量替换
            for (String DBvariables : DBMap.keySet()) {
                String UseDBvariables = "<<" + DBvariables + ">>";
                if (resource.contains(UseDBvariables)) {
                    Object VariableValue = DBMap.get(DBvariables);
                    resource = resource.replace(UseDBvariables, VariableValue.toString());
                }
            }

            HashMap<String, ApiCasedata> headmap=fixhttprequestdatas("Header",apiCasedataList);
            HashMap<String, ApiCasedata> bodymap=fixhttprequestdatas("Body",apiCasedataList);
            HashMap<String, ApiCasedata> paramsmap=fixhttprequestdatas("Params",apiCasedataList);

            //Header
            HttpHeader header = new HttpHeader();
            header = AddHeaderByRequestContentType(header, requestcontenttype);
            if(headmap.size()>0)
            {
                for (String key : headmap.keySet()) {
                    String Value = headmap.get(key).getApiparamvalue().trim();
                    Object ObjectValue = Value;
                    if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                    {
                        ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                    }
                    header.addParam(key, ObjectValue);
                }
            }
            //url参数
            HttpParamers paramers = new  HttpParamers();
            // 设置参数
            if(paramsmap.size()>0)
            {
                for (String key : paramsmap.keySet()) {
                    String Value = paramsmap.get(key).getApiparamvalue().trim();
                    String DataType = paramsmap.get(key).getParamstype();
                    Object ObjectValue = Value;
                    if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                    {
                        ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                    }
                    Object Result = GetDataByType(ObjectValue.toString(), DataType);
                    paramers.addParam(key, Result);
                }
            }
            //Body参数
            HttpParamers Bodyparamers = new HttpParamers();
            //Body内容
            String PostData = "";
            // 设置Body
            if(bodymap.size()>0)
            {
                if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                    for (String key : bodymap.keySet()) {
                        String DataType = bodymap.get(key).getParamstype();
                        String Value = bodymap.get(key).getApiparamvalue().trim();
                        Object ObjectValue = Value;
                        if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                        {
                            ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                        }
                        Object Result = GetDataByType(ObjectValue.toString(), DataType);
                        Bodyparamers.addParam(key, Result);
                    }
                    try {
                        PostData=GetParasPostData(requestcontenttype,Bodyparamers);
                    } catch (IOException e) {
                        TestCaseHelp.log.info("处理Body参数数据异常："+e.getMessage());
                        throw new Exception("处理Body参数数据异常："+e.getMessage());
                    }
                }
                else
                {
                    for (String Key : bodymap.keySet()) {
                        PostData = bodymap.get(Key).getApiparamvalue();
                        //1.替换随机变量
                        for (String VaraibaleName : RadomMap.keySet()) {
                            String UseVariableName = "[" + VaraibaleName + "]";
                            if (PostData.contains(UseVariableName)) {
                                Object VariableValue = GetRadomValue(VaraibaleName);
                                PostData = PostData.replace(UseVariableName, VariableValue.toString());
                            }
                        }
                        //2.替换接口变量
                        for (String VariableName : InterfaceMap.keySet()) {
                            String UseVariableName = "<" + VariableName + ">";
                            if (PostData.contains(UseVariableName)) {
                                String VariableValue = InterfaceMap.get(VariableName);
                                PostData = PostData.replace(UseVariableName, VariableValue);
                            }
                        }
                        //3.替换数据库变量
                        for (String VariableName : DBMap.keySet()) {
                            String UseVariableName = "<<" + VariableName + ">>";
                            if (PostData.contains(UseVariableName)) {
                                String VariableValue = DBMap.get(VariableName);
                                PostData = PostData.replace(UseVariableName, VariableValue);
                            }
                        }
                    }
                }
            }
            TestCaseHelp.log.info("处理Body完后，PostData："+PostData);
            ro.setPostData(PostData);
            ro.setExpect(expect);
            ro.setCasetype(casetype);
            ro.setHeader(header);
            ro.setProtocal(protocal);
            ro.setApistyle(apistyle);
            ro.setParamers(paramers);
            ro.setBodyparamers(Bodyparamers);
            ro.setRequestcontenttype(requestcontenttype);
            ro.setRequestmMthod(method);
            ro.setResource(resource);
            ro.setResponecontenttype(responecontenttype);
        }
        catch (Exception ex)
        {
            TestCaseHelp.log.info("GetCaseRequestData异常："+ex.getMessage());
            throw new Exception("接口子条件用例："+apicases.getCasename()+" 准备数据异常："+ex.getMessage());
        }
        return ro;
    }


    // 拼装请求需要的用例数据
    public RequestObject GetCaseRequestData(Dispatch dispatch, List<ApiCasedata> apiCasedataList, Api api, Apicases apicases, Deployunit deployunit, Macdepunit macdepunit, Machine machine) throws Exception {
        RequestObject ro=new RequestObject();
        try
       {
           // url请求资源路径
           String path = api.getPath();
           if (!path.startsWith("/")) {
               path = "/" + path;
           }
           String casetype=apicases.getCasetype();
           String expect=apicases.getExpect();

           //获取请求响应的数据格式
           String requestcontenttype =api.getRequestcontenttype();
           String responecontenttype =api.getResponecontenttype();
           // http请求方式 get，post
           String method = api.getVisittype();
           // api风格
           String apistyle =api.getApistyle();
           // 协议 http,https,rpc
           String protocal = deployunit.getProtocal().toLowerCase();
           // 发布单元端口
           String port = deployunit.getPort();
           // 获取发布单元访问方式，ip或者域名
           String deployunitvisittype =macdepunit.getVisittype();
           // 根据访问方式来确定ip还是域名
           String testserver = "";
           String resource = "";
           if(deployunitvisittype.equals(new String("ip")))
           {
               testserver =machine.getIp();
               resource = protocal + "://" + testserver + ":" + port + path;
           }
           else
           {
               testserver= macdepunit.getDomain();
               resource = protocal + "://" + testserver  + path;
           }

           Condition interfacecon = new Condition(TestvariablesValue.class);
           interfacecon.createCriteria().andCondition("planid = " + dispatch.getExecplanid())
           .andCondition("batchname = '" + dispatch.getBatchname()+"'")
           .andCondition("variablestype = '"+"接口"+"'");
           List<TestvariablesValue> interfaceValueList = tch.testvariablesValueService.listByCondition(interfacecon);

           Condition dbcon = new Condition(TestvariablesValue.class);
           dbcon.createCriteria().andCondition("planid = " + dispatch.getExecplanid())
                   .andCondition("batchname = '" + dispatch.getBatchname()+"'")
                   .andCondition("variablestype = '"+"数据库"+"'");
           List<TestvariablesValue> dbValueList = tch.testvariablesValueService.listByCondition(dbcon);

           Condition scriptcon = new Condition(TestvariablesValue.class);
           scriptcon.createCriteria().andCondition("planid = " + dispatch.getExecplanid())
                   .andCondition("batchname = '" + dispatch.getBatchname()+"'")
                   .andCondition("variablestype = '"+"脚本"+"'");
           List<TestvariablesValue> scriptValueList = tch.testvariablesValueService.listByCondition(scriptcon);


           HashMap<String, String> InterfaceMap = GetMap(interfaceValueList);
           HashMap<String, String> DBMap = GetMap(dbValueList);
           HashMap<String, String> ScriptMap = GetMap(scriptValueList);


           List<Variables> variablesList = tch.variablesService.listAll();
           HashMap<String, String> RadomMap = new HashMap<>();
           for (Variables va : variablesList) {
               RadomMap.put(va.getVariablesname(), va.getVariablestype());
           }

           //url中的变量替换
           //1.随机变量替换
           for (Variables variables : variablesList) {
               String VariableName = "[" + variables.getVariablesname() + "]";
               if (resource.contains(VariableName)) {
                   Object VariableValue = GetRadomValue(variables.getVariablesname());
                   resource = resource.replace(VariableName, VariableValue.toString());
               }
           }
           //2.接口变量替换
           for (String Interfacevariables : InterfaceMap.keySet()) {
               String UseInterfacevariables = "<" + Interfacevariables + ">";
               if (resource.contains(UseInterfacevariables)) {
                   Object VariableValue = InterfaceMap.get(Interfacevariables);// GetVariablesObjectValues("$" +Interfacevariables, ParamsValuesMap);
                   resource = resource.replace(UseInterfacevariables, VariableValue.toString());
               }
           }

           //3.数据库变量替换
           for (String DBvariables : DBMap.keySet()) {
               String UseDBvariables = "<<" + DBvariables + ">>";
               if (resource.contains(UseDBvariables)) {
                   Object VariableValue = DBMap.get(DBvariables);
                   resource = resource.replace(UseDBvariables, VariableValue.toString());
               }
           }

           HashMap<String, ApiCasedata> headmap=fixhttprequestdatas("Header",apiCasedataList);
           HashMap<String, ApiCasedata> bodymap=fixhttprequestdatas("Body",apiCasedataList);
           HashMap<String, ApiCasedata> paramsmap=fixhttprequestdatas("Params",apiCasedataList);

           //Header
           HttpHeader header = new HttpHeader();
           header = AddHeaderByRequestContentType(header, requestcontenttype);
           if(headmap.size()>0)
           {
               for (String key : headmap.keySet()) {
                   String Value = headmap.get(key).getApiparamvalue().trim();
                   Object ObjectValue = Value;
                   if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                   {
                        ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                   }
                   header.addParam(key, ObjectValue);
               }
           }
           //url参数
           HttpParamers paramers = new  HttpParamers();
           // 设置参数
           if(paramsmap.size()>0)
           {
               for (String key : paramsmap.keySet()) {
                   String Value = paramsmap.get(key).getApiparamvalue().trim();
                   String DataType = paramsmap.get(key).getParamstype().trim();
                   Object ObjectValue =Value ;
                   if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                   {
                       ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                   }
                   Object Result = GetDataByType(ObjectValue.toString(), DataType);
                   paramers.addParam(key, Result);
               }
           }
           //Body参数
           HttpParamers Bodyparamers = new HttpParamers();
           //Body内容
           String PostData = "";
           // 设置Body
           if(bodymap.size()>0)
           {
               if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                   for (String key : bodymap.keySet()) {
                       String Value = bodymap.get(key).getApiparamvalue().trim();
                       String DataType = bodymap.get(key).getParamstype();
                       Object ObjectValue = Value;
                       if((Value.contains("<")&&Value.contains(">"))||(Value.contains("<<")&&Value.contains(">>"))||(Value.contains("[")&&Value.contains("]")))
                       {
                           ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap,DBMap);
                       }
                       Object Result = GetDataByType(ObjectValue.toString(), DataType);
                       Bodyparamers.addParam(key, Result);
                   }
                   try {
                       PostData=GetParasPostData(requestcontenttype,Bodyparamers);
                   } catch (IOException e) {
                       TestCaseHelp.log.info("处理Body参数数据异常："+e.getMessage());
                       throw new Exception("处理Body参数数据异常："+e.getMessage());
                   }
               }
               else
               {
                   for (String Key : bodymap.keySet()) {
                       PostData = bodymap.get(Key).getApiparamvalue();

                       //1.替换随机变量
                       for (String VaraibaleName : RadomMap.keySet()) {
                           String UseVariableName = "[" + VaraibaleName + "]";
                           if (PostData.contains(UseVariableName)) {
                               Object VariableValue = GetRadomValue(VaraibaleName);
                               PostData = PostData.replace(UseVariableName, VariableValue.toString());
                           }
                       }
                       //2.替换接口变量
                       for (String VariableName : InterfaceMap.keySet()) {
                           String UseVariableName = "<" + VariableName + ">";
                           if (PostData.contains(UseVariableName)) {
                               String VariableValue = InterfaceMap.get(VariableName);
                               PostData = PostData.replace(UseVariableName, VariableValue);
                           }
                       }
                       //3.替换数据库变量
                       for (String VariableName : DBMap.keySet()) {
                           String UseVariableName = "<<" + VariableName + ">>";
                           if (PostData.contains(UseVariableName)) {
                               String VariableValue = DBMap.get(VariableName);
                               PostData = PostData.replace(UseVariableName, VariableValue);
                           }
                       }
                   }
               }
           }
           TestCaseHelp.log.info("处理Body完后，PostData："+PostData);
           ro.setPostData(PostData);
           ro.setExpect(expect);
           ro.setCasetype(casetype);
           ro.setHeader(header);
           ro.setProtocal(protocal);
           ro.setApistyle(apistyle);
           ro.setParamers(paramers);
           ro.setBodyparamers(Bodyparamers);
           ro.setRequestcontenttype(requestcontenttype);
           ro.setRequestmMthod(method);
           ro.setResource(resource);
           ro.setResponecontenttype(responecontenttype);
       }
       catch (Exception ex)
       {
           TestCaseHelp.log.info("GetCaseRequestData异常："+ex.getMessage());
       }
        return ro;
    }

    private HashMap<String, String> GetMap(List<TestvariablesValue> variableslist) {
        HashMap<String, String> RadomMap = new HashMap<>();
        for (TestvariablesValue testvariablesValue : variableslist) {
            RadomMap.put(testvariablesValue.getVariablesname(), testvariablesValue.getVariablesvalue());
        }
        return RadomMap;
    }

    private Object GetVaraibaleValue(String Value, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap) throws Exception {
        Object ObjectValue = Value;
        boolean exist=false; //标记是否Value有变量处理，false表示没有对应的子条件处理过
        //参数值替换接口变量
        for (String interfacevariablesName : InterfaceMap.keySet()) {
            boolean flag = GetSubOrNot(InterfaceMap, Value, "<", ">");
            if (Value.contains("<" + interfacevariablesName + ">")) {
                exist=true;
                String ActualValue = InterfaceMap.get(interfacevariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<" + interfacevariablesName + ">", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    Testvariables testvariables = tch.testvariablesService.getBy("testvariablesname", interfacevariablesName);//  testMysqlHelp.GetVariablesDataType(interfacevariablesName);
                    if (testvariables == null) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, testvariables.getValuetype());
                    }
                }
            }
        }
        //参数值替换数据库变量
        for (String DBvariablesName : DBMap.keySet()) {
            boolean flag = GetSubOrNot(DBMap, Value, "<<", ">>");
            if (Value.contains("<<" + DBvariablesName + ">>")) {
                exist=true;
                String ActualValue = DBMap.get(DBvariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<<" + DBvariablesName + ">>", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    Dbvariables dbvariables = tch.dbvariablesService.getBy("dbvariablesname", DBvariablesName);//  testMysqlHelp.GetVariablesDataType(interfacevariablesName);
                    if (dbvariables == null) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, dbvariables.getValuetype());
                    }
                }
            }
        }
        //参数值替换随机变量
        for (String variables : RadomMap.keySet()) {
            boolean flag = GetSubOrNot(RadomMap, Value, "[", "]");
            if (Value.contains("[" + variables + "]")) {
                exist=true;
                if (flag) {
                    Object RadomValue = GetRadomValue(variables);
                    Value = Value.replace("[" + variables + "]", RadomValue.toString());
                    ObjectValue = Value;
                } else {
                    ObjectValue = GetRadomValue(variables);
                }
            }
        }
        if(!exist)
        {
            throw new Exception("当前接口子条件参数值中存在变量："+Value+" 未找到对应值，请检查是否有配置变量对应的子条件获取此变量值");
        }

        return ObjectValue;
    }

    //判断是否有拼接
    private boolean GetSubOrNot(HashMap<String, String> VariablesMap, String Value, String prefix, String profix) {
        boolean flag = false;
        for (String Key : VariablesMap.keySet()) {
            String ActualValue = prefix + Key + profix;
            if (Value.contains(ActualValue)) {
                String LeftValue = Value.replace(ActualValue, "");
                if (LeftValue.length() > 0) {
                    //表示有拼接
                    return true;
                } else {
                    return false;
                }
            }
        }
        return flag;
    }

    //获取随机变量值
    private Object GetRadomValue(String Value) {
        Object Result = Value;
        String FunctionName = Value;
        List<Variables> variablesList = tch.variablesService.listAll();
        for (Variables variables : variablesList) {
            if (variables.getVariablesname().equalsIgnoreCase(FunctionName)) {
                String Params = variables.getVariablecondition();
                String Variablestype = variables.getVariablestype();
                RadomVariables radomVariables = new RadomVariables();
                if (Variablestype.equalsIgnoreCase("随机字符串")) {
                    try {
                        Integer length = Integer.parseInt(Params);
                        Result = radomVariables.GetRadmomStr(length);
                    } catch (Exception ex) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写参数为数字类型表示字符串长度";
                    }
                }
                if (Variablestype.equalsIgnoreCase("随机整数")) {
                    String ParamsArray[] = Params.split(",");
                    if (ParamsArray.length < 2) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写需要的字符串长度";
                    } else {
                        try {
                            Long Start = Long.parseLong(ParamsArray[0]);
                            Long End = Long.parseLong(ParamsArray[1]);
                            Result = radomVariables.GetRadmomNum(Start, End);
                        } catch (Exception exception) {
                            Result = "随机变量GetRadmomNum输入参数不合法，请填写最小和最大值数字范围";
                        }
                    }
                }
                if (Variablestype.equalsIgnoreCase("随机小数")) {
                    String ParamsArray[] = Params.split(",");
                    if (ParamsArray.length < 2) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写需要的字符串长度";
                    } else {
                        try {
                            Long Start = Long.parseLong(ParamsArray[0]);
                            Long End = Long.parseLong(ParamsArray[1]);
                            Result = radomVariables.GetRadmomDouble(Start, End);
                        } catch (Exception exception) {
                            Result = "随机变量GetRadmomNum输入参数不合法，请填写最小和最大值数字范围";
                        }
                    }
                }
                if (Variablestype.equalsIgnoreCase("Guid")) {
                    Result = radomVariables.GetGuid();
                }
                if (Variablestype.equalsIgnoreCase("随机IP")) {
                    Result = radomVariables.GetRadmonIP();
                }
                if (Variablestype.equalsIgnoreCase("当前时间")) {
                    Result = radomVariables.GetCurrentTime();
                }
                if (Variablestype.equalsIgnoreCase("当前日期")) {
                    Result = radomVariables.GetCurrentDate(Params);
                }
                if (Variablestype.equalsIgnoreCase("当前时间戳")) {
                    Result = radomVariables.GetCurrentTimeMillis();
                }
            }
        }
        return Result;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, ApiCasedata> fixhttprequestdatas(String MapType,List<ApiCasedata> casedatalist) {
        HashMap<String, ApiCasedata> DataMap=new HashMap<>();
        for (ApiCasedata data : casedatalist) {
            String propertytype= data.getPropertytype();
            if (propertytype.equals(MapType)) {
                DataMap.put(data.getApiparam().trim(), data);
            }
        }
        return DataMap;
    }

    private String GetParasPostData(String RequestContentType, HttpParamers paramers) throws IOException {
        String Result = "";
        if (RequestContentType.equals("json")) {
            paramers.setJsonParamer();
            Result = paramers.getJsonParamer();
        }
        if (RequestContentType.equals("form表单")) {
            Result = paramers.getQueryString("UTF-8");
        }
        if (RequestContentType.equals("xml")) {

        } else {

        }
        return Result;
    }

    //根据请求数据类型增加header
    private HttpHeader AddHeaderByRequestContentType(HttpHeader httpHeader, String RequestContentType) {
        if (RequestContentType.equalsIgnoreCase("json")) {
            httpHeader.addParam("Content-Type", "application/json;charset=utf-8");
        }
        if (RequestContentType.equalsIgnoreCase("xml")) {
            httpHeader.addParam("Content-Type", "application/xml;charset=utf-8");
        }
        if (RequestContentType.equalsIgnoreCase("Form表单")) {
            httpHeader.addParam("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpHeader;
    }

    //获取参数值的具体内容，支持$变量
    private Object GetVariablesObjectValues(String Value, String PlanId, String BatchName) {
        Object Result = "";
        if (Value.trim().contains("$")) {
            if (Value.trim().length() == 1) {
                Result = Value;
            } else {
                String Prix[]=Value.split("\\+");
                for (String PrixStr: Prix) {
                    if(PrixStr.contains("$"))
                    {
                        TestCaseHelp.log.info("TestHttpRequestData $PrixStr :  " + PrixStr );
                        Result=Result.toString()+GetVariablesDataType(PrixStr,PlanId,BatchName);
                        TestCaseHelp.log.info("TestHttpRequestData $PrixStr Result :  " + Result );
                    }
                    else
                    {
                        TestCaseHelp.log.info("TestHttpRequestData PrixStr :  " + PrixStr );
                        Result=Result+PrixStr;
                    }
                }
//                Value = Value.substring(1);
//                String Caseid = testMysqlHelp.GetCaseIdByVariablesName(Value);
//                if(Caseid.isEmpty())
//                {
//                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-变量管理中是否存在此变量";
//                    return Result;
//                }
//                String ValueType = testMysqlHelp.GetVariablesDataType(Value);
//                if(ValueType.isEmpty())
//                {
//                    Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
//                    return Result;
//                }
//                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
//                String VariablesNameValue = testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
//                if(VariablesNameValue.isEmpty())
//                {
//                    Result="未找到变量："+Value+"的值，请检查变量管理-变量结果中是否存在此变量值";
//                    return Result;
//                }
//                else
//                {
//                    Result=GetDataByType(VariablesNameValue,ValueType);
//                }
            }
        } else {
            Result = Value;
        }
        return Result;
    }


    private Object GetVariablesDataType(String Value,String PlanId, String BatchName)
    {
        Object Result="";
        Value = Value.substring(1);
        TestCaseHelp.log.info( "TestHttpRequestData GetVariablesDataType Value :  " + Value );

        ApicasesVariables apicasesVariables = tch.apicasesVariablesService.getBy("variablesname",Value);// testMysqlHelp.GetCaseIdByVariablesName(Value);
        if(apicasesVariables==null)
        {
            Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-变量管理中是否存在此变量";
            return Result;
        }

        Testvariables testvariables = tch.testvariablesService.getBy("testvariablesname",Value); //testMysqlHelp.GetVariablesDataType(Value);
        if(testvariables==null)
        {
            Result="未找到变量："+Value+"绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
            return Result;
        }
        //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
        String Caseid=apicasesVariables.getCaseid().toString();
        TestvariablesValue testvariablesValue = tch.testvariablesValueService.findtestvariablesvalue(Long.parseLong(PlanId),Long.parseLong(Caseid),BatchName,Value);//.fi   testMysqlHelp.GetVariablesValues(PlanId, Caseid, BatchName, Value);
        if(testvariablesValue==null)
        {
            Result="未找到变量："+Value+"的值，请检查变量管理-变量结果中是否存在此变量值";
            return Result;
        }
        else
        {
            String ValueType=testvariables.getTestvariablestype();
            String VariablesNameValue =testvariablesValue.getVariablesvalue();
            Result=GetDataByType(VariablesNameValue,ValueType);
        }
        return Result;
    }

    //根据数据类型转换
    private Object GetDataByType(String Data,String ValueType)
    {
        Object Result=new Object();
        if (ValueType.equalsIgnoreCase("Number")) {
            try {
                Result = Long.parseLong(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是数字类型，请检查！";
            }
        }
        if (ValueType.equalsIgnoreCase("Json")) {
            try {
                Result = JSON.parse(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是数字类型，请检查！";
            }
        }
        if (ValueType.equalsIgnoreCase("String")||ValueType.isEmpty()) {
            Result = Data;
        }
        if (ValueType.equalsIgnoreCase("Array")) {
            String[] Array = Data.split(",");
            Result = Array;
        }
        if (ValueType.equalsIgnoreCase("Bool")) {
            try {
                Result = Boolean.parseBoolean(Data);
            } catch (Exception ex) {
                Result = "变量值：" + Data + " 不是布尔类型，请检查！";
            }
        }
        return Result;
    }

    // 发送http请求
    public TestResponeData request(RequestObject requestObject) throws Exception {
        TestResponeData result=new TestResponeData();
        TestHttp testHttp=new TestHttp();
        if (requestObject.getProtocal().equals("http")||requestObject.getProtocal().equals("https")) {
            result = testHttp.doService(requestObject,30000);
        }
        return result;
    }


}
