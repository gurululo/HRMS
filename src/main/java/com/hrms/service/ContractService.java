package com.hrms.service;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Contract;

import java.util.List;

/**
 * 合同业务服务接口
 */
public interface ContractService {

    List<Contract> getAllContracts();

    List<Contract> searchContracts(String keyword);

    Contract getContractById(Long id);

    ResultMsg addContract(Contract contract);

    ResultMsg updateContract(Contract contract);

    ResultMsg deleteContract(Long id);
}
