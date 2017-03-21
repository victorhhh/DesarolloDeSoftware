/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacontrollers;

import basededatos.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import basededatos.Pagoingreso;
import basededatos.Clase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author victor
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) throws PreexistingEntityException, Exception {
        if (alumno.getClaseCollection() == null) {
            alumno.setClaseCollection(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoingreso IDPago = alumno.getIDPago();
            if (IDPago != null) {
                IDPago = em.getReference(IDPago.getClass(), IDPago.getIDIngreso());
                alumno.setIDPago(IDPago);
            }
            Collection<Clase> attachedClaseCollection = new ArrayList<Clase>();
            for (Clase claseCollectionClaseToAttach : alumno.getClaseCollection()) {
                claseCollectionClaseToAttach = em.getReference(claseCollectionClaseToAttach.getClass(), claseCollectionClaseToAttach.getIDClase());
                attachedClaseCollection.add(claseCollectionClaseToAttach);
            }
            alumno.setClaseCollection(attachedClaseCollection);
            em.persist(alumno);
            if (IDPago != null) {
                IDPago.getAlumnoCollection().add(alumno);
                IDPago = em.merge(IDPago);
            }
            for (Clase claseCollectionClase : alumno.getClaseCollection()) {
                Alumno oldIDAlumnoOfClaseCollectionClase = claseCollectionClase.getIDAlumno();
                claseCollectionClase.setIDAlumno(alumno);
                claseCollectionClase = em.merge(claseCollectionClase);
                if (oldIDAlumnoOfClaseCollectionClase != null) {
                    oldIDAlumnoOfClaseCollectionClase.getClaseCollection().remove(claseCollectionClase);
                    oldIDAlumnoOfClaseCollectionClase = em.merge(oldIDAlumnoOfClaseCollectionClase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumno(alumno.getIDMatricula()) != null) {
                throw new PreexistingEntityException("Alumno " + alumno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIDMatricula());
            Pagoingreso IDPagoOld = persistentAlumno.getIDPago();
            Pagoingreso IDPagoNew = alumno.getIDPago();
            Collection<Clase> claseCollectionOld = persistentAlumno.getClaseCollection();
            Collection<Clase> claseCollectionNew = alumno.getClaseCollection();
            if (IDPagoNew != null) {
                IDPagoNew = em.getReference(IDPagoNew.getClass(), IDPagoNew.getIDIngreso());
                alumno.setIDPago(IDPagoNew);
            }
            Collection<Clase> attachedClaseCollectionNew = new ArrayList<Clase>();
            for (Clase claseCollectionNewClaseToAttach : claseCollectionNew) {
                claseCollectionNewClaseToAttach = em.getReference(claseCollectionNewClaseToAttach.getClass(), claseCollectionNewClaseToAttach.getIDClase());
                attachedClaseCollectionNew.add(claseCollectionNewClaseToAttach);
            }
            claseCollectionNew = attachedClaseCollectionNew;
            alumno.setClaseCollection(claseCollectionNew);
            alumno = em.merge(alumno);
            if (IDPagoOld != null && !IDPagoOld.equals(IDPagoNew)) {
                IDPagoOld.getAlumnoCollection().remove(alumno);
                IDPagoOld = em.merge(IDPagoOld);
            }
            if (IDPagoNew != null && !IDPagoNew.equals(IDPagoOld)) {
                IDPagoNew.getAlumnoCollection().add(alumno);
                IDPagoNew = em.merge(IDPagoNew);
            }
            for (Clase claseCollectionOldClase : claseCollectionOld) {
                if (!claseCollectionNew.contains(claseCollectionOldClase)) {
                    claseCollectionOldClase.setIDAlumno(null);
                    claseCollectionOldClase = em.merge(claseCollectionOldClase);
                }
            }
            for (Clase claseCollectionNewClase : claseCollectionNew) {
                if (!claseCollectionOld.contains(claseCollectionNewClase)) {
                    Alumno oldIDAlumnoOfClaseCollectionNewClase = claseCollectionNewClase.getIDAlumno();
                    claseCollectionNewClase.setIDAlumno(alumno);
                    claseCollectionNewClase = em.merge(claseCollectionNewClase);
                    if (oldIDAlumnoOfClaseCollectionNewClase != null && !oldIDAlumnoOfClaseCollectionNewClase.equals(alumno)) {
                        oldIDAlumnoOfClaseCollectionNewClase.getClaseCollection().remove(claseCollectionNewClase);
                        oldIDAlumnoOfClaseCollectionNewClase = em.merge(oldIDAlumnoOfClaseCollectionNewClase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIDMatricula();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIDMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            Pagoingreso IDPago = alumno.getIDPago();
            if (IDPago != null) {
                IDPago.getAlumnoCollection().remove(alumno);
                IDPago = em.merge(IDPago);
            }
            Collection<Clase> claseCollection = alumno.getClaseCollection();
            for (Clase claseCollectionClase : claseCollection) {
                claseCollectionClase.setIDAlumno(null);
                claseCollectionClase = em.merge(claseCollectionClase);
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
