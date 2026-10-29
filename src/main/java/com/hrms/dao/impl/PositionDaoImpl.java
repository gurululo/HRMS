package com.hrms.dao.impl;

import com.hrms.dao.PositionDao;
import com.hrms.entity.Position;
import com.hrms.util.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * 岗位数据访问实现类
 */
@Repository
public class PositionDaoImpl implements PositionDao {

    private static final String FILE_NAME = "positions.json";

    @Autowired
    private DataStorageManager dataStorageManager;

    @Override
    public List<Position> selectAll() {
        return dataStorageManager.loadList(FILE_NAME, Position.class);
    }

    @Override
    public Position selectById(Long id) {
        if (id == null) {
            return null;
        }
        List<Position> list = selectAll();
        for (Position p : list) {
            if (id.equals(p.getId())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public synchronized void insert(Position position) {
        List<Position> list = selectAll();
        if (position.getId() == null) {
            position.setId(dataStorageManager.generateNextId(FILE_NAME));
        }
        list.add(position);
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void update(Position position) {
        if (position == null || position.getId() == null) {
            return;
        }
        List<Position> list = selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (position.getId().equals(list.get(i).getId())) {
                list.set(i, position);
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
        List<Position> list = selectAll();
        Iterator<Position> it = list.iterator();
        while (it.hasNext()) {
            Position p = it.next();
            if (id.equals(p.getId())) {
                it.remove();
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }
}
