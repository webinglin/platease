package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public List<Role> queryUserRoles(String userId) {
        Query query = getSession().createNamedQuery("queryUserRoles").setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<Function> queryUserPermissions(String userId) {
        Query query = getSession().createNamedQuery("queryUserPermissions").setParameter("userId", userId);
        return query.list();
    }

    @Override
    public void updateUser(User user) throws Exception {
        if(user==null || StringUtils.isBlank(user.getId())){
            logger.warn("用户/用户ID为空，无法更新用户信息");
            return ;
        }
        executeQueryByName("updateUser", BeanMapUtil.trans2Map(user));
    }
}
