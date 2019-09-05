/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 17119
 */
@Embeddable
public class GuardProjPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "proj_id")
    private int projId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "user_tel")
    private String userTel;

    public GuardProjPK() {
    }

    public GuardProjPK(int projId, String userTel) {
        this.projId = projId;
        this.userTel = userTel;
    }

    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) projId;
        hash += (userTel != null ? userTel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GuardProjPK)) {
            return false;
        }
        GuardProjPK other = (GuardProjPK) object;
        if (this.projId != other.projId) {
            return false;
        }
        if ((this.userTel == null && other.userTel != null) || (this.userTel != null && !this.userTel.equals(other.userTel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GuardProjPK[ projId=" + projId + ", userTel=" + userTel + " ]";
    }
    
}
