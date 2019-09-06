/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Query;

/**
 *
 * @author 17119
 */

@Named("AllController")
@SessionScoped
public class AllController implements Serializable{
    @EJB
    private Entity.ProjectFacade projFacade;    
    @EJB
    private Entity.InvesteProjFacade investFacade;    
    @EJB
    private Entity.UserFacade userFacade;   
    @EJB
    private Entity.GuardProjFacade guardFacade;
    
    //用于存放当前登录的用户数据
    private User current = null;
 
    private int kind=0;
    
    public AllController(){
        current = new User("13671021552","wqm","password"); 
    }
    
    //当前用户申请项目总数
    public int MyProjectSum(){
        int sum;
        Query query = getGuardFacade().getEntityManager().createNamedQuery("Project.CountByUserTel").setParameter("userTel", current.getUserTel());
        sum = query.
        return sum;
    }
    
    //当前用户守护项目总数
    public int MyGuardSum(){
        int sum;
        return sum;
    }
    
    //当前用户资助项目总数
    public int MyInvestProject(){
        int sum;
        return sum;
    }
    
    //当前用户守护的项目
    public List<GuardProj> MyGurad(){
        List<GuardProj> myGuard;
        Query query = getGuardFacade().getEntityManager().createNamedQuery("GuardProj.findByUserTel").setParameter("userTel", current.getUserTel());
        myGuard = query.getResultList();
        return myGuard;
    }
    
    //当前用户资助过的项目
    public List<InvesteProj> MyInvest(){
        List<InvesteProj> myInvest;
        Query query = getInvestFacade().getEntityManager().createNamedQuery("InvesteProj.findByUserTel").setParameter("userTel", current.getUserTel());
        myInvest =query.getResultList();
        return myInvest;
    }
    
    //当前用户申请过的项目
    public List<Project> MyProject(){
        List<Project> myProject;
        Query query = getProjFacade().getEntityManager().createNamedQuery("Project.findByUserTel").setParameter("userTel", current.getUserTel());
        myProject = query.getResultList();
        return myProject;
    }
    
    
    public int getKind(){
        return kind;
    }
    
    public void setKind(int kind_){
        kind = kind_;
    }
    
    private ProjectFacade getProjFacade() {
        return projFacade;
    }
    
    private InvesteProjFacade getInvestFacade() {
        return investFacade;
    }
    
    private UserFacade getUserFacade() {
        return userFacade;
    }
    
    private GuardProjFacade getGuardFacade() {
        return guardFacade;
    }
        
}
