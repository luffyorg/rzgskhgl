package cn.yznu.rzgskhgl.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.yznu.rzgskhgl.dao.IBaseDao;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseDaoImpl implements IBaseDao {
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public <T> Serializable save(T entity) {
		try {
			Serializable id = getSession().save(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.info("存储实体成功" + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("存储实体失败", e);
			throw e;
		}
	}

	public <T> void batchSave(List<T> entitys) {
		logger.info("batchsave");
		for (int i = 0; i < entitys.size(); i++) {
			getSession().saveOrUpdate(entitys.get(i));
			if (i % 20 == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
		getSession().flush();
		getSession().clear();

	}

	public <T> void batchUpdate(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
		getSession().flush();
		getSession().clear();
	}

	public <T> void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();
		logger.info("保存或者更新实体成功======" + entity);

	}

	public <T> T get(Class<T> entityName, Serializable id) {
		logger.info("getClass");
		return (T) getSession().get(entityName, id);
	}

	public <T> T load(Class<T> entityName, Serializable id) {
		logger.info("getClass");
		return (T) getSession().load(entityName, id);
	}

	public <T> void delete(T entitiy) {
		getSession().delete(entitiy);
		getSession().flush();
		logger.info("删除实体====" + entitiy);
	}

	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		logger.info("执行了findUniqueByProperty");
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		logger.info("执行了findByProperty");
		return (List<T>) createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	public <T> List<T> loadAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		logger.info("执行了loadAll");
		return criteria.list();
	}

	public <T> T getEntity(Class entityName, Serializable id) {

		T t = (T) getSession().get(entityName, id);
		if (t != null) {
			getSession().flush();
			logger.info("执行了 getEntity");
		}
		return t;
	}

	public <T> void deleteEntityById(Class<T> entityName, Serializable id) {
		delete(get(entityName, id));
		getSession().flush();

	}

	public <T> void deleteAllEntitie(Collection<T> entitys) {
		for (Object entity : entitys) {
			getSession().delete(entity);
			getSession().flush();
		}

	}

	public <T> void updateEntitie(T pojo) {
		getSession().update(pojo);
		getSession().flush();

	}

	public <T> void updateEntityById(Class entityName, Serializable id) {
		updateEntitie(get(entityName, id));

	}

	public <T> int getCount(Class<T> clazz) {
		int count = DataAccessUtils
				.intResult(getSession().createQuery("select count(*) from " + clazz.getName()).list());
		return count;
	}

	public <T> List<T> findHql(String hql, Object... param) {
		Query q = getSession().createQuery(hql);

		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public <T> List<T> findHql(Class<T> class1, String hql) {
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	public Integer executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, Object... param) {
		System.out.println(hql);
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < param.length; i++) {
			q.setParameter(i, param[i]);
		}
		return q.executeUpdate();
	}

	@Override
	public void queryHql(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();

	}

	@Override
	public void querySql(String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();

	}

	@Override
	public <T> T getSingleByHQL(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	@Override
	public <T> T getSingleBySQL(Class clazz, String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString).addEntity(clazz);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	@Override
	public <T> List<T> getListByHQL(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@Override
	public <T> List<T> getListBySQL(Class clazz, String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString).addEntity(clazz);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@Override
	public <T> List<T> getListBySQL(String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}
	@Override
	public <T> List<T> queryForPage(String hql, int offset, int length) {
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(offset);
		q.setMaxResults(length);
		return q.list();
	}

}
