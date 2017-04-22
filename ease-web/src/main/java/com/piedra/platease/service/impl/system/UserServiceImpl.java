package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.UserService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.query.Query;
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
}
