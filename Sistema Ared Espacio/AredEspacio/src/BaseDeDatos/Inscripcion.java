/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yoresroy
 */
@Entity
@Table(name = "inscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll", query = "SELECT i FROM Inscripcion i")
    , @NamedQuery(name = "Inscripcion.findByIDInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.iDInscripcion = :iDInscripcion")
    , @NamedQuery(name = "Inscripcion.findByMonto", query = "SELECT i FROM Inscripcion i WHERE i.monto = :monto")
    , @NamedQuery(name = "Inscripcion.findByFechaInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.fechaInscripcion = :fechaInscripcion")})
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDInscripcion")
    private Integer iDInscripcion;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fechaInscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @OneToMany(mappedBy = "iDInscripcionA")
    private Collection<Alumno> alumnoCollection;

    public Inscripcion() {
    }

    public Inscripcion(Integer iDInscripcion) {
        this.iDInscripcion = iDInscripcion;
    }

    public Inscripcion(Integer iDInscripcion, int monto, Date fechaInscripcion) {
        this.iDInscripcion = iDInscripcion;
        this.monto = monto;
        this.fechaInscripcion = fechaInscripcion;
    }

    public List<Inscripcion> buscarInscripcionPorID(int IDInscripcion) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Inscripcion> resultList = em.createNamedQuery("Inscripcion.findByIDInscripcion").setParameter("nombre",  + IDInscripcion ).getResultList();
        return resultList;
    }
    public Integer getIDInscripcion() {
        return iDInscripcion;
    }

    public void setIDInscripcion(Integer iDInscripcion) {
        this.iDInscripcion = iDInscripcion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDInscripcion != null ? iDInscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripcion)) {
            return false;
        }
        Inscripcion other = (Inscripcion) object;
        if ((this.iDInscripcion == null && other.iDInscripcion != null) || (this.iDInscripcion != null && !this.iDInscripcion.equals(other.iDInscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDeDatos.Inscripcion[ iDInscripcion=" + iDInscripcion + " ]";
    }
    
}
