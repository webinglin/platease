<%--
  User: webinglin
  Date: 2017/5/13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PLAT-EASE介绍</title>
</head>
<body>

<pre>

    ### platease
    1. SpringVMC + Spring + Hibernate + Velocity
    2. 采用apache shiro权限认证框架来控制访问，利用Ehcache对用户权限资源进行缓存
    3. 工具类采用 apache-commons 系列
    4. 前端：JS框架[JQuery]、 UI风格[JQueryUI]、表格插件[jqGrid]、树插件[ztree]、表单验证插件[validform]、多选下拉框[multiselect]


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
    3. 生成的hbm文件可以添加velocity模板代码，动态生成sql语句，参数的设置还是要用Hibernate的占位符形式，防止sql注入。


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


    ### 四大基础模块理念
    > 四个模块都包含基本的CRUD操作

    #### 用户管理
    添加用户，对于设定的密码必须使用MD5加密，传输到后台之后，后台根据随机生成一个字符串，在和MD5加密过的密码再次进行HASH运算，作为
    最终用户的密码，同时数据库也需要保存那一串用于Hash的随机字符串（即Salt）

    删除为逻辑删除，通过标记为来表示用户已经无效

    #### 角色管理

    角色的授权操作，采用 fieldset的形式，对每个模块的资源进行划分，用checkbox的方式进行授权

    角色删除为直接删除

    #### 权限管理

    权限分级管理，通过 菜单（PARENT_ID为0000....的） 父节点ID非0...的为 按钮，链接 等其他的类型的权限

    删除为直接删除

    #### 单位管理

    同权限管理页面


    ### TODO

    1. 通用操作日志记录框架-->根据配置将日志信息保存到ES中去 --> 保留可扩展性
    2. 通过安装脚本一次性将需要的数据库以及相关的服务都一起安装完成
    3. 用户，角色，功能菜单，日志管理，配置管理  等几个基础功能


    ### 作者
    webinglin (中国厦门)

    邮箱: webinglin@163.com

</pre>

</body>
</html>
