/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
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
@Table(name = "mensualidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensualidad.findAll", query = "SELECT m FROM Mensualidad m")
    , @NamedQuery(name = "Mensualidad.findByIDMensualidad", query = "SELECT m FROM Mensualidad m WHERE m.iDMensualidad = :iDMensualidad")
    , @NamedQuery(name = "Mensualidad.findByMonto", query = "SELECT m FROM Mensualidad m WHERE m.monto = :monto")
    , @NamedQuery(name = "Mensualidad.findByFechaPago", query = "SELECT m FROM Mensualidad m WHERE m.fechaPago = :fechaPago")})
public class Mensualidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMensualidad")
    private Integer iDMensualidad;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @OneToMany(mappedBy = "iDMensualidadA")
    private Collection<Alumno> alumnoCollection;
    @JoinColumn(name = "IDPromocionM", referencedColumnName = "IDPromocion")
    @ManyToOne
    private Promocion iDPromocionM;

    public Mensualidad() {
    }

    public Mensualidad(Integer iDMensualidad) {
        this.iDMensualidad = iDMensualidad;
    }

    public Mensualidad(Integer iDMensualidad, int monto, Date fechaPago) {
        this.iDMensualidad = iDMensualidad;
        this.monto = monto;
        this.fechaPago = fechaPago;
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

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    public Promocion getIDPromocionM() {
        return iDPromocionM;
    }

    public void setIDPromocionM(Promocion iDPromocionM) {
        this.iDPromocionM = iDPromocionM;
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
        return "BaseDeDatos.Mensualidad[ iDMensualidad=" + iDMensualidad + " ]";
    }
    
}
