package com.piedra.platease.service;

import java.io.Serializable;
import java.util.List;

/**
 * 基本的Service接口定义
 * @param <T>   实体类型
 * @param <PK>  实体对应的主键类型，默认为String
 * @since 2017-04-05 by webinglin
 */
public interface BaseService<T, PK extends Serializable> {

    /**
     * 通过id加载po实例
     * @param id    主键
     * @return 实体对象
     */
     T load(PK id);

    /**
     * 通过id加载po实例
     * @param id    主键
     * @return 实体对象
     */
     T get(PK id);

    /**
     * 根据ID数组获取实体对象集合.
     * @param ids   主键列表
     * @return 实体对象集合
     */
     List<T> get(PK[] ids);

    /**
     * 根据属性名和属性值获取实体对象.
     * @param propertyName  属性
     * @param value         属性值
     * @return 实体对象
     */
     T get(String propertyName, Object value);

    /**
     * 根据属性名和属性值获取实体对象集合.
     * @param propertyName  属性
     * @param value         属性值
     * @return 实体对象集合
     */
     List<T> getList(String propertyName, Object value);

    /**
     * 获取所有实体对象总数.
     * @return 实体对象总数
     */
     Integer getAllCount();

    /**
     * 保存实体对象.
     * @param entity    实体
     * @return ID   主键
     */
     PK save(T entity);

    /**
     * 保存或更新一个对象
     * @param entity    实体对象
     */
     void saveOrUpdate(T entity);

    /**
     * 更新实体对象.
     * @param entity    实体对象
     */
     void update(T entity);

    /**
     * 合并一个对象
     * @param entity    实体对象
     */
     void merge(T entity);

    /**
     * 删除实体对象
     * @param entity    实体对象
     */
     void delete(T entity);

    /**
     * 根据ID删除实体对象
     * @param id    要删除的实体对象的ID
     */
     void delete(PK id);

    /**
     * 根据ID数组删除实体对象
     * @param ids   要删除的实体对象的ID列表
     */
     void delete(PK[] ids);

    /**
     * 根据实体集合删除实体对象
     * @param list  要删除的实体对象
     */
     void delete(List<T> list);

    /**
     * 获取全部列表
     * @return  返回所有的实体列表
     */
     List<T> list();
}
