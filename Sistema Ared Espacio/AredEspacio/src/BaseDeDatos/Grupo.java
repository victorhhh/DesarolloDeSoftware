/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import JPAControllers.GrupoJpaController;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yoresroy
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByIDAlumno", query = "SELECT g FROM Grupo g WHERE g.iDAlumnoG.iDAlumno = :IDAlumnoG")
    , @NamedQuery(name = "Grupo.findByIDClase", query = "SELECT g FROM Grupo g WHERE g.iDClaseG.iDClase = :IDClase")
    , @NamedQuery(name = "Grupo.findByIDGrupo", query = "SELECT g FROM Grupo g WHERE g.iDGrupo = :iDGrupo")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGrupo")
    private Integer iDGrupo;
    @JoinColumn(name = "IDAlumnoG", referencedColumnName = "IDAlumno")
    @ManyToOne
    private Alumno iDAlumnoG;
    @JoinColumn(name = "IDClaseG", referencedColumnName = "IDClase")
    @ManyToOne
    private Clase iDClaseG;

    public Grupo() {
    }
    
    public List<Grupo> buscarGruposAlumno(int IDAlumno) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Grupo> resultList = em.createNamedQuery("Grupo.findByIDAlumno").setParameter("IDAlumnoG",  IDAlumno).getResultList();
        return resultList;
    }
    
    public List<Clase> buscarClaseAlumno(int IDClase) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Clase> resultList = em.createNamedQuery("Grupo.findByIDClase").setParameter("IDClase",  IDClase).getResultList();
        return resultList;
    }
    
    public Grupo(Integer iDGrupo) {
        this.iDGrupo = iDGrupo;
    }

    public Integer getIDGrupo() {
        return iDGrupo;
    }

    public void setIDGrupo(Integer iDGrupo) {
        this.iDGrupo = iDGrupo;
    }

    public Alumno getIDAlumnoG() {
        return iDAlumnoG;
    }

    public void setIDAlumnoG(Alumno iDAlumnoG) {
        this.iDAlumnoG = iDAlumnoG;
    }

    public Clase getIDClaseG() {
        return iDClaseG;
    }

    public void setIDClaseG(Clase iDClaseG) {
        this.iDClaseG = iDClaseG;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGrupo != null ? iDGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.iDGrupo == null && other.iDGrupo != null) || (this.iDGrupo != null && !this.iDGrupo.equals(other.iDGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDeDatos.Grupo[ iDGrupo=" + iDGrupo + " ]";
    }
    
}
