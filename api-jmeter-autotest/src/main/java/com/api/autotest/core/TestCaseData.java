package com.api.autotest.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.dto.ApicasesAssert;
import com.api.autotest.dto.RequestObject;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TestCaseData {
    public static String logplannameandcasename = "";
    private Logger logger = null;
    TestMysqlHelp testMysqlHelp=null;
    public TestCaseData(Logger log,TestMysqlHelp mysqlHelp)
    {
        testMysqlHelp=mysqlHelp;
        logger=log;
    }

    //性能初始化数据根据jmeter传递下来的数据拼装用例请求的数据
    public RequestObject InitHttpDatabyJmeter(JavaSamplerContext context) throws Exception {
        RequestObject newob = new RequestObject();
        try
        {
            String casename = context.getParameter("casename");
            String executeplanname = context.getParameter("executeplanname");
            logplannameandcasename = executeplanname + "--" + casename + " :";
            //logger.info(logplannameandcasename + "用例数据casename is :  " + casename);
            //logger.info(logplannameandcasename + "用例数据executeplanname is :  " + executeplanname);

            String testplanid = context.getParameter("testplanid");
            String caseid = context.getParameter("caseid");
            String slaverid = context.getParameter("slaverid");
            String batchid = context.getParameter("batchid");
            String batchname = context.getParameter("batchname");
            String machineip = context.getParameter("machineip");
            String deployvisitytype = context.getParameter("deployvisitytype");
            String RequestmMthod = context.getParameter("RequestmMthod");
            //logger.info(logplannameandcasename + "用例数据 RequestmMthod is :  " + RequestmMthod);

            String resource = context.getParameter("resource");
            //logger.info(logplannameandcasename + "用例数据 resource is :  " + resource);

            String apistyle = context.getParameter("apistyle");
            //logger.info(logplannameandcasename + "用例数据 apistyle is :  " + apistyle);

            String requestcontenttype = context.getParameter("requestcontenttype");
            //logger.info(logplannameandcasename + "用例数据 requestcontenttype is :  " + requestcontenttype);

            String responecontenttype = context.getParameter("responecontenttype");
            //logger.info(logplannameandcasename + "用例数据 responecontenttype is :  " + responecontenttype);

            String headjson = context.getParameter("headjson").replace("Autometer"," ");
            //logger.info(logplannameandcasename + "用例数据 headjson is :  " + headjson);

            String paramsjson = context.getParameter("paramsjson").replace("Autometer"," ");
            //logger.info(logplannameandcasename + "用例数据 paramsjson is :  " + paramsjson);

            String bodyjson = context.getParameter("bodyjson").replace("Autometer"," ");
            //logger.info(logplannameandcasename + "用例数据 bodyjson is :  " + bodyjson);

            String postdata = context.getParameter("postdata").replace("Autometer"," ");
            //logger.info(logplannameandcasename + "用例数据 postdata is :  " + postdata);

            String variablesjson = context.getParameter("variablesjson").replace("Autometer"," ");
            //logger.info(logplannameandcasename + "用例数据 variablesjson is :  " + variablesjson);

            String casetype = context.getParameter("casetype");
            //logger.info(logplannameandcasename + "用例数据 casetype is :  " + casetype);

            String protocal = context.getParameter("protocal");
            //logger.info(logplannameandcasename + "用例数据 protocal is :  " + protocal);

            newob.setCaseid(caseid);
            newob.setCasename(casename);
            newob.setTestplanid(testplanid);
            newob.setSlaverid(slaverid);
            newob.setBatchname(batchname);
            newob.setBatchid(batchid);
            newob.setCasetype(casetype);
            newob.setResponecontenttype(responecontenttype);
            newob.setResource(resource);
            newob.setRequestmMthod(RequestmMthod);
            newob.setRequestcontenttype(requestcontenttype);
            newob.setApistyle(apistyle);
            newob.setProtocal(protocal);
            newob.setHeadjson(headjson);
            newob.setBodyjson(bodyjson);
            newob.setParamjson(paramsjson);
            newob.setPostData(postdata);
            newob.setVariablesjson(variablesjson);
            newob.setDeployunitvisittype(deployvisitytype);
            newob.setMachineip(machineip);

            String expect = context.getParameter("expect").replace("Autometer"," ");;
            //logger.info(logplannameandcasename + "用例数据 expect is :  " + expect);
            List<ApicasesAssert> apicasesAssertList = new ArrayList<>();
            if ((!expect.isEmpty())&&(expect!=null)) {
                apicasesAssertList = JSONObject.parseArray(expect, ApicasesAssert.class);
            }
            newob.setApicasesAssertList(apicasesAssertList);
            expect = GetAssertInfo(apicasesAssertList);
            newob.setExpect(expect);
        }
        catch (Exception ex)
        {
            logger.info(logplannameandcasename + "性能用例数据 InitHttpDatabyJmeter 异常 :  " + ex.getMessage());
            throw new Exception("性能用例数据获取异常："+ex.getMessage());
        }
        return newob;
    }

    // 功能用例拼装请求需要的用例数据
    public RequestObject GetCaseRequestData(String PlanId, String TestCaseId, String SlaverId, String BatchId, String BatchName, String ExecPlanName) {
        RequestObject ro = new RequestObject();
        try
        {
            //ArrayList<HashMap<String, String>> planlist = getcaseData("select * from executeplan where id=" + PlanId);
            ArrayList<HashMap<String, String>> deployunitlist = testMysqlHelp.getcaseData("select b.protocal,b.port,b.id from apicases a inner join deployunit b on a.deployunitid=b.id where a.id=" + TestCaseId);
            ArrayList<HashMap<String, String>> apilist = testMysqlHelp.getcaseData("select b.visittype,b.apistyle,b.path,b.requestcontenttype,b.responecontenttype from apicases a inner join api b on a.apiid=b.id where a.id=" + TestCaseId);
            ArrayList<HashMap<String, String>> deployunitmachineiplist = testMysqlHelp.getcaseData("select m.ip,a.domain,a.visittype from macdepunit a INNER JOIN apicases b INNER JOIN executeplan c JOIN machine m on a.depunitid=b.deployunitid and  a.envid=c.envid and  m.id=a.machineid where b.id=" + TestCaseId + " and c.id=" + PlanId);
            ArrayList<HashMap<String, String>> caselist = testMysqlHelp.getcaseData("select * from apicases where id=" + TestCaseId);
            ArrayList<HashMap<String, String>> caseassertlist = testMysqlHelp.getcaseData("select * from apicases_assert where caseid=" + TestCaseId);

            // url请求资源路径
            String path = testMysqlHelp.getcaseValue("path", apilist);
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            //获取请求响应的数据格式
            String requestcontenttype = testMysqlHelp.getcaseValue("requestcontenttype", apilist);
            String responecontenttype = testMysqlHelp.getcaseValue("responecontenttype", apilist);
            // http请求方式 get，post
            String method = testMysqlHelp.getcaseValue("visittype", apilist).toLowerCase();
            // api风格
            String apistyle = testMysqlHelp.getcaseValue("apistyle", apilist).toLowerCase();
            // 协议 http,https,rpc
            String protocal = testMysqlHelp.getcaseValue("protocal", deployunitlist).toLowerCase();
            // 发布单元端口
            String port = testMysqlHelp.getcaseValue("port", deployunitlist);
            String deployunitid = testMysqlHelp.getcaseValue("id", deployunitlist);

            // 获取发布单元访问方式，ip或者域名
            String deployunitvisittype = testMysqlHelp.getcaseValue("visittype", deployunitmachineiplist);

            String IP=testMysqlHelp.getcaseValue("ip", deployunitmachineiplist);
            // 根据访问方式来确定ip还是域名
            String testserver = "";
            String resource = "";
            if (deployunitvisittype.equalsIgnoreCase("ip")) {
                testserver = IP;
                resource = protocal + "://" + testserver + ":" + port + path;
            } else {
                testserver = testMysqlHelp.getcaseValue("domain", deployunitmachineiplist);
                resource = protocal + "://" + testserver + path;
            }

            //获取断言记录
            TestAssert testAssert = new TestAssert(logger);
            List<ApicasesAssert> apicasesAssertList = testAssert.GetApicasesAssertList(caseassertlist);
            ro.setApicasesAssertList(apicasesAssertList);

            String casetype = testMysqlHelp.getcaseValue("casetype", caselist);
            String CaseName = testMysqlHelp.getcaseValue("casename", caselist);
            String expect = GetAssertInfo(apicasesAssertList);
            logger.info(logplannameandcasename + "用例数据 resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path + " casetype is: " + casetype);

            ro.setCaseid(TestCaseId);
            ro.setCasename(CaseName);
            ro.setDeployunitid(deployunitid);
            ro.setTestplanid(PlanId);
            ro.setSlaverid(SlaverId);
            ro.setBatchname(BatchName);
            ro.setTestplanname(ExecPlanName);
            ro.setBatchid(BatchId);
            ro.setExpect(expect);
            ro.setCasetype(casetype);
            ro.setProtocal(protocal);
            ro.setApistyle(apistyle);
            ro.setRequestcontenttype(requestcontenttype);
            ro.setRequestmMthod(method);
            ro.setResource(resource);
            ro.setResponecontenttype(responecontenttype);
            ro.setDeployunitvisittype(deployunitvisittype);
            ro.setMachineip(IP);
        }
        catch (Exception ex)
        {
            logger.info(logplannameandcasename + "功能用例数据GetCaseRequestData异常 :  " + ex.getMessage());
        }
        return ro;
    }


    //获取断言信息
    private String GetAssertInfo(List<ApicasesAssert> apicasesAssertList) {
        String expectValue = "";
        if (apicasesAssertList.size() > 0) {
            for (ApicasesAssert apicasesAssert : apicasesAssertList) {
                if (apicasesAssert.getAsserttype().equals(new String("Respone"))) {
                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言子类型：" + apicasesAssert.getAssertsubtype() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype() + "】";
                } else {
                    expectValue = expectValue + "【断言类型：" + apicasesAssert.getAsserttype() + "， 断言表达式：" + apicasesAssert.getExpression() + "， 断言条件：" + apicasesAssert.getAssertcondition() + "， 断言值：" + apicasesAssert.getAssertvalues() + "， 断言值类型：" + apicasesAssert.getAssertvaluetype() + "】";
                }
            }
        }
        return expectValue;
    }
}
