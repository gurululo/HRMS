package com.hrms.dao;

import com.hrms.entity.Contract;

import java.util.List;

/**
 * 合同数据访问接口
 */
public interface ContractDao {

    List<Contract> selectAll();

    Contract selectById(Long id);

    List<Contract> selectByEmpNo(String empNo);

    void insert(Contract contract);

    void update(Contract contract);

    void delete(Long id);
}
