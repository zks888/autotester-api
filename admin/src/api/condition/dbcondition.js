import request from '@/utils/request'

export function getdbconditionList(params) {
  return request({
    url: '/condition/db',
    method: 'get',
    params
  })
}

export function listalldbcondition() {
  return request({
    url: '/condition/db/listalldbcondition',
    method: 'get'
  })
}

export function getdbconditionnum() {
  return request({
    url: '/condition/db/getdbconditionnum',
    method: 'get'
  })
}

export function search(dbconditionForm) {
  return request({
    url: '/condition/db/search',
    method: 'post',
    data: dbconditionForm
  })
}

export function adddbcondition(dbconditionForm) {
  return request({
    url: '/condition/db',
    method: 'post',
    data: dbconditionForm
  })
}

export function updatedbcondition(dbconditionForm) {
  return request({
    url: '/condition/db/detail',
    method: 'put',
    data: dbconditionForm
  })
}

export function removedbcondition(dbconditionId) {
  return request({
    url: '/condition/db/' + dbconditionId,
    method: 'delete'
  })
}
