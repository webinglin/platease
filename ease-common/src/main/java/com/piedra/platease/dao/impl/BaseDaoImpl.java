package com.piedra.platease.dao.impl;

import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.Page;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 基础的Service业务类
 * 由Controller调用，如果对于负责的业务逻辑，在具体的Service实现类里面实现，然后再去调用Dao实现类的方法来达到事务的控制
 * @param <T>   实体对象
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Resource
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	@SuppressWarnings({ "unchecked" })
	public BaseDaoImpl() {
		this.entityClass = null;
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public Criteria getCriteria(){
		return getSession().createCriteria(entityClass);
	}
	
	public T load(String id) {
		return getSession().load(this.entityClass, id);
	}

	public T get(String id) {
		return getSession().get(this.entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(String[] ids) {
		return getSession().createQuery("from "+entityClass.getName()+" as model where model.id in (:ids)").setParameterList("ids", ids).list();
	}

    @SuppressWarnings("unchecked")
    public T get(String propertyName, Object value) {
        return (T) getSession().createQuery("from "+entityClass.getName()+" as model where model."+propertyName+" = ?").setParameter(0, value).uniqueResult();
    }

    public T get(DetachedCriteria criteria){
        List<T> list = list(criteria);
        return list.size()>0?list.get(0):null;
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(String propertyName, Object value) {
        return getSession().createQuery("from "+entityClass.getName()+" as model where model."+propertyName+" = ?").setParameter(0, value).list();
    }

	public Integer queryCount() {
		return Integer.parseInt(getCriteria().setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	public Integer queryCount(DetachedCriteria criteria) {
		return Integer.parseInt(criteria.setProjection(Projections.rowCount()).getExecutableCriteria(getSession()).uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	public String save(T entity) {
		return (String) getSession().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void merge(T entity){
		getSession().merge(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void delete(String id) {
		getSession().delete(load(id));
	}

	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	public void delete(List<T> list){
		if(list!=null&&!list.isEmpty()){
			list.forEach(T -> {
				getSession().delete(T);
			});
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		return getCriteria().list();
	}

	@SuppressWarnings("unchecked")
	public List<T> list(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).list();
	}

	public Object findObject(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> page(DetachedCriteria criteria,Integer pageSize,Integer pageIndex){
		return criteria.getExecutableCriteria(getSession()).setFirstResult((pageIndex-1)*pageSize).setMaxResults(pageSize).list();
	}

	public Page<T> getPage(DetachedCriteria criteria, Page<T> page) {
		page.setTotalCount(queryCount(criteria));
		criteria.setProjection(null);
		if(page.getOrderBy()!=null){
			if(page.getOrderType().equals(Page.OrderType.asc)){
				criteria.addOrder(Order.asc(page.getOrderBy()));
			}else{
				criteria.addOrder(Order.desc(page.getOrderBy()));
			}
		}
		page.setDatas(page(criteria, page.getPageSize(), page.getPageIndex()));
		return page;
	}


    /**
     * 根据Page的排序分页信息，封装SQL语句
     * @param sqlBuilder    sql语句
     * @param page  分页信息
     * @param bm    参数
     * @return  返回添加了分页信息的sql语句
     */
    @SuppressWarnings("unchecked")
    public NativeQuery createNativeQuery(StringBuilder sqlBuilder, Page page, BeanMap bm) {
        // 封装分页信息
        if(page!=null){
            String orderBy = page.getOrderBy();
            if(StringUtils.isNotBlank(orderBy)) {
                sqlBuilder.append(" ORDER BY ").append(orderBy).append(page.getOrderType()==Page.OrderType.asc ? " ASC " : " DESC ");
            }
            int pageSize = page.getPageSize(), pageIndex = page.getPageIndex();
            sqlBuilder.append(" LIMIT ").append((pageIndex-1)*pageSize).append(" , ").append(pageSize);
        }

        NativeQuery query = getSession().createNativeQuery(sqlBuilder.toString());
        if(bm==null){
            return query;
        }
        Set<String> keys = bm.keySet();
        if(keys.size()==0){
            return query;
        }
        keys.forEach(k -> query.setParameter(k, bm.get(k)));
        return query;
    }

    /**
     * 为更新的SQL语句封装参数
     * @param sqlBuilder    更新的SQL语句
     * @param bm    参数
     * @return  返回Query对象
     */
    public NativeQuery createNativeQueryForUpdate(StringBuilder sqlBuilder, BeanMap bm){
        return createNativeQuery(sqlBuilder, null, bm);
    }
}
