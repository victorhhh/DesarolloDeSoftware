/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ossiel
 */
@Entity
@Table(name = "clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c")
    ,@NamedQuery(name = "Clase.containsNombre", query = "SELECT c FROM Clase c WHERE c.nombre LIKE :nombre")
    , @NamedQuery(name = "Clase.findByIDClase", query = "SELECT c FROM Clase c WHERE c.iDClase = :iDClase")
    , @NamedQuery(name = "Clase.findByNombre", query = "SELECT c FROM Clase c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clase.findByEstado", query = "SELECT c FROM Clase c WHERE c.estado = :estado")
    , @NamedQuery(name = "Clase.findByDia", query = "SELECT c FROM Clase c WHERE c.dia = :dia")
    , @NamedQuery(name = "Clase.findByHora", query = "SELECT c FROM Clase c WHERE c.hora = :hora")
    , @NamedQuery(name = "Clase.findByCosto", query = "SELECT c FROM Clase c WHERE c.costo = :costo")})
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDClase")
    private Integer iDClase;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @Column(name = "dia")
    private String dia;
    @Basic(optional = false)
    @Column(name = "hora")
    private String hora;
    @Basic(optional = false)
    @Column(name = "costo")
    private double costo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDClaseAsis")
    private List<Asistencia> asistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDClaseG")
    private List<Grupo> grupoList;
    @JoinColumn(name = "IDMaestroC", referencedColumnName = "IDMaestro")
    @ManyToOne
    private Maestro iDMaestroC;

    public List<Clase> buscarClasesPorNombre(String nombre) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Clase> resultList = em.createNamedQuery("Clase.containsNombre").setParameter("nombre", "%" + nombre + "%").getResultList();
        return resultList;
    }

    public List<Clase> obtenerListaDeClases() {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Clase> resultList = em.createNamedQuery("Clase.findAll").getResultList();
        return resultList;
    }

    public List<Clase> findAll() {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Clase> resultList = em.createNamedQuery("Clase.findAll").getResultList();
        return resultList;
    }

    public List<Clase> buscarClasePorID(int iDClase) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        List<Clase> resultList = em.createNamedQuery("Clase.findByIDClase").setParameter("iDClase", iDClase).getResultList();
        return resultList;
    }
    public Clase() {
    }

    public Clase(Integer iDClase) {
        this.iDClase = iDClase;
    }

    public Clase(Integer iDClase, String nombre, boolean estado, String dia, String hora, double costo) {
        this.iDClase = iDClase;
        this.nombre = nombre;
        this.estado = estado;
        this.dia = dia;
        this.hora = hora;
        this.costo = costo;
    }

    public Integer getIDClase() {
        return iDClase;
    }

    public void setIDClase(Integer iDClase) {
        this.iDClase = iDClase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public Maestro getIDMaestroC() {
        return iDMaestroC;
    }

    public void setIDMaestroC(Maestro iDMaestroC) {
        this.iDMaestroC = iDMaestroC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDClase != null ? iDClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.iDClase == null && other.iDClase != null) || (this.iDClase != null && !this.iDClase.equals(other.iDClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDeDatos.Clase[ iDClase=" + iDClase + " ]";
    }
    
}
