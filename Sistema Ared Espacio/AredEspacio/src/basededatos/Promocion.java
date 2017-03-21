/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "promocion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promocion.findAll", query = "SELECT p FROM Promocion p")
    , @NamedQuery(name = "Promocion.findByIDPromocion", query = "SELECT p FROM Promocion p WHERE p.iDPromocion = :iDPromocion")
    , @NamedQuery(name = "Promocion.findByNombre", query = "SELECT p FROM Promocion p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Promocion.findByDescripcion", query = "SELECT p FROM Promocion p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Promocion.findByIDPago", query = "SELECT p FROM Promocion p WHERE p.iDPago = :iDPago")})
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPromocion")
    private Integer iDPromocion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "IDPago")
    private int iDPago;
    @OneToMany(mappedBy = "iDPromocion")
    private Collection<Pagoingreso> pagoingresoCollection;

    public Promocion() {
    }

    public Promocion(Integer iDPromocion) {
        this.iDPromocion = iDPromocion;
    }

    public Promocion(Integer iDPromocion, String nombre, String descripcion, int iDPago) {
        this.iDPromocion = iDPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iDPago = iDPago;
    }

    public Integer getIDPromocion() {
        return iDPromocion;
    }

    public void setIDPromocion(Integer iDPromocion) {
        this.iDPromocion = iDPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (iDPromocion != null ? iDPromocion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocion)) {
            return false;
        }
        Promocion other = (Promocion) object;
        if ((this.iDPromocion == null && other.iDPromocion != null) || (this.iDPromocion != null && !this.iDPromocion.equals(other.iDPromocion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Promocion[ iDPromocion=" + iDPromocion + " ]";
    }
    
}
