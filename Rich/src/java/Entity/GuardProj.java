/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 17119
 */
@Entity
@Table(name = "guard_proj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GuardProj.findAll", query = "SELECT g FROM GuardProj g")
    , @NamedQuery(name = "GuardProj.findByProjId", query = "SELECT g FROM GuardProj g WHERE g.guardProjPK.projId = :projId")
    , @NamedQuery(name = "GuardProj.findByUserTel", query = "SELECT g FROM GuardProj g WHERE g.guardProjPK.userTel = :userTel")
    , @NamedQuery(name = "GuardProj.findByAddtion", query = "SELECT g FROM GuardProj g WHERE g.addtion = :addtion")
    , @NamedQuery(name = "GuardProj.findSumByUserTel", query = "SELECT g FROM GuardProj g WHERE g.guardProjPK.userTel = :userTel")
    //Add
    , @NamedQuery(name = "GuardProj.countByUserTel", query = "SELECT COUNT(g.guardProjPK.projId) FROM GuardProj g WHERE g.guardProjPK.userTel = :userTel")})
public class GuardProj implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GuardProjPK guardProjPK;
    @Column(name = "addtion")
    private Integer addtion;
    @JoinColumn(name = "proj_id", referencedColumnName = "proj_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "user_tel", referencedColumnName = "user_tel", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public GuardProj() {
    }

    public GuardProj(GuardProjPK guardProjPK) {
        this.guardProjPK = guardProjPK;
    }

    public GuardProj(int projId, String userTel) {
        this.guardProjPK = new GuardProjPK(projId, userTel);
    }

    public GuardProjPK getGuardProjPK() {
        return guardProjPK;
    }

    public void setGuardProjPK(GuardProjPK guardProjPK) {
        this.guardProjPK = guardProjPK;
    }

    public Integer getAddtion() {
        return addtion;
    }

    public void setAddtion(Integer addtion) {
        this.addtion = addtion;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (guardProjPK != null ? guardProjPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GuardProj)) {
            return false;
        }
        GuardProj other = (GuardProj) object;
        if ((this.guardProjPK == null && other.guardProjPK != null) || (this.guardProjPK != null && !this.guardProjPK.equals(other.guardProjPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GuardProj[ guardProjPK=" + guardProjPK + " ]";
    }
    
}
