/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 17119
 */
@Entity
@Table(name = "investe_proj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvesteProj.findAll", query = "SELECT i FROM InvesteProj i")
    , @NamedQuery(name = "InvesteProj.findByProjId", query = "SELECT i FROM InvesteProj i WHERE i.investeProjPK.projId = :projId")
    , @NamedQuery(name = "InvesteProj.findByUserTel", query = "SELECT i FROM InvesteProj i WHERE i.investeProjPK.userTel = :userTel")
    , @NamedQuery(name = "InvesteProj.findByFunding", query = "SELECT i FROM InvesteProj i WHERE i.funding = :funding")
    , @NamedQuery(name = "InvesteProj.CountByUserTel", query = "SELECT COUNT(i.investeProjPK.projId) FROM InvesteProj i WHERE i.investeProjPK.userTel = :userTel")})
public class InvesteProj implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvesteProjPK investeProjPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "funding")
    private double funding;
    @JoinColumn(name = "proj_id", referencedColumnName = "proj_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "user_tel", referencedColumnName = "user_tel", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public InvesteProj() {
    }

    public InvesteProj(InvesteProjPK investeProjPK) {
        this.investeProjPK = investeProjPK;
    }

    public InvesteProj(InvesteProjPK investeProjPK, double funding) {
        this.investeProjPK = investeProjPK;
        this.funding = funding;
    }

    public InvesteProj(int projId, String userTel) {
        this.investeProjPK = new InvesteProjPK(projId, userTel);
    }

    public InvesteProjPK getInvesteProjPK() {
        return investeProjPK;
    }

    public void setInvesteProjPK(InvesteProjPK investeProjPK) {
        this.investeProjPK = investeProjPK;
    }

    public double getFunding() {
        return funding;
    }

    public void setFunding(double funding) {
        this.funding = funding;
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
        hash += (investeProjPK != null ? investeProjPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvesteProj)) {
            return false;
        }
        InvesteProj other = (InvesteProj) object;
        if ((this.investeProjPK == null && other.investeProjPK != null) || (this.investeProjPK != null && !this.investeProjPK.equals(other.investeProjPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.InvesteProj[ investeProjPK=" + investeProjPK + " ]";
    }
    
}
