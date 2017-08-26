package net.dingyabin.dao;

import net.dingyabin.bean.Weight;

public interface WeightMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Weight record);

    int insertSelective(Weight record);

    Weight selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Weight record);

    int updateByPrimaryKey(Weight record);
}