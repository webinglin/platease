package com.piedra.platease.dao.impl;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.Page;
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
import java.math.BigInteger;
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
			Type[] parameterizedTypeArr = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedTypeArr[0];
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
			list.forEach(T -> getSession().delete(T));
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
		page.setRecords(queryCount(criteria));
		criteria.setProjection(null);
		if(page.getSidx()!=null){
			if(page.getSord().equals(Constants.Order.ASC)){
				criteria.addOrder(Order.asc(page.getSidx()));
			}else{
				criteria.addOrder(Order.desc(page.getSidx()));
			}
		}
		page.setDatas(page(criteria, page.getRows(), page.getPage()));
		return page;
	}

/* ******************************************************************************
 *  sql-query   ----begin----
 * ***************************************************************************** */

    /**
     * 根据queryName获取query，并解析 velocity 模板语法为nativeSQL
     * @param queryName 查询语句的名称
     * @param params    参数
     * @return  返回Query对象
     */
    private NativeQuery getNamedQuery(String queryName, Map<String, Object> params) {
        Query query = getSession().getNamedQuery(queryName);
        NativeQuery rsQuery = null;
        try {
            Velocity.init();
            VelocityContext context = new VelocityContext();
            params.forEach((k,v) -> context.put(k, params.get(k)));
            StringWriter sqlWriter = new StringWriter();
            Velocity.evaluate(context, sqlWriter, null, query.getQueryString());
            rsQuery = getSession().createNativeQuery(sqlWriter.toString());
        } catch (Exception e) {
            logger.error("查询命名SQL【"+queryName+"】出错！", e);
        }
        return rsQuery;
    }

    /**
     *  设置参数到NativeSql中
     * @param query     Query对象
     * @param params    参数集合
     * @return  返回Query对象
     */
    @SuppressWarnings("rawtypes")
    private NativeQuery setQueryNameParameters(NativeQuery query, Map<String, Object> params){
        Set<String> nameParams = query.getParameterMetadata().getNamedParameterNames();
        nameParams.forEach(k -> {
            Object obj = params.get(k);
            if(obj instanceof Collection){
                query.setParameterList(k, (Collection) obj);
            }else if(obj.getClass().isArray()){
                query.setParameterList(k, (Object[])obj);
            }else{
                query.setParameter(k, obj);
            }
        });
        return query;
    }

    /**
     * 根据queryName查询数据 sql语句查询到的数据总量
     * @param countQueryName    查数据量的sql-query名称
     * @param params    参数集合
     * @return  返回数据总量
     */
    public Integer queryCntByNameQuery(String countQueryName, Map<String, Object> params) {
        NativeQuery countQuery = getNamedQuery(countQueryName, params);
        countQuery = setQueryNameParameters(countQuery, params);
        List countList = countQuery.list();
        if(CollectionUtils.isEmpty(countList)){
            return 0;
        }
        BigInteger queryCount = ((BigInteger) countList.get(0));
        if(queryCount == null){
            return 0;
        }
        return queryCount.intValue();
    }

    /**
     * 分页查询， 不查询数据总量
     * @param page          分页对象
     * @param queryName     sql-query的名称
     * @param params        参数集合
     * @return  返回分页结果（只包含当前查询页的数据集合）
     */
    @SuppressWarnings({ "unchecked" })
    public<M> Page<M> queryByNameWithoutTotal(Page<M> page, String queryName, Map<String, Object> params) {
        if(StringUtils.isNotBlank(page.getSidx())){
            params.put("sidx", page.getSidx());
            params.put("sord", page.getSord());
        }
        NativeQuery query = getNamedQuery(queryName, params);
        setQueryNameParameters(query, params);
        query.setFirstResult((page.getPage()-1)*page.getRows());
        query.setMaxResults(page.getRows());
        if(page.getEntityClass()==null){
            logger.error("[[[Page实体的泛型类型没有设定，需要通过 page.setEntityClass 来指定，这样Page返回的实体可以有明确的返回类型。]]]");
        }
        if(page.getEntityClass()!=null) {
            query.addEntity(page.getEntityClass());
        }
        List<M> resultList = query.list();
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        page.setDatas(resultList);
        return page;
    }

    /**
     * 分页查询， 同时查出数据总量
     * @param page          分页对象
     * @param queryName     sql-query的名称
     * @param params        参数集合
     * @return  返回分页结果（包含数据总量和当前查询页的数据集合）
     */
    public<M> Page<M> queryByNameWithTotal(Page<M> page, String countQueryName, String queryName, Map<String, Object> params) {
        Integer totalCount = queryCntByNameQuery(countQueryName, params);
        page.setRecords(totalCount);
        if(totalCount==0){
            page.setDatas(new ArrayList<>());
            return page;
        }
        page = queryByNameWithoutTotal(page, queryName, params);
        return page;
    }

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
    public<M> Page<M> queryByNameWithoutTotal(Page<M> page, String queryName, Map<String, Object> params, ResultTransformer transformer) {
        if(StringUtils.isNotBlank(page.getSidx())){
            params.put("sidx", page.getSidx());
            params.put("sord", page.getSord());
        }
        NativeQuery query = getNamedQuery(queryName, params);
        setQueryNameParameters(query, params);
        query.setFirstResult((page.getPage()-1)*page.getRows());
        query.setMaxResults(page.getRows());
        if(transformer!=null){
            query.setResultTransformer(transformer);
        }

        List<M> resultList = query.list();
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        page.setDatas(resultList);
        return page;
    }

    /**
     * 根据sql-query名称查询结果，包含数据总量，允许将结果转成非持久化的对象（根据ResultTransformer转换）
     * @param page          分页条件
     * @param queryName     sql-query名称
     * @param params        参数集合
     * @param transformer   转换对象
     * @param <M>           新的对象类型
     * @return  返回查询的当前页的数据集合，包含数据总量
     */
    public <M> Page<M> queryByNameWithTotal(Page<M> page, String countQueryName, String queryName, Map<String, Object> params, ResultTransformer transformer) {
        Integer totalCnt = queryCntByNameQuery(countQueryName, params);
        page.setRecords(totalCnt);
        if(totalCnt==0){
            page.setDatas(new ArrayList<>());
            return page;
        }

        page = queryByNameWithoutTotal(page, queryName, params, transformer);
        return page;
    }

    /**
     * 执行CUD语句
     * @param queryName sql-query的名称
     * @param params    参数集合
     * @return  返回执行结果
     */
    public int executeQueryByName(String queryName, Map<String,Object> params){
        NativeQuery query = getNamedQuery(queryName, params);
        query = setQueryNameParameters(query, params);
        return query.executeUpdate();
    }

/* ******************************************************************************
 *  sql-query   ----end----
 * ***************************************************************************** */

}
