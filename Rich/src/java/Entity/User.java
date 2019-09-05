/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 17119
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserTel", query = "SELECT u FROM User u WHERE u.userTel = :userTel")
    , @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName")
    , @NamedQuery(name = "User.findByUserPsw", query = "SELECT u FROM User u WHERE u.userPsw = :userPsw")})
public class User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<GuardProj> guardProjCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "user_tel")
    private String userTel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_psw")
    private String userPsw;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<Project> projectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<InvesteProj> investeProjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTel")
    private Collection<Project> projectCollection1;

    public User() {
    }

    public User(String userTel) {
        this.userTel = userTel;
    }

    public User(String userTel, String userName, String userPsw) {
        this.userTel = userTel;
        this.userName = userName;
        this.userPsw = userPsw;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    @XmlTransient
    public Collection<InvesteProj> getInvesteProjCollection() {
        return investeProjCollection;
    }

    public void setInvesteProjCollection(Collection<InvesteProj> investeProjCollection) {
        this.investeProjCollection = investeProjCollection;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection1() {
        return projectCollection1;
    }

    public void setProjectCollection1(Collection<Project> projectCollection1) {
        this.projectCollection1 = projectCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userTel != null ? userTel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userTel == null && other.userTel != null) || (this.userTel != null && !this.userTel.equals(other.userTel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.User[ userTel=" + userTel + " ]";
    }

    @XmlTransient
    public Collection<GuardProj> getGuardProjCollection() {
        return guardProjCollection;
    }

    public void setGuardProjCollection(Collection<GuardProj> guardProjCollection) {
        this.guardProjCollection = guardProjCollection;
    }
    
}
