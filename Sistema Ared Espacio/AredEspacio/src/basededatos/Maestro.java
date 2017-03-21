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
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m")
    , @NamedQuery(name = "Maestro.findByIDMaestro", query = "SELECT m FROM Maestro m WHERE m.iDMaestro = :iDMaestro")
    , @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Maestro.findByPrimerApellido", query = "SELECT m FROM Maestro m WHERE m.primerApellido = :primerApellido")
    , @NamedQuery(name = "Maestro.findBySegundoApellido", query = "SELECT m FROM Maestro m WHERE m.segundoApellido = :segundoApellido")
    , @NamedQuery(name = "Maestro.findByNumeroDeCelular", query = "SELECT m FROM Maestro m WHERE m.numeroDeCelular = :numeroDeCelular")
    , @NamedQuery(name = "Maestro.findByFechaNacimiento", query = "SELECT m FROM Maestro m WHERE m.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Maestro.findByDireccion", query = "SELECT m FROM Maestro m WHERE m.direccion = :direccion")
    , @NamedQuery(name = "Maestro.findByEstado", query = "SELECT m FROM Maestro m WHERE m.estado = :estado")
    , @NamedQuery(name = "Maestro.findByIDClase", query = "SELECT m FROM Maestro m WHERE m.iDClase = :iDClase")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMaestro")
    private Integer iDMaestro;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "primerApellido")
    private String primerApellido;
    @Basic(optional = false)
    @Column(name = "segundoApellido")
    private String segundoApellido;
    @Basic(optional = false)
    @Column(name = "numeroDeCelular")
    private String numeroDeCelular;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @Column(name = "IDClase")
    private int iDClase;
    @JoinColumn(name = "IDPagoEgreso", referencedColumnName = "IDEgreso")
    @ManyToOne(optional = false)
    private Pagoegreso iDPagoEgreso;
    @OneToMany(mappedBy = "iDMaestro")
    private Collection<Clase> claseCollection;

    public Maestro() {
    }

    public Maestro(Integer iDMaestro) {
        this.iDMaestro = iDMaestro;
    }

    public Maestro(Integer iDMaestro, String nombre, String primerApellido, String segundoApellido, String numeroDeCelular, Date fechaNacimiento, String direccion, boolean estado, int iDClase) {
        this.iDMaestro = iDMaestro;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.numeroDeCelular = numeroDeCelular;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.estado = estado;
        this.iDClase = iDClase;
    }

    public Integer getIDMaestro() {
        return iDMaestro;
    }

    public void setIDMaestro(Integer iDMaestro) {
        this.iDMaestro = iDMaestro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroDeCelular() {
        return numeroDeCelular;
    }

    public void setNumeroDeCelular(String numeroDeCelular) {
        this.numeroDeCelular = numeroDeCelular;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIDClase() {
        return iDClase;
    }

    public void setIDClase(int iDClase) {
        this.iDClase = iDClase;
    }

    public Pagoegreso getIDPagoEgreso() {
        return iDPagoEgreso;
    }

    public void setIDPagoEgreso(Pagoegreso iDPagoEgreso) {
        this.iDPagoEgreso = iDPagoEgreso;
    }

    @XmlTransient
    public Collection<Clase> getClaseCollection() {
        return claseCollection;
    }

    public void setClaseCollection(Collection<Clase> claseCollection) {
        this.claseCollection = claseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMaestro != null ? iDMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.iDMaestro == null && other.iDMaestro != null) || (this.iDMaestro != null && !this.iDMaestro.equals(other.iDMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Maestro[ iDMaestro=" + iDMaestro + " ]";
    }
    
}
