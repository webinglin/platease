package com.piedra.platease.service.impl;

import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.service.BaseService;

import java.io.Serializable;
import java.util.List;

/**
 * 基础业务出现类
 * @author webinglin
 * @since 2017-04-05
 */
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	private BaseDao<T, PK> baseDao;

    protected BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}
	protected void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public T load(PK id) {
		return baseDao.load(id);
	}

	public T get(PK id) {
		return baseDao.get(id);
	}

	public List<T> get(PK[] ids) {
		return baseDao.get(ids);
	}

	public T get(String propertyName, Object value) {
		return baseDao.get(propertyName,value);
	}

	public List<T> getList(String propertyName, Object value) {
		return baseDao.getList(propertyName, value);
	}

	public Integer getAllCount() {
		return baseDao.queryCount();
	}

	public PK save(T entity) {
		return baseDao.save(entity);
	}

	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	public void merge(T entity) {
		baseDao.merge(entity);
	}

	public void delete(T entity) {
		baseDao.delete(entity);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	public void delete(List<T> list) {
		baseDao.delete(list);
	}

	public List<T> list() {
		return baseDao.list();
	}

}
