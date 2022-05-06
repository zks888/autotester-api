import request from '@/utils/request'

export function getexecuteplanList(params) {
  return request({
    url: '/executeplan',
    method: 'get',
    params
  })
}

export function getstaticsplan() {
  return request({
    url: '/executeplan/getstaticsplan',
    method: 'get'
  })
}

export function getexecuteplannum() {
  return request({
    url: '/executeplan/getexecuteplannum',
    method: 'get'
  })
}

export function getallexplanbytype(params) {
  return request({
    url: '/executeplan/getallexplanbytype',
    method: 'get',
    params
  })
}

export function getallexplan() {
  return request({
    url: '/executeplan/getallexplan',
    method: 'get'
  })
}

export function search(executeplanForm) {
  return request({
    url: '/executeplan/search',
    method: 'post',
    data: executeplanForm
  })
}

export function addexecuteplan(executeplanForm) {
  return request({
    url: '/executeplan',
    method: 'post',
    data: executeplanForm
  })
}

export function executeplan(executeplanForm) {
  return request({
    url: '/executeplan/execplancases',
    method: 'post',
    data: executeplanForm
  })
}

export function checkplancondition(executeplanForm) {
  return request({
    url: '/executeplan/checkcondition',
    method: 'post',
    data: executeplanForm
  })
}

export function updateexecuteplan(executeplanForm) {
  return request({
    url: '/executeplan/detail',
    method: 'put',
    data: executeplanForm
  })
}

export function updateexecuteplanstatus(executeplanForm) {
  return request({
    url: '/executeplan/updatestatus',
    method: 'post',
    data: executeplanForm
  })
}

export function removeexecuteplan(executeplanId) {
  return request({
    url: '/executeplan/' + executeplanId,
    method: 'delete'
  })
}
