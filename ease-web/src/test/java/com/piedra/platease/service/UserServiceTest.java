package com.piedra.platease.service;

import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author webinglin
 * @since 2017-04-27
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context-application.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUpdateUser() throws Exception{
        UserDTO user = new UserDTO();
        user.setId("373e146793c04c0a9b3d1dc434b2d509");
        user.setRealName("单元测试");
        user.setUserName("junit4");

        userService.updateUser(user);
        System.out.println("SUCCESS");
    }

    @Test
    public void testQueryPermissions() throws Exception{
        String userId = "373e146793c04c0a9b3d1dc434b2d509";
        List<Function> functionList = userService.queryUserPermissions(userId);
        System.out.println(functionList.size());
    }


    @Test
    public void testQueryRoles() throws Exception{
        String userId = "373e146793c04c0a9b3d1dc434b2d509";
        List<Role> roleList = userService.queryUserRoles(userId);
        System.out.println(roleList.size());
    }


    @Test
    public void testQueryByPage() throws Exception {

        Page<User> page = new Page<>();
        page.setPage(1);
        page.setRows(3);
        page.setSidx("USER_NAME");

        UserDTO userDto = new UserDTO();
//        userDto.setId("373e146793c04c0a9b3d1dc434b2d509");
//        userDto.setRealName("单元测试' OR '1'='1");
        userDto.setUserName("junit4 or 1 = 1  ");

        page = userService.queryByPage(page, userDto);

        Assert.assertEquals(1, page.getDatas().size());
    }


    /**
     * 测试用户角色授权
     */
    @Test
    public void testUpdateRoles() throws Exception {
        Set<String> roleIdSet = new HashSet<>();
        roleIdSet.add("a");
        roleIdSet.add("b");
        roleIdSet.add("e");
        roleIdSet.add("f");
        userService.updateUserRoles("373e146793c04c0a9b3d1dc434b2d509", roleIdSet);
    }
}
