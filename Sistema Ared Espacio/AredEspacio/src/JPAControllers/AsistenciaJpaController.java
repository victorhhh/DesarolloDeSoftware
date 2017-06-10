/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAControllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BaseDeDatos.Alumno;
import BaseDeDatos.Asistencia;
import BaseDeDatos.Clase;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno IDAlumnoAsis = asistencia.getIDAlumnoAsis();
            if (IDAlumnoAsis != null) {
                IDAlumnoAsis = em.getReference(IDAlumnoAsis.getClass(), IDAlumnoAsis.getIDAlumno());
                asistencia.setIDAlumnoAsis(IDAlumnoAsis);
            }
            Clase IDClaseAsis = asistencia.getIDClaseAsis();
            if (IDClaseAsis != null) {
                IDClaseAsis = em.getReference(IDClaseAsis.getClass(), IDClaseAsis.getIDClase());
                asistencia.setIDClaseAsis(IDClaseAsis);
            }
            em.persist(asistencia);
            if (IDAlumnoAsis != null) {
                IDAlumnoAsis.getAsistenciaList().add(asistencia);
                IDAlumnoAsis = em.merge(IDAlumnoAsis);
            }
            if (IDClaseAsis != null) {
                IDClaseAsis.getAsistenciaList().add(asistencia);
                IDClaseAsis = em.merge(IDClaseAsis);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getIDAsistencia());
            Alumno IDAlumnoAsisOld = persistentAsistencia.getIDAlumnoAsis();
            Alumno IDAlumnoAsisNew = asistencia.getIDAlumnoAsis();
            Clase IDClaseAsisOld = persistentAsistencia.getIDClaseAsis();
            Clase IDClaseAsisNew = asistencia.getIDClaseAsis();
            if (IDAlumnoAsisNew != null) {
                IDAlumnoAsisNew = em.getReference(IDAlumnoAsisNew.getClass(), IDAlumnoAsisNew.getIDAlumno());
                asistencia.setIDAlumnoAsis(IDAlumnoAsisNew);
            }
            if (IDClaseAsisNew != null) {
                IDClaseAsisNew = em.getReference(IDClaseAsisNew.getClass(), IDClaseAsisNew.getIDClase());
                asistencia.setIDClaseAsis(IDClaseAsisNew);
            }
            asistencia = em.merge(asistencia);
            if (IDAlumnoAsisOld != null && !IDAlumnoAsisOld.equals(IDAlumnoAsisNew)) {
                IDAlumnoAsisOld.getAsistenciaList().remove(asistencia);
                IDAlumnoAsisOld = em.merge(IDAlumnoAsisOld);
            }
            if (IDAlumnoAsisNew != null && !IDAlumnoAsisNew.equals(IDAlumnoAsisOld)) {
                IDAlumnoAsisNew.getAsistenciaList().add(asistencia);
                IDAlumnoAsisNew = em.merge(IDAlumnoAsisNew);
            }
            if (IDClaseAsisOld != null && !IDClaseAsisOld.equals(IDClaseAsisNew)) {
                IDClaseAsisOld.getAsistenciaList().remove(asistencia);
                IDClaseAsisOld = em.merge(IDClaseAsisOld);
            }
            if (IDClaseAsisNew != null && !IDClaseAsisNew.equals(IDClaseAsisOld)) {
                IDClaseAsisNew.getAsistenciaList().add(asistencia);
                IDClaseAsisNew = em.merge(IDClaseAsisNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencia.getIDAsistencia();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getIDAsistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Alumno IDAlumnoAsis = asistencia.getIDAlumnoAsis();
            if (IDAlumnoAsis != null) {
                IDAlumnoAsis.getAsistenciaList().remove(asistencia);
                IDAlumnoAsis = em.merge(IDAlumnoAsis);
            }
            Clase IDClaseAsis = asistencia.getIDClaseAsis();
            if (IDClaseAsis != null) {
                IDClaseAsis.getAsistenciaList().remove(asistencia);
                IDClaseAsis = em.merge(IDClaseAsis);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
