package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.model.system.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {
}
