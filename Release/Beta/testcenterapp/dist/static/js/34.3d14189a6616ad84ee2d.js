webpackJsonp([34],{OB0b:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("Dd8w"),a=i.n(n),o=i("nv77"),s=i("STSY"),c=i("E4LH"),l=i("0xDb"),r=i("NYxO"),u={created:function(){this.hasPermission("role:update")&&this.getRoleList(),this.hasPermission("account:list")&&this.getAccountList()},data:function(){return{accountList:[],roleList:[],filterRoleNameList:[],listLoading:!1,total:0,listQuery:{page:1,size:9},dialogStatus:"add",dialogFormVisible:!1,textMap:{updateRole:"修改账号角色",update:"修改账号",add:"添加账号"},btnLoading:!1,tmpAccount:{id:"",accountId:"",email:"",name:"",password:"",roleId:2},search:{page:null,size:null,accountName:null,roleName:null},createRules:{email:[{required:!0,trigger:"blur",validator:function(t,e,i){Object(c.a)(e)?i():i(new Error("邮箱格式错误"))}}],name:[{required:!0,trigger:"blur",validator:function(t,e,i){e.length<3?i(new Error("账户名长度必须 ≥ 3")):i()}}],password:[{required:!0,trigger:"blur",validator:function(t,e,i){e.length<6?i(new Error("密码长度必须 ≥ 6")):i()}}]}}},computed:a()({},Object(r.b)(["accountId"])),methods:{unix2CurrentTime:l.b,getRoleList:function(){var t=this;Object(s.b)().then(function(e){t.roleList=e.data.list}).catch(function(e){t.$message.error("加载角色失败")})},getAccountList:function(){var t=this;this.listLoading=!0,Object(o.b)(this.listQuery).then(function(e){t.accountList=e.data.list,t.total=e.data.total;for(var i=0;i<t.accountList.length;i++){var n={};n.text=t.accountList[i].roleName,n.value=t.accountList[i].roleName,t.filterRoleNameList.push(n)}t.listLoading=!1}).catch(function(e){t.$message.error("加载账户列表失败")})},searchBy:function(){var t=this;this.btnLoading=!0,this.listLoading=!0,this.search.page=this.listQuery.page,this.search.size=this.listQuery.size,Object(o.g)(this.search).then(function(e){t.accountList=e.data.list,t.total=e.data.total}).catch(function(e){t.$message.error("搜索失败")}),this.btnLoading=!1,this.listLoading=!1},handleSizeChange:function(t){this.listQuery.size=t,this.listQuery.page=1,this.getAccountList()},handleCurrentChange:function(t){this.listQuery.page=t,this.getAccountList()},getIndex:function(t){return(this.listQuery.page-1)*this.listQuery.size+t+1},showAddAccountDialog:function(){this.dialogFormVisible=!0,this.dialogStatus="add",this.tmpAccount.id="",this.tmpAccount.email="",this.tmpAccount.name="",this.tmpAccount.password=""},addAccount:function(){var t=this;this.$refs.tmpAccount.validate(function(e){e&&t.isUniqueDetail(t.tmpAccount)&&(t.btnLoading=!0,Object(o.e)(t.tmpAccount).then(function(){t.$message.success("添加成功"),t.getAccountList(),t.dialogFormVisible=!1,t.btnLoading=!1}).catch(function(e){t.$message.error("添加账户失败"),t.btnLoading=!1}))})},showUpdateAccountDialog:function(t){this.dialogFormVisible=!0,this.dialogStatus="update",this.tmpAccount.id=this.accountList[t].id,this.tmpAccount.email=this.accountList[t].email,this.tmpAccount.name=this.accountList[t].name,this.tmpAccount.password="",this.tmpAccount.roleId=this.accountList[t].roleId},updateAccount:function(){var t=this;this.isUniqueDetail(this.tmpAccount)&&Object(o.h)(this.tmpAccount).then(function(){t.$message.success("更新成功"),t.getAccountList(),t.dialogFormVisible=!1}).catch(function(e){t.$message.error("更新失败")})},showUpdateAccountRoleDialog:function(t){this.dialogFormVisible=!0,this.dialogStatus="updateRole",this.tmpAccount.accountId=this.accountList[t].id,this.tmpAccount.email=this.accountList[t].email,this.tmpAccount.name=this.accountList[t].name,this.tmpAccount.password="",this.tmpAccount.roleId=this.accountList[t].roleId},updateAccountRole:function(){var t=this;Object(s.g)(this.tmpAccount).then(function(){t.$message.success("更新成功"),t.getAccountList(),t.dialogFormVisible=!1}).catch(function(e){t.$message.error("更新失败")})},isUniqueDetail:function(t){for(var e=0;e<this.accountList.length;e++)if(this.accountList[e].id!==t.id){if(this.accountList[e].name===t.name)return this.$message.error("账户名已存在"),!1;if(this.accountList[e].email===t.email)return this.$message.error("邮箱已存在"),!1}return!0},removeAccount:function(t){var e=this;this.$confirm("删除该账户？","警告",{confirmButtonText:"是",cancelButtonText:"否",type:"warning"}).then(function(){var i=e.accountList[t].id;Object(o.f)(i).then(function(){e.$message.success("删除成功"),e.getAccountList()})}).catch(function(){e.$message.info("已取消删除")})}}},d={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("el-form",{attrs:{inline:!0}},[i("el-form-item",[t.hasPermission("account:list")?i("el-button",{attrs:{type:"success",size:"mini",icon:"el-icon-refresh"},nativeOn:{click:function(e){return e.preventDefault(),t.getAccountList(e)}}},[t._v("刷新")]):t._e(),t._v(" "),t.hasPermission("account:add")?i("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},nativeOn:{click:function(e){return e.preventDefault(),t.showAddAccountDialog(e)}}},[t._v("添加账号")]):t._e()],1),t._v(" "),t.hasPermission("account:search")?i("span",[i("el-form-item",[i("el-input",{attrs:{placeholder:"账户名"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.searchBy(e)}},model:{value:t.search.accountName,callback:function(e){t.$set(t.search,"accountName",e)},expression:"search.accountName"}})],1),t._v(" "),i("el-form-item",[i("el-select",{attrs:{placeholder:"角色"},model:{value:t.search.roleName,callback:function(e){t.$set(t.search,"roleName",e)},expression:"search.roleName"}},[i("el-option",{attrs:{label:"请选择",value:""}}),t._v(" "),t._l(t.roleList,function(t,e){return i("div",{key:e},[i("el-option",{attrs:{label:t.name,value:t.name}})],1)})],2)],1),t._v(" "),i("el-form-item",[i("el-button",{attrs:{type:"primary",loading:t.btnLoading},on:{click:t.searchBy}},[t._v("查询")])],1)],1):t._e()],1)],1),t._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:t.accountList,"element-loading-text":"loading",border:"",fit:"","highlight-current-row":""}},[i("el-table-column",{attrs:{label:"编号",align:"center",width:"60"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("span",{domProps:{textContent:t._s(t.getIndex(e.$index))}})]}}])}),t._v(" "),i("el-table-column",{attrs:{label:"账户名",align:"center",prop:"name",width:"100"}}),t._v(" "),i("el-table-column",{attrs:{label:"邮箱",align:"center",prop:"email",width:"160"}}),t._v(" "),i("el-table-column",{attrs:{label:"注册时间",align:"center",prop:"registerTime",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.unix2CurrentTime(e.row.registerTime)))]}}])}),t._v(" "),i("el-table-column",{attrs:{label:"最后登录时间",align:"center",prop:"loginTime",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.loginTime?t.unix2CurrentTime(e.row.loginTime):"从未登录"))]}}])}),t._v(" "),i("el-table-column",{attrs:{label:"角色",align:"center",prop:"roleName",width:"100"}}),t._v(" "),t.hasPermission("role:update")||t.hasPermission("account:update")||t.hasPermission("account:delete")?i("el-table-column",{attrs:{label:"管理",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t.hasPermission("role:update")&&e.row.id!==t.id?i("el-button",{attrs:{type:"warning",size:"mini"},nativeOn:{click:function(i){return i.preventDefault(),t.showUpdateAccountDialog(e.$index)}}},[t._v("账户")]):t._e(),t._v(" "),t.hasPermission("account:update")&&e.row.id!==t.accountId?i("el-button",{attrs:{type:"warning",size:"mini"},nativeOn:{click:function(i){return i.preventDefault(),t.showUpdateAccountRoleDialog(e.$index)}}},[t._v("角色")]):t._e(),t._v(" "),t.hasPermission("account:delete")&&e.row.id!==t.id?i("el-button",{attrs:{type:"danger",size:"mini"},nativeOn:{click:function(i){return i.preventDefault(),t.removeAccount(e.$index)}}},[t._v("删除")]):t._e()]}}],null,!1,1655748849)}):t._e()],1),t._v(" "),i("el-pagination",{attrs:{"current-page":t.listQuery.page,"page-size":t.listQuery.size,total:t.total,"page-sizes":[9,18,36,72],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}}),t._v(" "),i("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[i("el-form",{ref:"tmpAccount",staticClass:"small-space",staticStyle:{width:"300px","margin-left":"50px"},attrs:{"status-icon":"","label-position":"left","label-width":"75px",model:t.tmpAccount,rules:t.createRules}},[i("el-form-item",{attrs:{label:"账户名",prop:"name",required:""}},[i("el-input",{attrs:{type:"text","prefix-icon":"el-icon-edit","auto-complete":"off",disabled:"updateRole"===t.dialogStatus},model:{value:t.tmpAccount.name,callback:function(e){t.$set(t.tmpAccount,"name",e)},expression:"tmpAccount.name"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[i("el-input",{attrs:{type:"text","prefix-icon":"el-icon-message","auto-complete":"off",disabled:"updateRole"===t.dialogStatus},model:{value:t.tmpAccount.email,callback:function(e){t.$set(t.tmpAccount,"email",e)},expression:"tmpAccount.email"}})],1),t._v(" "),"updateRole"!==t.dialogStatus?i("el-form-item",{attrs:{label:"密码",prop:"password",required:""}},["updateRole"!==t.dialogStatus?i("el-input",{attrs:{type:"password","prefix-icon":"el-icon-edit","auto-complete":"off"},model:{value:t.tmpAccount.password,callback:function(e){t.$set(t.tmpAccount,"password",e)},expression:"tmpAccount.password"}}):t._e()],1):t._e(),t._v(" "),"updateRole"===t.dialogStatus?i("el-form-item",{attrs:{label:"角色"}},[i("el-select",{attrs:{placeholder:"请选择"},model:{value:t.tmpAccount.roleId,callback:function(e){t.$set(t.tmpAccount,"roleId",e)},expression:"tmpAccount.roleId"}},t._l(t.roleList,function(t){return i("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})}),1)],1):t._e()],1),t._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{nativeOn:{click:function(e){e.preventDefault(),t.dialogFormVisible=!1}}},[t._v("取消")]),t._v(" "),"add"===t.dialogStatus?i("el-button",{attrs:{type:"danger"},nativeOn:{click:function(e){return e.preventDefault(),t.$refs.tmpAccount.resetFields()}}},[t._v("重置")]):t._e(),t._v(" "),"add"===t.dialogStatus?i("el-button",{attrs:{type:"success",loading:t.btnLoading},nativeOn:{click:function(e){return e.preventDefault(),t.addAccount(e)}}},[t._v("添加")]):t._e(),t._v(" "),"update"===t.dialogStatus?i("el-button",{attrs:{type:"primary",loading:t.btnLoading},nativeOn:{click:function(e){return e.preventDefault(),t.updateAccount(e)}}},[t._v("更新资料")]):t._e(),t._v(" "),"updateRole"===t.dialogStatus?i("el-button",{attrs:{type:"primary",loading:t.btnLoading},nativeOn:{click:function(e){return e.preventDefault(),t.updateAccountRole(e)}}},[t._v("更新角色")]):t._e()],1)],1)],1)},staticRenderFns:[]},m=i("VU/8")(u,d,!1,null,null,null);e.default=m.exports}});
//# sourceMappingURL=34.3d14189a6616ad84ee2d.js.map