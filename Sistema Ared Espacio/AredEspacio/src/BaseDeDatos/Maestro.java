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
import javax.persistence.CascadeType;
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
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m")
    , @NamedQuery(name = "Maestro.findByName", query = "SELECT m FROM Maestro m WHERE m.nombre LIKE :nombre")
    , @NamedQuery(name = "Maestro.findByIDMaestro", query = "SELECT m FROM Maestro m WHERE m.iDMaestro = :iDMaestro")
    , @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Maestro.findByPrimerApellido", query = "SELECT m FROM Maestro m WHERE m.primerApellido = :primerApellido")
    , @NamedQuery(name = "Maestro.findBySegundoApellido", query = "SELECT m FROM Maestro m WHERE m.segundoApellido = :segundoApellido")
    , @NamedQuery(name = "Maestro.findByNumeroDeTelefono", query = "SELECT m FROM Maestro m WHERE m.numeroDeTelefono = :numeroDeTelefono")
    , @NamedQuery(name = "Maestro.findByFechaNacimiento", query = "SELECT m FROM Maestro m WHERE m.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Maestro.findByDireccion", query = "SELECT m FROM Maestro m WHERE m.direccion = :direccion")
    , @NamedQuery(name = "Maestro.findByEstado", query = "SELECT m FROM Maestro m WHERE m.estado = :estado")
    , @NamedQuery(name = "Maestro.findBySueldo", query = "SELECT m FROM Maestro m WHERE m.sueldo = :sueldo")
    , @NamedQuery(name = "Maestro.findByRutaImagen", query = "SELECT m FROM Maestro m WHERE m.rutaImagen = :rutaImagen")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "numeroDeTelefono")
    private String numeroDeTelefono;
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
    @Column(name = "sueldo")
    private double sueldo;
    @Column(name = "rutaImagen")
    private String rutaImagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMaestroPE")
    private Collection<Pagoegreso> pagoegresoCollection;
    @OneToMany(mappedBy = "iDMaestroC")
    private Collection<Clase> claseCollection;

    public Maestro() {
    }
    
    public List<Maestro> buscarMaestroPorNombre(String nombre){
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU",null).createEntityManager();
        List<Maestro> resultList = em.createNamedQuery("Maestro.findByName").setParameter("nombre", "%"+nombre+"%").getResultList();   
        return resultList;
    }


    public Maestro(Integer iDMaestro) {
        this.iDMaestro = iDMaestro;
    }

    public Maestro(Integer iDMaestro, String nombre, String primerApellido, String segundoApellido, String numeroDeTelefono, Date fechaNacimiento, String direccion, boolean estado, double sueldo) {
        this.iDMaestro = iDMaestro;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.numeroDeTelefono = numeroDeTelefono;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.estado = estado;
        this.sueldo = sueldo;
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

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
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

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @XmlTransient
    public Collection<Pagoegreso> getPagoegresoCollection() {
        return pagoegresoCollection;
    }

    public void setPagoegresoCollection(Collection<Pagoegreso> pagoegresoCollection) {
        this.pagoegresoCollection = pagoegresoCollection;
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
        return "BaseDeDatos.Maestro[ iDMaestro=" + iDMaestro + " ]";
    }
    
}
