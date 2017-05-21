/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ossiel
 */
@Entity
@Table(name = "asistencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")
    , @NamedQuery(name = "Asistencia.findByIDAsistencia", query = "SELECT a FROM Asistencia a WHERE a.iDAsistencia = :iDAsistencia")
    , @NamedQuery(name = "Asistencia.findByFecha", query = "SELECT a FROM Asistencia a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Asistencia.findByAsistencia", query = "SELECT a FROM Asistencia a WHERE a.asistencia = :asistencia")})
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAsistencia")
    private Integer iDAsistencia;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "asistencia")
    private boolean asistencia;
    @JoinColumn(name = "IDAlumnoAsis", referencedColumnName = "IDAlumno")
    @ManyToOne(optional = false)
    private Alumno iDAlumnoAsis;
    @JoinColumn(name = "IDClaseAsis", referencedColumnName = "IDClase")
    @ManyToOne(optional = false)
    private Clase iDClaseAsis;

    public Asistencia() {
    }

    public Asistencia(Integer iDAsistencia) {
        this.iDAsistencia = iDAsistencia;
    }

    public Asistencia(Integer iDAsistencia, Date fecha, boolean asistencia) {
        this.iDAsistencia = iDAsistencia;
        this.fecha = fecha;
        this.asistencia = asistencia;
    }

    public Integer getIDAsistencia() {
        return iDAsistencia;
    }

    public void setIDAsistencia(Integer iDAsistencia) {
        this.iDAsistencia = iDAsistencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Alumno getIDAlumnoAsis() {
        return iDAlumnoAsis;
    }

    public void setIDAlumnoAsis(Alumno iDAlumnoAsis) {
        this.iDAlumnoAsis = iDAlumnoAsis;
    }

    public Clase getIDClaseAsis() {
        return iDClaseAsis;
    }

    public void setIDClaseAsis(Clase iDClaseAsis) {
        this.iDClaseAsis = iDClaseAsis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAsistencia != null ? iDAsistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.iDAsistencia == null && other.iDAsistencia != null) || (this.iDAsistencia != null && !this.iDAsistencia.equals(other.iDAsistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseDeDatos.Asistencia[ iDAsistencia=" + iDAsistencia + " ]";
    }
    
}
