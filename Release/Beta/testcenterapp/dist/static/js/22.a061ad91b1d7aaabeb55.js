webpackJsonp([22],{Y48K:function(i,t,e){"use strict";t.a=function(i){return Object(a.a)({url:"/dbcondition/variables",method:"post",data:i})},t.d=function(i){return Object(a.a)({url:"/dbcondition/variables/detail",method:"put",data:i})},t.b=function(i){return Object(a.a)({url:"/dbcondition/variables/getbyvariablesid",method:"post",data:i})},t.c=function(i){return Object(a.a)({url:"/dbcondition/variables/"+i,method:"delete"})};var a=e("vLgD")},h90a:function(i,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=e("Dd8w"),s=e.n(a),n=e("vLgD");function r(i){return Object(n.a)({url:"/scriptvariables/search",method:"post",data:i})}var l=e("Y48K"),o=e("XcM5"),c=e("2d0t"),d=e("0xDb"),b=e("NYxO"),p=e("5kbS"),u={filters:{statusFilter:function(i){return{published:"success",draft:"gray",deleted:"danger"}[i]}},data:function(){return{itemKey:null,tmpscriptvariablesname:"",apiList:[],caseList:[],deployunitList:[],scriptvariablesList:[],DbconditionList:[],DbconditionVariablesList:[],listLoading:!1,total:0,dialogStatus:"add",BinddialogStatus:"add",dialogFormVisible:!1,BindVariablesDialogVisible:!1,BindFormVisible:!1,textMap:{updateRole:"修改脚本变量",update:"修改脚本变量",add:"添加脚本变量"},BindtextMap:{updateRole:"修改绑定脚本条件",update:"修改绑定脚本条件",add:"添加绑定脚本条件"},apiquery:{casedeployunitname:"",caseapiname:""},deployunitQuery:{deployunitname:""},VariablescaseQuery:{variablesid:""},btnLoading:!1,tmpscriptvariables:{id:"",scriptvariablesname:"",variablesdes:"",valuetype:"",memo:"",creator:""},tmpDbConditionVariables:{id:"",dbconditionname:"",dbconditionid:"",variablesid:"",variablesname:"",fieldname:"",roworder:"",memo:""},search:{page:1,size:10,scriptvariablesname:null}}},created:function(){this.getscriptvariablesList(),this.getdepunitList()},computed:s()({},Object(b.b)(["name","sidebar","avatar"])),methods:{unix2CurrentTime:d.b,getscriptvariablesList:function(){var i=this;this.listLoading=!0,this.search.scriptvariablesname=this.tmpscriptvariablesname,r(this.search).then(function(t){i.scriptvariablesList=t.data.list,i.total=t.data.total,i.listLoading=!1}).catch(function(t){i.$message.error("加载变量列表失败")})},getbyvariablesid:function(){var i=this;Object(l.b)(this.VariablescaseQuery).then(function(t){i.DbconditionVariablesList=t.data}).catch(function(t){i.$message.error("加载变量列表失败")})},listalldbcondition:function(){var i=this;Object(p.b)().then(function(t){i.DbconditionList=t.data.list}).catch(function(t){i.$message.error("加载脚本条件失败")})},getdepunitList:function(){var i=this;this.listLoading=!0,Object(o.d)(this.listQuery).then(function(t){i.deployunitList=t.data.list,i.listLoading=!1}).catch(function(t){i.$message.error("加载发布单元列表失败")})},dbconditionselectChanged:function(i){for(var t=0;t<this.DbconditionList.length;t++)this.DbconditionList[t].subconditionname===i&&(this.tmpDbConditionVariables.dbconditionid=this.DbconditionList[t].id,console.log(this.tmpDbConditionVariables.dbconditionid))},apiselectChanged:function(i){for(var t=this,e=0;e<this.apiList.length;e++)this.apiList[e].apiname===i&&(this.tmpApicasesVariables.apiid=this.apiList[e].id);this.tmpApicasesVariables.casename="",this.apiquery.caseapiname=i,this.apiquery.casedeployunitname=this.tmpApicasesVariables.deployunitname,Object(c.d)(this.apiquery).then(function(i){t.caseList=i.data}).catch(function(i){t.$message.error("加载apicase列表失败")})},testcaseselectChanged:function(i){for(var t=0;t<this.caseList.length;t++)this.caseList[t].casename===i&&(this.tmpApicasesVariables.caseid=this.caseList[t].id)},searchBy:function(){var i=this;this.search.page=1,this.listLoading=!0,r(this.search).then(function(t){i.itemKey=Math.random(),i.scriptvariablesList=t.data.list,i.total=t.data.total}).catch(function(t){i.$message.error("搜索失败")}),this.tmpscriptvariablesname=this.search.scriptvariablesname,this.listLoading=!1,this.btnLoading=!1},handleSizeChange:function(i){this.search.page=1,this.search.size=i,this.getscriptvariablesList()},handleCurrentChange:function(i){this.search.page=i,this.getscriptvariablesList()},getIndex:function(i){return(this.search.page-1)*this.search.size+i+1},showDbconditionVariablesDialog:function(i){this.listalldbcondition(),this.BindVariablesDialogVisible=!0,this.VariablescaseQuery.variablesid=this.scriptvariablesList[i].id,this.tmpDbConditionVariables.variablesid=this.scriptvariablesList[i].id,this.tmpDbConditionVariables.variablesname=this.scriptvariablesList[i].scriptvariablesname,this.getbyvariablesid()},showAddConditionVariablesDialog:function(i){this.BindFormVisible=!0,this.BinddialogStatus="add",this.tmpDbConditionVariables.id="",this.tmpDbConditionVariables.dbconditionname="",this.tmpDbConditionVariables.fieldname="",this.tmpDbConditionVariables.roworder="",this.tmpDbConditionVariables.memo="",this.tmpDbConditionVariables.creator=this.name},showAddscriptvariablesDialog:function(){this.dialogFormVisible=!0,this.dialogStatus="add",this.tmpscriptvariables.id="",this.tmpscriptvariables.scriptvariablesname="",this.tmpscriptvariables.variablesdes="",this.tmpscriptvariables.scriptvariablestype="",this.tmpscriptvariables.variablesexpress="",this.tmpscriptvariables.memo="",this.tmpscriptvariables.valuetype="",this.tmpscriptvariables.tmpscriptvariables="",this.tmpscriptvariables.creator=this.name},addscriptvariables:function(){var i=this;this.$refs.tmpscriptvariables.validate(function(t){var e;t&&(i.btnLoading=!0,(e=i.tmpscriptvariables,Object(n.a)({url:"/scriptvariables",method:"post",data:e})).then(function(){i.$message.success("添加成功"),i.getscriptvariablesList(),i.dialogFormVisible=!1,i.btnLoading=!1}).catch(function(t){i.$message.error("添加失败"),i.btnLoading=!1}))})},addDBConditionVariables:function(){var i=this;this.$refs.tmpDbConditionVariables.validate(function(t){t&&Object(l.a)(i.tmpDbConditionVariables).then(function(){i.$message.success("添加成功"),i.BindFormVisible=!1,i.getbyvariablesid()}).catch(function(t){i.$message.error("添加失败"),i.btnLoading=!1})})},showUpdateDBCconditionVariablesDialog:function(i){this.BindFormVisible=!0,this.BinddialogStatus="update",this.tmpDbConditionVariables.id=this.DbconditionVariablesList[i].id,this.tmpDbConditionVariables.fieldname=this.DbconditionVariablesList[i].fieldname,this.tmpDbConditionVariables.roworder=this.DbconditionVariablesList[i].roworder,this.tmpDbConditionVariables.dbconditionname=this.DbconditionVariablesList[i].dbconditionname;for(var t=0;t<this.DbconditionList.length;t++)this.DbconditionList[t].subconditionname===this.tmpDbConditionVariables.dbconditionname&&(this.tmpDbConditionVariables.dbconditionid=this.DbconditionList[t].id,console.log(this.tmpDbConditionVariables.dbconditionid));this.tmpDbConditionVariables.memo=this.DbconditionVariablesList[i].memo},updateDBConditionVariables:function(){var i=this;this.$refs.tmpDbConditionVariables.validate(function(t){t&&Object(l.d)(i.tmpDbConditionVariables).then(function(){i.$message.success("更新成功"),i.getbyvariablesid(),i.BindFormVisible=!1}).catch(function(t){i.$message.error("更新失败")})})},showUpdatescriptvariablesDialog:function(i){this.dialogFormVisible=!0,this.dialogStatus="update",this.tmpscriptvariables.id=this.scriptvariablesList[i].id,this.tmpscriptvariables.scriptvariablesname=this.scriptvariablesList[i].scriptvariablesname,this.tmpscriptvariables.variablesdes=this.scriptvariablesList[i].variablesdes,this.tmpscriptvariables.scriptvariablestype=this.scriptvariablesList[i].scriptvariablestype,this.tmpscriptvariables.variablesexpress=this.scriptvariablesList[i].variablesexpress,this.tmpscriptvariables.tmpscriptvariables=this.scriptvariablesList[i].tmpscriptvariables,this.tmpscriptvariables.valuetype=this.scriptvariablesList[i].valuetype,this.tmpscriptvariables.memo=this.scriptvariablesList[i].memo,this.tmpscriptvariables.creator=this.name},updatescriptvariables:function(){var i=this;this.$refs.tmpscriptvariables.validate(function(t){var e;t&&(e=i.tmpscriptvariables,Object(n.a)({url:"/scriptvariables/detail",method:"put",data:e})).then(function(){i.$message.success("更新成功"),i.getscriptvariablesList(),i.dialogFormVisible=!1}).catch(function(t){i.$message.error("更新失败")})})},removedbconditionvariables:function(i){var t=this;this.$confirm("删除该脚本绑定？","警告",{confirmButtonText:"是",cancelButtonText:"否",type:"warning"}).then(function(){var e=t.DbconditionVariablesList[i].id;Object(l.c)(e).then(function(){t.$message.success("删除成功"),t.getbyvariablesid()})}).catch(function(){t.$message.info("已取消删除")})},removescriptvariables:function(i){var t=this;this.$confirm("删除该变量？","警告",{confirmButtonText:"是",cancelButtonText:"否",type:"warning"}).then(function(){var e,a=t.scriptvariablesList[i].id;(e=a,Object(n.a)({url:"/scriptvariables/"+e,method:"delete"})).then(function(){t.$message.success("删除成功"),t.getscriptvariablesList()})}).catch(function(){t.$message.info("已取消删除")})},isUniqueDetail:function(i){for(var t=0;t<this.scriptvariablesList.length;t++)if(this.scriptvariablesList[t].id!==i.id&&this.scriptvariablesList[t].scriptvariablesname===i.scriptvariablesname)return this.$message.error("变量名已存在"),!1;return!0}}},m={render:function(){var i=this,t=i.$createElement,e=i._self._c||t;return e("div",{staticClass:"app-container"},[e("div",{staticClass:"filter-container"},[e("el-form",{attrs:{inline:!0}},[e("el-form-item",[i.hasPermission("scriptvariables:list")?e("el-button",{attrs:{type:"success",size:"mini",icon:"el-icon-refresh"},nativeOn:{click:function(t){return t.preventDefault(),i.getscriptvariablesList(t)}}},[i._v("刷新")]):i._e(),i._v(" "),i.hasPermission("scriptvariables:add")?e("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},nativeOn:{click:function(t){return t.preventDefault(),i.showAddscriptvariablesDialog(t)}}},[i._v("添加脚本变量")]):i._e()],1),i._v(" "),i.hasPermission("scriptvariables:search")?e("span",[e("el-form-item",[e("el-input",{attrs:{clearable:"",maxlength:"40",placeholder:"变量名"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&i._k(t.keyCode,"enter",13,t.key,"Enter")?null:i.searchBy(t)}},model:{value:i.search.scriptvariablesname,callback:function(t){i.$set(i.search,"scriptvariablesname",t)},expression:"search.scriptvariablesname"}})],1),i._v(" "),e("el-form-item",[e("el-button",{attrs:{type:"primary",loading:i.btnLoading},on:{click:i.searchBy}},[i._v("查询")])],1)],1):i._e()],1)],1),i._v(" "),e("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:i.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:i.itemKey,attrs:{data:i.scriptvariablesList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[e("el-table-column",{attrs:{label:"编号",align:"center",width:"60"},scopedSlots:i._u([{key:"default",fn:function(t){return[e("span",{domProps:{textContent:i._s(i.getIndex(t.$index))}})]}}])}),i._v(" "),e("el-table-column",{attrs:{label:"脚本变量名",align:"center",prop:"scriptvariablesname",width:"180"}}),i._v(" "),e("el-table-column",{attrs:{label:"变量描述",align:"center",prop:"variablesdes",width:"100"}}),i._v(" "),e("el-table-column",{attrs:{label:"变量值类型",align:"center",prop:"valuetype",width:"100"}}),i._v(" "),e("el-table-column",{attrs:{label:"备注",align:"center",prop:"memo",width:"100"}}),i._v(" "),e("el-table-column",{attrs:{label:"操作人",align:"center",prop:"creator",width:"100"}}),i._v(" "),e("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"150"},scopedSlots:i._u([{key:"default",fn:function(t){return[i._v(i._s(i.unix2CurrentTime(t.row.createTime)))]}}])}),i._v(" "),e("el-table-column",{attrs:{label:"最后修改时间",align:"center",prop:"lastmodifyTime",width:"150"},scopedSlots:i._u([{key:"default",fn:function(t){return[i._v(i._s(i.unix2CurrentTime(t.row.lastmodifyTime))+"\n      ")]}}])}),i._v(" "),i.hasPermission("scriptvariables:update")||i.hasPermission("scriptvariables:delete")?e("el-table-column",{attrs:{label:"管理",align:"center"},scopedSlots:i._u([{key:"default",fn:function(t){return[i.hasPermission("scriptvariables:update")?e("el-button",{attrs:{type:"warning",size:"mini"},nativeOn:{click:function(e){return e.preventDefault(),i.showUpdatescriptvariablesDialog(t.$index)}}},[i._v("修改")]):i._e(),i._v(" "),i.hasPermission("scriptvariables:delete")?e("el-button",{attrs:{type:"danger",size:"mini"},nativeOn:{click:function(e){return e.preventDefault(),i.removescriptvariables(t.$index)}}},[i._v("删除")]):i._e(),i._v(" "),i.hasPermission("scriptvariables:update")?e("el-button",{attrs:{type:"primary",size:"mini"},nativeOn:{click:function(e){return e.preventDefault(),i.showDbconditionVariablesDialog(t.$index)}}},[i._v("绑定脚本操作")]):i._e()]}}],null,!1,16326391)}):i._e()],1),i._v(" "),e("el-pagination",{attrs:{"current-page":i.search.page,"page-size":i.search.size,total:i.total,"page-sizes":[10,20,30,40],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":i.handleSizeChange,"current-change":i.handleCurrentChange}}),i._v(" "),e("el-dialog",{attrs:{title:i.textMap[i.dialogStatus],visible:i.dialogFormVisible},on:{"update:visible":function(t){i.dialogFormVisible=t}}},[e("el-form",{ref:"tmpscriptvariables",staticClass:"small-space",staticStyle:{width:"500px","margin-left":"50px"},attrs:{"status-icon":"","label-position":"left","label-width":"120px",model:i.tmpscriptvariables}},[e("el-form-item",{attrs:{label:"变量名",prop:"scriptvariablesname",required:""}},[e("el-input",{attrs:{maxlength:"50",type:"text","prefix-icon":"el-icon-edit","auto-complete":"off"},model:{value:i.tmpscriptvariables.scriptvariablesname,callback:function(t){i.$set(i.tmpscriptvariables,"scriptvariablesname",t)},expression:"tmpscriptvariables.scriptvariablesname"}})],1),i._v(" "),e("el-form-item",{attrs:{label:"变量描述",prop:"variablesdes",required:""}},[e("el-input",{attrs:{maxlength:"20",type:"text","prefix-icon":"el-icon-edit","auto-complete":"off"},model:{value:i.tmpscriptvariables.variablesdes,callback:function(t){i.$set(i.tmpscriptvariables,"variablesdes",t)},expression:"tmpscriptvariables.variablesdes"}})],1),i._v(" "),e("el-form-item",{attrs:{label:"变量值类型",prop:"valuetype",required:""}},[e("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"变量值类型"},model:{value:i.tmpscriptvariables.valuetype,callback:function(t){i.$set(i.tmpscriptvariables,"valuetype",t)},expression:"tmpscriptvariables.valuetype"}},[e("el-option",{attrs:{label:"Number",value:"Number"}}),i._v(" "),e("el-option",{attrs:{label:"String",value:"String"}}),i._v(" "),e("el-option",{attrs:{label:"Array",value:"Array"}}),i._v(" "),e("el-option",{attrs:{label:"Bool",value:"Bool"}})],1)],1),i._v(" "),e("el-form-item",{attrs:{label:"备注",prop:"memo"}},[e("el-input",{attrs:{maxlength:"50",type:"text","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:i.tmpscriptvariables.memo,callback:function(t){i.$set(i.tmpscriptvariables,"memo",t)},expression:"tmpscriptvariables.memo"}})],1)],1),i._v(" "),e("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{nativeOn:{click:function(t){t.preventDefault(),i.dialogFormVisible=!1}}},[i._v("取消")]),i._v(" "),"add"===i.dialogStatus?e("el-button",{attrs:{type:"danger"},nativeOn:{click:function(t){return t.preventDefault(),i.$refs.tmpscriptvariables.resetFields()}}},[i._v("重置")]):i._e(),i._v(" "),"add"===i.dialogStatus?e("el-button",{attrs:{type:"success",loading:i.btnLoading},nativeOn:{click:function(t){return t.preventDefault(),i.addscriptvariables(t)}}},[i._v("添加")]):i._e(),i._v(" "),"update"===i.dialogStatus?e("el-button",{attrs:{type:"success",loading:i.btnLoading},nativeOn:{click:function(t){return t.preventDefault(),i.updatescriptvariables(t)}}},[i._v("修改")]):i._e()],1)],1),i._v(" "),e("el-dialog",{attrs:{title:i.BindtextMap[i.BinddialogStatus],visible:i.BindFormVisible},on:{"update:visible":function(t){i.BindFormVisible=t}}},[e("el-form",{ref:"tmpDbConditionVariables",staticClass:"small-space",staticStyle:{width:"450px","margin-left":"50px"},attrs:{"status-icon":"","label-position":"left","label-width":"140px",model:i.tmpDbConditionVariables}},[e("el-form-item",{attrs:{label:"脚本子条件",prop:"dbconditionname",required:""}},[e("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"脚本子条件"},on:{change:function(t){return i.dbconditionselectChanged(t)}},model:{value:i.tmpDbConditionVariables.dbconditionname,callback:function(t){i.$set(i.tmpDbConditionVariables,"dbconditionname",t)},expression:"tmpDbConditionVariables.dbconditionname"}},[e("el-option",{staticStyle:{display:"none"},attrs:{label:"请选择",value:"''"}}),i._v(" "),i._l(i.DbconditionList,function(i,t){return e("div",{key:t},[e("el-option",{attrs:{label:i.subconditionname,value:i.subconditionname,required:""}})],1)})],2)],1),i._v(" "),e("el-form-item",{attrs:{label:"绑定Sql列名",prop:"fieldname",required:""}},[e("el-input",{attrs:{placeholder:"脚本子条件查询Sql结果列名",maxLength:"10",type:"text","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:i.tmpDbConditionVariables.fieldname,callback:function(t){i.$set(i.tmpDbConditionVariables,"fieldname",t)},expression:"tmpDbConditionVariables.fieldname"}})],1),i._v(" "),e("el-form-item",{attrs:{label:"绑定结果行号",prop:"roworder",required:""}},[e("el-input",{attrs:{placeholder:"脚本子条件查询Sql结果行号",oninput:"value=value.replace(/[^\\d]/g,'')",maxLength:"10",type:"number","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:i.tmpDbConditionVariables.roworder,callback:function(t){i.$set(i.tmpDbConditionVariables,"roworder",t)},expression:"tmpDbConditionVariables.roworder"}})],1),i._v(" "),e("el-form-item",{attrs:{label:"备注",prop:"memo"}},[e("el-input",{attrs:{type:"text","prefix-icon":"el-icon-message","auto-complete":"off"},model:{value:i.tmpDbConditionVariables.memo,callback:function(t){i.$set(i.tmpDbConditionVariables,"memo",t)},expression:"tmpDbConditionVariables.memo"}})],1)],1),i._v(" "),e("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{nativeOn:{click:function(t){t.preventDefault(),i.BindFormVisible=!1}}},[i._v("取消")]),i._v(" "),"add"===i.BinddialogStatus?e("el-button",{attrs:{type:"success",loading:i.btnLoading},nativeOn:{click:function(t){return t.preventDefault(),i.addDBConditionVariables(t)}}},[i._v("保存")]):i._e(),i._v(" "),"update"===i.BinddialogStatus?e("el-button",{attrs:{type:"success",loading:i.btnLoading},nativeOn:{click:function(t){return t.preventDefault(),i.updateDBConditionVariables(t)}}},[i._v("修改")]):i._e()],1)],1),i._v(" "),e("el-dialog",{attrs:{title:"绑定脚本子条件",visible:i.BindVariablesDialogVisible},on:{"update:visible":function(t){i.BindVariablesDialogVisible=t}}},[e("div",{staticClass:"filter-container"},[e("el-form",{attrs:{inline:!0}},[e("el-form-item",[i.hasPermission("ApicasesVariables:add")?e("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},nativeOn:{click:function(t){return t.preventDefault(),i.showAddConditionVariablesDialog(t)}}},[i._v("绑定脚本条件")]):i._e()],1)],1)],1),i._v(" "),e("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:i.listLoading,expression:"listLoading",modifiers:{body:!0}}],key:i.itemKey,attrs:{data:i.DbconditionVariablesList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[e("el-table-column",{attrs:{label:"编号",align:"center",width:"60"},scopedSlots:i._u([{key:"default",fn:function(t){return[e("span",{domProps:{textContent:i._s(i.getIndex(t.$index))}})]}}])}),i._v(" "),e("el-table-column",{attrs:{label:"脚本子条件",align:"center",prop:"dbconditionname",width:"150"}}),i._v(" "),e("el-table-column",{attrs:{label:"绑定查询Sql列名",align:"center",prop:"fieldname",width:"150"}}),i._v(" "),e("el-table-column",{attrs:{label:"绑定查询结果行号",align:"center",prop:"roworder",width:"140"}}),i._v(" "),i.hasPermission("ApicasesVariables:update")||i.hasPermission("ApicasesVariables:delete")?e("el-table-column",{attrs:{label:"管理",align:"center"},scopedSlots:i._u([{key:"default",fn:function(t){return[i.hasPermission("ApicasesVariables:update")?e("el-button",{attrs:{type:"warning",size:"mini"},nativeOn:{click:function(e){return e.preventDefault(),i.showUpdateDBCconditionVariablesDialog(t.$index)}}},[i._v("修改")]):i._e(),i._v(" "),i.hasPermission("ApicasesVariables:delete")?e("el-button",{attrs:{type:"danger",size:"mini"},nativeOn:{click:function(e){return e.preventDefault(),i.removedbconditionvariables(t.$index)}}},[i._v("删除")]):i._e()]}}],null,!1,41601701)}):i._e()],1)],1)],1)},staticRenderFns:[]},v=e("VU/8")(u,m,!1,null,null,null);t.default=v.exports}});
//# sourceMappingURL=22.a061ad91b1d7aaabeb55.js.map