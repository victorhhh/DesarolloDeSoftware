/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacontrollers;

import basededatos.Inscripcion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import basededatos.Pagoingreso;
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
public class InscripcionJpaController implements Serializable {

    public InscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscripcion inscripcion) throws PreexistingEntityException, Exception {
        if (inscripcion.getPagoingresoCollection() == null) {
            inscripcion.setPagoingresoCollection(new ArrayList<Pagoingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pagoingreso> attachedPagoingresoCollection = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionPagoingresoToAttach : inscripcion.getPagoingresoCollection()) {
                pagoingresoCollectionPagoingresoToAttach = em.getReference(pagoingresoCollectionPagoingresoToAttach.getClass(), pagoingresoCollectionPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollection.add(pagoingresoCollectionPagoingresoToAttach);
            }
            inscripcion.setPagoingresoCollection(attachedPagoingresoCollection);
            em.persist(inscripcion);
            for (Pagoingreso pagoingresoCollectionPagoingreso : inscripcion.getPagoingresoCollection()) {
                Inscripcion oldIDInscripcionOfPagoingresoCollectionPagoingreso = pagoingresoCollectionPagoingreso.getIDInscripcion();
                pagoingresoCollectionPagoingreso.setIDInscripcion(inscripcion);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
                if (oldIDInscripcionOfPagoingresoCollectionPagoingreso != null) {
                    oldIDInscripcionOfPagoingresoCollectionPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionPagoingreso);
                    oldIDInscripcionOfPagoingresoCollectionPagoingreso = em.merge(oldIDInscripcionOfPagoingresoCollectionPagoingreso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInscripcion(inscripcion.getIDInscripcion()) != null) {
                throw new PreexistingEntityException("Inscripcion " + inscripcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscripcion inscripcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion persistentInscripcion = em.find(Inscripcion.class, inscripcion.getIDInscripcion());
            Collection<Pagoingreso> pagoingresoCollectionOld = persistentInscripcion.getPagoingresoCollection();
            Collection<Pagoingreso> pagoingresoCollectionNew = inscripcion.getPagoingresoCollection();
            Collection<Pagoingreso> attachedPagoingresoCollectionNew = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionNewPagoingresoToAttach : pagoingresoCollectionNew) {
                pagoingresoCollectionNewPagoingresoToAttach = em.getReference(pagoingresoCollectionNewPagoingresoToAttach.getClass(), pagoingresoCollectionNewPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollectionNew.add(pagoingresoCollectionNewPagoingresoToAttach);
            }
            pagoingresoCollectionNew = attachedPagoingresoCollectionNew;
            inscripcion.setPagoingresoCollection(pagoingresoCollectionNew);
            inscripcion = em.merge(inscripcion);
            for (Pagoingreso pagoingresoCollectionOldPagoingreso : pagoingresoCollectionOld) {
                if (!pagoingresoCollectionNew.contains(pagoingresoCollectionOldPagoingreso)) {
                    pagoingresoCollectionOldPagoingreso.setIDInscripcion(null);
                    pagoingresoCollectionOldPagoingreso = em.merge(pagoingresoCollectionOldPagoingreso);
                }
            }
            for (Pagoingreso pagoingresoCollectionNewPagoingreso : pagoingresoCollectionNew) {
                if (!pagoingresoCollectionOld.contains(pagoingresoCollectionNewPagoingreso)) {
                    Inscripcion oldIDInscripcionOfPagoingresoCollectionNewPagoingreso = pagoingresoCollectionNewPagoingreso.getIDInscripcion();
                    pagoingresoCollectionNewPagoingreso.setIDInscripcion(inscripcion);
                    pagoingresoCollectionNewPagoingreso = em.merge(pagoingresoCollectionNewPagoingreso);
                    if (oldIDInscripcionOfPagoingresoCollectionNewPagoingreso != null && !oldIDInscripcionOfPagoingresoCollectionNewPagoingreso.equals(inscripcion)) {
                        oldIDInscripcionOfPagoingresoCollectionNewPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionNewPagoingreso);
                        oldIDInscripcionOfPagoingresoCollectionNewPagoingreso = em.merge(oldIDInscripcionOfPagoingresoCollectionNewPagoingreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripcion.getIDInscripcion();
                if (findInscripcion(id) == null) {
                    throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.");
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
            Inscripcion inscripcion;
            try {
                inscripcion = em.getReference(Inscripcion.class, id);
                inscripcion.getIDInscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.", enfe);
            }
            Collection<Pagoingreso> pagoingresoCollection = inscripcion.getPagoingresoCollection();
            for (Pagoingreso pagoingresoCollectionPagoingreso : pagoingresoCollection) {
                pagoingresoCollectionPagoingreso.setIDInscripcion(null);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
            }
            em.remove(inscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscripcion> findInscripcionEntities() {
        return findInscripcionEntities(true, -1, -1);
    }

    public List<Inscripcion> findInscripcionEntities(int maxResults, int firstResult) {
        return findInscripcionEntities(false, maxResults, firstResult);
    }

    private List<Inscripcion> findInscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscripcion.class));
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

    public Inscripcion findInscripcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscripcion> rt = cq.from(Inscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
