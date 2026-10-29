package com.hrms.service;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Position;

import java.util.List;

/**
 * 岗位业务服务接口
 */
public interface PositionService {

    List<Position> getAllPositions();

    List<Position> searchPositions(String positionName);

    Position getPositionById(Long id);

    ResultMsg addPosition(Position position);

    ResultMsg updatePosition(Position position);

    ResultMsg deletePosition(Long id);
}
