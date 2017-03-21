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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "mensualidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensualidad.findAll", query = "SELECT m FROM Mensualidad m")
    , @NamedQuery(name = "Mensualidad.findByIDMensualidad", query = "SELECT m FROM Mensualidad m WHERE m.iDMensualidad = :iDMensualidad")
    , @NamedQuery(name = "Mensualidad.findByMonto", query = "SELECT m FROM Mensualidad m WHERE m.monto = :monto")
    , @NamedQuery(name = "Mensualidad.findByFechaPago", query = "SELECT m FROM Mensualidad m WHERE m.fechaPago = :fechaPago")
    , @NamedQuery(name = "Mensualidad.findByIDAlumno", query = "SELECT m FROM Mensualidad m WHERE m.iDAlumno = :iDAlumno")
    , @NamedQuery(name = "Mensualidad.findByIDPago", query = "SELECT m FROM Mensualidad m WHERE m.iDPago = :iDPago")})
public class Mensualidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMensualidad")
    private Integer iDMensualidad;
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
    @Basic(optional = false)
    @Column(name = "IDPago")
    private int iDPago;
    @OneToMany(mappedBy = "iDMensualidad")
    private Collection<Pagoingreso> pagoingresoCollection;

    public Mensualidad() {
    }

    public Mensualidad(Integer iDMensualidad) {
        this.iDMensualidad = iDMensualidad;
    }

    public Mensualidad(Integer iDMensualidad, int monto, Date fechaPago, int iDAlumno, int iDPago) {
        this.iDMensualidad = iDMensualidad;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.iDAlumno = iDAlumno;
        this.iDPago = iDPago;
    }

    public Integer getIDMensualidad() {
        return iDMensualidad;
    }

    public void setIDMensualidad(Integer iDMensualidad) {
        this.iDMensualidad = iDMensualidad;
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

    public int getIDPago() {
        return iDPago;
    }

    public void setIDPago(int iDPago) {
        this.iDPago = iDPago;
    }

    @XmlTransient
    public Collection<Pagoingreso> getPagoingresoCollection() {
        return pagoingresoCollection;
    }

    public void setPagoingresoCollection(Collection<Pagoingreso> pagoingresoCollection) {
        this.pagoingresoCollection = pagoingresoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMensualidad != null ? iDMensualidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensualidad)) {
            return false;
        }
        Mensualidad other = (Mensualidad) object;
        if ((this.iDMensualidad == null && other.iDMensualidad != null) || (this.iDMensualidad != null && !this.iDMensualidad.equals(other.iDMensualidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Mensualidad[ iDMensualidad=" + iDMensualidad + " ]";
    }
    
}
