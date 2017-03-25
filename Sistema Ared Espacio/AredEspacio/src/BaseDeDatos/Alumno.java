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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.containsNombre", query = "SELECT a FROM Alumno a WHERE a.nombre LIKE :nombre")
    , @NamedQuery(name = "Alumno.findByIDAlumno", query = "SELECT a FROM Alumno a WHERE a.iDAlumno = :iDAlumno")
    , @NamedQuery(name = "Alumno.findByNombre", query = "SELECT a FROM Alumno a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumno.findByPrimerApellido", query = "SELECT a FROM Alumno a WHERE a.primerApellido = :primerApellido")
    , @NamedQuery(name = "Alumno.findBySegundoApellido", query = "SELECT a FROM Alumno a WHERE a.segundoApellido = :segundoApellido")
    , @NamedQuery(name = "Alumno.findByNumeroDeCelular", query = "SELECT a FROM Alumno a WHERE a.numeroDeCelular = :numeroDeCelular")
    , @NamedQuery(name = "Alumno.findByDireccion", query = "SELECT a FROM Alumno a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "Alumno.findByFechaNacimiento", query = "SELECT a FROM Alumno a WHERE a.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Alumno.findByEstado", query = "SELECT a FROM Alumno a WHERE a.estado = :estado")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAlumno")
    private Integer iDAlumno;
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
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @JoinTable(name = "grupo", joinColumns = {
        @JoinColumn(name = "IDAlumnoG", referencedColumnName = "IDAlumno")}, inverseJoinColumns = {
        @JoinColumn(name = "IDClase", referencedColumnName = "IDClase")})
    @ManyToMany
    private Collection<Clase> claseCollection;
    @OneToMany(mappedBy = "iDAlumnoPI")
    private Collection<Pagoingreso> pagoingresoCollection;
    @JoinColumn(name = "IDInscripcionA", referencedColumnName = "IDInscripcion")
    @ManyToOne
    private Inscripcion iDInscripcionA;
    @OneToMany(mappedBy = "iDAlumnoM")
    private Collection<Mensualidad> mensualidadCollection;

    public Alumno() {
    }

    public Alumno(Integer iDAlumno) {
        this.iDAlumno = iDAlumno;
    }

    public List<Alumno> buscarAlumnosPorNombre(String nombre) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Alumno> resultList = em.createNamedQuery("Alumno.containsNombre").setParameter("nombre", "%" + nombre + "%").getResultList();
        return resultList;
    }

    public Alumno(Integer iDAlumno, String nombre, String primerApellido, String segundoApellido, String numeroDeCelular, String direccion, Date fechaNacimiento, boolean estado) {
        this.iDAlumno = iDAlumno;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.numeroDeCelular = numeroDeCelular;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public Integer getIDAlumno() {
        return iDAlumno;
    }

    public void setIDAlumno(Integer iDAlumno) {
        this.iDAlumno = iDAlumno;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Clase> getClaseCollection() {
        return claseCollection;
    }

    public void setClaseCollection(Collection<Clase> claseCollection) {
        this.claseCollection = claseCollection;
    }

    @XmlTransient
    public Collection<Pagoingreso> getPagoingresoCollection() {
        return pagoingresoCollection;
    }

    public void setPagoingresoCollection(Collection<Pagoingreso> pagoingresoCollection) {
        this.pagoingresoCollection = pagoingresoCollection;
    }

    public Inscripcion getIDInscripcionA() {
        return iDInscripcionA;
    }

    public void setIDInscripcionA(Inscripcion iDInscripcionA) {
        this.iDInscripcionA = iDInscripcionA;
    }

    @XmlTransient
    public Collection<Mensualidad> getMensualidadCollection() {
        return mensualidadCollection;
    }

    public void setMensualidadCollection(Collection<Mensualidad> mensualidadCollection) {
        this.mensualidadCollection = mensualidadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAlumno != null ? iDAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.iDAlumno == null && other.iDAlumno != null) || (this.iDAlumno != null && !this.iDAlumno.equals(other.iDAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDeDatos.Alumno[ iDAlumno=" + iDAlumno + " ]";
    }

}
