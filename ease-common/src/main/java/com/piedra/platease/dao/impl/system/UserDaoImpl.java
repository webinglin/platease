package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public List<Role> queryUserRoles(String userId) {
        	    String sql = " SELECT r.* FROM SYS_ROLE r  JOIN SYS_USER_ROLE ur ON r.ID=ur.ROLE_ID AND ur.USER_ID=:userId ";
	    NativeQuery query = getSession().createNativeQuery(sql).setParameter("userId", userId).addEntity(Role.class);
        return query.list();
    }

    @Override
    public List<Function> queryUserPermissions(String userId) {
        String sql = " SELECT f.* FROM SYS_FUNCTION f " +
                " JOIN SYS_ROLE_FUNC rf ON rf.FUNC_ID=f.FUNC_ID " +
                " JOIN SYS_USER_ROLE ur ON ur.ROLE_ID=rf.ROLE_ID AND ur.USER_ID=:userId ";
        NativeQuery query = getSession().createNativeQuery(sql).setParameter("userId", userId).addEntity(Function.class);
        return query.list();
    }

    @Override
    public void updateUser(User user) throws Exception {
        if(StringUtils.isBlank(user.getId())){
            logger.warn("用户ID为空，无法更新用户信息");
            return ;
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" UPDATE SYS_USER SET ID=ID ");
        if(StringUtils.isNotBlank(user.getUserName())){
            sqlBuilder.append(" ,USER_NAME=:userName");
        }
        if(StringUtils.isNotBlank(user.getIdcard())){
            sqlBuilder.append(" ,IDCARD=:idcard");
        }
        if(StringUtils.isNotBlank(user.getRealName())){
            sqlBuilder.append(" ,REAL_NAME=:realName");
        }
        if(StringUtils.isNotBlank(user.getPassword())){
            sqlBuilder.append(" ,PASSWORD=:password");
        }
        if(StringUtils.isNotBlank(user.getDeptId())){
            sqlBuilder.append(" ,DEPT_ID=:deptId");
        }
        if(StringUtils.isNotBlank(user.getDeptCode())){
            sqlBuilder.append(" ,DEPT_CODE=:deptCode");
        }
        if(StringUtils.isNotBlank(user.getHomeBgurl())){
            sqlBuilder.append(" ,HOME_BGURL=:telphone");
        }
        if(StringUtils.isNotBlank(user.getRemark())){
            sqlBuilder.append(" ,REMARK=:remark");
        }
        sqlBuilder.append(" WHERE ID=:id ");
        createNativeQueryForUpdate(sqlBuilder, new BeanMap(user)).executeUpdate();
    }






    public static void main(String[] args) {
        User user = new User();
        user.setRealName("a");
        user.setDeptCode("222");
        user.setCreateTime(new Date());
        new BeanMap(user);
        System.out.println(22);
    }

}
