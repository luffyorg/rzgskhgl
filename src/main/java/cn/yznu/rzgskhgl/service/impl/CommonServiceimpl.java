package cn.yznu.rzgskhgl.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.dao.IBaseDao;
import cn.yznu.rzgskhgl.service.ICommonService;

@Service("commonService")
@Transactional
public class CommonServiceimpl implements ICommonService {

	@Autowired
	IBaseDao dao;

	@Override
	public <T> Serializable save(T entity) {
		
		return dao.save(entity);
	}

	@Override
	public <T> void batchSave(List<T> entitys) {
		dao.batchSave(entitys);

	}

	@Override
	public <T> void batchUpdate(List<T> entitys) {
		dao.batchUpdate(entitys);

	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		dao.saveOrUpdate(entity);

	}

	@Override
	public <T> T get(Class<T> entityName, Serializable id) {
		return dao.get(entityName, id);
	}
	
	@Override
	public <T> T load(Class<T> entityName, Serializable id) {
		return dao.load(entityName, id);
	}

	@Override
	public <T> void delete(T entitiy) {
		dao.delete(entitiy);
	}

	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		return dao.findUniqueByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		return dao.findByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> List<T> loadAll(Class<T> entityClass) {
		return dao.loadAll(entityClass);
	}

	@Override
	public <T> T getEntity(Class entityName, Serializable id) {
		return dao.getEntity(entityName, id);
	}

	@Override
	public <T> void deleteEntityById(Class<T> entityName, Serializable id) {
		dao.deleteEntityById(entityName, id);
	}

	@Override
	public <T> void deleteAllEntitie(Collection<T> entitys) {
		dao.deleteAllEntitie(entitys);
	}

	@Override
	public <T> void updateEntitie(T pojo) {
		dao.updateEntitie(pojo);

	}

	@Override
	public <T> void updateEntityById(Class entityName, Serializable id) {
		dao.updateEntityById(entityName, id);

	}

	@Override
	public <T> int getCount(Class<T> clazz) {
		return dao.getCount(clazz);
	}

	@Override
	public <T> List<T> findHql(String hql, Object... param) {
		return dao.findHql(hql, param);
	}

	@Override
	public <T> List<T> findHql(Class<T> class1, String hql) {
		return dao.findHql(class1, hql);
	}

	@Override
	public Integer executeHql(String hql) {
		return dao.executeHql(hql);
	}

	@Override
	public Integer executeHql(String hql, Object... param) {
		return dao.executeHql(hql, param);
	}

	@Override
	public void queryHql(String hqlString, Object... values) {
		
		dao.queryHql(hqlString, values);
		
	}

	@Override
	public void querySql(String sqlString, Object... values) {
		
		dao.querySql(sqlString, values);
	}

	@Override
	public <T> T getSingleByHQL(String hqlString, Object... values) {
		
		return dao.getSingleByHQL(hqlString, values);
	}

	@Override
	public <T> T getSingleBySQL(Class clazz, String sqlString, Object... values) {
		return dao.getSingleBySQL(clazz, sqlString, values);
	}

	@Override
	public <T> List<T> getListByHQL(String hqlString, Object... values) {
		return dao.getListByHQL(hqlString, values);
	}

	@Override
	public <T> List<T> getListBySQL(Class clazz, String sqlString, Object... values) {
		return dao.getListBySQL(clazz, sqlString, values);
	}

}
