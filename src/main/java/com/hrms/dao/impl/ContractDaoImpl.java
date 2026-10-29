package com.hrms.dao.impl;

import com.hrms.dao.ContractDao;
import com.hrms.entity.Contract;
import com.hrms.util.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 合同数据访问实现类
 */
@Repository
public class ContractDaoImpl implements ContractDao {

    private static final String FILE_NAME = "contracts.json";

    @Autowired
    private DataStorageManager dataStorageManager;

    @Override
    public List<Contract> selectAll() {
        return dataStorageManager.loadList(FILE_NAME, Contract.class);
    }

    @Override
    public Contract selectById(Long id) {
        if (id == null) {
            return null;
        }
        List<Contract> list = selectAll();
        for (Contract c : list) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Contract> selectByEmpNo(String empNo) {
        List<Contract> result = new ArrayList<Contract>();
        if (empNo == null || empNo.trim().isEmpty()) {
            return result;
        }
        List<Contract> list = selectAll();
        for (Contract c : list) {
            if (empNo.trim().equalsIgnoreCase(c.getEmpNo())) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public synchronized void insert(Contract contract) {
        List<Contract> list = selectAll();
        if (contract.getId() == null) {
            contract.setId(dataStorageManager.generateNextId(FILE_NAME));
        }
        list.add(contract);
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void update(Contract contract) {
        if (contract == null || contract.getId() == null) {
            return;
        }
        List<Contract> list = selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (contract.getId().equals(list.get(i).getId())) {
                list.set(i, contract);
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            return;
        }
        List<Contract> list = selectAll();
        Iterator<Contract> it = list.iterator();
        while (it.hasNext()) {
            Contract c = it.next();
            if (id.equals(c.getId())) {
                it.remove();
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }
}
