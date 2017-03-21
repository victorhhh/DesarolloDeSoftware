/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c")
    , @NamedQuery(name = "Clase.findByIDClase", query = "SELECT c FROM Clase c WHERE c.iDClase = :iDClase")
    , @NamedQuery(name = "Clase.findByNombre", query = "SELECT c FROM Clase c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clase.findByEstado", query = "SELECT c FROM Clase c WHERE c.estado = :estado")
    , @NamedQuery(name = "Clase.findByDia", query = "SELECT c FROM Clase c WHERE c.dia = :dia")
    , @NamedQuery(name = "Clase.findByHora", query = "SELECT c FROM Clase c WHERE c.hora = :hora")})
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
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
    @JoinColumn(name = "IDAlumno", referencedColumnName = "IDMatricula")
    @ManyToOne
    private Alumno iDAlumno;
    @JoinColumn(name = "IDMaestro", referencedColumnName = "IDMaestro")
    @ManyToOne
    private Maestro iDMaestro;

    public Clase() {
    }

    public Clase(Integer iDClase) {
        this.iDClase = iDClase;
    }

    public Clase(Integer iDClase, String nombre, boolean estado, String dia, String hora) {
        this.iDClase = iDClase;
        this.nombre = nombre;
        this.estado = estado;
        this.dia = dia;
        this.hora = hora;
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

    public Alumno getIDAlumno() {
        return iDAlumno;
    }

    public void setIDAlumno(Alumno iDAlumno) {
        this.iDAlumno = iDAlumno;
    }

    public Maestro getIDMaestro() {
        return iDMaestro;
    }

    public void setIDMaestro(Maestro iDMaestro) {
        this.iDMaestro = iDMaestro;
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
        return "basededatos.Clase[ iDClase=" + iDClase + " ]";
    }
    
}
