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
import BaseDeDatos.Promocion;
import BaseDeDatos.Alumno;
import BaseDeDatos.Mensualidad;
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
public class MensualidadJpaController implements Serializable {

    public MensualidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mensualidad mensualidad) {
        if (mensualidad.getAlumnoCollection() == null) {
            mensualidad.setAlumnoCollection(new ArrayList<Alumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion IDPromocionM = mensualidad.getIDPromocionM();
            if (IDPromocionM != null) {
                IDPromocionM = em.getReference(IDPromocionM.getClass(), IDPromocionM.getIDPromocion());
                mensualidad.setIDPromocionM(IDPromocionM);
            }
            Collection<Alumno> attachedAlumnoCollection = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionAlumnoToAttach : mensualidad.getAlumnoCollection()) {
                alumnoCollectionAlumnoToAttach = em.getReference(alumnoCollectionAlumnoToAttach.getClass(), alumnoCollectionAlumnoToAttach.getIDAlumno());
                attachedAlumnoCollection.add(alumnoCollectionAlumnoToAttach);
            }
            mensualidad.setAlumnoCollection(attachedAlumnoCollection);
            em.persist(mensualidad);
            if (IDPromocionM != null) {
                IDPromocionM.getMensualidadCollection().add(mensualidad);
                IDPromocionM = em.merge(IDPromocionM);
            }
            for (Alumno alumnoCollectionAlumno : mensualidad.getAlumnoCollection()) {
                Mensualidad oldIDMensualidadAOfAlumnoCollectionAlumno = alumnoCollectionAlumno.getIDMensualidadA();
                alumnoCollectionAlumno.setIDMensualidadA(mensualidad);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
                if (oldIDMensualidadAOfAlumnoCollectionAlumno != null) {
                    oldIDMensualidadAOfAlumnoCollectionAlumno.getAlumnoCollection().remove(alumnoCollectionAlumno);
                    oldIDMensualidadAOfAlumnoCollectionAlumno = em.merge(oldIDMensualidadAOfAlumnoCollectionAlumno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mensualidad mensualidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mensualidad persistentMensualidad = em.find(Mensualidad.class, mensualidad.getIDMensualidad());
            Promocion IDPromocionMOld = persistentMensualidad.getIDPromocionM();
            Promocion IDPromocionMNew = mensualidad.getIDPromocionM();
            Collection<Alumno> alumnoCollectionOld = persistentMensualidad.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = mensualidad.getAlumnoCollection();
            if (IDPromocionMNew != null) {
                IDPromocionMNew = em.getReference(IDPromocionMNew.getClass(), IDPromocionMNew.getIDPromocion());
                mensualidad.setIDPromocionM(IDPromocionMNew);
            }
            Collection<Alumno> attachedAlumnoCollectionNew = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionNewAlumnoToAttach : alumnoCollectionNew) {
                alumnoCollectionNewAlumnoToAttach = em.getReference(alumnoCollectionNewAlumnoToAttach.getClass(), alumnoCollectionNewAlumnoToAttach.getIDAlumno());
                attachedAlumnoCollectionNew.add(alumnoCollectionNewAlumnoToAttach);
            }
            alumnoCollectionNew = attachedAlumnoCollectionNew;
            mensualidad.setAlumnoCollection(alumnoCollectionNew);
            mensualidad = em.merge(mensualidad);
            if (IDPromocionMOld != null && !IDPromocionMOld.equals(IDPromocionMNew)) {
                IDPromocionMOld.getMensualidadCollection().remove(mensualidad);
                IDPromocionMOld = em.merge(IDPromocionMOld);
            }
            if (IDPromocionMNew != null && !IDPromocionMNew.equals(IDPromocionMOld)) {
                IDPromocionMNew.getMensualidadCollection().add(mensualidad);
                IDPromocionMNew = em.merge(IDPromocionMNew);
            }
            for (Alumno alumnoCollectionOldAlumno : alumnoCollectionOld) {
                if (!alumnoCollectionNew.contains(alumnoCollectionOldAlumno)) {
                    alumnoCollectionOldAlumno.setIDMensualidadA(null);
                    alumnoCollectionOldAlumno = em.merge(alumnoCollectionOldAlumno);
                }
            }
            for (Alumno alumnoCollectionNewAlumno : alumnoCollectionNew) {
                if (!alumnoCollectionOld.contains(alumnoCollectionNewAlumno)) {
                    Mensualidad oldIDMensualidadAOfAlumnoCollectionNewAlumno = alumnoCollectionNewAlumno.getIDMensualidadA();
                    alumnoCollectionNewAlumno.setIDMensualidadA(mensualidad);
                    alumnoCollectionNewAlumno = em.merge(alumnoCollectionNewAlumno);
                    if (oldIDMensualidadAOfAlumnoCollectionNewAlumno != null && !oldIDMensualidadAOfAlumnoCollectionNewAlumno.equals(mensualidad)) {
                        oldIDMensualidadAOfAlumnoCollectionNewAlumno.getAlumnoCollection().remove(alumnoCollectionNewAlumno);
                        oldIDMensualidadAOfAlumnoCollectionNewAlumno = em.merge(oldIDMensualidadAOfAlumnoCollectionNewAlumno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mensualidad.getIDMensualidad();
                if (findMensualidad(id) == null) {
                    throw new NonexistentEntityException("The mensualidad with id " + id + " no longer exists.");
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
            Mensualidad mensualidad;
            try {
                mensualidad = em.getReference(Mensualidad.class, id);
                mensualidad.getIDMensualidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensualidad with id " + id + " no longer exists.", enfe);
            }
            Promocion IDPromocionM = mensualidad.getIDPromocionM();
            if (IDPromocionM != null) {
                IDPromocionM.getMensualidadCollection().remove(mensualidad);
                IDPromocionM = em.merge(IDPromocionM);
            }
            Collection<Alumno> alumnoCollection = mensualidad.getAlumnoCollection();
            for (Alumno alumnoCollectionAlumno : alumnoCollection) {
                alumnoCollectionAlumno.setIDMensualidadA(null);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            em.remove(mensualidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mensualidad> findMensualidadEntities() {
        return findMensualidadEntities(true, -1, -1);
    }

    public List<Mensualidad> findMensualidadEntities(int maxResults, int firstResult) {
        return findMensualidadEntities(false, maxResults, firstResult);
    }

    private List<Mensualidad> findMensualidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensualidad.class));
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

    public Mensualidad findMensualidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mensualidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getMensualidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mensualidad> rt = cq.from(Mensualidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
