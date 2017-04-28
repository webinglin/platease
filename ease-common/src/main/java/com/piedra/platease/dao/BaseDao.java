package com.piedra.platease.dao;


import com.piedra.platease.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;

import java.io.StringWriter;
import java.util.*;

/**
 * Dao操作接口定义
 * @author webinglin
 * @since 2017-04-05
 */
public interface BaseDao<T> {

    /**
     * 获取hibernate session对象
     */
    Session getSession();

    /**
     * 创建一个criteria
     */
    Criteria getCriteria();

    /**
     * 通过id加载实体
     * @param id    实体主键
     * @return 实体对象
     */
     T load(String id);

    /**
     * 通过id加载实体
     * @param id    实体主键
     * @return 实体对象
     */
     T get(String id);

    /**
     * 根据ID数组获取实体对象集合.
     * @param ids
     * @return 实体对象集合
     */
     List<T> get(String[] ids);

    /**
     * 根据属性名和属性值获取实体对象.
     * @param propertyName  属性名称
     * @param value         属性的值
     * @return 实体对象
     */
     T get(String propertyName, Object value);

    /**
     * 根据DetachedCriteria获取对象
     * @param criteria  查询条件
     * @return  查询的实体对象
     */
     T get(DetachedCriteria criteria);

    /**
     * 根据属性名和属性值获取实体对象集合.
     * @param propertyName  属性名
     * @param value         属性值
     * @return 实体对象集合
     */
     List<T> getList(String propertyName, Object value);

    /**
     * 获取所有实体对象总数.
     * @return 实体对象总数
     */
     Integer queryCount();

    /**
     * 根据DetachedCriteria获取实体数量
     * @param criteria  查询条件
     * @return  返回查询条数
     */
     Integer queryCount(DetachedCriteria criteria);

    /**
     * 保存实体对象.
     * @param entity    要保存的实体对象
     * @return ID
     */
    String save(T entity);

    /**
     * 保存或更新一个对象
     * @param entity    实体对象
     */
     void saveOrUpdate(T entity);

    /**
     * 更新实体对象.
     * @param entity    要更新的实体对象
     */
     void update(T entity);

    /**
     * 合并一个对象
     * @param entity    合并实体
     */
     void merge(T entity);

    /**
     * 删除实体对象.
     * @param entity    待删除实体对象
     */
     void delete(T entity);

    /**
     * 根据ID删除实体对象.
     * @param id    待删除的实体ID
     */
     void delete(String id);

    /**
     * 根据ID数组删除实体对象.
     * @param ids   待删除的实体对象ID列表
     */
     void delete(String[] ids);

    /**
     * 根据实体集合删除实体对象
     * @param list  待删除的实体对象列表
     */
     void delete(List<T> list);

    /**
     * 获取全部列表
     * @return  所有实体对象列表
     */
     List<T> list();

    /**
     * 根据DetachedCriteria 获取列表
     * @param criteria  查询条件
     * @return  实体对象列表
     */
     List<T> list(DetachedCriteria criteria);

    /**
     * 执行criteria查询获得一个结果
     * @param criteria  查询条件
     * @return  实体对象
     */
     Object findObject(DetachedCriteria criteria);

    /**
     * DetachedCriteria分页查询
     * @param criteria      查询条件
     * @param pageSize      每页大小
     * @param pageNumber    当前页数
     * @return  分页结果
     */
     List<T> page(DetachedCriteria criteria, Integer pageSize, Integer pageNumber);

    /**
     * 根据DetachedCriteria和Page对象获取分页page对象
     * @param criteria  查询条件
     * @param page      分页参数
     * @return  分页对象
     */
     Page<T> getPage(DetachedCriteria criteria, Page<T> page);


/* ******************************************************************************
 *  sql-query   ----begin----
 * ***************************************************************************** */

    /**
     * 根据queryName查询数据 sql语句查询到的数据总量
     * @param countQueryName    查数据量的sql-query名称
     * @param params    参数集合
     * @return  返回数据总量
     */
    Integer queryCntByNameQuery(String countQueryName, Map<String, Object> params) ;

    /**
     * 分页查询， 不查询数据总量
     * @param page          分页对象
     * @param queryName     sql-query的名称
     * @param params        参数集合
     * @return  返回分页结果（只包含当前查询页的数据集合）
     */
    @SuppressWarnings({ "unchecked" })
    Page<T> queryByNameWithoutTotal(Page<T> page, String queryName, Map<String, Object> params);

    /**
     * 分页查询， 同时查出数据总量
     * @param page          分页对象
     * @param queryName     sql-query的名称
     * @param params        参数集合
     * @return  返回分页结果（包含数据总量和当前查询页的数据集合）
     */
    Page<T> queryByNameWithTotal(Page<T> page, String countQueryName, String queryName, Map<String, Object> params) ;

    /**
     * 根据sql-query名称查询结果，不包含数据总量，允许将结果转成非持久化的对象（根据ResultTransformer转换）
     * @param page          分页条件
     * @param queryName     sql-query名称
     * @param params        参数集合
     * @param transformer   转换对象
     * @param <M>           新的对象类型
     * @return  返回查询的当前页的数据集合
     */
    @SuppressWarnings("unchecked")
    <M> Page<M> queryByNameWithoutTotal(Page<M> page, String queryName, Map<String, Object> params, ResultTransformer transformer) ;

    /**
     * 根据sql-query名称查询结果，包含数据总量，允许将结果转成非持久化的对象（根据ResultTransformer转换）
     * @param page          分页条件
     * @param queryName     sql-query名称
     * @param params        参数集合
     * @param transformer   转换对象
     * @param <M>           新的对象类型
     * @return  返回查询的当前页的数据集合，包含数据总量
     */
    <M> Page<M> queryByNameWithTotal(Page<M> page, String countQueryName, String queryName, Map<String, Object> params, ResultTransformer transformer);

    /**
     * 执行CUD语句
     * @param queryName sql-query的名称
     * @param params    参数集合
     * @return  返回执行结果
     */
    int executeQueryByName(String queryName, Map<String,Object> params);

/* ******************************************************************************
 *  sql-query   ----end----
 * ***************************************************************************** */

}
