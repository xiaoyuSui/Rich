/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 17119
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {
        
    
    @PersistenceContext(unitName = "RichPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectFacade() {
        super(Project.class);  
    }
    
    public List<Project> findByUserTel(String tel){
        Query query = em.createNamedQuery("Project.findByUserTel").setParameter("userTel", tel);
        return query.getResultList();
    }
    
    public List<Project> findByProjID(int projID){
        Query query = em.createNamedQuery("Project.findByprojID").setParameter("proj_id", projID);
        return query.getResultList();
    }
    
    public int countByUserTel(String tel){
        Query query = em.createNamedQuery("Project.countByUserTel").setParameter("userTel", tel);
        return ((Long)query.getSingleResult()).intValue();
    }
    
    public Project findByProjId(int id){
        Query query = em.createNamedQuery("Project.findByProjId").setParameter("projId", id);
        return (Project) query.getSingleResult();
    }
    
    public List<Project> findByProjCondition(int condition){
        Query query=em.createNamedQuery("Project.findByProjCondition").setParameter("projCondition", condition);
        return query.getResultList();
    }
    
     public void projectChange(Project project,int condition,int pjID){//应该是string
         String hql="UPDATE Project p SET p.projCondition= :condition WHERE p.projId= :pjID";
         em.createQuery(hql)
                 .setParameter("pjID", pjID)
                 .setParameter("condition", condition)
                 .executeUpdate();
         System.out.println("执行sql语句");
     }

}
