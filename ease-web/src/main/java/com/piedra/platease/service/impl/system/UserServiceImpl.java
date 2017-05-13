package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.dto.DeptDTO;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
        if(userDTO!=null && StringUtils.isNotBlank(userDTO.getDeptCode())) {
            userDTO.setDeptCode(StringUtils.removePattern(userDTO.getDeptCode(),"0*$")+"%");
        }

        Map<String,Object> params = BeanMapUtil.trans2Map(userDTO);
        return userDao.queryByNameWithTotal(page, "SysUser.queryUserListCnt", "SysUser.queryUserList", params);
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

    @SuppressWarnings("unchecked")
    @Override
    public void updateUserRoles(String userId, Set<String> newRoleIds) throws Exception {
        if(newRoleIds==null){
            return ;
        }

        // 用户原有的角色集合
        List<Role> originalUserRoles = userDao.queryUserRoles(userId);
        Set<String> originalRoleIds = new HashSet<>();
        originalUserRoles.forEach(role -> originalRoleIds.add(role.getId()));

        // 两者都有的，不需要改变的角色ID集合
        Collection<String> intersectionCollection = CollectionUtils.intersection(originalRoleIds, newRoleIds);

        // 需要新增的用户角色
        newRoleIds.removeAll(intersectionCollection);

        // 需要删掉的用户角色
        originalRoleIds.removeAll(intersectionCollection);

        if(CollectionUtils.isNotEmpty(newRoleIds)) {
            userDao.addUserRoles(userId, newRoleIds);
        }
        if(CollectionUtils.isNotEmpty(originalRoleIds)) {
            userDao.deleteUserRoles(userId, originalRoleIds);
        }
    }

    @Override
    public void delUser(String userId) throws Exception {
        userDao.delete(userId);
        Map<String,Object> params = new HashMap<>();
        params.put("userId", userId);
        userDao.executeQueryByName("SysUser.delUserRole" +
                "ByUserId",params);
    }
}
