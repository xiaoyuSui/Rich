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
       int sum = getProjFacade().countByUserTel(current.getUserTel());        
        return sum;
    }
    
    //当前用户守护项目总数
    public int MyGuardSum(){
        int sum = getGuardFacade().countByUserTel(current.getUserTel());        
        return sum;
    }
    
    //当前用户资助项目总数
    public int MyInvestSum(){
        int sum = getInvestFacade().countByUserTel(current.getUserTel());        
        return sum;
    }
    
    //当前用户守护的项目
    public List<GuardProj> MyGurad(){
        List<GuardProj> myGuard = getGuardFacade().findByUserTel(current.getUserTel());        
        return myGuard;
    }
    
    //当前用户资助过的项目
    public List<InvesteProj> MyInvest(){
       List<InvesteProj> myInvest = getInvestFacade().findByUserTel(current.getUserTel());        
       return myInvest;
    }
    
    //当前用户申请过的项目
    public List<Project> MyProject(){
        List<Project> myProject = getProjFacade().findByUserTel(current.getUserTel());        
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
