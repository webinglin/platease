# 基础框架(platease)	

> 旨在开发一个基础框架，能够快速的进行其他业务功能，数据分析功能的开发工作

[TOC]

### platease
1. SpringVMC + Spring + Hibernate+Velocity
2. 采用apache shiro权限认证框架来控制访问，利用Ehcache对用户权限资源进行缓存
3. 工具类采用 apache-common 系列


### 自定义service+dao
对于新添加的Dao，需要定义一个实现BaseDao的接口，同时实现类继承了BaseDaoImpl. service的定义也是类似的实现BaseService和继承BaseServiceImpl
```
// 以用户的Dao和Service为例子

// Dao接口及实现类

public interface UserDao extends BaseDao<User> {
}

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
}


// service接口及实现类		如果不需要从Service直接get,delete,update User的信息,也可以选择不继承BaseService<User>

public interface UserService extends BaseService<User> {
}

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	@Autowired
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}
	@Autowired
	private UserDao userDao;
}

```



### 使用IDEA自动生成hbm映射文件
1. 给 ease-common 模块添加 Hibernate 的特性。
2. 通过Persistence视图hibernate.cfg.xml右键->Generate Persistence Mapping->By Database Schema
   来生成相应的hibernate映射文件和实体类（注意修改相应的类名或者数据类型）
3. 生成的hbm文件可以添加velocity模板代码，动态生成sql语句，参数的设置还是要用Hibernate的占位符形式，防止sql注入。 模板代码示例:
```
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>

    <class name="com.piedra.platease.model.system.User" table="sys_user" schema="platease">
        <id name="id">
            <column name="ID" sql-type="char(32)" length="32"/>
        </id>
        <property name="userName">
            <column name="USER_NAME" sql-type="varchar(255)"/>
        </property>
        <property name="idcard">
            <column name="IDCARD" sql-type="varchar(32)" length="32"/>
        </property>
        <property name="realName">
            <column name="REAL_NAME" sql-type="varchar(255)"/>
        </property>
        <property name="password">
            <column name="PASSWORD" sql-type="varchar(255)"/>
        </property>
        <property name="salt">
            <column name="SALT" sql-type="varchar(8)"/>
        </property>
        <property name="deptId">
            <column name="DEPT_ID" sql-type="char(32)" length="32"/>
        </property>
        <property name="deptCode">
            <column name="DEPT_CODE" sql-type="char(12)" length="12"/>
        </property>
        <property name="status">
            <column name="STATUS" sql-type="tinyint(4)"/>
        </property>
        <property name="lastLoginIp">
            <column name="LAST_LOGIN_IP" sql-type="varchar(255)"/>
        </property>
        <property name="lastLoginTime">
            <column name="LAST_LOGIN_TIME" sql-type="datetime"/>
        </property>
        <property name="telphone">
            <column name="TELPHONE" sql-type="varchar(32)" length="32"/>
        </property>
        <property name="homeBgurl">
            <column name="HOME_BGURL" sql-type="varchar(255)"/>
        </property>
        <property name="creatorId">
            <column name="CREATOR_ID" sql-type="char(32)" length="32"/>
        </property>
        <property name="createTime">
            <column name="CREATE_TIME" sql-type="datetime"/>
        </property>
        <property name="remark">
            <column name="REMARK" sql-type="varchar(255)"/>
        </property>
    </class>

    <!-- 查询用户的角色信息 -->
    <sql-query name="SysUser.queryUserRoles">
        <return alias="r" class="com.piedra.platease.model.system.Role" />
        <![CDATA[
            SELECT r.* FROM SYS_ROLE r  JOIN SYS_USER_ROLE ur ON r.ID=ur.ROLE_ID AND ur.USER_ID=:userId
        ]]>
    </sql-query>

    <!-- 更新用户信息 -->
    <sql-query name="SysUser.updateUser">
        <![CDATA[
            UPDATE SYS_USER SET ID=ID
            #if($userName)
                ,USER_NAME=:userName
            #end
            #if($idcard)
                ,IDCARD=:idcard
            #end
            #if($realName)
                ,REAL_NAME=:realName
            #end
            #if($password)
                ,PASSWORD=:password
            #end
            #if($deptId)
                ,DEPT_ID=:deptId
            #end
            #if($deptCode)
                ,DEPT_CODE=:deptCode
            #end
            #if($telphone)
                ,TELPHONE=:telphone
            #end
            #if($homeBgurl)
                ,HOME_BGURL=:homeBgurl
            #end
            #if($remark)
                ,REMARK=:remark
            #end
            WHERE ID=:id
        ]]>
    </sql-query>

	<!-- other codes go here ... ... -->

</hibernate-mapping>
```


### 规范	

#### 命名规范
JAVA类，变量: 都采用驼峰标识	eg: `userService`

JSP文件: 都用下划线分割		eg: `user-index.jsp`

CSS文件: 文件名称也用下划线分割，具体的class样式，则用中划线分割 eg: `sys_user.css` `.user-title`

##### 方法命名规范
查询类的以 query 开头;  修改类以 update 开头; 删除类以 del 开头; 导出以 export开头; 新增以 add开头
特别是Controller层的命名，而且Controller的方法如果有service抛出的异常，必须要try...catch...


#### 异常处理规范
dao层和service的异常信息都直接往上一层抛出，不作处理，当成不知道如何处理该异常。在最上一层，也就是Controller层，需要对所有
的异常信息进行捕获处理。并返回友好提示信息。

#### 事务规范
事务控制不做到对具体方法的控制，直接使用@Transaction注解对Service的实现类进行注解，全局控制事务
所有涉及到事务的操作（比如一个操作包含了对两个表的非查询类的操作），那么必须将这一组dao操作放在service的一个方法中



### TODO

1. 通用操作日志记录框架-->根据配置将日志信息保存到ES中去 --> 保留可扩展性
2. 通过安装脚本一次性将需要的数据库以及相关的服务都一起安装完成
3. 用户，角色，功能菜单，日志管理，配置管理  等几个基础功能



### 作者
webinglin (中国厦门)

邮箱: webinglin@163.com
