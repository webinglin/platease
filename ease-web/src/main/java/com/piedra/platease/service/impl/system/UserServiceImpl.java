package com.piedra.platease.service.impl.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.StatusConstants;
import com.piedra.platease.dao.system.UserDao;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.BeanMapUtil;
import com.piedra.platease.utils.Md5Util;
import com.piedra.platease.utils.PasswordUtil;
import com.piedra.platease.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("userName",username)).add(Restrictions.eq("status", Constants.Status.AVAILABLE));
	    return  userDao.get(criteria);
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

        // 密码的修改需要重新生成salt
        if(StringUtils.isNotBlank(user.getPassword())) {
            user.setSalt(RandomStringUtils.randomAlphanumeric(8));
            user.setPassword(PasswordUtil.encryptMd5Password(user.getPassword(), user.getSalt()));
        }
        userDao.updateUser(user);

        // 判断是否需要更 用户-角色 关系表
        this.packRoleIdsForUpdateUserRoles(userDto, user);
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
        userDao.executeQueryByName("SysUser.delUserRoleByUserId",params);
    }

    @Override
    public void addUser(UserDTO userDto) throws Exception {
        if(userDto==null){
            return ;
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        if(StringUtils.isBlank(user.getPassword())) {
            user.setPassword(Md5Util.getMd5(Constants.DEFAULT_PASSWORD));
        }
        user.setId(UUIDUtil.generateUUID());
        // 对密码再度进行加盐HASH
        user.setSalt(RandomStringUtils.randomAlphanumeric(8));
        // TODO 密码的传输过程采用 RSA非对称加密算法进行传输
        user.setPassword(PasswordUtil.encryptMd5Password(user.getPassword(), user.getSalt()));

        user.setStatus(StatusConstants.USER_AVAILABLE);

        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        user.setCreatorId(currentUser.getId());

        user.setCreateTime(new Date());

        userDao.save(user);
        this.packRoleIdsForUpdateUserRoles(userDto, user);
    }

    private void packRoleIdsForUpdateUserRoles(UserDTO userDto, User user) throws Exception {
        // 判断是否需要更 用户-角色 关系表
        if(userDto==null || StringUtils.isBlank(userDto.getRoleIds())) {
            return ;
        }

        Set<String> roleIdSet = new HashSet<>();
        String[] roleIdArr = userDto.getRoleIds().split(Constants.COMMA);
        Arrays.asList(roleIdArr).forEach(roleId -> roleIdSet.add(roleId));
        this.updateUserRoles(user.getId(), roleIdSet);
    }

    @Override
    public void delUsers(String[] userIds) throws Exception {
        if(userIds == null || userIds.length==0){
            return;
        }

        for(String userId: userIds){
            this.delUser(userId);
        }
    }
}
