/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 17119
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findByProjId", query = "SELECT p FROM Project p WHERE p.projId = :projId")
    , @NamedQuery(name = "Project.findByApplicantName", query = "SELECT p FROM Project p WHERE p.applicantName = :applicantName")
    , @NamedQuery(name = "Project.findByApplicantIDcard", query = "SELECT p FROM Project p WHERE p.applicantIDcard = :applicantIDcard")
    , @NamedQuery(name = "Project.findByApplicantMail", query = "SELECT p FROM Project p WHERE p.applicantMail = :applicantMail")
    , @NamedQuery(name = "Project.findByApplDate", query = "SELECT p FROM Project p WHERE p.applDate = :applDate")
    , @NamedQuery(name = "Project.findByProjCatelog", query = "SELECT p FROM Project p WHERE p.projCatelog = :projCatelog")
    , @NamedQuery(name = "Project.findByProjClass", query = "SELECT p FROM Project p WHERE p.projClass = :projClass")
    , @NamedQuery(name = "Project.findByProjName", query = "SELECT p FROM Project p WHERE p.projName = :projName")
    , @NamedQuery(name = "Project.findByProjProvince", query = "SELECT p FROM Project p WHERE p.projProvince = :projProvince")
    , @NamedQuery(name = "Project.findByProjCity", query = "SELECT p FROM Project p WHERE p.projCity = :projCity")
    , @NamedQuery(name = "Project.findByProjRegion", query = "SELECT p FROM Project p WHERE p.projRegion = :projRegion")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proj_id")
    private Integer projId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "applicant_name")
    private String applicantName;
    @Size(max = 19)
    @Column(name = "applicant_IDcard")
    private String applicantIDcard;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "applicant_mail")
    private String applicantMail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "appl_date")
    @Temporal(TemporalType.DATE)
    private Date applDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "proj_catelog")
    private String projCatelog;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "proj_class")
    private String projClass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "proj_name")
    private String projName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "proj_province")
    private String projProvince;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "proj_city")
    private String projCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "proj_region")
    private String projRegion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "proj_addr")
    private String projAddr;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "proj_depict")
    private String projDepict;
    @JoinTable(name = "guard_proj", joinColumns = {
        @JoinColumn(name = "proj_id", referencedColumnName = "proj_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_tel", referencedColumnName = "user_tel")})
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Collection<InvesteProj> investeProjCollection;
    @JoinColumn(name = "user_tel", referencedColumnName = "user_tel")
    @ManyToOne(optional = false)
    private User userTel;

    public Project() {
    }

    public Project(Integer projId) {
        this.projId = projId;
    }

    public Project(Integer projId, String applicantName, String applicantMail, Date applDate, String projCatelog, String projClass, String projName, String projProvince, String projCity, String projRegion, String projAddr, String projDepict) {
        this.projId = projId;
        this.applicantName = applicantName;
        this.applicantMail = applicantMail;
        this.applDate = applDate;
        this.projCatelog = projCatelog;
        this.projClass = projClass;
        this.projName = projName;
        this.projProvince = projProvince;
        this.projCity = projCity;
        this.projRegion = projRegion;
        this.projAddr = projAddr;
        this.projDepict = projDepict;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantIDcard() {
        return applicantIDcard;
    }

    public void setApplicantIDcard(String applicantIDcard) {
        this.applicantIDcard = applicantIDcard;
    }

    public String getApplicantMail() {
        return applicantMail;
    }

    public void setApplicantMail(String applicantMail) {
        this.applicantMail = applicantMail;
    }

    public Date getApplDate() {
        return applDate;
    }

    public void setApplDate(Date applDate) {
        this.applDate = applDate;
    }

    public String getProjCatelog() {
        return projCatelog;
    }

    public void setProjCatelog(String projCatelog) {
        this.projCatelog = projCatelog;
    }

    public String getProjClass() {
        return projClass;
    }

    public void setProjClass(String projClass) {
        this.projClass = projClass;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjProvince() {
        return projProvince;
    }

    public void setProjProvince(String projProvince) {
        this.projProvince = projProvince;
    }

    public String getProjCity() {
        return projCity;
    }

    public void setProjCity(String projCity) {
        this.projCity = projCity;
    }

    public String getProjRegion() {
        return projRegion;
    }

    public void setProjRegion(String projRegion) {
        this.projRegion = projRegion;
    }

    public String getProjAddr() {
        return projAddr;
    }

    public void setProjAddr(String projAddr) {
        this.projAddr = projAddr;
    }

    public String getProjDepict() {
        return projDepict;
    }

    public void setProjDepict(String projDepict) {
        this.projDepict = projDepict;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<InvesteProj> getInvesteProjCollection() {
        return investeProjCollection;
    }

    public void setInvesteProjCollection(Collection<InvesteProj> investeProjCollection) {
        this.investeProjCollection = investeProjCollection;
    }

    public User getUserTel() {
        return userTel;
    }

    public void setUserTel(User userTel) {
        this.userTel = userTel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projId != null ? projId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projId == null && other.projId != null) || (this.projId != null && !this.projId.equals(other.projId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Project[ projId=" + projId + " ]";
    }
    
}
