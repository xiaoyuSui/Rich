/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author 17119
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;

    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findAll(int[] range, Integer cata, Integer prov) {

        if ((cata == 0) && (prov == 0)) {
            
            String hql = "SELECT p FROM Project p WHERE p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            return q.getResultList();
        } else if (cata == 0 && prov != 0) {
            String hql = "SELECT p FROM Project p WHERE p.projProvince = :projProvince AND p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projProvince", prov);
            return q.getResultList();
        } else if (cata != 0 && prov == 0) {
            String hql = "SELECT p FROM Project p WHERE p.projClass = :projClass AND p.projCondition = 1";
            //String hql = "SELECT p FROM Project p WHERE p.applicantName = :applicantName AND p.projCondition = :condition";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projClass", cata);
            return q.getResultList();
        } else {
            String hql = "SELECT p FROM Project p WHERE p.projClass = :projClass AND p.projProvince = :projProvince AND p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projProvince", prov);
            q.setParameter("projClass", cata);
            return q.getResultList();
        }

    }


    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findRange(int[] range, Integer cata, Integer prov) {

        if ((cata == 0) && (prov == 0)) {
            String hql = "SELECT p FROM Project p WHERE p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } else if (cata == 0 && prov != 0) {
            String hql = "SELECT p FROM Project p WHERE p.projProvince = :projProvince AND p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projProvince", prov);
           
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } else if (cata != 0 && prov == 0) {
            String hql = "SELECT p FROM Project p WHERE p.projClass = :projClass AND p.projCondition = 1";
            //String hql = "SELECT p FROM Project p WHERE p.applicantName = :applicantName AND p.projCondition = :condition";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projClass", cata);
            //q.setParameter("applicantName", "aa");
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } else {
            String hql = "SELECT p FROM Project p WHERE p.projClass = :projClass AND p.projProvince = :projProvince AND p.projCondition = 1";
            javax.persistence.Query q = getEntityManager().createQuery(hql);
            q.setParameter("projProvince", prov);
            //q.setParameter("condition", 1);
            q.setParameter("projClass", cata);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        }

    }


    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findAll(int addtion) {//string
        String hql = "SELECT p FROM project p WHERE p.addtion = :addtion";
        
        javax.persistence.Query q = getEntityManager().createQuery(hql);
        q.setParameter("addtion", addtion);
       return q.getResultList();
    }
}
