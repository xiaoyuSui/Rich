/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 17119
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "RichPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User checkLogin(String phonenumber,String password){
        User check;
        try{
            check = (User)em.createQuery("SELECT u FROM User u WHERE u.userTel = :phonenumber AND u.userPsw=:password")
                .setParameter("phonenumber", phonenumber)
                .setParameter("password",password)
                .getSingleResult();
            return check;
        }catch (NoResultException e){
            return null;
        }
    }
    
     public User checklogup(String phonenumber){  //注册的时候判断是否被注册过，登录的时候判断是否已注册
        User check;
        try{
            check = (User)em.createQuery("SELECT u FROM User u WHERE u.userTel = :phonenumber")
                .setParameter("phonenumber", phonenumber)
                .getSingleResult();
            return check;
        }catch (NoResultException e){
            return null;
        }
    }

    
}
