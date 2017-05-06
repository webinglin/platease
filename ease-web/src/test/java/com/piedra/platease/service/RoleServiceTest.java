package com.piedra.platease.service;

import com.piedra.platease.service.system.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author webinglin
 * @since 2017-04-27
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context-application.xml"})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    // 获取所有的角色
    @Test
    public void testQuery() throws Exception{
    }


    // 更改角色

    // 新增角色

    // 修改一个角色拥有的权限
    @Test
    public void testUpdateRoleFuncs() throws Exception{

        Set<String> funcSet = new HashSet<>();
        funcSet.add("9b8eb1bb324411e7a87db881988f4746");
        funcSet.add("ae8c1efd324411e7a87db881988f4746");

        roleService.updateRoleFunctions("b", funcSet);

    }
}
