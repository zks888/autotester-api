webpackJsonp([20],{d6nB:function(t,e){},tYTA:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a("fqUa"),r=a("XcM5"),n=a("vuyV"),s=a("W5b6"),o={filters:{statusFilter:function(t){return{published:"success",draft:"gray",deleted:"danger"}[t]}},data:function(){return{itemKey:null,tmptestplanname:"",tmptestplanid:null,tmpbatchname:null,apireportList:[],apiList:[],planbatchList:[],execplanList:[],deployunitList:[],listLoading:!1,dicvisitypeQuery:{page:1,size:30,diccode:"httpvisittype"},total:0,listQuery:{page:1,size:10,listLoading:!0,testplanname:"",testplanid:null,batchname:null},dialogStatus:"add",dialogFormVisible:!1,textMap:{updateRole:"修改apireport",update:"修改apireport",add:"添加apireport"},btnLoading:!1,tmpapireport:{executeplanid:"",id:"",deployunitid:"",deployunitname:"",batchname:"",requestheader:"",requestdatas:"",visittype:"",path:"",url:"",requestmethod:"",memo:""},tmpexecplantype:{usetype:""},search:{page:1,size:10,testplanname:"",testplanid:null,batchname:null}}},created:function(){this.getexecplanList(),this.getdepunitList(),this.getapireportList()},methods:{unix2CurrentTime:a("0xDb").b,testplanselectChanged:function(t){var e=this;this.search.batchname=null,this.tmptestplanid=null;for(var a=0;a<this.execplanList.length;a++)this.execplanList[a].executeplanname===t&&(this.tmptestplanid=this.execplanList[a].id,this.tmpapireport.executeplanid=this.execplanList[a].id);Object(s.b)(this.tmpapireport).then(function(t){e.planbatchList=t.data}).catch(function(t){e.$message.error("加载执行计划列表失败")})},selectChanged:function(t){for(var e=0;e<this.deployunitList.length;e++)this.deployunitList[e].deployunitname===t&&(this.tmpapireport.deployunitid=this.deployunitList[e].id),console.log(this.deployunitList[e].id)},getapireportList:function(){var t=this;this.listLoading=!0,this.search.testplanname=this.tmptestplanname,this.search.batchname=this.tmpbatchname,Object(i.h)(this.search).then(function(e){t.apireportList=e.data.list,t.total=e.data.total,t.listLoading=!1}).catch(function(e){t.$message.error("加载api报告列表失败")})},getexecplanList:function(){var t=this;this.tmpexecplantype.usetype="功能",Object(n.e)(this.tmpexecplantype).then(function(e){t.execplanList=e.data}).catch(function(e){t.$message.error("加载计划列表失败")})},getdepunitList:function(){var t=this;Object(r.d)(this.listQuery).then(function(e){t.deployunitList=e.data.list}).catch(function(e){t.$message.error("加载发布单元列表失败")})},searchBy:function(){var t=this;this.search.page=1,this.search.testplanid=this.tmptestplanid,this.listLoading=!0,Object(i.h)(this.search).then(function(e){t.itemKey=Math.random(),t.apireportList=e.data.list,t.total=e.data.total}).catch(function(e){t.$message.error("搜索失败")}),this.tmptestplanname=this.search.testplanname,this.tmpbatchname=this.search.batchname,this.listLoading=!1,this.btnLoading=!1},handleSizeChange:function(t){this.search.page=1,this.search.size=t,this.getapireportList()},handleCurrentChange:function(t){this.search.page=t,this.getapireportList()},getIndex:function(t){return(this.search.page-1)*this.search.size+t+1},showAddapireportDialog:function(){this.dialogFormVisible=!0,this.dialogStatus="add",this.tmpapireport.id="",this.tmpapireport.deployunitid="",this.tmpapireport.deployunitname="",this.tmpapireport.apireportname="",this.tmpapireport.visittype="",this.tmpapireport.path="",this.tmpapireport.memo=""},addapireport:function(){var t=this;this.$refs.tmpapireport.validate(function(e){e&&t.isUniqueDetail(t.tmpapireport)&&(t.btnLoading=!0,Object(i.a)(t.tmpapireport).then(function(){t.$message.success("添加成功"),t.getapireportList(),t.dialogFormVisible=!1,t.btnLoading=!1}).catch(function(e){t.$message.error("添加失败"),t.btnLoading=!1}))})},showUpdateapireportDialog:function(t){this.dialogFormVisible=!0,this.dialogStatus="update",this.tmpapireport.id=this.apireportList[t].id,this.tmpapireport.deployunitid=this.apireportList[t].deployunitid,this.tmpapireport.deployunitname=this.apireportList[t].deployunitname,this.tmpapireport.apireportname=this.apireportList[t].apireportname,this.tmpapireport.visittype=this.apireportList[t].visittype,this.tmpapireport.path=this.apireportList[t].path,this.tmpapireport.memo=this.apireportList[t].memo},updateapireport:function(){var t=this;this.isUniqueDetail(this.tmpapireport)&&Object(i.i)(this.tmpapireport).then(function(){t.$message.success("更新成功"),t.getapireportList(),t.dialogFormVisible=!1}).catch(function(e){t.$message.error("更新失败")})},removeapireport:function(t){var e=this;this.$confirm("删除该apireport？","警告",{confirmButtonText:"是",cancelButtonText:"否",type:"warning"}).then(function(){var a=e.apireportList[t].id;Object(i.g)(a).then(function(){e.$message.success("删除成功"),e.getapireportList()})}).catch(function(){e.$message.info("已取消删除")})},isUniqueDetail:function(t){console.log(t.id);for(var e=0;e<this.apireportList.length;e++)if(this.apireportList[e].id!==t.id&&(console.log(this.apireportList[e].id),this.apireportList[e].apireportname===t.apireportname))return this.$message.error("apireport名已存在"),!1;return!0}}},p={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("el-form",{attrs:{inline:!0}},[a("el-form-item",[t.hasPermission("apireport:list")?a("el-button",{attrs:{type:"success",size:"mini",icon:"el-icon-refresh"},nativeOn:{click:function(e){return e.preventDefault(),t.getapireportList(e)}}},[t._v("刷新")]):t._e()],1),t._v(" "),t.hasPermission("apireport:search")?a("span",[a("el-form-item",{attrs:{label:"测试集合",prop:"testplanname"}},[a("el-select",{attrs:{placeholder:"测试集合"},on:{change:function(e){return t.testplanselectChanged(e)}},model:{value:t.search.testplanname,callback:function(e){t.$set(t.search,"testplanname",e)},expression:"search.testplanname"}},[a("el-option",{attrs:{label:"请选择",value:""}}),t._v(" "),t._l(t.execplanList,function(t,e){return a("div",{key:e},[a("el-option",{attrs:{label:t.executeplanname,value:t.executeplanname}})],1)})],2)],1),t._v(" "),a("el-form-item",{attrs:{label:"执行计划",prop:"batchname"}},[a("el-select",{attrs:{placeholder:"执行计划"},model:{value:t.search.batchname,callback:function(e){t.$set(t.search,"batchname",e)},expression:"search.batchname"}},[a("el-option",{attrs:{label:"请选择",value:""}}),t._v(" "),t._l(t.planbatchList,function(t,e){return a("div",{key:e},[a("el-option",{attrs:{label:t.batchname,value:t.batchname}})],1)})],2)],1),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",loading:t.btnLoading},on:{click:t.searchBy}},[t._v("查询")])],1)],1):t._e()],1)],1),t._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:t.itemKey,attrs:{data:t.apireportList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"编号",align:"center",width:"50"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",{domProps:{textContent:t._s(t.getIndex(e.$index))}})]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"执行计划",align:"center",prop:"batchname",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"用例名",align:"center",prop:"casename",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"API",align:"center",prop:"apiname",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"请求方式",align:"center",prop:"requestmethod",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{label:"状态",align:"center",prop:"status",width:"50"},scopedSlots:t._u([{key:"default",fn:function(e){return["失败"===e.row.status?a("span",{staticStyle:{color:"red"}},[t._v(t._s(e.row.status))]):a("span",{staticStyle:{color:"#37B328"}},[t._v(t._s(e.row.status))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"发布单元",align:"center",prop:"deployunitname",width:"120"}}),t._v(" "),a("el-table-column",{attrs:{label:"请求地址",align:"center",prop:"url",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.url))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"请求头",align:"center",prop:"requestheader",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.requestheader))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"请求数据",align:"center",prop:"requestdatas",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top-start"}},[a("p",[t._v(t._s(e.row.requestdatas))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"响应",align:"center",prop:"respone",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.respone))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"断言",align:"center",prop:"expect",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.expect))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"断言结果",align:"center",prop:"assertvalue",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.assertvalue))]),t._v(" "),a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{attrs:{size:"medium"}},[t._v("...")])],1)])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"运行时间(ms)",align:"center",prop:"runtime",width:"100"}}),t._v(" "),a("el-table-column",{attrs:{label:"异常信息",align:"center",prop:"errorinfo",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{trigger:"hover",placement:"top"}},[a("p",[t._v(t._s(e.row.errorinfo))]),t._v(" "),""!==e.row.errorinfo?a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{staticStyle:{color:"red"},attrs:{size:"medium"}},[t._v("异常...")])],1):t._e(),t._v(" "),""===e.row.errorinfo?a("div",{staticClass:"name-wrapper",attrs:{slot:"reference"},slot:"reference"},[a("el-tag",{staticStyle:{color:"green"},attrs:{size:"medium"}},[t._v("...")])],1):t._e()])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"120"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.createTime)))]}}])})],1),t._v(" "),a("el-pagination",{attrs:{"current-page":t.search.page,"page-size":t.search.size,total:t.total,"page-sizes":[10,20,30,40],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}}),t._v(" "),a("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("el-form",{ref:"tmpapireport",staticClass:"small-space",staticStyle:{width:"300px","margin-left":"50px"},attrs:{"status-icon":"","label-position":"left","label-width":"100px",model:t.tmpapireport}},[a("el-form-item",{attrs:{label:"apireport名",prop:"apireportname",required:""}},[a("el-input",{attrs:{type:"text","prefix-icon":"el-icon-edit","auto-complete":"off"},model:{value:t.tmpapireport.apireportname,callback:function(e){t.$set(t.tmpapireport,"apireportname",e)},expression:"tmpapireport.apireportname"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"资源路径",prop:"path",required:""}},[a("el-input",{attrs:{type:"text","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:t.tmpapireport.path,callback:function(e){t.$set(t.tmpapireport,"path",e)},expression:"tmpapireport.path"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"发布单元",prop:"deployunitname",required:""}},[a("el-select",{attrs:{placeholder:"发布单元"},on:{change:function(e){return t.selectChanged(e)}},model:{value:t.tmpapireport.deployunitname,callback:function(e){t.$set(t.tmpapireport,"deployunitname",e)},expression:"tmpapireport.deployunitname"}},[a("el-option",{staticStyle:{display:"none"},attrs:{label:"请选择",value:"''"}}),t._v(" "),t._l(t.deployunitList,function(t,e){return a("div",{key:e},[a("el-option",{attrs:{label:t.deployunitname,value:t.deployunitname,required:""}})],1)})],2)],1),t._v(" "),a("el-form-item",{attrs:{label:"备注",prop:"memo"}},[a("el-input",{attrs:{type:"text","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:t.tmpapireport.memo,callback:function(e){t.$set(t.tmpapireport,"memo",e)},expression:"tmpapireport.memo"}})],1)],1),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{nativeOn:{click:function(e){e.preventDefault(),t.dialogFormVisible=!1}}},[t._v("取消")]),t._v(" "),"add"===t.dialogStatus?a("el-button",{attrs:{type:"danger"},nativeOn:{click:function(e){return e.preventDefault(),t.$refs.tmpapireport.resetFields()}}},[t._v("重置")]):t._e(),t._v(" "),"add"===t.dialogStatus?a("el-button",{attrs:{type:"success",loading:t.btnLoading},nativeOn:{click:function(e){return e.preventDefault(),t.addapireport(e)}}},[t._v("添加")]):t._e(),t._v(" "),"update"===t.dialogStatus?a("el-button",{attrs:{type:"success",loading:t.btnLoading},nativeOn:{click:function(e){return e.preventDefault(),t.updateapireport(e)}}},[t._v("修改")]):t._e()],1)],1)],1)},staticRenderFns:[]};var l=a("VU/8")(o,p,!1,function(t){a("d6nB")},null,null);e.default=l.exports}});
//# sourceMappingURL=20.5a1fd484c25595e3f265.js.map