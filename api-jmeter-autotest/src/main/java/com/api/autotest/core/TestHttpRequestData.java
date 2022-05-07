package com.api.autotest.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.RadomVariables;
import com.api.autotest.dto.RequestObject;
import com.api.autotest.dto.Variables;
import org.apache.log.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.autotest.core.TestCaseData.logplannameandcasename;


public class TestHttpRequestData {

    private Logger logger = null;
    TestMysqlHelp testMysqlHelp = null;

    public TestHttpRequestData(Logger log, TestMysqlHelp mysqlHelp) {
        testMysqlHelp = mysqlHelp;
        logger = log;
    }

    //功能用例获取Http请求数据
    public RequestObject GetFuntionHttpRequestData(RequestObject requestObject) {
        RequestObject Result = requestObject;
        try {
            String TestCaseId = requestObject.getCaseid();
            String PlanId = requestObject.getTestplanid();
            String BatchName = requestObject.getBatchname();
            String requestcontenttype = requestObject.getRequestcontenttype();
            ArrayList<HashMap<String, String>> caselist = testMysqlHelp.getcaseData("select * from apicases where id=" + TestCaseId);
            String APIId = testMysqlHelp.getcaseValue("apiid", caselist);

            ArrayList<HashMap<String, String>> casedatalist = testMysqlHelp.getcaseData("select * from api_casedata where caseid=" + TestCaseId);
            ArrayList<HashMap<String, String>> caseptlist = testMysqlHelp.getcaseData("select DISTINCT propertytype  from api_casedata where caseid=" + TestCaseId);
            ArrayList<HashMap<String, String>> planparamslist = testMysqlHelp.getcaseData("select * from executeplan_params where executeplanid=" + PlanId);

            List<String> PropertyList = GetCaseDataPropertyType(caseptlist);

            ArrayList<HashMap<String, String>> Interfacevariableslist = testMysqlHelp.getcaseData("select variablesname,variablesvalue   from testvariables_value where planid= " + PlanId + " and batchname = '" + BatchName + "' and variablestype='" + "接口" + "'");
            ArrayList<HashMap<String, String>> DBvariableslist = testMysqlHelp.getcaseData("select variablesname,variablesvalue   from testvariables_value where planid= " + PlanId + " and batchname = '" + BatchName + "' and variablestype='" + "数据库" + "'");

            logger.info(logplannameandcasename + "TestHttpRequestData 获取接口变量值。。。。 ");
            ArrayList<HashMap<String, String>> radomvariableslist = testMysqlHelp.getcaseData("select variablesname,variablestype   from variables ");
            logger.info(logplannameandcasename + "TestHttpRequestData 获取随机变量值。。。。 ");
            HashMap<String, String> InterfaceMap = GetMap(Interfacevariableslist, "variablesname", "variablesvalue");
            HashMap<String, String> DBMap = GetMap(DBvariableslist, "variablesname", "variablesvalue");
            HashMap<String, String> RadomMap = GetMap(radomvariableslist, "variablesname", "variablestype");

            //Url替换变量
            String RequestUrl = requestObject.getResource();
            //1.替换随机变量
            for (String VaraibaleName : RadomMap.keySet()) {
                String UseVariableName = "[" + VaraibaleName + "]";
                if (RequestUrl.contains(UseVariableName)) {
                    Object VariableValue = GetRadomValue(VaraibaleName, radomvariableslist);
                    RequestUrl = RequestUrl.replace(UseVariableName, VariableValue.toString());
                }
            }
            //2.替换接口变量
            for (String VariableName : InterfaceMap.keySet()) {
                String UseVariableName = "<" + VariableName + ">";
                if (RequestUrl.contains(UseVariableName)) {
                    String VariableValue = InterfaceMap.get(VariableName);
                    RequestUrl = RequestUrl.replace(UseVariableName, VariableValue);
                }
            }
            //3.替换数据库变量
            for (String VariableName : DBMap.keySet()) {
                String UseVariableName = "<<" + VariableName + ">>";
                if (RequestUrl.contains(UseVariableName)) {
                    String VariableValue = DBMap.get(VariableName);
                    RequestUrl = RequestUrl.replace(UseVariableName, VariableValue);
                }
            }
            requestObject.setResource(RequestUrl);

            //Header
            HttpHeader header = new HttpHeader();
            header = AddHeaderByRequestContentType(header, requestcontenttype);
            //Params参数：Url拼接
            HttpParamers paramers = new HttpParamers();
            //Body参数
            HttpParamers Bodyparamers = new HttpParamers();
            String PostData = "";
            for (String property : PropertyList) {
                if (property.equalsIgnoreCase("Header")) {
                    //值支持变量
                    header = GetHttpHeader(casedatalist, header, RadomMap, InterfaceMap, DBMap, radomvariableslist);
                    for (String Key : header.getParams().keySet()) {
                        logger.info(logplannameandcasename + "TestHttpRequestData Header Key :  " + Key + " Value: " + header.getParams().get(Key));
                    }
                }
                if (property.equalsIgnoreCase("Params")) {
                    //值支持变量
                    paramers = GetHttpParams(casedatalist, paramers, RadomMap, InterfaceMap, DBMap, "Params", radomvariableslist);
                    for (String Key : paramers.getParams().keySet()) {
                        logger.info(logplannameandcasename + "TestHttpRequestData Params Key :  " + Key + " Value: " + paramers.getParams().get(Key));
                    }
                }
                if (property.equalsIgnoreCase("Body")) {
                    // 设置Body
                    if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                        //值支持变量
                        Bodyparamers = GetHttpParams(casedatalist, Bodyparamers, RadomMap, InterfaceMap, DBMap, "Body", radomvariableslist);
                        if (Bodyparamers.getParams().size() > 0) {
                            try {
                                PostData = Bodyparamers.getQueryString();
                                logger.info(logplannameandcasename + "TestHttpRequestData Bodyparamers  PostData:  " + PostData);
                            } catch (UnsupportedEncodingException e) {
                                logger.info(logplannameandcasename + "TestHttpRequestData Bodyparamers表单编码异常 :  " + e.getMessage());
                            }
                        }
                    } else {
                        HashMap<String, String> bodymap = testMysqlHelp.fixhttprequestdatas("Body", casedatalist);
                        for (String Key : bodymap.keySet()) {
                            PostData = bodymap.get(Key);
                            //1.替换随机变量
                            for (String VaraibaleName : RadomMap.keySet()) {
                                String UseVariableName = "[" + VaraibaleName + "]";
                                if (PostData.contains(UseVariableName)) {
                                    Object VariableValue = GetRadomValue(VaraibaleName, radomvariableslist);
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
                            logger.info(logplannameandcasename + "TestHttpRequestData Body  PostData:  " + PostData);
                        }
                    }
                }
            }
            //处理全局Header参数
            header = GetHeaderFromTestPlanParam(header, planparamslist, RadomMap, InterfaceMap, DBMap, radomvariableslist);
            for (String Key : header.getParams().keySet()) {
                logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header Key :  " + Key + " Value: " + header.getParams().get(Key));
            }
//        PostData = GetBodyFromTestPlanParam(PostData, planparamslist);
//        logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Body :  " + PostData);
            //处理全局参数
            Result.setHeader(header);
            Result.setParamers(paramers);
            Result.setPostData(PostData);
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "功能获取GetFuntionHttpRequestData异常 :  " + ex.getMessage());
        }
        return Result;
    }

    //性能用例获取Http请求数据
    public RequestObject GetPerformanceHttpRequestData(RequestObject newob) throws Exception {
        RequestObject resultobj = new RequestObject();
        try {
            resultobj = newob;
            String requestcontenttype = resultobj.getRequestcontenttype();
            String headjson = resultobj.getHeadjson();
            //logger.info(logplannameandcasename + "TestHttpRequestData headjson： " + headjson);
            String paramsjson = resultobj.getParamjson();
            String bodyjson = resultobj.getBodyjson();
            String postdata = resultobj.getPostData();

            //获取随机变量json
            String variablesjson = resultobj.getVariablesjson();
            //logger.info(logplannameandcasename + "TestHttpRequestData variablesjson： " + variablesjson);
            //获取随机变量列表
            List<Variables> variablesList = new ArrayList<>();
            HashMap<String, String> RadomVariablesHashMap = new HashMap<>();
            if ((!variablesjson.isEmpty()) && (variablesjson != null)) {
                variablesList = JSONObject.parseArray(variablesjson, Variables.class);
                for (Variables va : variablesList) {
                    if (!RadomVariablesHashMap.containsKey(va.getVariablesname())) {
                        RadomVariablesHashMap.put(va.getVariablesname(), va.getVariablestype());
                    }
                }
            }
            logger.info(logplannameandcasename + "TestHttpRequestData 获取随机变量列表： " + variablesjson);

            //Url替换随机变量
            String RequestUrl = resultobj.getResource();
            logger.info(logplannameandcasename + "TestHttpRequestData Url替换随机变量前： " + RequestUrl);

            for (String Variables : RadomVariablesHashMap.keySet()) {
                String CompleteVaraiables = "[" + Variables + "]";
                if (RequestUrl.contains(CompleteVaraiables)) {
                    Object VariableValue = GetPerformanceRadomValue(Variables, variablesList);
                    RequestUrl = RequestUrl.replace(CompleteVaraiables, VariableValue.toString());
                }
            }
            resultobj.setResource(RequestUrl);
            logger.info(logplannameandcasename + "TestHttpRequestData Url替换随机变量后： " + RequestUrl);

            //Header
            HttpHeader header = new HttpHeader();
            AddHeaderByRequestContentType(header, requestcontenttype);
            if ((headjson != null) && (!headjson.isEmpty())) {
                Map headermaps = (Map) JSON.parse(headjson);
                //logger.info(logplannameandcasename + "TestHttpRequestData head： " + headjson);
                for (Object key : headermaps.entrySet()) {
                    Object Value = ((Map.Entry) key).getValue();
                    //logger.info(logplannameandcasename + "TestHttpRequestData Header 值：  " + Value);
                    Object ObjectValue = PerformanceGetVaraibaleValue(Value.toString(), RadomVariablesHashMap, variablesList);
                    header.addParam(((Map.Entry) key).getKey().toString(), ObjectValue);
                    //logger.info(logplannameandcasename + "TestHttpRequestData Header处理变量后 值：  " + Value);
                }
            }
            resultobj.setHeader(header);
            logger.info(logplannameandcasename + "TestHttpRequestData head finish： " + headjson);

            //Params
            HttpParamers params = new HttpParamers();
            if ((!paramsjson.isEmpty()) && (paramsjson != null)) {
                logger.info(logplannameandcasename + "TestHttpRequestData paramsjson： " + paramsjson);
                Map paramsmaps = (Map) JSON.parse(paramsjson);
                //logger.info(logplannameandcasename + "TestHttpRequestData 结束 JSON.parse(paramsjson) ");
                for (Object key : paramsmaps.entrySet()) {
                    Object Value = ((Map.Entry) key).getValue();
                    //logger.info(logplannameandcasename + "TestHttpRequestData Params 值：  " + Value);
                    Object ObjectValue = PerformanceGetVaraibaleValue(Value.toString(), RadomVariablesHashMap, variablesList);
                    params.addParam(((Map.Entry) key).getKey().toString(), ObjectValue);
                    //logger.info(logplannameandcasename + "TestHttpRequestData Params处理变量后 值：  " + Value);
                }
            }
            resultobj.setParamers(params);
            logger.info(logplannameandcasename + "TestHttpRequestData Params finish： " + paramsjson);

            //Body
            String PostData = "";
            if ((!bodyjson.isEmpty()) && (bodyjson != null)) {
                //logger.info(logplannameandcasename + "TestHttpRequestData bodyjson： " + bodyjson);
                Map bodyparamsmaps = (Map) JSON.parse(bodyjson);
                //logger.info(logplannameandcasename + "TestHttpRequestData 结束 JSON.parse(bodyjson) ");
                if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                    HttpParamers Bodyparams = new HttpParamers();
                    for (Object key : bodyparamsmaps.entrySet()) {
                        Object Value = ((Map.Entry) key).getValue();
                        //logger.info(logplannameandcasename + "TestHttpRequestData BodyParams 值：  " + Value);
                        Object ObjectValue = PerformanceGetVaraibaleValue(Value.toString(), RadomVariablesHashMap, variablesList);
                        Bodyparams.addParam(((Map.Entry) key).getKey().toString(), ObjectValue);
                        //logger.info(logplannameandcasename + "TestHttpRequestData BodyParams处理完变量后 值：  " + Value);
                    }
                    if (Bodyparams.getParams().size() > 0) {
                        try {
                            PostData = Bodyparams.getQueryString();
                            //logger.info(logplannameandcasename + "TestHttpRequestData Form表单 性能 Bodyparamers  PostData:  " + PostData);
                        } catch (UnsupportedEncodingException e) {
                            //logger.info(logplannameandcasename + "TestHttpRequestData Form表单 性能 Bodyparamers表单编码异常 :  " + e.getMessage());
                        }
                    }
                }
                logger.info(logplannameandcasename + "TestHttpRequestData Body Form表单 finish： " + PostData);
            } else {
                PostData = postdata;
                //logger.info(logplannameandcasename + "TestHttpRequestData 非Form表单 PostData  is: " + PostData);
                for (String Variables : RadomVariablesHashMap.keySet()) {
                    String CompleteVaraiables = "[" + Variables + "]";
                    if (PostData.contains(CompleteVaraiables)) {
                        Object VariableValue = GetPerformanceRadomValue(Variables, variablesList);
                        PostData = PostData.replace(CompleteVaraiables, VariableValue.toString());
                    }
                }
                logger.info(logplannameandcasename + "TestHttpRequestData 非Form表单 替换变量后PostData is: " + PostData);
            }
            resultobj.setPostData(PostData);
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "TestHttpRequestData 性能异常 " + ex.getMessage());
            throw new Exception("性能用例数据获取异常：" + ex.getMessage());
        }
        return resultobj;
    }

    private HashMap<String, String> GetMap(ArrayList<HashMap<String, String>> variableslist, String KeyColumn, String CloumnValue) {
        HashMap<String, String> RadomMap = new HashMap<>();
        for (HashMap<String, String> map : variableslist) {
            String Key = "";
            String Value = "";
            for (String column : map.keySet()) {
                if (column.equalsIgnoreCase(KeyColumn)) {
                    Key = map.get(column);
                }
                if (column.equalsIgnoreCase(CloumnValue)) {
                    Value = map.get(column);
                }
            }
            RadomMap.put(Key, Value);
        }
        return RadomMap;
    }

    //获取用例值的参数类型列表
    private List<String> GetCaseDataPropertyType(ArrayList<HashMap<String, String>> casedataptlist) {
        List<String> PropertyType = new ArrayList<>();
        for (HashMap<String, String> hs : casedataptlist) {
            for (String property : hs.keySet()) {
                PropertyType.add(hs.get(property));
            }
        }
        return PropertyType;
    }

    // 设置header
    private HttpHeader GetHttpHeader(ArrayList<HashMap<String, String>> casedatalist, HttpHeader header, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap, ArrayList<HashMap<String, String>> radomvariableslist) {
        HashMap<String, String> headmap = testMysqlHelp.fixhttprequestdatas("Header", casedatalist);
        for (String key : headmap.keySet()) {
            String Value = headmap.get(key);
            Object ObjectValue = Value;
            if ((Value.contains("<") && Value.contains(">")) || (Value.contains("<<") && Value.contains(">>")) || (Value.contains("[") && Value.contains("]"))) {
                try {
                    ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap, DBMap, radomvariableslist);
                } catch (Exception exception) {
                    logger.info(logplannameandcasename + "TestHttpRequestData Header替换变量异常 :  " + exception.getMessage());
                }
            }
            header.addParam(key, ObjectValue);
            logger.info(logplannameandcasename + "TestHttpRequestData Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
        }
        return header;
    }

    //判断是否有拼接
    private boolean GetSubOrNot(HashMap<String, String> VariablesMap, String Value, String prefix, String profix) {
        boolean flag = false;
        for (String Key : VariablesMap.keySet()) {
            String ActualValue = prefix + Key + profix;
            if (Value.contains(ActualValue)) {
                String LeftValue = Value.replace(ActualValue, "");
                logger.info(logplannameandcasename + "TestHttpRequestData GetSubOrNot判断是否有拼接 Value is :  " + Value + " 替换ActualValue:" + ActualValue + " 剩下的字符串LeftValue：" + LeftValue);
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

    private Object GetVaraibaleValue(String Value, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap, ArrayList<HashMap<String, String>> radomvariableslist) throws Exception {
        Object ObjectValue = Value;
        boolean exist = false; //标记是否Value有变量处理，false表示没有对应的子条件处理过
        //参数值替换接口变量
        for (String interfacevariablesName : InterfaceMap.keySet()) {
            boolean flag = GetSubOrNot(InterfaceMap, Value, "<", ">");
            if (Value.contains("<" + interfacevariablesName + ">")) {
                exist = true;
                String ActualValue = InterfaceMap.get(interfacevariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<" + interfacevariablesName + ">", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    String ValueType = testMysqlHelp.GetVariablesDataType(interfacevariablesName);
                    if (ValueType.isEmpty()) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, ValueType);
                    }
                }
            }
        }

        //参数值替换数据库变量
        for (String DBvariablesName : DBMap.keySet()) {
            boolean flag = GetSubOrNot(DBMap, Value, "<<", ">>");
            if (Value.contains("<<" + DBvariablesName + ">>")) {
                exist = true;
                String ActualValue = DBMap.get(DBvariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<<" + DBvariablesName + ">>", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    String ValueType = testMysqlHelp.GetDBVariablesDataType(DBvariablesName);
                    if (ValueType.isEmpty()) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, ValueType);
                    }
                }
            }
        }

        //参数值替换随机变量
        for (String variables : RadomMap.keySet()) {
            boolean flag = GetSubOrNot(RadomMap, Value, "[", "]");
            if (Value.contains("[" + variables + "]")) {
                exist = true;
                if (flag) {
                    Object RadomValue = GetRadomValue(variables, radomvariableslist);
                    Value = Value.replace("[" + variables + "]", RadomValue.toString());
                    ObjectValue = Value;
                } else {
                    ObjectValue = GetRadomValue(variables, radomvariableslist);
                }
            }
        }
        if (!exist) {
            throw new Exception("当前用例参数值中存在变量：" + Value + " 未找到对应值，请检查是否有配置对应变量的子条件获取此变量值");
        }
        return ObjectValue;
    }

    //性能替换随机变量
    private Object PerformanceGetVaraibaleValue(String Value, HashMap<String, String> RadomMap, List<Variables> variablesList) {
        Object ObjectValue = Value;
        //参数值替换随机变量
        for (String variables : RadomMap.keySet()) {
            boolean flag = GetSubOrNot(RadomMap, Value, "[", "]");
            if (Value.contains("[" + variables + "]")) {
                if (flag) {
                    Object RadomValue = GetPerformanceRadomValue(variables, variablesList);
                    Value = Value.replace("[" + variables + "]", RadomValue.toString());
                    ObjectValue = Value;
                } else {
                    ObjectValue = GetPerformanceRadomValue(variables, variablesList);
                }
            }
        }
        return ObjectValue;
    }

    // 设置参数params
    private HttpParamers GetHttpParams(ArrayList<HashMap<String, String>> casedatalist, HttpParamers paramers, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap, String Property, ArrayList<HashMap<String, String>> radomvariableslist) {
        for (HashMap<String, String> data : casedatalist) {
            String propertytype = data.get("propertytype");
            if (propertytype.equals(Property)) {
                String Key = data.get("apiparam").trim();
                String Value = data.get("apiparamvalue").trim();
                String DataType = data.get("paramstype").trim();
                Object ObjectValue = Value;
                try {
                    if ((Value.contains("<") && Value.contains(">")) || (Value.contains("<<") && Value.contains(">>")) || (Value.contains("[") && Value.contains("]"))) {
                        ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap, DBMap, radomvariableslist);
                    }
                    ObjectValue = GetDataByType(ObjectValue.toString(), DataType);
                } catch (Exception exception) {
                    logger.info(logplannameandcasename + "TestHttpRequestData 处理Params参数变量替换异常" + exception.getMessage());
                }
                paramers.addParam(Key, ObjectValue);
                logger.info(logplannameandcasename + "TestHttpRequestData -" + Property + "-中添加Key is :  " + Key + "   Value  is:   " + ObjectValue + " 类型：" + DataType);
            }
        }
        return paramers;
    }

    private Object GetRadomValue(String Value, ArrayList<HashMap<String, String>> list) {
        Object Result = Value;
        String FunctionName = Value;
        //ArrayList<HashMap<String, String>> list = testMysqlHelp.GetRadomVariables();
        for (HashMap<String, String> varaiablesmap : list) {
            for (String Key : varaiablesmap.keySet()) {
                if (varaiablesmap.get(Key).equalsIgnoreCase(FunctionName)) {
                    logger.info("随机变量名为:  " + varaiablesmap.get(Key) + " 开始处理函数");
                    String Params = varaiablesmap.get("variablecondition");
                    logger.info("随机变量名为开始处理函数条件为：" + Params);
                    String Variablestype = varaiablesmap.get("variablestype");
                    logger.info("随机变量名为开始处理函数变量类型为：" + Variablestype);
                    Result = RadomValue(Variablestype, Params);
                }
            }
        }
        return Result;
    }

    //性能测试获取随机变量值
    private Object GetPerformanceRadomValue(String Value, List<Variables> variablesList) {
        Object Result = Value;
        String FunctionName = Value;
        for (Variables varaiables : variablesList) {
            if (varaiables.getVariablesname().equalsIgnoreCase(FunctionName)) {
                logger.info("随机变量名为:  " + varaiables.getVariablesname() + " 开始处理函数");
                String Params = varaiables.getVariablecondition();
                logger.info("随机变量名为开始处理函数条件为：" + Params);
                String Variablestype = varaiables.getVariablestype();
                logger.info("随机变量名为开始处理函数变量类型为：" + Variablestype);
                Result = RadomValue(Variablestype, Params);
            }
        }
        return Result;
    }

    //随机变量值
    private Object RadomValue(String Variablestype, String Params) {
        Object Result = "";
        RadomVariables radomVariables = new RadomVariables();
        if (Variablestype.equalsIgnoreCase("随机字符串")) {
            try {
                Integer length = Integer.parseInt(Params);
                Result = radomVariables.GetRadmomStr(length);
                logger.info("随机变量名随机字符串为:  " + Result);
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
                    logger.info("随机变量名随机整数为:  " + Result);
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
                    logger.info("随机变量名随机小数为:  " + Result);
                } catch (Exception exception) {
                    Result = "随机变量GetRadmomNum输入参数不合法，请填写最小和最大值数字范围";
                }
            }
        }
        if (Variablestype.equalsIgnoreCase("Guid")) {
            Result = radomVariables.GetGuid();
            logger.info("随机变量名Guid为:  " + Result);
        }
        if (Variablestype.equalsIgnoreCase("随机IP")) {
            Result = radomVariables.GetRadmonIP();
            logger.info("随机变量名随机IP为:  " + Result);
        }
        if (Variablestype.equalsIgnoreCase("当前时间")) {
            Result = radomVariables.GetCurrentTime();
            logger.info("随机变量名当前时间为:  " + Result);
        }
        if (Variablestype.equalsIgnoreCase("当前日期")) {
            Result = radomVariables.GetCurrentDate(Params);
            logger.info("随机变量名当前日期为:  " + Result);
        }
        if (Variablestype.equalsIgnoreCase("当前时间戳")) {
            Result = radomVariables.GetCurrentTimeMillis();
            logger.info("随机变量名当前时间戳为:  " + Result);
        }
        return Result;
    }

    //获取全局Header
    private HttpHeader GetHeaderFromTestPlanParam(HttpHeader header, ArrayList<HashMap<String, String>> planparamslist, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap, ArrayList<HashMap<String, String>> radomvariableslist) {
        HashMap<String, String> headmapfromparam = testMysqlHelp.getparamsdatabytype("Header", planparamslist);
        for (String key : headmapfromparam.keySet()) {
            String Value = headmapfromparam.get(key);
            Object ObjectValue = Value;
            if ((Value.contains("<") && Value.contains(">")) || (Value.contains("<<") && Value.contains(">>")) || (Value.contains("[") && Value.contains("]"))) {
                try {
                    ObjectValue = GetVaraibaleValue(Value, RadomMap, InterfaceMap, DBMap, radomvariableslist);
                } catch (Exception exception) {
                    logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header处理变量替换异常:  " + exception.getMessage());
                }
            }
            //如果有相同的参数，则以全局参数的覆盖之,如果没有则添加
            header.getParams().put(key, ObjectValue);
            logger.info(logplannameandcasename + "TestHttpRequestData 全局参数Header中添加Key is :  " + key + "   Value  is:   " + ObjectValue);
        }
        return header;
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

    //根据数据类型转换
    private Object GetDataByType(String Data, String ValueType) {
        Object Result = new Object();
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
        if (ValueType.equalsIgnoreCase("String") || ValueType.isEmpty()) {
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
}
