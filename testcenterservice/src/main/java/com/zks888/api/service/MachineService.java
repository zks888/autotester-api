package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Enviroment;
import com.zks888.api.entity.Machine;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/15
*/
public interface MachineService extends Service<Machine> {

    Machine findmachinebymachinename(String machinename);
    Machine findmachinebymachineandip(String machinename,String ip,Long ID);


    Machine findmachinebyip(String ip);
    int ifexist(Condition condition);
    List<Machine> findMachineWithName(final Map<String, Object> params);
    void updateMachine(Machine params);

    Integer getmachinenum();


}
