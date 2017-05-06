package com.piedra.platease.service;

import com.piedra.platease.service.system.FunctionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author webinglin
 * @since 2017-04-27
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context-application.xml"})
public class FunctionServiceTest {

    @Autowired
    private FunctionService functionService;

    @Test
    public void testQuery() throws Exception{
    }




}
