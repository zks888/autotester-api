webpackJsonp([10],{OiUL:function(t,e){},SYIK:function(t,e,a){"use strict";e.c=function(t){return Object(r.a)({url:"/apicases/report/performance",method:"get",params:t})},e.i=function(t){return Object(r.a)({url:"/apicases/report/performance/search",method:"post",data:t})},e.b=function(t){return Object(r.a)({url:"/apicases/report/performance/findApicasereportWithNameandStatus",method:"post",data:t})},e.f=function(t){return Object(r.a)({url:"/apicases/report/performance/getperformancecasestatics",method:"post",data:t})},e.e=function(t){return Object(r.a)({url:"/apicases/report/performance/getperformanceallstatics",method:"post",data:t})},e.g=function(t){return Object(r.a)({url:"/apicases/report/performance/getperformanceslaverstatics",method:"post",data:t})},e.d=function(t){return Object(r.a)({url:"/apicases/report/performance/getperformanceCaseSandF",method:"post",data:t})},e.a=function(t){return Object(r.a)({url:"/apicases/report/performance",method:"post",data:t})},e.j=function(t){return Object(r.a)({url:"/apicases/report/performance",method:"put",data:t})},e.h=function(t){return Object(r.a)({url:"/apicases/report/performance/"+t,method:"delete"})};var r=a("vLgD")},d4Dj:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=a("Xxa5"),n=a.n(r),i=a("exGp"),l=a.n(i),s=a("zXKF"),o=a("SYIK"),c=a("bR3O"),p=a("W5b6"),u=a("vuyV"),d={filters:{statusFilter:function(t){return{published:"success",draft:"gray",deleted:"danger"}[t]}},data:function(){return{activeName:"zero",chartPie:null,seriesData:[],casestaticsList:[],performancecasesstaticsList:[],salvercaseList:[],caseconditionreport:[],dispatchdata:[],itemKey:null,tmptestplanname:"",tmptestplanid:null,tmpbatchname:null,apireportList:[],apiList:[],planbatchList:[],execplanList:[],deployunitList:[],listLoading:!1,dicvisitypeQuery:{page:1,size:30,diccode:"httpvisittype"},total:0,conditiontotal:0,dispatchtotal:0,listQuery:{page:1,size:10,listLoading:!0,testplanname:"",testplanid:null,batchname:null},dialogStatus:"add",dialogFormVisible:!1,textMap:{updateRole:"修改apireport",update:"修改apireport",add:"添加apireport"},btnLoading:!1,tmpapireport:{executeplanid:"",id:"",deployunitid:"",deployunitname:"",batchname:"",requestheader:"",requestdatas:"",visittype:"",path:"",url:"",requestmethod:"",memo:""},tmpquery:{page:1,size:10,testplanname:"",executeplanid:"",batchid:"",batchname:"",caseststus:""},tmpconditionquery:{page:1,size:10,executeplanid:"",batchid:"",batchname:""},tmpdispatchquery:{page:1,size:10,executeplanid:"",batchid:"",batchname:""},tmpexecplantype:{usetype:""},search:{page:1,size:10,testplanname:"",testplanid:null,batchname:null}}},created:function(){this.getexecplanList()},mounted:function(){this.drawLine()},methods:{unix2CurrentTime:a("0xDb").b,drawLine:function(){var t,e=a("XLwt").init(document.getElementById("mian"));t={title:{text:"性能用例执行结果比例",left:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b} : {c} ({d}%)"},color:["#44c23f","#d7212f"],series:[{name:"类型",type:"pie",radius:"75%",center:["50%","48%"],data:this.seriesData}]},e.setOption(t)},getapireportList:function(){var t=this;this.$refs.tmpquery.validate(function(e){e&&Object(o.i)(t.tmpquery).then(function(e){t.apireportList=e.data.list,t.total=e.data.total}).catch(function(e){t.$message.error("加载api报告列表失败")})})},findconditionreport:function(){var t=this;Object(c.b)(this.tmpconditionquery).then(function(e){t.caseconditionreport=e.data.list,t.conditiontotal=e.data.total}).catch(function(e){t.$message.error("加载api报告列表失败")})},getperformanceallstatics:function(){var t=this;Object(o.e)(this.tmpconditionquery).then(function(e){t.casestaticsList=e.data}).catch(function(e){t.$message.error("加载api报告列表失败")})},getperformanceslaverstatics:function(){var t=this;Object(o.g)(this.tmpconditionquery).then(function(e){t.salvercaseList=e.data}).catch(function(e){t.$message.error("加载api报告列表失败")})},getDispatchWithstatus:function(){var t=this;Object(s.b)(this.tmpdispatchquery).then(function(e){t.dispatchdata=e.data.list,t.dispatchtotal=e.data.total}).catch(function(e){t.$message.error("加载api报告列表失败")})},searchcaseReportBy:function(){var t=this;this.$refs.tmpquery.validate(function(e){e&&Object(o.b)(t.tmpquery).then(function(e){t.apireportList=e.data.list,t.total=e.data.total}).catch(function(e){t.$message.error("加载用例结果报告列表失败")})})},getexecplanList:function(){var t=this;this.tmpexecplantype.usetype="性能",Object(u.e)(this.tmpexecplantype).then(function(e){t.execplanList=e.data}).catch(function(e){t.$message.error("加载计划列表失败")})},getperformanceCaseSandF:function(){var t=this;return l()(n.a.mark(function e(){return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Object(o.d)(t.tmpquery).then(function(e){t.seriesData=e.data}).catch(function(e){t.$message.error("加载用例结果比例列表失败")});case 2:case"end":return e.stop()}},e,t)}))()},getperformancestatics:function(){var t=this;return l()(n.a.mark(function e(){return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return t.getapireportList(),t.getperformanceallstatics(),t.getperformanceslaverstatics(),e.next=5,t.getperformanceCaseSandF();case 5:t.drawLine(),t.getperformancecasestatics(),t.findconditionreport(),t.getDispatchWithstatus();case 9:case"end":return e.stop()}},e,t)}))()},getperformancecasestatics:function(){var t=this;this.$refs.tmpquery.validate(function(e){e&&Object(o.f)(t.tmpquery).then(function(e){t.performancecasesstaticsList=e.data.list}).catch(function(t){})})},testplanselectChanged:function(t){for(var e=this,a=0;a<this.execplanList.length;a++)this.execplanList[a].executeplanname===t&&(this.tmpquery.executeplanid=this.execplanList[a].id,this.tmpconditionquery.executeplanid=this.execplanList[a].id,this.tmpdispatchquery.executeplanid=this.execplanList[a].id);Object(p.b)(this.tmpquery).then(function(t){e.planbatchList=t.data}).catch(function(t){e.$message.error("加载执行计划列表失败")})},testbatchselectChanged:function(t){this.tmpquery.batchname=t;for(var e=0;e<this.planbatchList.length;e++)this.planbatchList[e].batchname===t&&(this.tmpquery.batchid=this.planbatchList[e].id,this.tmpconditionquery.batchid=this.planbatchList[e].id,this.tmpdispatchquery.batchid=this.planbatchList[e].id,this.tmpdispatchquery.batchname=t,this.tmpconditionquery.batchname=t)},getIndex:function(t){return(this.tmpquery.page-1)*this.tmpquery.size+t+1},conditiongetIndex:function(t){return(this.tmpconditionquery.page-1)*this.tmpconditionquery.size+t+1},dispatchgetIndex:function(t){return(this.tmpdispatchquery.page-1)*this.tmpdispatchquery.size+t+1},handleSizeChange:function(t){this.tmpquery.page=1,this.tmpquery.size=t,this.getapireportList()},conditionhandleSizeChange:function(t){this.tmpconditionquery.page=1,this.tmpconditionquery.size=t,this.findconditionreport()},dispatchhandleSizeChange:function(t){this.tmpdispatchquery.page=1,this.tmpdispatchquery.size=t,this.getDispatchWithstatus()},handleCurrentChange:function(t){this.tmpquery.page=t,this.getapireportList()},conditionhandleCurrentChange:function(t){this.tmpconditionquery.page=t,this.findconditionreport()},dispatchhandleCurrentChange:function(t){this.tmpdispatchquery.page=t,this.getDispatchWithstatus()}}},m={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("el-form",{ref:"tmpquery",attrs:{inline:!0,model:t.tmpquery}},[a("el-form-item",[t.hasPermission("apireport:list")?a("el-button",{attrs:{type:"success",size:"mini",icon:"el-icon-refresh"},nativeOn:{click:function(e){return e.preventDefault(),t.getapireportList(e)}}},[t._v("刷新")]):t._e()],1),t._v(" "),t.hasPermission("apireport:search")?a("span",[a("el-form-item",{attrs:{label:"性能测试集合",prop:"testplanname",required:""}},[a("el-select",{attrs:{placeholder:"测试集合"},on:{change:function(e){return t.testplanselectChanged(e)}},model:{value:t.tmpquery.testplanname,callback:function(e){t.$set(t.tmpquery,"testplanname",e)},expression:"tmpquery.testplanname"}},[a("el-option",{attrs:{label:"请选择"}}),t._v(" "),t._l(t.execplanList,function(t,e){return a("div",{key:e},[a("el-option",{attrs:{label:t.executeplanname,value:t.executeplanname}})],1)})],2)],1),t._v(" "),a("el-form-item",{attrs:{label:"执行计划",prop:"batchname",required:""}},[a("el-select",{attrs:{placeholder:"执行计划"},on:{change:function(e){return t.testbatchselectChanged(e)}},model:{value:t.tmpquery.batchname,callback:function(e){t.$set(t.tmpquery,"batchname",e)},expression:"tmpquery.batchname"}},[a("el-option",{attrs:{label:"请选择"}}),t._v(" "),t._l(t.planbatchList,function(t,e){return a("div",{key:e},[a("el-option",{attrs:{label:t.batchname,value:t.batchname}})],1)})],2)],1),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",loading:t.btnLoading},on:{click:t.getperformancestatics}},[t._v("查询")])],1)],1):t._e()],1)],1),t._v(" "),a("div",{staticClass:"dashboard-editor-container"},[a("github-corner",{staticClass:"github-corner"}),t._v(" "),a("el-row",{attrs:{gutter:50}},[a("el-col",{attrs:{xs:24,sm:24,lg:9}},[a("div",{staticClass:"chart-wrapper",attrs:{id:"22"}},[[a("div",{staticStyle:{width:"300px",height:"280px"},attrs:{id:"mian"}})]],2)]),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,lg:15}},[a("div",{staticClass:"chart-wrapper",attrs:{id:"12"}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],staticStyle:{width:"290vh"},attrs:{data:t.casestaticsList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"用例总数",align:"center",prop:"caseNum",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"线程总数",align:"center",prop:"threadnums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"循环总数",align:"center",prop:"loops",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"执行机总数",align:"center",prop:"slavernums",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"运行总数",align:"center",prop:"execCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"成功总数",align:"center",prop:"successCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"失败总数",align:"center",prop:"failCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"总耗时(秒)",align:"center",prop:"costtime",width:"80"}})],1)],1)]),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,lg:15}},[a("div",{staticClass:"chart-wrapper",attrs:{id:"13"}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,staticStyle:{width:"120vh"},attrs:{data:t.performancecasesstaticsList,size:"mini","element-loading-text":"loading",border:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"TPS",align:"center",prop:"tps",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"运行次数",align:"center",prop:"samples",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"错误次数",align:"center",prop:"errorcount",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"错误率%",align:"center",prop:"errorrate",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"average(ms)",align:"center",prop:"average",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"90th pct(ms)",align:"center",prop:"nzpct",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"95th pct(ms)",align:"center",prop:"nfpct",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"99th pct(ms)",align:"center",prop:"nnpct",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"消耗时间(s)",align:"center",prop:"runtime",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"min(ms)",align:"center",prop:"min",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"max(ms)",align:"center",prop:"max",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"median(ms)",align:"center",prop:"median",width:"100"}})],1)],1)]),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,lg:15}},[a("div",{staticClass:"chart-wrapper",attrs:{id:"14"}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,staticStyle:{width:"100vh"},attrs:{data:t.salvercaseList,size:"mini","element-loading-text":"loading",border:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"分布执行机",align:"center",prop:"slaverName",width:"160"}}),t._v(" "),a("el-table-column",{attrs:{label:"线程数",align:"center",prop:"threadnums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"循环数",align:"center",prop:"loops",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"运行总数",align:"center",prop:"execCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"成功总数",align:"center",prop:"successCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"失败总数",align:"center",prop:"failCaseNums",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"耗时(秒)",align:"center",prop:"costtime",width:"80"}})],1)],1)])],1)],1),t._v(" "),a("el-tabs",{ref:"tabs",attrs:{type:"card"},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"用例执行报告",name:"zero"}},[a("div",{staticClass:"filter-container"},[a("el-form",{attrs:{inline:!0}},[a("el-form-item",{attrs:{label:"状态:",prop:"testplanname"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"值类型"},model:{value:t.tmpquery.caseststus,callback:function(e){t.$set(t.tmpquery,"caseststus",e)},expression:"tmpquery.caseststus"}},[a("el-option",{attrs:{label:"成功",value:"成功"}}),t._v(" "),a("el-option",{attrs:{label:"失败",value:"失败"}})],1)],1),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",loading:t.btnLoading},on:{click:t.searchcaseReportBy}},[t._v("查询")])],1)],1)],1),t._v(" "),[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,attrs:{data:t.apireportList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"编号",align:"center",width:"50"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{domProps:{textContent:t._s(t.getIndex(e.$index))}})]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"执行计划",align:"center",prop:"batchname",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"用例名",align:"center",prop:"casename",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"API",align:"center",prop:"apiname",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"请求方式",align:"center",prop:"requestmethod",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"状态",align:"center",prop:"status",width:"50"},scopedSlots:t._u([{key:"default",fn:function(e){return["失败"===e.row.status?a("span",{staticStyle:{color:"red"}},[t._v(t._s(e.row.status))]):a("span",{staticStyle:{color:"#37B328"}},[t._v(t._s(e.row.status))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"发布单元",align:"center",prop:"deployunitname",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"请求地址",align:"center",prop:"url",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.url))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"请求头",align:"center",prop:"requestheader",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.requestheader))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"请求数据",align:"center",prop:"requestdatas",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top-start"}},[a("p",[t._v(t._s(e.row.requestdatas))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"响应",align:"center",prop:"respone",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.respone))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"断言",align:"center",prop:"expect",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.expect))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"断言结果",align:"center",prop:"assertvalue",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.assertvalue))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"运行时间(ms)",align:"center",prop:"runtime",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"异常信息",align:"center",prop:"errorinfo",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.errorinfo))]),t._v(" "),""!==e.row.errorinfo?a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{staticStyle:{color:"red"},attrs:{size:"medium"}},[t._v("异常...")])],1):t._e(),t._v(" "),""===e.row.errorinfo?a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{staticStyle:{color:"green"},attrs:{size:"medium"}},[t._v("...")])],1):t._e()])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"120"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.createTime)))]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.search.page,"page-size":t.search.size,total:t.total,"page-sizes":[10,20,30,40],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}})]],2),t._v(" "),a("el-tab-pane",{attrs:{label:"前置条件执行结果",name:"first"}},[[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,attrs:{data:t.caseconditionreport,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"编号",align:"center",width:"60"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{domProps:{textContent:t._s(t.conditiongetIndex(e.$index))}})]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"集合/用例名",align:"center",prop:"planname",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{label:"执行计划名",align:"center",prop:"batchname",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"父条件名",align:"center",prop:"conditionname",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"子条件名",align:"center",prop:"subconditionname",width:"180"}}),t._v(" "),a("el-table-column",{attrs:{label:"子条件类型",align:"center",prop:"subconditiontype",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"条件结果",align:"center",prop:"conditionresult",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.conditionresult))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"条件状态",align:"center",prop:"conditionstatus",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return["失败"===e.row.conditionstatus?a("span",{staticStyle:{color:"red"}},[t._v(t._s(e.row.conditionstatus))]):a("span",{staticStyle:{color:"#37B328"}},[t._v(t._s(e.row.conditionstatus))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"消耗时长(ms)",align:"center",prop:"runtime",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"160"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.createTime)))]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.tmpconditionquery.page,"page-size":t.tmpconditionquery.size,total:t.conditiontotal,"page-sizes":[10,20,30,40],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.conditionhandleSizeChange,"current-change":t.conditionhandleCurrentChange}})]],2),t._v(" "),a("el-tab-pane",{attrs:{label:"异常用例",name:"three"}},[[a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,attrs:{data:t.dispatchdata,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"编号",align:"center",width:"60"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{domProps:{textContent:t._s(t.dispatchgetIndex(e.$index))}})]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"执行机",align:"center",prop:"slavername",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{label:"测试集合",align:"center",prop:"execplanname",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{label:"执行计划",align:"center",prop:"batchname",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{label:"执行用例",align:"center",prop:"testcasename",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{label:"状态",align:"center",prop:"status",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"备注",align:"center",prop:"memo",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[""!==e.row.memo?a("span",{staticStyle:{color:"red"}},[t._v(t._s(e.row.memo))]):t._e()]}}])}),t._v(">\n\n\n            "),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"140"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.createTime)))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"最后修改时间",align:"center",prop:"lastmodifyTime",width:"140"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.lastmodifyTime))+"\n              ")]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.tmpdispatchquery.page,"page-size":t.tmpdispatchquery.size,total:t.dispatchtotal,"page-sizes":[10,20,30,40],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.dispatchhandleSizeChange,"current-change":t.dispatchhandleCurrentChange}})]],2)],1)],1)},staticRenderFns:[]};var h=a("VU/8")(d,m,!1,function(t){a("OiUL")},"data-v-282276b2",null);e.default=h.exports}});
//# sourceMappingURL=10.f296648c010913cfe6a3.js.map