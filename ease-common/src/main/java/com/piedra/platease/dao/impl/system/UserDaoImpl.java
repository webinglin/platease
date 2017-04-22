package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public List<Role> queryUserRoles(String userId) {
        	    String sql = " SELECT r.* FROM SYS_ROLE r  JOIN SYS_USER_ROLE ur ON r.ID=ur.ROLE_ID AND ur.USER_ID=:userId ";
	    Query query = getSession().createNativeQuery(sql).setParameter("userId", userId).addEntity(Role.class);
        return query.list();
    }

    @Override
    public List<Function> queryUserPermissions(String userId) {
        String sql = " SELECT f.* FROM SYS_FUNCTION f " +
                " JOIN SYS_ROLE_FUNC rf ON rf.FUNC_ID=f.FUNC_ID " +
                " JOIN SYS_USER_ROLE ur ON ur.ROLE_ID=rf.ROLE_ID AND ur.USER_ID=:userId ";
        Query query = getSession().createNativeQuery(sql).setParameter("userId", userId).addEntity(Function.class);
        return query.list();
    }
}
