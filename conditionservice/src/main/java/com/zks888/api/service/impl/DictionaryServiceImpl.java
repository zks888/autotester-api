package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Dictionary;
import com.zks888.api.mapper.DictionaryMapper;
import com.zks888.api.service.DictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/16
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl extends AbstractService<Dictionary> implements DictionaryService {
@Resource
private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> findDicNameValueWithCode(String params) {
        return this.dictionaryMapper.findDicNameValueWithCode(params);
    }

    @Override
    public List<Dictionary> findDicWithName(Map<String, Object> params) {
        return this.dictionaryMapper.findDicWithName(params);
    }

    @Override
    public void updateDic(Dictionary params)
   {
       dictionaryMapper.updateDic(params);
   }


}
