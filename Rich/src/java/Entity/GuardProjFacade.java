/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
public class GuardProjFacade extends AbstractFacade<GuardProj> {

    @PersistenceContext(unitName = "RichPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GuardProjFacade() {
        super(GuardProj.class);
    }
    
    public List<GuardProj> findByUserTel(String tel){
        Query query = em.createNamedQuery("GuardProj.findByUserTel").setParameter("userTel", tel);
        return query.getResultList();
    }
    
    public int countByUserTel(String tel){
        Query query = em.createNamedQuery("GuardProj.countByUserTel").setParameter("userTel", tel);
        return ((Long)query.getSingleResult()).intValue();
    }
    
     //读入用户，项目，取得一个守护实体，（如果这个用户和项目不存在守护关系那么返回未初始化的实体）
    public GuardProj getGuard(User user,Project proj){
            String hql="SELECT g FROM GuardProj g where (g.guardProjPK.userTel = :userTel and g.guardProjPK.projId = :projId)";
            List<GuardProj> guard = em.createQuery(hql)
                    .setParameter("userTel",user.getUserTel())
                    .setParameter("projId", proj.getProjId())
                    .getResultList();
            if(!guard.isEmpty())
                return guard.get(0);
            else
                return new GuardProj(0,"defult");
    }
    //写数据库，增加守护
    public void guardProj(User user,Project proj){
            GuardProj guard=new GuardProj();
            guard.setProject(proj);
            guard.setUser(user);
            GuardProjPK guardpk=new GuardProjPK();
            guardpk.setProjId(proj.getProjId());
            guardpk.setUserTel(user.getUserTel());
            guard.setGuardProjPK(guardpk);
            guard.setAddtion(0);
           /* String hql="INSERT INTO GuardProj g (g.guardProjPK.projId, g.guardProjPK.userTel) VALUES (:userTel,:projId);";
           em.createQuery(hql)
                    .setParameter("userTel",user.getUserTel())
                    .setParameter("projId", proj.getProjId()).executeUpdate();*/
           em.persist(guard);
                 
    }
    //删除行，取消守护
    public void deleteGuard(User user,Project proj){ 
        String hql="delete from GuardProj g where (g.guardProjPK.userTel = :userTel and g.guardProjPK.projId = :projId)";
           em.createQuery(hql)
                    .setParameter("userTel",user.getUserTel())
                    .setParameter("projId", proj.getProjId()).executeUpdate();
        
    }
    
}
