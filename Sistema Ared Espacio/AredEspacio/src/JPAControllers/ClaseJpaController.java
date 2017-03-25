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
import BaseDeDatos.Maestro;
import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class ClaseJpaController implements Serializable {

    public ClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) {
        if (clase.getAlumnoCollection() == null) {
            clase.setAlumnoCollection(new ArrayList<Alumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro IDMaestroC = clase.getIDMaestroC();
            if (IDMaestroC != null) {
                IDMaestroC = em.getReference(IDMaestroC.getClass(), IDMaestroC.getIDMaestro());
                clase.setIDMaestroC(IDMaestroC);
            }
            Collection<Alumno> attachedAlumnoCollection = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionAlumnoToAttach : clase.getAlumnoCollection()) {
                alumnoCollectionAlumnoToAttach = em.getReference(alumnoCollectionAlumnoToAttach.getClass(), alumnoCollectionAlumnoToAttach.getIDAlumno());
                attachedAlumnoCollection.add(alumnoCollectionAlumnoToAttach);
            }
            clase.setAlumnoCollection(attachedAlumnoCollection);
            em.persist(clase);
            if (IDMaestroC != null) {
                IDMaestroC.getClaseCollection().add(clase);
                IDMaestroC = em.merge(IDMaestroC);
            }
            for (Alumno alumnoCollectionAlumno : clase.getAlumnoCollection()) {
                alumnoCollectionAlumno.getClaseCollection().add(clase);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            em.getTransaction().commit();
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
            Maestro IDMaestroCOld = persistentClase.getIDMaestroC();
            Maestro IDMaestroCNew = clase.getIDMaestroC();
            Collection<Alumno> alumnoCollectionOld = persistentClase.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = clase.getAlumnoCollection();
            if (IDMaestroCNew != null) {
                IDMaestroCNew = em.getReference(IDMaestroCNew.getClass(), IDMaestroCNew.getIDMaestro());
                clase.setIDMaestroC(IDMaestroCNew);
            }
            Collection<Alumno> attachedAlumnoCollectionNew = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionNewAlumnoToAttach : alumnoCollectionNew) {
                alumnoCollectionNewAlumnoToAttach = em.getReference(alumnoCollectionNewAlumnoToAttach.getClass(), alumnoCollectionNewAlumnoToAttach.getIDAlumno());
                attachedAlumnoCollectionNew.add(alumnoCollectionNewAlumnoToAttach);
            }
            alumnoCollectionNew = attachedAlumnoCollectionNew;
            clase.setAlumnoCollection(alumnoCollectionNew);
            clase = em.merge(clase);
            if (IDMaestroCOld != null && !IDMaestroCOld.equals(IDMaestroCNew)) {
                IDMaestroCOld.getClaseCollection().remove(clase);
                IDMaestroCOld = em.merge(IDMaestroCOld);
            }
            if (IDMaestroCNew != null && !IDMaestroCNew.equals(IDMaestroCOld)) {
                IDMaestroCNew.getClaseCollection().add(clase);
                IDMaestroCNew = em.merge(IDMaestroCNew);
            }
            for (Alumno alumnoCollectionOldAlumno : alumnoCollectionOld) {
                if (!alumnoCollectionNew.contains(alumnoCollectionOldAlumno)) {
                    alumnoCollectionOldAlumno.getClaseCollection().remove(clase);
                    alumnoCollectionOldAlumno = em.merge(alumnoCollectionOldAlumno);
                }
            }
            for (Alumno alumnoCollectionNewAlumno : alumnoCollectionNew) {
                if (!alumnoCollectionOld.contains(alumnoCollectionNewAlumno)) {
                    alumnoCollectionNewAlumno.getClaseCollection().add(clase);
                    alumnoCollectionNewAlumno = em.merge(alumnoCollectionNewAlumno);
                }
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
            Maestro IDMaestroC = clase.getIDMaestroC();
            if (IDMaestroC != null) {
                IDMaestroC.getClaseCollection().remove(clase);
                IDMaestroC = em.merge(IDMaestroC);
            }
            Collection<Alumno> alumnoCollection = clase.getAlumnoCollection();
            for (Alumno alumnoCollectionAlumno : alumnoCollection) {
                alumnoCollectionAlumno.getClaseCollection().remove(clase);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
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
