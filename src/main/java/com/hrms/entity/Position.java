package com.hrms.entity;

import java.io.Serializable;

/**
 * 岗位实体
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String positionName;

    public Position() {
    }

    public Position(Long id, String positionName) {
        this.id = id;
        this.positionName = positionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
