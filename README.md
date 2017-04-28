# 基础框架(platease)	

> 旨在开发一个基础框架，能够快速的进行其他业务功能，数据分析功能的开发工作

platease框架包含：
1. SpringVMC + Spring + Hibernate
2. 采用apache shiro权限认证框架来控制访问, 对权限的修改退出重新登录(会清空权限缓存并重新加载)
3. 工具类采用 apache-common 系列


### ease-common说明
对于新添加的Dao，需要定义一个实现BaseDao的接口，同时实现类继承了BaseDaoImpl. service的定义也是类似的实现BaseService和继承BaseServiceImpl
```
// 以用户的Dao和Service为例子

// Dao接口及实现类

public interface UserDao extends BaseDao<User> {
}

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
}


// service接口及实现类

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



### 命名规范	
JAVA类，变量: 都采用驼峰标识	eg: `userService`

JSP文件: 都用下划线分割		eg: `user-index.jsp`

CSS文件: 文件名称也用下划线分割，具体的class样式，则用中划线分割 eg: `sys_user.css` `.user-title`



### TODO

1. 浏览器直接关闭, 如何退出？
2. 通用操作日志记录框架-->根据配置将日志信息保存到ES中去 --> 保留可扩展性
3. 通过安装脚本一次性将需要的数据库以及相关的服务都一起安装完成
4. 目前页面均未处理
5. 默认的页面样式，通用的表单验证及转换提交插件，通用的表格插件 等
6. 用户，角色，功能菜单，日志管理，配置管理  等几个基础功能



# NEXT: 大数据框架(afterlife)
> afterlife翻译为来世，源自《神盾局特工》
>
> 旨在开发关于大数据存储，大数据计算，大数据可视化，以及数据挖掘相关
>
> 该框架将分为不同的模块，每个模块互相独立，构建后给其他系统提供依赖




### 作者
webinglin (中国厦门)

邮箱: webinglin@163.com