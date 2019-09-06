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
    
    private List<Project> projectList;
    private int kind=0;
    
    
    public AllController(){
        current = new User("13671021552","wqm","password"); 
    }
    
    private ProjectFacade getProjFacade() {
        return projFacade;
    }
    
    public List<Project> getProjectList(){
        projectList = this.getProjFacade().findAll();
        System.out.print("我在projectList里面！！！！！！！！！！！！！");
        return projectList;
    }
    
    public void setProjectList(){
        
    }
    
    public int getKind(){
        return kind;
    }
    
    public void setKind(int kind_){
        kind = kind_;
    }
       
}
