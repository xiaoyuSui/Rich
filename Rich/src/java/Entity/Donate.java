/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author lenovo
 */
@Named("Donate")
@ManagedBean
public class Donate {
    double funding;
    String user_name;
    public Donate(){
        this.funding=0;
        this.user_name="defult";
    }
    public Donate(double funding,String user_name){
        this.funding=funding;
        this.user_name=user_name;
    }
    
    public void setFunding(double funding){
        this.funding=funding;
    }
    public double getFunding(){
        return this.funding;
    }
    public void setUserName(String user_name){
        this.user_name=user_name;
    }
    public String getUserName(){
        return this.user_name;
    }
    
    
    
}
