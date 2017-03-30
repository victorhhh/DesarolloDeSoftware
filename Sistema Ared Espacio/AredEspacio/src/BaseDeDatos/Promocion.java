/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yoresroy
 */
@Entity
@Table(name = "promocion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promocion.findAll", query = "SELECT p FROM Promocion p")
    , @NamedQuery(name = "Promocion.findByIDPromocion", query = "SELECT p FROM Promocion p WHERE p.iDPromocion = :iDPromocion")
    , @NamedQuery(name = "Promocion.findByNombre", query = "SELECT p FROM Promocion p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Promocion.findByDescripcion", query = "SELECT p FROM Promocion p WHERE p.descripcion = :descripcion")})
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPromocion")
    private Integer iDPromocion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "iDPromocion")
    private Collection<Pagoingreso> pagoingresoCollection;

    public Promocion() {
    }

    public Promocion(Integer iDPromocion) {
        this.iDPromocion = iDPromocion;
    }

    public Promocion(Integer iDPromocion, String nombre, String descripcion) {
        this.iDPromocion = iDPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
        return "BaseDeDatos.Promocion[ iDPromocion=" + iDPromocion + " ]";
    }
    
}
