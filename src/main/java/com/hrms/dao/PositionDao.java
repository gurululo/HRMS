package com.hrms.dao;

import com.hrms.entity.Position;

import java.util.List;

/**
 * 岗位数据访问接口
 */
public interface PositionDao {

    List<Position> selectAll();

    Position selectById(Long id);

    void insert(Position position);

    void update(Position position);

    void delete(Long id);
}
