/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import basededatos.Alumno;
import basededatos.Clase;
import basededatos.Maestro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author victor
 */
public class ClaseJpaController implements Serializable {

    public ClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno IDAlumno = clase.getIDAlumno();
            if (IDAlumno != null) {
                IDAlumno = em.getReference(IDAlumno.getClass(), IDAlumno.getIDMatricula());
                clase.setIDAlumno(IDAlumno);
            }
            Maestro IDMaestro = clase.getIDMaestro();
            if (IDMaestro != null) {
                IDMaestro = em.getReference(IDMaestro.getClass(), IDMaestro.getIDMaestro());
                clase.setIDMaestro(IDMaestro);
            }
            em.persist(clase);
            if (IDAlumno != null) {
                IDAlumno.getClaseCollection().add(clase);
                IDAlumno = em.merge(IDAlumno);
            }
            if (IDMaestro != null) {
                IDMaestro.getClaseCollection().add(clase);
                IDMaestro = em.merge(IDMaestro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClase(clase.getIDClase()) != null) {
                throw new PreexistingEntityException("Clase " + clase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getIDClase());
            Alumno IDAlumnoOld = persistentClase.getIDAlumno();
            Alumno IDAlumnoNew = clase.getIDAlumno();
            Maestro IDMaestroOld = persistentClase.getIDMaestro();
            Maestro IDMaestroNew = clase.getIDMaestro();
            if (IDAlumnoNew != null) {
                IDAlumnoNew = em.getReference(IDAlumnoNew.getClass(), IDAlumnoNew.getIDMatricula());
                clase.setIDAlumno(IDAlumnoNew);
            }
            if (IDMaestroNew != null) {
                IDMaestroNew = em.getReference(IDMaestroNew.getClass(), IDMaestroNew.getIDMaestro());
                clase.setIDMaestro(IDMaestroNew);
            }
            clase = em.merge(clase);
            if (IDAlumnoOld != null && !IDAlumnoOld.equals(IDAlumnoNew)) {
                IDAlumnoOld.getClaseCollection().remove(clase);
                IDAlumnoOld = em.merge(IDAlumnoOld);
            }
            if (IDAlumnoNew != null && !IDAlumnoNew.equals(IDAlumnoOld)) {
                IDAlumnoNew.getClaseCollection().add(clase);
                IDAlumnoNew = em.merge(IDAlumnoNew);
            }
            if (IDMaestroOld != null && !IDMaestroOld.equals(IDMaestroNew)) {
                IDMaestroOld.getClaseCollection().remove(clase);
                IDMaestroOld = em.merge(IDMaestroOld);
            }
            if (IDMaestroNew != null && !IDMaestroNew.equals(IDMaestroOld)) {
                IDMaestroNew.getClaseCollection().add(clase);
                IDMaestroNew = em.merge(IDMaestroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clase.getIDClase();
                if (findClase(id) == null) {
                    throw new NonexistentEntityException("The clase with id " + id + " no longer exists.");
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
            Clase clase;
            try {
                clase = em.getReference(Clase.class, id);
                clase.getIDClase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clase with id " + id + " no longer exists.", enfe);
            }
            Alumno IDAlumno = clase.getIDAlumno();
            if (IDAlumno != null) {
                IDAlumno.getClaseCollection().remove(clase);
                IDAlumno = em.merge(IDAlumno);
            }
            Maestro IDMaestro = clase.getIDMaestro();
            if (IDMaestro != null) {
                IDMaestro.getClaseCollection().remove(clase);
                IDMaestro = em.merge(IDMaestro);
            }
            em.remove(clase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clase> findClaseEntities() {
        return findClaseEntities(true, -1, -1);
    }

    public List<Clase> findClaseEntities(int maxResults, int firstResult) {
        return findClaseEntities(false, maxResults, firstResult);
    }

    private List<Clase> findClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clase.class));
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

    public Clase findClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clase.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clase> rt = cq.from(Clase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
