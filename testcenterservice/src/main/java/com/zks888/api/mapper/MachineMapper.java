package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Machine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MachineMapper extends MyMapper<Machine> {
    Machine findmachinebymachinename(@Param("machinename")String  machinename);
    Machine findmachinebyip(@Param("ip")String ip);
    List<Machine> findMachineWithName(final Map<String, Object> params);
    Integer getmachinenum();
    void updateMachine(Machine params);
    Machine findmachinebymachineandip(@Param("machinename") String machinename,@Param("ip") String ip,@Param("id") Long ID);
}
