package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.*;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.mapper.*;
import com.zoctan.api.service.*;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zks888
 * @date 2020/04/17
 */
@Slf4j
@RestController
@RequestMapping("/exectestplancase")
public class TestPlanCaseController {
    @Value("${spring.datasource.dynamic.datasource.testcenter.url}")
    private String url;
    @Value("${spring.datasource.dynamic.datasource.testcenter.username}")
    private String username;
    @Value("${spring.datasource.dynamic.datasource.testcenter.password}")
    private String password;
    @Autowired
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;
    @Autowired
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private DeployunitService deployunitService;
    @Autowired(required = false)
    private ApicasesMapper apicasesMapper;
    @Autowired(required = false)
    private TestPlanCaseService tpcservice;
    @Autowired(required = false)
    private ApicasesReportService apicasereportservice;
    @Autowired(required = false)
    private ApicasesService apicasesService;
    @Autowired(required = false)
    private ApicasesVariablesService apicasesVariablesService;
    @Autowired(required = false)
    private TestvariablesValueService testvariablesValueService;
    @Autowired(required = false)
    private ApiCasedataService apiCasedataService;
    @Autowired(required = false)
    private TestvariablesService testvariablesService;
    @Autowired(required = false)
    private ApiParamsService apiParamsService;
    @Autowired(required = false)
    private ApiService apiService;
    @Autowired(required = false)
    private ExecuteplanService epservice;
    @Autowired(required = false)
    private MacdepunitService macdepunitService;
    @Autowired(required = false)
    private MachineService machineService;
    @Autowired(required = false)
    private ApicasesAssertService apicasesAssertService;
    @Autowired(required = false)
    private ExecuteplanParamsService executeplanParamsService;
    @Autowired(required = false)
    private ApicasesReportPerformanceMapper apicasesReportPerformanceMapper;
    @Autowired(required = false)
    private VariablesService variablesService;
    @Autowired(required = false)
    private DbvariablesService dbvariablesService;

    @PostMapping("/exec")
    public Result exec(@RequestBody Testplanandbatch planbatch) throws Exception {
        Long execplanid = planbatch.getPlanid();
        String batchname = planbatch.getBatchname();
        Executeplanbatch epb = executeplanbatchMapper.getbatchidbyplanidandbatchname(execplanid, batchname);
        // 检查plan当前的状态，如果状态为new，stop，finish继续执行
        Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
        List<ExecuteplanTestcase> caselist = executeplanTestcaseMapper.findcasebytestplanid(execplanid);
        log.info("计划id" + execplanid + " 批次为：" + batchname + " 获取用例数：" + caselist.size());
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        log.info("当前机器的IP为：" + ip);
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        List<Dispatch> dispatchList = new ArrayList<>();
        if (slaverlist.size() == 0) {
            log.info("当前执行机的IP为：" + ip + " 还未注册，请先完成注册");
            throw new Exception("当前执行机的IP为：" + ip + " 还未注册，请先完成注册");
        } else {
            Long slaverid = slaverlist.get(0).getId();
            String slavername = slaverlist.get(0).getSlavername();
            log.info("slaverid：" + slaverid + " slavername ：" + slavername);
            for (ExecuteplanTestcase testcase : caselist) {
                //需要执行的用例，先进入调度，由调度定时器统一执行
                Dispatch dis = new Dispatch();
                dis.setExpect(testcase.getExpect());
                dis.setExecplanid(execplanid);
                dis.setTestcaseid(testcase.getTestcaseid());
                dis.setDeployunitname(testcase.getDeployunitname());
                dis.setStatus("待分配");
                dis.setBatchname(batchname);
                dis.setBatchid(epb.getId());
                dis.setCasejmxname(testcase.getCasejmxname());
                dis.setExecplanname(ep.getExecuteplanname());
                dis.setSlaverid(slaverid);
                dis.setSlavername(slavername);
                dis.setTestcasename(testcase.getCasename());
                dis.setPlantype(ep.getUsetype());
                dis.setThreadnum(testcase.getThreadnum());
                dis.setLoops(testcase.getLoops());
                dispatchList.add(dis);
            }
        }
        dispatchMapper.insertBatchDispatch(dispatchList);
        log.info("完成保存调度用例条数：" + dispatchList.size());
        return ResultGenerator.genOkResult();
    }

    public boolean jmeterclassexistornot(String jarpath, String jmeterclassname) {
        boolean flag = false;
        try {
            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
            File f = new File(jarpath);
            URL url1 = f.toURI().toURL();
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(jarpath);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while (enumFiles.hasMoreElements()) {
                entry = (JarEntry) enumFiles.nextElement();
                if (entry.getName().indexOf("META-INF") < 0) {
                    String classFullName = entry.getName();
                    if (classFullName.indexOf(".class") > 0) {
                        //去掉后缀.class
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        if (className.equals(jmeterclassname)) {
                            flag = true;
                        }
                        //打印类名
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~打印类名:~~~~~~~~~~~~~~~~~~~~~~~~~" + className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/test")
    public Result gettest(@RequestBody ExecuteplanTestcase plancase) {
        //tpcservice.executeplancase(plancase.getExecuteplanid(),plancase.getTestcaseid());
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/execperformancetest")
    public Result execperformancetest(@RequestBody Dispatch dispatch) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        if (slaverlist.size() == 0) {
            log.error("性能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
            return ResultGenerator.genFailedResult("未找到ip为：" + ip + "的slaver");
        }
        Long SlaverId = slaverlist.get(0).getId();
        String ProjectPath = System.getProperty("user.dir");
        String JmeterPath = "";
        String JmxPath = "";
        String JmeterPerformanceReportPath = "";
        String JmeterPerformanceReportLogFilePath = "";

        if (ProjectPath.contains("slaverservice")) {
            JmeterPath = ProjectPath + "/apache-jmeter-5.3/bin";
            JmxPath = ProjectPath + "/servicejmxcase";
            JmeterPerformanceReportPath = ProjectPath + "/performancereport";
            JmeterPerformanceReportLogFilePath = ProjectPath + "/performancereportlogfile";

        } else {
            JmeterPath = ProjectPath + "/slaverservice/apache-jmeter-5.3/bin";
            JmxPath = ProjectPath + "/slaverservice/servicejmxcase";
            JmeterPerformanceReportPath = ProjectPath + "/slaverservice/performancereport";
            JmeterPerformanceReportLogFilePath = ProjectPath + "/slaverservice/performancereportlogfile";
        }
        File dir = new File(JmeterPerformanceReportPath);
        if (!dir.exists()) {// 判断目录是否存在
            dir.mkdir();
            log.info("创建性能报告目录performancereport完成 :" + JmeterPerformanceReportPath);
        }
        File dirlog = new File(JmeterPerformanceReportLogFilePath);
        if (!dirlog.exists()) {// 判断目录是否存在
            dirlog.mkdir();
            log.info("创建性能报告日志目录performancereport完成 :" + JmeterPerformanceReportLogFilePath);
        }
        String JmxCaseName = dispatch.getCasejmxname();
        String DeployUnitName = dispatch.getDeployunitname();
        String CaseName = dispatch.getTestcasename();
        log.info("性能任务-执行多机并行性能用例名 is......." + CaseName);
        Deployunit Deployunit = deployunitService.findDeployNameValueWithCode(DeployUnitName);
        if (Deployunit == null) {
            return ResultGenerator.genFailedResult("未找到发布单元为：" + DeployUnitName);
        }
        String Protocal = Deployunit.getProtocal();
        //如果是http,https，直接使用httpapitestcase下的functionhttpapi或者performancehttpapi来执行测试
        String JmeterClassName = "";
        String ClassName = "";
        String DeployUnitNameForJmeter = "";
        if (Protocal.equals("http") || Protocal.equals("https")) {
            DeployUnitNameForJmeter = "httpapitestcase";
            JmeterClassName = "HttpApiPerformance";
            ClassName = "com.api.autotest.test." + DeployUnitNameForJmeter + "." + JmeterClassName;
        }
        if (Protocal.equals("rpc")) {
            DeployUnitNameForJmeter = dispatch.getDeployunitname();
            JmeterClassName = DeployUnitName;
            ClassName = "com.api.autotest.test." + DeployUnitName + "." + JmxCaseName;
        }
        log.info("性能任务-DeployUnitNameForJmeter is......." + DeployUnitNameForJmeter + " JmeterClassName is........" + JmeterClassName);
        if (!JmeterClassExist(ClassName, JmeterPath)) {
            JmeterClassNotExist(dispatch, ClassName, CaseName);
            String memo = CaseName + "未开发对应的JmeterClass：" + ClassName;
            dispatchMapper.updatedispatchstatusandmemo("调度异常", memo, dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
        } else {
            JmeterPerformanceObject jmeterPerformanceObject = null;
            try {
                jmeterPerformanceObject = GetJmeterPerformance(dispatch);
                if (jmeterPerformanceObject != null) {
                    // 增加逻辑 获取计划的当前状态，如果为stop，放弃整个循环执行,return 掉
                    tpcservice.ExecuteHttpPerformancePlanCase(jmeterPerformanceObject, DeployUnitNameForJmeter, JmeterPath, JmxPath, JmeterClassName, JmeterPerformanceReportPath, JmeterPerformanceReportLogFilePath, dispatch.getThreadnum(), dispatch.getLoops(), dispatch.getCreator());
                    // 更新调度表对应用例状态为已分配
                    dispatchMapper.updatedispatchstatus("已分配", dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
                    log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。更新dispatch状态为已分配.....开始调用jmeter..。。。。。。。。。。。。。。。。。。。。。。。。。" + dispatch.getId());
                    slaverMapper.updateSlaverStaus(SlaverId, "运行中");
                    executeplanbatchMapper.updatebatchstatus(dispatch.getExecplanid(), dispatch.getBatchname(), "运行中");
                    log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。调用jmeter完成..。。。。。。。。。。。。。。。。。。。。。。。。。" + dispatch.getId());
                }
            } catch (Exception ex) {
                dispatchMapper.updatedispatchstatusandmemo("调度异常", "执行机Slaver运行性能测试异常：" + ex.getMessage(), dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
                log.info("性能任务-调度异常。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。获取 JmeterPerformanceObject对象异常报错..。。。。。。。。。。。。。。。。。。。。。。。。。" + ex.getMessage());
            }
        }
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/execfunctiontest")
    public Result execfunctiontest(@RequestBody List<Dispatch> dispatchList) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        if (slaverlist.size() == 0) {
            log.error("功能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
            return ResultGenerator.genFailedResult("未找到IP为：" + ip + "的slaver");
        }
        Long SlaverId = slaverlist.get(0).getId();
        try {
            String ProjectPath = System.getProperty("user.dir");
            String JmeterPath = "";
            String JmxPath = "";
            if (ProjectPath.contains("slaverservice")) {
                JmeterPath = ProjectPath + "/apache-jmeter-5.3/bin";
                JmxPath = ProjectPath + "/servicejmxcase";
            } else {
                JmeterPath = ProjectPath + "/slaverservice/apache-jmeter-5.3/bin";
                JmxPath = ProjectPath + "/slaverservice/servicejmxcase";
            }
            log.info("功能任务-获取待执行的功能用例数：。。。。。。。。。。。。。。。。。。。。。。。。" + dispatchList.size());
            int FunctionJmeter = 1;// GetJmeterProcess("FunctionJmeterProcess", "功能");
            HashMap<String, List<Dispatch>> ProtocolDispatchRun = GetProtocolDispatch(dispatchList);
            for (String Protocol : ProtocolDispatchRun.keySet()) {
                int ProtocolJmeterNum = 0;
                if (FunctionJmeter == 1) {
                    ProtocolJmeterNum = 1;
                } else {
                    ProtocolJmeterNum = FunctionJmeter / ProtocolDispatchRun.size();
                }
                log.info("功能任务-ProtocolJmeterNum：。。。。。。。。" + ProtocolJmeterNum);
                List<List<Dispatch>> last = FunctionDispatch(ProtocolJmeterNum, ProtocolDispatchRun.get(Protocol));
                if (Protocol.equalsIgnoreCase("http") || Protocol.equalsIgnoreCase("https")) {
                    for (int i = 0; i < last.size(); i++) {
                        List<Dispatch> JmeterList = last.get(i);
                        String DispatchIDs = "";
                        for (Dispatch dis : JmeterList) {
                            DispatchIDs = DispatchIDs + dis.getId() + ",";
                        }
                        if (!DispatchIDs.isEmpty()) {
                            DispatchIDs = DispatchIDs.substring(0, DispatchIDs.length() - 1);
                            log.info("功能任务-DispatchIDs:=======================" + DispatchIDs);
                            try {
                                tpcservice.ExecuteHttpPlanFunctionCase(SlaverId, JmeterPath, JmxPath, DispatchIDs, url, username, password, i);
                                for (Dispatch dis : JmeterList) {
                                    dispatchMapper.updatedispatchstatus("已分配", dis.getSlaverid(), dis.getExecplanid(), dis.getBatchid(), dis.getTestcaseid());
                                    log.info("功能任务-更新调度状态为已分配：。。。。。。。。" + dis.getId());
                                }
                                slaverMapper.updateSlaverStaus(SlaverId, "运行中");
                            } catch (Exception ex) {
                                log.info("调用JmeterCMD异常：。。。。。。。。" + ex.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            slaverMapper.updateSlaverStaus(SlaverId, "空闲");
            log.error("功能任务-execfunctiontest 异常:=======================" + ex.getMessage());
            return ResultGenerator.genFailedResult(ex.getMessage());
        }
        return ResultGenerator.genOkResult();
    }

    public JmeterPerformanceObject GetJmeterPerformanceCaseData(Dispatch dispatch) throws Exception {
        JmeterPerformanceObject jmeterPerformanceObject = new JmeterPerformanceObject();
        jmeterPerformanceObject.setTestplanid(dispatch.getExecplanid());
        jmeterPerformanceObject.setCaseid(dispatch.getTestcaseid());
        jmeterPerformanceObject.setSlaverid(dispatch.getSlaverid());
        jmeterPerformanceObject.setBatchid(dispatch.getBatchid());
        jmeterPerformanceObject.setCasename(dispatch.getTestcasename());
        jmeterPerformanceObject.setBatchname(dispatch.getBatchname());
        jmeterPerformanceObject.setExecuteplanname(dispatch.getExecplanname());
        Apicases apicases = apicasesService.getBy("id", dispatch.getTestcaseid());
        if (apicases == null) {
            throw new Exception("未找到用例，请检查是否被删除！");
        }
        jmeterPerformanceObject.setCasetype(apicases.getCasetype());
        Api api = apiService.getBy("id", apicases.getApiid());
        if (api == null) {
            throw new Exception("未找到用例的API，请检查是否被删除！");
        }
        jmeterPerformanceObject.setApistyle(api.getApistyle());
        jmeterPerformanceObject.setRequestmMthod(api.getVisittype());
        jmeterPerformanceObject.setRequestcontenttype(api.getRequestcontenttype());
        jmeterPerformanceObject.setResponecontenttype(api.getResponecontenttype());
        Deployunit deployunit = deployunitService.getBy("id", api.getDeployunitid());
        if (deployunit == null) {
            throw new Exception("未找到用例的API所在的发布单元，请检查是否被删除！");
        }
        jmeterPerformanceObject.setProtocal(deployunit.getProtocal());

        Executeplan executeplan = epservice.getBy("id", dispatch.getExecplanid());
        if (executeplan == null) {
            throw new Exception("未找到用例的测试集合，请检查是否被删除！");
        }
        Long EnvID = executeplan.getEnvid();
        Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(EnvID, deployunit.getId());
        if (macdepunit != null) {
            String testserver = "";
            String resource = "";
            if (macdepunit.getVisittype().equalsIgnoreCase("ip")) {
                Long MachineID = macdepunit.getMachineid();
                Machine machine = machineService.getBy("id", MachineID);
                if (machine == null) {
                    throw new Exception("未在环境中部署找到服务器，请检查是否被删除！");
                }
                jmeterPerformanceObject.setMachineip(machine.getIp());
                testserver = machine.getIp();
                resource = deployunit.getProtocal() + "://" + testserver + ":" + deployunit.getPort() + api.getPath();
            } else {
                testserver = macdepunit.getDomain();
                resource = deployunit.getProtocal() + "://" + testserver + api.getPath();
            }
            jmeterPerformanceObject.setDeployunitvisittype(macdepunit.getVisittype());
            jmeterPerformanceObject.setResource(resource.trim());
        } else {
            throw new Exception("未在环境中部署用例API所在的发布单元，请检查是否被删除！");
        }

        List<ApicasesAssert> apicasesAssertList = apicasesAssertService.findAssertbycaseid(dispatch.getTestcaseid().toString());
        if (apicasesAssertList.size() > 0) {
            String ExpectJson = JSON.toJSONString(apicasesAssertList);
            jmeterPerformanceObject.setExpect(ExpectJson);
        } else {
            jmeterPerformanceObject.setExpect("");
        }

        jmeterPerformanceObject.setMysqlurl(url.trim());
        jmeterPerformanceObject.setMysqlusername(username.trim());
        jmeterPerformanceObject.setMysqlpassword(password.trim());
        return jmeterPerformanceObject;
    }

    public JmeterPerformanceObject GetJmeterPerformanceCaseRequestData(JmeterPerformanceObject jmeterPerformanceObject, Dispatch dispatch, Api api) throws Exception {
        String PlanID = String.valueOf(jmeterPerformanceObject.getTestplanid());
        String BatchName = jmeterPerformanceObject.getBatchname();
        String RequestContentType = jmeterPerformanceObject.getRequestcontenttype();
        List<ApiCasedata> apiCasedataList = apiCasedataService.getcasedatabycaseid(dispatch.getTestcaseid());

        log.info("GetJmeterPerformanceCaseRequestData :" + PlanID + " BatchName:" + BatchName);
        List<TestvariablesValue> testvariablesValueList = testvariablesValueService.gettvlist(Long.parseLong(PlanID), BatchName, "接口");

        List<TestvariablesValue> DBtestvariablesValueList = testvariablesValueService.gettvlist(Long.parseLong(PlanID), BatchName, "数据库");

        log.info("gettvlist size :" + testvariablesValueList.size());

        HashMap<String, String> InterFaceMap = new HashMap<>();
        for (TestvariablesValue te : testvariablesValueList) {
            InterFaceMap.put(te.getVariablesname(), te.getVariablesvalue());
        }

        HashMap<String, String> DBMap = new HashMap<>();
        for (TestvariablesValue te : DBtestvariablesValueList) {
            DBMap.put(te.getVariablesname(), te.getVariablesvalue());
        }

        //1.Url替换接口变量
        String RequestUrl = jmeterPerformanceObject.getResource();
        for (String VariableName : InterFaceMap.keySet()) {
            String UseVariableName = "<" + VariableName + ">";
            if (RequestUrl.contains(UseVariableName)) {
                String VariableValue = InterFaceMap.get(VariableName);
                RequestUrl = RequestUrl.replace(UseVariableName, VariableValue);
            }
        }
        //2.Url替换数据库变量
        for (String VariableName : DBMap.keySet()) {
            String UseVariableName = "<<" + VariableName + ">>";
            if (RequestUrl.contains(UseVariableName)) {
                String VariableValue = DBMap.get(VariableName);
                RequestUrl = RequestUrl.replace(UseVariableName, VariableValue);
            }
        }

        jmeterPerformanceObject.setResource(RequestUrl);
        //String Caseid=dispatch.getTestcaseid().toString();
        HashMap<String, Object> HeaderMap = new HashMap<>();
        HashMap<String, Object> ParamsMap = new HashMap<>();
        HashMap<String, Object> BodyMap = new HashMap<>();
        String PostData = "";
        for (ApiCasedata apiCasedata : apiCasedataList) {
            String ParamName = apiCasedata.getApiparam();
            String Paramvalue = apiCasedata.getApiparamvalue();
            String DataType = apiCasedata.getParamstype();
            if (apiCasedata.getPropertytype().equalsIgnoreCase("Params")) {
                Object Result = Paramvalue;
                if ((Paramvalue.contains("<") && Paramvalue.contains(">")) || (Paramvalue.contains("<<") && Paramvalue.contains(">>")) || (Paramvalue.contains("[") && Paramvalue.contains("]"))) {
                    Result = GetVaraibaleValue(Paramvalue, InterFaceMap, DBMap);
                }
                Object LastObjectValue = GetDataByType(Result.toString(), DataType);
                ParamsMap.put(ParamName, LastObjectValue);
            }
            if (apiCasedata.getPropertytype().equalsIgnoreCase("Header")) {
                Object Result = Paramvalue;
                if ((Paramvalue.contains("<") && Paramvalue.contains(">")) || (Paramvalue.contains("<<") && Paramvalue.contains(">>")) || (Paramvalue.contains("[") && Paramvalue.contains("]"))) {
                    Result = GetVaraibaleValue(Paramvalue, InterFaceMap, DBMap);
                }
                HeaderMap.put(ParamName, Result);
            }
            if (apiCasedata.getPropertytype().equalsIgnoreCase("Body")) {
                if (RequestContentType.equalsIgnoreCase("Form表单")) {
                    Object Result = Paramvalue;
                    if ((Paramvalue.contains("<") && Paramvalue.contains(">")) || (Paramvalue.contains("<<") && Paramvalue.contains(">>")) || (Paramvalue.contains("[") && Paramvalue.contains("]"))) {
                        Result = GetVaraibaleValue(Paramvalue, InterFaceMap, DBMap);
                    }
                    Object LastObjectValue = GetDataByType(Result.toString(), DataType);
                    BodyMap.put(ParamName, LastObjectValue);
                } else {
                    PostData = Paramvalue;
                    //替换接口变量
                    for (String VariableName : InterFaceMap.keySet()) {
                        String UseVariableName = "<" + VariableName + ">";
                        if (PostData.contains(UseVariableName)) {
                            String VariableValue = InterFaceMap.get(VariableName);
                            PostData = PostData.replace(UseVariableName, VariableValue);
                        }
                    }
                    //替换数据库变量
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
        //全局参数Header
        GetHeaderFromTestPlanParam(HeaderMap, dispatch, InterFaceMap, DBMap);
        if (HeaderMap.size() > 0) {
            jmeterPerformanceObject.setHeadjson(JSON.toJSONString(HeaderMap));
        } else {
            jmeterPerformanceObject.setHeadjson("");
        }
        if (ParamsMap.size() > 0) {
            jmeterPerformanceObject.setParamsjson(JSON.toJSONString(ParamsMap));
        } else {
            jmeterPerformanceObject.setParamsjson("");
        }
        if (BodyMap.size() > 0) {
            jmeterPerformanceObject.setBodyjson(JSON.toJSONString(BodyMap));
        } else {
            jmeterPerformanceObject.setBodyjson("");
        }
        jmeterPerformanceObject.setPostdata(PostData);

        //增加随机变量json
        List<Variables> variablesList = variablesService.listAll();
        String variablesjson = "";
        if (variablesList.size() > 0) {
            variablesjson = JSON.toJSONString(variablesList);
        }
        jmeterPerformanceObject.setRadomvariablejson(variablesjson);

        //断言
        List<ApicasesAssert> apicasesAssertList = apicasesAssertService.findAssertbycaseid(dispatch.getTestcaseid().toString());
        if (apicasesAssertList.size() > 0) {
            String ExpectJson = JSON.toJSONString(apicasesAssertList);
            jmeterPerformanceObject.setExpect(ExpectJson);
        } else {
            jmeterPerformanceObject.setExpect("");
        }
        return jmeterPerformanceObject;
    }

    //判断是否有拼接
    private boolean GetSubOrNot(HashMap<String, String> VariablesMap, String Value, String prefix, String profix) {
        for (String Key : VariablesMap.keySet()) {
            String ActualValue = prefix + Key + profix;
            if (Value.contains(ActualValue)) {
                String LeftValue = Value.replace(ActualValue, "");
                //表示有拼接
                return LeftValue.length() > 0;
            }
        }
        return false;
    }

    private Object GetVaraibaleValue(String Value, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap) throws Exception {
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
                    Testvariables testvariables = testvariablesService.getBy("testvariablesname", interfacevariablesName);//  testMysqlHelp.GetVariablesDataType(interfacevariablesName);
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
                exist = true;
                String ActualValue = DBMap.get(DBvariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<<" + DBvariablesName + ">>", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    Dbvariables dbvariables = dbvariablesService.getBy("dbvariablesname", DBvariablesName);
                    if (dbvariables == null) {
                        ObjectValue = "未找到变量：" + Value + " 请检查变量管理-数据库变量中是否存在此变量";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, dbvariables.getValuetype());
                    }
                }
            }
        }
        if (!exist) {
            throw new Exception("当前用例参数值中存在变量：" + Value + " 未找到对应值，请检查是否有配置对应变量的子条件获取此变量值");
        }
        return ObjectValue;
    }

    private void GetHeaderFromTestPlanParam(HashMap<String, Object> HeaderMap, Dispatch dispatch, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap) throws Exception {
        List<ExecuteplanParams> executeplanHeaderParamList = executeplanParamsService.getParamsbyepid(dispatch.getExecplanid(), "Header");
        //全局Header如果有参数，则替换原参数，没有则加上全局Header参数
        for (ExecuteplanParams executeplanParams : executeplanHeaderParamList) {
            String ParamName = executeplanParams.getKeyname();
            String ParamValue = executeplanParams.getKeyvalue();
            Object Result = ParamValue;
            if ((ParamValue.contains("<") && ParamValue.contains(">")) || (ParamValue.contains("<<") && ParamValue.contains(">>")) || (ParamValue.contains("[") && ParamValue.contains("]"))) {
                Result = GetVaraibaleValue(ParamValue, InterfaceMap, DBMap);
            }
            HeaderMap.put(ParamName, Result);
        }
    }

    public JmeterPerformanceObject GetJmeterPerformance(Dispatch dispatch) throws Exception {
        JmeterPerformanceObject jmeterPerformanceObject = GetJmeterPerformanceCaseData(dispatch);
        Apicases apicases = apicasesService.getBy("id", dispatch.getTestcaseid());
        Api api = apiService.getBy("id", apicases.getApiid());
        jmeterPerformanceObject = GetJmeterPerformanceCaseRequestData(jmeterPerformanceObject, dispatch, api);
        return jmeterPerformanceObject;
    }

    public void JmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
        ApicasesReport ar = new ApicasesReport();
        ar.setTestplanid(dis.getExecplanid());
        ar.setCaseid(dis.getTestcaseid());
        ar.setCasename(dis.getTestcasename());
        ar.setErrorinfo("性能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
        ar.setBatchname(dis.getBatchname());
        ar.setExpect(dis.getExpect());
        ar.setStatus("失败");
        ar.setSlaverid(dis.getSlaverid());
        ar.setRuntime(0L);
        apicasesReportPerformanceMapper.addcasereport(ar);
    }

    public boolean JmeterClassExist(String jmeterclassname, String JmeterPath) {
        String JmeterExtJarPath = JmeterPath.replace("bin", "lib");
        String JarPath = JmeterExtJarPath + "/ext/api-jmeter-autotest-1.0.jar";
        boolean flag = false;
        try {
            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
            File f = new File(JarPath);
            URL url1 = f.toURI().toURL();
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(JarPath);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while (enumFiles.hasMoreElements()) {
                entry = (JarEntry) enumFiles.nextElement();
                if (entry.getName().indexOf("META-INF") < 0) {
                    String classFullName = entry.getName();
                    if (classFullName.indexOf(".class") > 0) {
                        //去掉后缀.class
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        if (className.equals(jmeterclassname)) {
                            flag = true;
                        }
                        //打印类名
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void FunJmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
        ApicasesReport ar = new ApicasesReport();
        ar.setTestplanid(dis.getExecplanid());
        ar.setCaseid(dis.getTestcaseid());
        ar.setCasename(dis.getTestcasename());
        ar.setErrorinfo("功能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
        ar.setBatchname(dis.getBatchname());
        ar.setExpect(dis.getExpect());
        ar.setStatus("失败");
        ar.setRuntime(0L);
        Long planid = dis.getExecplanid();

        apicasereportservice.addcasereport(ar);
        epservice.updatetestplanstatus(planid, "fail");
        log.info("功能任务-未找到用例对应的jmeter-class类......." + jmeterclassname);
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
            Result = Data.split(",");
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

    private Object GetVariablesDataType(String Value, String PlanId, String BatchName, String Caseid) throws Exception {
        Object Result = "";
        Value = Value.substring(1);

        Testvariables testvariables = testvariablesService.getBy("testvariablesname", Value);
        if (testvariables == null) {
            throw new Exception("未找到变量：" + Value + "绑定的接口用例，请检查变量管理-变量管理中是否存在此变量");
        }
        String ValueType = testvariables.getValuetype();

        Condition con = new Condition(ApicasesVariables.class);
        con.createCriteria().andCondition("variablesname = '" + Value.replace("'", "''") + "'").andCondition(" caseid = " + Caseid);
        List<ApicasesVariables> apicasesVariablesList = apicasesVariablesService.listByCondition(con);
        if (apicasesVariablesList.size() == 0) {
            throw new Exception("未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例");
        }
        //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
        TestvariablesValue testvariablesValue = testvariablesValueService.gettestvariablesvalue(Long.parseLong(PlanId), Long.parseLong(Caseid), Value, BatchName);
        if (testvariablesValue != null) {
            String VariablesNameValue = testvariablesValue.getVariablesvalue();
            Result = GetDataByType(VariablesNameValue, ValueType);
        } else {
            throw new Exception("未找到变量：" + Value + "的值，请检查变量管理-变量结果中是否存在此变量值");
        }
        return Result;
    }

    // 功能用例平均分配
    public List<List<Dispatch>> FunctionDispatch(int Jmeternums, List<Dispatch> dispatchList) {
        if (dispatchList.size() < Jmeternums) {
            Jmeternums = dispatchList.size();
        }
        List<List<Dispatch>> LastDispatchList = new ArrayList<List<Dispatch>>();
        List<Dispatch> splitdispatchList;
        int sizemode = (dispatchList.size()) / Jmeternums;
        int sizeleft = (dispatchList.size()) % Jmeternums;
        int j = 0;
        int x = 0;
        for (int i = 0; i < Jmeternums; i++) {
            splitdispatchList = new ArrayList<Dispatch>();
            for (j = x; j < (sizemode + x); j++) {
                Dispatch dis = dispatchList.get(j);
                splitdispatchList.add(dis);
            }
            x = j;
            LastDispatchList.add(splitdispatchList);
        }
        if (sizeleft != 0) {
            for (int y = 1; y < sizeleft + 1; y++) {
                Dispatch dis = dispatchList.get(dispatchList.size() - y);
                LastDispatchList.get(LastDispatchList.size() - 1).add(dis);
            }
        }
        return LastDispatchList;
    }

    public int GetJmeterProcess(String DictionaryCode, String DicType) {
        List<Dictionary> slavermaxfunthreaddic = dictionaryMapper.findDicNameValueWithCode(DictionaryCode);

        int JmeterProcess = 1;
        //字典表未配置，默认取一条
        if (slavermaxfunthreaddic.size() == 0) {
            log.info("功能任务-字典表未配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
        } else {
            String slavermaxthread = slavermaxfunthreaddic.get(0).getDicitmevalue();
            try {
                JmeterProcess = Integer.valueOf(slavermaxthread);
            } catch (Exception ex) {
                log.error("功能任务-字典表未正确配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
            }
            log.info("功能任务-获取字典表slaver并发执行jmerter进程个数：。。。。。。。。" + slavermaxthread);
        }
        return JmeterProcess;
    }

    public HashMap<String, List<Dispatch>> GetProtocolDispatch(List<Dispatch> dispatchList) {
        List<Dispatch> dispatchResultList = new ArrayList<>();
        HashMap<Long, List<Dispatch>> GroupDispatch = new HashMap<>();

        //获取计划id分组中的第一组列表
        for (Dispatch dispatch : dispatchList) {
            Long planid = dispatch.getExecplanid();
            if (!GroupDispatch.containsKey(planid)) {
                List<Dispatch> dispatchListtmp = new ArrayList<>();
                dispatchListtmp.add(dispatch);
                GroupDispatch.put(planid, dispatchListtmp);
            } else {
                GroupDispatch.get(planid).add(dispatch);
            }
        }
        for (Long planid : GroupDispatch.keySet()) {
            dispatchResultList = GroupDispatch.get(planid);
            break;
        }

        //获取发布单元分组列表
        HashMap<String, List<Dispatch>> DeployUnitGroupDispatch = new HashMap<>();
        for (Dispatch dispatch : dispatchResultList) {
            String DeployUnit = dispatch.getDeployunitname();
            if (!DeployUnitGroupDispatch.containsKey(DeployUnit)) {
                List<Dispatch> dispatchListtmp = new ArrayList<>();
                dispatchListtmp.add(dispatch);
                DeployUnitGroupDispatch.put(DeployUnit, dispatchListtmp);
            } else {
                DeployUnitGroupDispatch.get(DeployUnit).add(dispatch);
            }
        }

        //合并协议列表
        HashMap<String, List<Dispatch>> ProtocolGroupDispatch = new HashMap<>();
        for (String DeployUnit : DeployUnitGroupDispatch.keySet()) {
            Deployunit deployunit = deployunitService.findDeployNameValueWithCode(DeployUnit);
            String Protocal = deployunit.getProtocal();
            if (Protocal.equalsIgnoreCase("http") || Protocal.equalsIgnoreCase("https")) {
                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "http");
            }
            if (Protocal.equalsIgnoreCase("rpc")) {
                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "rpc");
            }
        }
        return ProtocolGroupDispatch;
    }

    public HashMap<String, List<Dispatch>> MergeCaseList(HashMap<String, List<Dispatch>> ProtocolGroupDispatch, HashMap<String, List<Dispatch>> DeployUnitGroupDispatch, String DeployUnit, String Protocol) {
        HashMap<String, List<Dispatch>> ProtocolGroupResultDispatch = ProtocolGroupDispatch;
        if (!ProtocolGroupResultDispatch.containsKey(Protocol)) {
            List<Dispatch> dispatchListtmp = new ArrayList<>();
            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
                dispatchListtmp.add(dis);
            }
            ProtocolGroupResultDispatch.put(Protocol, dispatchListtmp);
        } else {
            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
                ProtocolGroupResultDispatch.get(Protocol).add(dis);
            }
        }
        return ProtocolGroupResultDispatch;
    }
}

