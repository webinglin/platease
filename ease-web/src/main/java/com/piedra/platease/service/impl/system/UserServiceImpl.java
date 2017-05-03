package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.BeanMapUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 用户实体业务实现层
 * @author webinglin
 * @since 2017-04-05
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    public void setBaseDao(UserDao userDao) {
        super.setBaseDao(userDao);
    }
	@Autowired
	private UserDao userDao;

    @Override
    public User getByUsername(String username) throws Exception {
	    return userDao.get("userName", username);
    }

    @Override
    public List<Role> queryUserRoles(String userId) throws Exception{
        return userDao.queryUserRoles(userId);
    }

    @Override
    public List<Function> queryUserPermissions(String userId) throws Exception{
	    return userDao.queryUserPermissions(userId);
    }


    /**
     * 根据分页参数以及查询条件进行分页查询
     * @param page    分页参数
     * @param userDTO 过滤参数
     * @return 返回用户结果信息
     */
    @Override
    public Page<User> queryByPage(Page<User> page, UserDTO userDTO) throws Exception {
        Map<String,Object> params = BeanMapUtil.trans2Map(userDTO);
        return userDao.queryByNameWithTotal(page, "queryUserListCnt", "queryUserList", params);
    }

    /**
     * 更新用户信息
     * @param userDto 用户信息
     * @throws Exception 抛出异常
     */
    @Override
    public void updateUser(UserDTO userDto) throws Exception {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userDao.updateUser(user);
    }
}
