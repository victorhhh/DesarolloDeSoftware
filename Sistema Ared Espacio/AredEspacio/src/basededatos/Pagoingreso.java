/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "pagoingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagoingreso.findAll", query = "SELECT p FROM Pagoingreso p")
    , @NamedQuery(name = "Pagoingreso.findByIDIngreso", query = "SELECT p FROM Pagoingreso p WHERE p.iDIngreso = :iDIngreso")
    , @NamedQuery(name = "Pagoingreso.findByMonto", query = "SELECT p FROM Pagoingreso p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagoingreso.findByFechaPago", query = "SELECT p FROM Pagoingreso p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pagoingreso.findByIDAlumno", query = "SELECT p FROM Pagoingreso p WHERE p.iDAlumno = :iDAlumno")})
public class Pagoingreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDIngreso")
    private Integer iDIngreso;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Basic(optional = false)
    @Column(name = "IDAlumno")
    private int iDAlumno;
    @JoinColumn(name = "IDInscripcion", referencedColumnName = "IDInscripcion")
    @ManyToOne
    private Inscripcion iDInscripcion;
    @JoinColumn(name = "IDMensualidad", referencedColumnName = "IDMensualidad")
    @ManyToOne
    private Mensualidad iDMensualidad;
    @JoinColumn(name = "IDPromocion", referencedColumnName = "IDPromocion")
    @ManyToOne
    private Promocion iDPromocion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPago")
    private Collection<Alumno> alumnoCollection;

    public Pagoingreso() {
    }

    public Pagoingreso(Integer iDIngreso) {
        this.iDIngreso = iDIngreso;
    }

    public Pagoingreso(Integer iDIngreso, int monto, Date fechaPago, int iDAlumno) {
        this.iDIngreso = iDIngreso;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.iDAlumno = iDAlumno;
    }

    public Integer getIDIngreso() {
        return iDIngreso;
    }

    public void setIDIngreso(Integer iDIngreso) {
        this.iDIngreso = iDIngreso;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getIDAlumno() {
        return iDAlumno;
    }

    public void setIDAlumno(int iDAlumno) {
        this.iDAlumno = iDAlumno;
    }

    public Inscripcion getIDInscripcion() {
        return iDInscripcion;
    }

    public void setIDInscripcion(Inscripcion iDInscripcion) {
        this.iDInscripcion = iDInscripcion;
    }

    public Mensualidad getIDMensualidad() {
        return iDMensualidad;
    }

    public void setIDMensualidad(Mensualidad iDMensualidad) {
        this.iDMensualidad = iDMensualidad;
    }

    public Promocion getIDPromocion() {
        return iDPromocion;
    }

    public void setIDPromocion(Promocion iDPromocion) {
        this.iDPromocion = iDPromocion;
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
        hash += (iDIngreso != null ? iDIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagoingreso)) {
            return false;
        }
        Pagoingreso other = (Pagoingreso) object;
        if ((this.iDIngreso == null && other.iDIngreso != null) || (this.iDIngreso != null && !this.iDIngreso.equals(other.iDIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Pagoingreso[ iDIngreso=" + iDIngreso + " ]";
    }
    
}
