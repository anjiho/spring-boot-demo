package com.example.demo.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDAO {

    @Select("SELECT variable FROM sys.sys_config")
    List<String> selectSysConfig();
}
