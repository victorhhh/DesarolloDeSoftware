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
import java.util.Collection;
import BaseDeDatos.Grupo;
import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
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
        if (alumno.getAsistenciaCollection() == null) {
            alumno.setAsistenciaCollection(new ArrayList<Asistencia>());
        }
        if (alumno.getGrupoCollection() == null) {
            alumno.setGrupoCollection(new ArrayList<Grupo>());
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
            Collection<Asistencia> attachedAsistenciaCollection = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionAsistenciaToAttach : alumno.getAsistenciaCollection()) {
                asistenciaCollectionAsistenciaToAttach = em.getReference(asistenciaCollectionAsistenciaToAttach.getClass(), asistenciaCollectionAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaCollection.add(asistenciaCollectionAsistenciaToAttach);
            }
            alumno.setAsistenciaCollection(attachedAsistenciaCollection);
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : alumno.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getIDGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            alumno.setGrupoCollection(attachedGrupoCollection);
            em.persist(alumno);
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoCollection().add(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            if (IDMensualidadA != null) {
                IDMensualidadA.getAlumnoCollection().add(alumno);
                IDMensualidadA = em.merge(IDMensualidadA);
            }
            for (Asistencia asistenciaCollectionAsistencia : alumno.getAsistenciaCollection()) {
                Alumno oldIDAlumnoAsisOfAsistenciaCollectionAsistencia = asistenciaCollectionAsistencia.getIDAlumnoAsis();
                asistenciaCollectionAsistencia.setIDAlumnoAsis(alumno);
                asistenciaCollectionAsistencia = em.merge(asistenciaCollectionAsistencia);
                if (oldIDAlumnoAsisOfAsistenciaCollectionAsistencia != null) {
                    oldIDAlumnoAsisOfAsistenciaCollectionAsistencia.getAsistenciaCollection().remove(asistenciaCollectionAsistencia);
                    oldIDAlumnoAsisOfAsistenciaCollectionAsistencia = em.merge(oldIDAlumnoAsisOfAsistenciaCollectionAsistencia);
                }
            }
            for (Grupo grupoCollectionGrupo : alumno.getGrupoCollection()) {
                Alumno oldIDAlumnoGOfGrupoCollectionGrupo = grupoCollectionGrupo.getIDAlumnoG();
                grupoCollectionGrupo.setIDAlumnoG(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldIDAlumnoGOfGrupoCollectionGrupo != null) {
                    oldIDAlumnoGOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldIDAlumnoGOfGrupoCollectionGrupo = em.merge(oldIDAlumnoGOfGrupoCollectionGrupo);
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
            Collection<Asistencia> asistenciaCollectionOld = persistentAlumno.getAsistenciaCollection();
            Collection<Asistencia> asistenciaCollectionNew = alumno.getAsistenciaCollection();
            Collection<Grupo> grupoCollectionOld = persistentAlumno.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = alumno.getGrupoCollection();
            List<String> illegalOrphanMessages = null;
            for (Asistencia asistenciaCollectionOldAsistencia : asistenciaCollectionOld) {
                if (!asistenciaCollectionNew.contains(asistenciaCollectionOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaCollectionOldAsistencia + " since its IDAlumnoAsis field is not nullable.");
                }
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoCollectionOldGrupo + " since its IDAlumnoG field is not nullable.");
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
            Collection<Asistencia> attachedAsistenciaCollectionNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionNewAsistenciaToAttach : asistenciaCollectionNew) {
                asistenciaCollectionNewAsistenciaToAttach = em.getReference(asistenciaCollectionNewAsistenciaToAttach.getClass(), asistenciaCollectionNewAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaCollectionNew.add(asistenciaCollectionNewAsistenciaToAttach);
            }
            asistenciaCollectionNew = attachedAsistenciaCollectionNew;
            alumno.setAsistenciaCollection(asistenciaCollectionNew);
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getIDGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            alumno.setGrupoCollection(grupoCollectionNew);
            alumno = em.merge(alumno);
            if (IDInscripcionAOld != null && !IDInscripcionAOld.equals(IDInscripcionANew)) {
                IDInscripcionAOld.getAlumnoCollection().remove(alumno);
                IDInscripcionAOld = em.merge(IDInscripcionAOld);
            }
            if (IDInscripcionANew != null && !IDInscripcionANew.equals(IDInscripcionAOld)) {
                IDInscripcionANew.getAlumnoCollection().add(alumno);
                IDInscripcionANew = em.merge(IDInscripcionANew);
            }
            if (IDMensualidadAOld != null && !IDMensualidadAOld.equals(IDMensualidadANew)) {
                IDMensualidadAOld.getAlumnoCollection().remove(alumno);
                IDMensualidadAOld = em.merge(IDMensualidadAOld);
            }
            if (IDMensualidadANew != null && !IDMensualidadANew.equals(IDMensualidadAOld)) {
                IDMensualidadANew.getAlumnoCollection().add(alumno);
                IDMensualidadANew = em.merge(IDMensualidadANew);
            }
            for (Asistencia asistenciaCollectionNewAsistencia : asistenciaCollectionNew) {
                if (!asistenciaCollectionOld.contains(asistenciaCollectionNewAsistencia)) {
                    Alumno oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia = asistenciaCollectionNewAsistencia.getIDAlumnoAsis();
                    asistenciaCollectionNewAsistencia.setIDAlumnoAsis(alumno);
                    asistenciaCollectionNewAsistencia = em.merge(asistenciaCollectionNewAsistencia);
                    if (oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia != null && !oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia.equals(alumno)) {
                        oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia.getAsistenciaCollection().remove(asistenciaCollectionNewAsistencia);
                        oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia = em.merge(oldIDAlumnoAsisOfAsistenciaCollectionNewAsistencia);
                    }
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Alumno oldIDAlumnoGOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getIDAlumnoG();
                    grupoCollectionNewGrupo.setIDAlumnoG(alumno);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldIDAlumnoGOfGrupoCollectionNewGrupo != null && !oldIDAlumnoGOfGrupoCollectionNewGrupo.equals(alumno)) {
                        oldIDAlumnoGOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldIDAlumnoGOfGrupoCollectionNewGrupo = em.merge(oldIDAlumnoGOfGrupoCollectionNewGrupo);
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
            Collection<Asistencia> asistenciaCollectionOrphanCheck = alumno.getAsistenciaCollection();
            for (Asistencia asistenciaCollectionOrphanCheckAsistencia : asistenciaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Asistencia " + asistenciaCollectionOrphanCheckAsistencia + " in its asistenciaCollection field has a non-nullable IDAlumnoAsis field.");
            }
            Collection<Grupo> grupoCollectionOrphanCheck = alumno.getGrupoCollection();
            for (Grupo grupoCollectionOrphanCheckGrupo : grupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Grupo " + grupoCollectionOrphanCheckGrupo + " in its grupoCollection field has a non-nullable IDAlumnoG field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inscripcion IDInscripcionA = alumno.getIDInscripcionA();
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoCollection().remove(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            Mensualidad IDMensualidadA = alumno.getIDMensualidadA();
            if (IDMensualidadA != null) {
                IDMensualidadA.getAlumnoCollection().remove(alumno);
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
