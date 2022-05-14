package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Account;

import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface AccountService extends Service<Account> {

  List<Account> findWithUsername(String UserNmae);

}
