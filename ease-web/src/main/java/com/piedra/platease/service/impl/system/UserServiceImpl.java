package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.UserService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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


	public Page<User> getUserPage(Integer pageSize,Integer pageIndex){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		Page<User> page = new Page<>();
		page.setOrderBy("id");
		page.setPageSize(pageSize);
		page.setPageIndex(pageIndex);
		return getBaseDao().getPage(criteria, page);
	}

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
    public List<User> queryByPage(Page<User> page, UserDTO userDTO) throws Exception {


        ///TODO.. 根据对象转成相应的 DetachedCriteria 对象


        return null;
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
