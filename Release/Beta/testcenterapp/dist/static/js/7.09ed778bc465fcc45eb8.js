webpackJsonp([7,19],{H12d:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("4aJU"),r=a("Ounb"),s=a("u2wq"),l=a("2f+2"),o={name:"DashboardAdmin",components:{mycreateinfo:n.default,RunTable:r.default,myrecentfunctioninfo:s.default,myrecentperformanceinfo:l.default},data:function(){return{}},methods:{}},i={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-editor-container"},[a("el-row",{attrs:{gutter:1}},[a("div",[a("label",[t._v("我创建的:")])]),t._v(" "),a("el-col",{staticStyle:{"padding-right":"8px","margin-bottom":"5px"},attrs:{xs:{span:24},sm:{span:24},md:{span:24},lg:{span:100},xl:{span:12}}},[a("mycreateinfo")],1)],1),t._v(" "),a("el-row",{attrs:{gutter:1}},[a("div",[a("label",[t._v("我运行的:")])]),t._v(" "),a("el-col",{staticStyle:{"padding-right":"8px","margin-bottom":"5px"},attrs:{xs:{span:24},sm:{span:24},md:{span:24},lg:{span:100},xl:{span:12}}},[a("RunTable")],1)],1),t._v(" "),a("el-row",{attrs:{gutter:1}},[a("div",[a("label",[t._v("最近的功能测试:")])]),t._v(" "),a("el-col",{staticStyle:{"padding-right":"8px","margin-bottom":"5px"},attrs:{xs:{span:24},sm:{span:24},md:{span:24},lg:{span:100},xl:{span:12}}},[a("myrecentfunctioninfo")],1)],1),t._v(" "),a("el-row",{attrs:{gutter:1}},[a("div",[a("label",[t._v("最近的性能测试:")])]),t._v(" "),a("el-col",{staticStyle:{"padding-right":"8px","margin-bottom":"5px"},attrs:{xs:{span:24},sm:{span:24},md:{span:24},lg:{span:100},xl:{span:12}}},[a("myrecentperformanceinfo")],1)],1)],1)},staticRenderFns:[]};var d=a("VU/8")(o,i,!1,function(t){a("efpH")},"data-v-3692c9d1",null);e.default=d.exports},efpH:function(t,e){},"x+yh":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n={name:"Dashboard",components:{adminDashboard:a("H12d").default},data:function(){return{currentRole:"adminDashboard"}}},r={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"dashboard-container"},[e(this.currentRole,{tag:"component"})],1)},staticRenderFns:[]},s=a("VU/8")(n,r,!1,null,null,null);e.default=s.exports}});