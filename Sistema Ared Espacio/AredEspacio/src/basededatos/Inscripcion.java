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
@Table(name = "inscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll", query = "SELECT i FROM Inscripcion i")
    , @NamedQuery(name = "Inscripcion.findByIDInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.iDInscripcion = :iDInscripcion")
    , @NamedQuery(name = "Inscripcion.findByMonto", query = "SELECT i FROM Inscripcion i WHERE i.monto = :monto")
    , @NamedQuery(name = "Inscripcion.findByFechaPago", query = "SELECT i FROM Inscripcion i WHERE i.fechaPago = :fechaPago")
    , @NamedQuery(name = "Inscripcion.findByIDAlumno", query = "SELECT i FROM Inscripcion i WHERE i.iDAlumno = :iDAlumno")
    , @NamedQuery(name = "Inscripcion.findByIDpago", query = "SELECT i FROM Inscripcion i WHERE i.iDpago = :iDpago")})
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDInscripcion")
    private Integer iDInscripcion;
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
    @Column(name = "IDpago")
    private int iDpago;
    @OneToMany(mappedBy = "iDInscripcion")
    private Collection<Pagoingreso> pagoingresoCollection;

    public Inscripcion() {
    }

    public Inscripcion(Integer iDInscripcion) {
        this.iDInscripcion = iDInscripcion;
    }

    public Inscripcion(Integer iDInscripcion, int monto, Date fechaPago, int iDAlumno, int iDpago) {
        this.iDInscripcion = iDInscripcion;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.iDAlumno = iDAlumno;
        this.iDpago = iDpago;
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

    public int getIDpago() {
        return iDpago;
    }

    public void setIDpago(int iDpago) {
        this.iDpago = iDpago;
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
        return "basededatos.Inscripcion[ iDInscripcion=" + iDInscripcion + " ]";
    }
    
}
