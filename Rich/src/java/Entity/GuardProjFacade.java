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
    
}
