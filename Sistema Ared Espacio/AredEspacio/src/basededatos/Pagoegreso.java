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
@Table(name = "pagoegreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagoegreso.findAll", query = "SELECT p FROM Pagoegreso p")
    , @NamedQuery(name = "Pagoegreso.findByIDEgreso", query = "SELECT p FROM Pagoegreso p WHERE p.iDEgreso = :iDEgreso")
    , @NamedQuery(name = "Pagoegreso.findByMonto", query = "SELECT p FROM Pagoegreso p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagoegreso.findByFechaPago", query = "SELECT p FROM Pagoegreso p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pagoegreso.findByIDMaestro", query = "SELECT p FROM Pagoegreso p WHERE p.iDMaestro = :iDMaestro")})
public class Pagoegreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDEgreso")
    private Integer iDEgreso;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Basic(optional = false)
    @Column(name = "IDMaestro")
    private int iDMaestro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPagoEgreso")
    private Collection<Maestro> maestroCollection;

    public Pagoegreso() {
    }

    public Pagoegreso(Integer iDEgreso) {
        this.iDEgreso = iDEgreso;
    }

    public Pagoegreso(Integer iDEgreso, int monto, Date fechaPago, int iDMaestro) {
        this.iDEgreso = iDEgreso;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.iDMaestro = iDMaestro;
    }

    public Integer getIDEgreso() {
        return iDEgreso;
    }

    public void setIDEgreso(Integer iDEgreso) {
        this.iDEgreso = iDEgreso;
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

    public int getIDMaestro() {
        return iDMaestro;
    }

    public void setIDMaestro(int iDMaestro) {
        this.iDMaestro = iDMaestro;
    }

    @XmlTransient
    public Collection<Maestro> getMaestroCollection() {
        return maestroCollection;
    }

    public void setMaestroCollection(Collection<Maestro> maestroCollection) {
        this.maestroCollection = maestroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEgreso != null ? iDEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagoegreso)) {
            return false;
        }
        Pagoegreso other = (Pagoegreso) object;
        if ((this.iDEgreso == null && other.iDEgreso != null) || (this.iDEgreso != null && !this.iDEgreso.equals(other.iDEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Pagoegreso[ iDEgreso=" + iDEgreso + " ]";
    }
    
}
