/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 17119
 */
@Stateless
public class InvesteProjFacade extends AbstractFacade<InvesteProj> {

    @PersistenceContext(unitName = "RichPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvesteProjFacade() {
        super(InvesteProj.class);
    }
    
    public InvesteProj findByUserTelAndProjId(String tel,int id){
        Query query = em.createNamedQuery("InvesteProj.findByUserTelAndProjId").setParameter("userTel", tel).setParameter("projId", id);
        return (InvesteProj) query.getSingleResult();
    }
    
    public List<InvesteProj> findByUserTel(String tel){
        Query query = em.createNamedQuery("InvesteProj.findByUserTel").setParameter("userTel", tel);
        return query.getResultList();
    }
    
    public int countByUserTel(String tel){
        Query query = em.createNamedQuery("InvesteProj.countByUserTel").setParameter("userTel", tel);
        return ((Long)query.getSingleResult()).intValue();
    }
    
    public List<Donate> getDonate(int projId){
        try{
            String hql="select i.funding,u.userName from InvesteProj i left join User u on (i.investeProjPK.userTel=u.userTel)  where (i.investeProjPK.projId= :id) order by i.funding desc";
            List<Object[]> rows=em.createQuery(hql).setParameter("id",projId).setMaxResults(5).getResultList();
            List<Donate> donate=new ArrayList<>(rows.size());
            
            for (Object[] row:rows){
                donate.add(new Donate((double)row[0],(String)row[1]));
            }
            return donate;

        }catch(NoResultException e){
            return null;
        }
    }
    
    public void investeProj(User user,Project proj,int funding){
         if(!this.isInveste(user,proj)){
            InvesteProj investe=new InvesteProj();
            investe.setProject(proj);
            investe.setUser(user);
            InvesteProjPK investpk=new InvesteProjPK();
            investpk.setProjId(proj.getProjId());
            investpk.setUserTel(user.getUserTel());
            investe.setInvesteProjPK(investpk);
            investe.setFunding(funding);
            em.persist(investe);
         }
         else{
             String hql="update InvesteProj i set i.funding=i.funding+:funding where i.investeProjPK.projId= :id and i.investeProjPK.userTel = :tel";
             //update investe_proj i set i.funding=i.funding+20 where proj_id=1 and user_tel='1';
             em.createQuery(hql)
                     .setParameter("id", proj.getProjId())
                     .setParameter("tel", user.getUserTel())
                     .setParameter("funding", funding)
                     .executeUpdate();
         }
     }
     
     public boolean isInveste(User user,Project proj){
         
        String hql="select i from InvesteProj i  where (i.investeProjPK.projId= :id and i.investeProjPK.userTel = :tel)";
        List<InvesteProj> invest=em.createQuery(hql)
                .setParameter("id",proj.getProjId())
                .setParameter("tel", user.getUserTel())
                .getResultList();
        if(invest.isEmpty())
            return false;
        else
            return true;

     }
    
}
