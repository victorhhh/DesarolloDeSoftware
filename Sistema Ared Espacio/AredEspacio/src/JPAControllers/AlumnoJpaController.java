/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAControllers;

import BaseDeDatos.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BaseDeDatos.Inscripcion;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Asistencia;
import java.util.ArrayList;
import java.util.List;
import BaseDeDatos.Grupo;
import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getAsistenciaList() == null) {
            alumno.setAsistenciaList(new ArrayList<Asistencia>());
        }
        if (alumno.getGrupoList() == null) {
            alumno.setGrupoList(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion IDInscripcionA = alumno.getIDInscripcionA();
            if (IDInscripcionA != null) {
                IDInscripcionA = em.getReference(IDInscripcionA.getClass(), IDInscripcionA.getIDInscripcion());
                alumno.setIDInscripcionA(IDInscripcionA);
            }
            Mensualidad IDMensualidadA = alumno.getIDMensualidadA();
            if (IDMensualidadA != null) {
                IDMensualidadA = em.getReference(IDMensualidadA.getClass(), IDMensualidadA.getIDMensualidad());
                alumno.setIDMensualidadA(IDMensualidadA);
            }
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : alumno.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            alumno.setAsistenciaList(attachedAsistenciaList);
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : alumno.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getIDGrupo());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            alumno.setGrupoList(attachedGrupoList);
            em.persist(alumno);
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoList().add(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            if (IDMensualidadA != null) {
                IDMensualidadA.getAlumnoList().add(alumno);
                IDMensualidadA = em.merge(IDMensualidadA);
            }
            for (Asistencia asistenciaListAsistencia : alumno.getAsistenciaList()) {
                Alumno oldIDAlumnoAsisOfAsistenciaListAsistencia = asistenciaListAsistencia.getIDAlumnoAsis();
                asistenciaListAsistencia.setIDAlumnoAsis(alumno);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldIDAlumnoAsisOfAsistenciaListAsistencia != null) {
                    oldIDAlumnoAsisOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldIDAlumnoAsisOfAsistenciaListAsistencia = em.merge(oldIDAlumnoAsisOfAsistenciaListAsistencia);
                }
            }
            for (Grupo grupoListGrupo : alumno.getGrupoList()) {
                Alumno oldIDAlumnoGOfGrupoListGrupo = grupoListGrupo.getIDAlumnoG();
                grupoListGrupo.setIDAlumnoG(alumno);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldIDAlumnoGOfGrupoListGrupo != null) {
                    oldIDAlumnoGOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldIDAlumnoGOfGrupoListGrupo = em.merge(oldIDAlumnoGOfGrupoListGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIDAlumno());
            Inscripcion IDInscripcionAOld = persistentAlumno.getIDInscripcionA();
            Inscripcion IDInscripcionANew = alumno.getIDInscripcionA();
            Mensualidad IDMensualidadAOld = persistentAlumno.getIDMensualidadA();
            Mensualidad IDMensualidadANew = alumno.getIDMensualidadA();
            List<Asistencia> asistenciaListOld = persistentAlumno.getAsistenciaList();
            List<Asistencia> asistenciaListNew = alumno.getAsistenciaList();
            List<Grupo> grupoListOld = persistentAlumno.getGrupoList();
            List<Grupo> grupoListNew = alumno.getGrupoList();
            List<String> illegalOrphanMessages = null;
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its IDAlumnoAsis field is not nullable.");
                }
            }
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoListOldGrupo + " since its IDAlumnoG field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDInscripcionANew != null) {
                IDInscripcionANew = em.getReference(IDInscripcionANew.getClass(), IDInscripcionANew.getIDInscripcion());
                alumno.setIDInscripcionA(IDInscripcionANew);
            }
            if (IDMensualidadANew != null) {
                IDMensualidadANew = em.getReference(IDMensualidadANew.getClass(), IDMensualidadANew.getIDMensualidad());
                alumno.setIDMensualidadA(IDMensualidadANew);
            }
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            alumno.setAsistenciaList(asistenciaListNew);
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getIDGrupo());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            alumno.setGrupoList(grupoListNew);
            alumno = em.merge(alumno);
            if (IDInscripcionAOld != null && !IDInscripcionAOld.equals(IDInscripcionANew)) {
                IDInscripcionAOld.getAlumnoList().remove(alumno);
                IDInscripcionAOld = em.merge(IDInscripcionAOld);
            }
            if (IDInscripcionANew != null && !IDInscripcionANew.equals(IDInscripcionAOld)) {
                IDInscripcionANew.getAlumnoList().add(alumno);
                IDInscripcionANew = em.merge(IDInscripcionANew);
            }
            if (IDMensualidadAOld != null && !IDMensualidadAOld.equals(IDMensualidadANew)) {
                IDMensualidadAOld.getAlumnoList().remove(alumno);
                IDMensualidadAOld = em.merge(IDMensualidadAOld);
            }
            if (IDMensualidadANew != null && !IDMensualidadANew.equals(IDMensualidadAOld)) {
                IDMensualidadANew.getAlumnoList().add(alumno);
                IDMensualidadANew = em.merge(IDMensualidadANew);
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Alumno oldIDAlumnoAsisOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getIDAlumnoAsis();
                    asistenciaListNewAsistencia.setIDAlumnoAsis(alumno);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldIDAlumnoAsisOfAsistenciaListNewAsistencia != null && !oldIDAlumnoAsisOfAsistenciaListNewAsistencia.equals(alumno)) {
                        oldIDAlumnoAsisOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldIDAlumnoAsisOfAsistenciaListNewAsistencia = em.merge(oldIDAlumnoAsisOfAsistenciaListNewAsistencia);
                    }
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Alumno oldIDAlumnoGOfGrupoListNewGrupo = grupoListNewGrupo.getIDAlumnoG();
                    grupoListNewGrupo.setIDAlumnoG(alumno);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldIDAlumnoGOfGrupoListNewGrupo != null && !oldIDAlumnoGOfGrupoListNewGrupo.equals(alumno)) {
                        oldIDAlumnoGOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldIDAlumnoGOfGrupoListNewGrupo = em.merge(oldIDAlumnoGOfGrupoListNewGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIDAlumno();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIDAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Asistencia> asistenciaListOrphanCheck = alumno.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable IDAlumnoAsis field.");
            }
            List<Grupo> grupoListOrphanCheck = alumno.getGrupoList();
            for (Grupo grupoListOrphanCheckGrupo : grupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Grupo " + grupoListOrphanCheckGrupo + " in its grupoList field has a non-nullable IDAlumnoG field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inscripcion IDInscripcionA = alumno.getIDInscripcionA();
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoList().remove(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            Mensualidad IDMensualidadA = alumno.getIDMensualidadA();
            if (IDMensualidadA != null) {
                IDMensualidadA.getAlumnoList().remove(alumno);
                IDMensualidadA = em.merge(IDMensualidadA);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
