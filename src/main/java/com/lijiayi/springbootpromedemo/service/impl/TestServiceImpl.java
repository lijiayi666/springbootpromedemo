package com.lijiayi.springbootpromedemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.lijiayi.springbootpromedemo.entity.TestAopEntity;
import com.lijiayi.springbootpromedemo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
    @Override
    public void testAopParamsGet(TestAopEntity testAopEntity) {
        log.info(JSON.toJSONString(testAopEntity));
    }
}
