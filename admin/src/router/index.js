import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'
// import nestedRouterTestManager from './modules/nested'
// import nestedRouterSubCondition from './modules/subconditionnested'

const _import = require('./_import_' + process.env.NODE_ENV)

Vue.use(Router)

/**
 * icon : the icon show in the sidebar
 * hidden : if `hidden:true` will not show in the sidebar
 * redirect : if `redirect:noRedirect` will not redirect in the levelBar
 * noDropDown : if `noDropDown:true` will not has submenu in the sidebar
 * meta : `{ permission: ['a:xx'] }`  will control the page permission
 **/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/dashboard',
  //   children: [
  //     {
  //       path: 'dashboard', component: () => import('dashboard/index'),
  //       name: 'Dashboard',
  //       meta: { title: 'Dashboard', icon: 'dashboard', affix: true }
  //     }
  //   ]
  // }
  {
    path: '',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    icon: 'dashboard',
    children: [
      { path: 'dashboard', name: '首页', component: _import('dashboard/dashboard/index'), meta: { title: '首页', icon: 'dashboard', noCache: true }},
      { path: 'myinfo', name: '我的总览', component: _import('dashboard/myinfo/index'), meta: { title: '我的总览', icon: 'dashboard', noCache: true }}
    ]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/enviroment',
    component: Layout,
    name: '环境管理',
    icon: 'enviroment',
    children: [
      { path: 'machine/list', name: '服务器管理', component: _import('assets/machine/index'), meta: { title: '服务器管理', permission: ['machine:list'] }},
      { path: 'testenviroment/list', name: '环境管理', component: _import('enviroment/testenviroment/index'), meta: { title: '环境管理', permission: ['enviroment:list'] }},
      // { path: 'envmachine/list', name: '环境服务器', component: _import('enviroment/envmachine/index'), meta: { title: '环境服务器', permission: ['envmachine:list'] }},
      { path: 'enviromentassemble/list', name: '环境组件', component: _import('enviroment/enviromentassemble/index'), meta: { title: '环境组件', permission: ['enviroment_assemble:list'] }},
      { path: 'macdepunit/list', name: '环境部署', component: _import('enviroment/macdepunit/index'), meta: { title: '环境部署', permission: ['macdepunit:list'] }
      }
    ]
  },
  {
    path: '/deployunit',
    component: Layout,
    name: '发布单元',
    alwaysShow: true,
    activeMenu: '/deployunit',
    icon: 'deploy',
    children: [
      { path: 'depunit/list', name: '发布单元', component: _import('deployunit/depunit/index'), meta: { title: '发布单元', permission: ['depunit:list'] }, icon: 'testmanager' },
      { path: 'api/list', name: 'API管理', component: _import('deployunit/api/index'), meta: { title: 'API管理', permission: ['api:list'] }}
      // { path: 'apiparams/list', name: 'API参数', component: _import('deployunit/apiparams/index'), meta: { title: 'API参数', permission: ['apiparams:list'] }}
    ]
  },
  {
    path: '/assets',
    component: Layout,
    name: '用例管理',
    alwaysShow: true,
    activeMenu: '/assets',
    icon: 'testmanager',
    children: [
      { path: 'apicases/list', name: '用例库', component: _import('assets/cases/apicases/index'), meta: { title: '用例库', permission: ['apicases:list'] }, icon: 'testmanager' }
      // { path: 'project/list', name: '项目/迭代', component: _import('assets/project/index'), meta: { title: '项目/迭代', permission: ['project:list'] }, icon: 'testmanager' }
      // { path: 'apiparams/list', name: 'API参数', component: _import('deployunit/apiparams/index'), meta: { title: 'API参数', permission: ['apiparams:list'] }}
    ]
  },
  // nestedRouterTestManager,
  // nestedRouterSubCondition,
  {
    path: '/executecenter',
    component: Layout,
    name: '执行中心',
    icon: 'execute',
    children: [
      { path: 'executeplan/list', name: '测试集合', component: _import('executecenter/executeplan/index'), meta: { title: '测试集合', permission: ['executeplan:list'] }},
      { path: 'executeplancase/list', name: '集合用例', component: _import('executecenter/executeplancase/index'), meta: { title: '集合用例', permission: ['executeplan:list'] }},
      { path: 'executeplanbatch/list', name: '计划执行', component: _import('executecenter/executeplanbatch/index'), meta: { title: '计划执行', permission: ['executeplanbatch:list'] }}
    ]
  },
  {
    path: '/dispatch',
    component: Layout,
    name: '调度中心',
    icon: 'dispatch',
    children: [
      { path: 'slaver/list', name: '测试执行机', component: _import('dispatch/slaver/index'), meta: { title: '测试执行机', permission: ['slaver:list'] }},
      { path: 'dispatch/list', name: '调度管理', component: _import('dispatch/dispatch/index'), meta: { title: '调度管理', permission: ['dispatch:list'] }}
    ]
  },
  {
    path: '/reportcenter',
    component: Layout,
    name: '报告中心',
    icon: 'report',
    children: [
      // { path: 'testconditionreport/list', name: '条件执行报告', component: _import('reportcenter/testconditionreport/index'), meta: { title: '条件执行报告', permission: ['testconditionreport:list'] }},
      { path: 'apinewreport/list', name: '集合功能报告', component: _import('reportcenter/apinewreport/index'), meta: { title: '集合功能报告', permission: ['apireport:list'] }},
      { path: 'apireport/list', name: '功能报告明细', component: _import('reportcenter/apireport/index'), meta: { title: '功能报告明细', permission: ['apireport:list'] }},
      { path: 'apinewperreport/list', name: '集合性能报告', component: _import('reportcenter/apinewperreport/index'), meta: { title: '集合性能报告', permission: ['apireport:list'] }},
      // { path: 'apireportstatics/list', name: '功能统计报告', component: _import('reportcenter/apireportstatics/index'), meta: { title: '功能统计报告', permission: ['apireportstatics:list'] }},
      // { path: 'apiperformancereport/list', name: '性能明细报告', component: _import('reportcenter/apiperformancereport/index'), meta: { title: '性能明细报告', permission: ['apiperformancereport:list'] }},
      { path: 'apiperformancestatistics/list', name: '性能统计报告', component: _import('reportcenter/apiperformancestatistics/index'), meta: { title: '性能统计报告', permission: ['apiperformancestatistics:list'] }}
    ]
  },
  {
    path: '/condition',
    component: Layout,
    name: '条件管理',
    icon: 'condition',
    children: [
      { path: 'condition/list', name: '父条件管理', component: _import('condition/condition/index'), meta: { title: '父条件管理', permission: ['condition:list'] }},
      { path: 'apicondition/list', name: '接口子条件', component: _import('condition/apicondition/index'), meta: { title: '接口子条件', permission: ['apicondition:list'] }},
      { path: 'dbcondition/list', name: '数据库子条件', component: _import('condition/dbcondition/index'), meta: { title: 'DB子条件', permission: ['dbcondition:list'] }},
      { path: 'scriptcondition/list', name: '脚本子条件', component: _import('condition/scriptcondition/index'), meta: { title: '脚本子条件', permission: ['scriptcondition:list'] }},
      { path: 'delaycondition/list', name: '延时子条件', component: _import('condition/delaycondition/index'), meta: { title: '延时子条件', permission: ['delaycondition:list'] }}
    ]
  },
  {
    path: '/testvariables',
    component: Layout,
    name: '变量管理',
    icon: 'paras',
    children: [
      { path: 'variables/list', name: '随机变量', component: _import('testvariables/variables/index'), meta: { title: '随机变量', permission: ['variables:list'] }},
      { path: 'testvariables/list', name: '接口变量', component: _import('testvariables/testvariables/index'), meta: { title: '接口变量', permission: ['testvariables:list'] }},
      { path: 'scriptvariables/list', name: '脚本变量', component: _import('testvariables/scriptvariables/index'), meta: { title: '脚本变量', permission: ['scriptvariables:list'] }},
      { path: 'dbvariables/list', name: '数据库变量', component: _import('testvariables/dbvariables/index'), meta: { title: '数据库变量', permission: ['dbvariables:list'] }}
      // { path: 'apicasesvariables/list', name: '绑定变量', component: _import('assets/cases/apicasesvariables/index'), meta: { title: '绑定变量', permission: ['testvariables:list'] }}
      // { path: 'testvariablesvalue/list', name: '变量结果', component: _import('testvariables/testvariablesvalue/index'), meta: { title: '变量结果', permission: ['testvariablesvalue:list'] }}
    ]
  },
  {
    path: '/system',
    component: Layout,
    name: '系统管理',
    icon: 'sys',
    children: [
      { path: 'account/list', name: '账户管理', component: _import('system/account/list'), meta: { title: '账户管理', permission: ['account:list'] }},
      { path: 'role/list', name: '角色管理', component: _import('system/role/list'), meta: { title: '角色管理', permission: ['role:detail'] }},
      // { path: 'deploytestcase/list', name: '发布用例', component: _import('system/deploytestcase/index'), meta: { permission: ['deploytestcase:list'] }},
      { path: 'dictionary/list', name: '字典管理', component: _import('system/dictionary/index'), meta: { title: '字典管理', permission: ['dictionary:list'] }}
    ]
  },
  {
    path: '/account',
    component: Layout,
    redirect: '/account/detail',
    hidden: true,
    children: [{
      path: 'detail',
      name: '账户中心',
      component: _import('system/account/detail'),
      meta: { title: '账户中心', permission: ['account:detail'] }
    }]
  }
]
