/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yoresroy
 */
@Entity
@Table(name = "pagoingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagoingreso.findAll", query = "SELECT p FROM Pagoingreso p")
    , @NamedQuery(name = "Pagoingreso.findByIDIngreso", query = "SELECT p FROM Pagoingreso p WHERE p.iDIngreso = :iDIngreso")
    , @NamedQuery(name = "Pagoingreso.findByMonto", query = "SELECT p FROM Pagoingreso p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagoingreso.findByFechaPago", query = "SELECT p FROM Pagoingreso p WHERE p.fechaPago = :fechaPago")})
public class Pagoingreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDIngreso")
    private Integer iDIngreso;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @JoinColumn(name = "IDAlumnoPI", referencedColumnName = "IDAlumno")
    @ManyToOne
    private Alumno iDAlumnoPI;
    @JoinColumn(name = "IDMensualidad", referencedColumnName = "IDMensualidad")
    @ManyToOne
    private Mensualidad iDMensualidad;
    @JoinColumn(name = "IDPromocion", referencedColumnName = "IDPromocion")
    @ManyToOne
    private Promocion iDPromocion;

    public Pagoingreso() {
    }

    public Pagoingreso(Integer iDIngreso) {
        this.iDIngreso = iDIngreso;
    }

    public Pagoingreso(Integer iDIngreso, int monto, Date fechaPago) {
        this.iDIngreso = iDIngreso;
        this.monto = monto;
        this.fechaPago = fechaPago;
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

    public Alumno getIDAlumnoPI() {
        return iDAlumnoPI;
    }

    public void setIDAlumnoPI(Alumno iDAlumnoPI) {
        this.iDAlumnoPI = iDAlumnoPI;
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
        return "BaseDeDatos.Pagoingreso[ iDIngreso=" + iDIngreso + " ]";
    }
    
}
