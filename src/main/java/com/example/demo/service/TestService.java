package com.example.demo.service;

import com.example.demo.mybatis.dao.TestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TestService {

    @Autowired
    private TestDAO testDAO;

    public List<String> findSysStingList() {
        List<String> list = testDAO.selectSysConfig();
        log.info(">>> " + list);
        return list;
    }
}
