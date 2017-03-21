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
import basededatos.Pagoingreso;
import basededatos.Promocion;
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
public class PromocionJpaController implements Serializable {

    public PromocionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocion promocion) throws PreexistingEntityException, Exception {
        if (promocion.getPagoingresoCollection() == null) {
            promocion.setPagoingresoCollection(new ArrayList<Pagoingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pagoingreso> attachedPagoingresoCollection = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionPagoingresoToAttach : promocion.getPagoingresoCollection()) {
                pagoingresoCollectionPagoingresoToAttach = em.getReference(pagoingresoCollectionPagoingresoToAttach.getClass(), pagoingresoCollectionPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollection.add(pagoingresoCollectionPagoingresoToAttach);
            }
            promocion.setPagoingresoCollection(attachedPagoingresoCollection);
            em.persist(promocion);
            for (Pagoingreso pagoingresoCollectionPagoingreso : promocion.getPagoingresoCollection()) {
                Promocion oldIDPromocionOfPagoingresoCollectionPagoingreso = pagoingresoCollectionPagoingreso.getIDPromocion();
                pagoingresoCollectionPagoingreso.setIDPromocion(promocion);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
                if (oldIDPromocionOfPagoingresoCollectionPagoingreso != null) {
                    oldIDPromocionOfPagoingresoCollectionPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionPagoingreso);
                    oldIDPromocionOfPagoingresoCollectionPagoingreso = em.merge(oldIDPromocionOfPagoingresoCollectionPagoingreso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromocion(promocion.getIDPromocion()) != null) {
                throw new PreexistingEntityException("Promocion " + promocion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocion promocion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion persistentPromocion = em.find(Promocion.class, promocion.getIDPromocion());
            Collection<Pagoingreso> pagoingresoCollectionOld = persistentPromocion.getPagoingresoCollection();
            Collection<Pagoingreso> pagoingresoCollectionNew = promocion.getPagoingresoCollection();
            Collection<Pagoingreso> attachedPagoingresoCollectionNew = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionNewPagoingresoToAttach : pagoingresoCollectionNew) {
                pagoingresoCollectionNewPagoingresoToAttach = em.getReference(pagoingresoCollectionNewPagoingresoToAttach.getClass(), pagoingresoCollectionNewPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollectionNew.add(pagoingresoCollectionNewPagoingresoToAttach);
            }
            pagoingresoCollectionNew = attachedPagoingresoCollectionNew;
            promocion.setPagoingresoCollection(pagoingresoCollectionNew);
            promocion = em.merge(promocion);
            for (Pagoingreso pagoingresoCollectionOldPagoingreso : pagoingresoCollectionOld) {
                if (!pagoingresoCollectionNew.contains(pagoingresoCollectionOldPagoingreso)) {
                    pagoingresoCollectionOldPagoingreso.setIDPromocion(null);
                    pagoingresoCollectionOldPagoingreso = em.merge(pagoingresoCollectionOldPagoingreso);
                }
            }
            for (Pagoingreso pagoingresoCollectionNewPagoingreso : pagoingresoCollectionNew) {
                if (!pagoingresoCollectionOld.contains(pagoingresoCollectionNewPagoingreso)) {
                    Promocion oldIDPromocionOfPagoingresoCollectionNewPagoingreso = pagoingresoCollectionNewPagoingreso.getIDPromocion();
                    pagoingresoCollectionNewPagoingreso.setIDPromocion(promocion);
                    pagoingresoCollectionNewPagoingreso = em.merge(pagoingresoCollectionNewPagoingreso);
                    if (oldIDPromocionOfPagoingresoCollectionNewPagoingreso != null && !oldIDPromocionOfPagoingresoCollectionNewPagoingreso.equals(promocion)) {
                        oldIDPromocionOfPagoingresoCollectionNewPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionNewPagoingreso);
                        oldIDPromocionOfPagoingresoCollectionNewPagoingreso = em.merge(oldIDPromocionOfPagoingresoCollectionNewPagoingreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocion.getIDPromocion();
                if (findPromocion(id) == null) {
                    throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.");
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
            Promocion promocion;
            try {
                promocion = em.getReference(Promocion.class, id);
                promocion.getIDPromocion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.", enfe);
            }
            Collection<Pagoingreso> pagoingresoCollection = promocion.getPagoingresoCollection();
            for (Pagoingreso pagoingresoCollectionPagoingreso : pagoingresoCollection) {
                pagoingresoCollectionPagoingreso.setIDPromocion(null);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
            }
            em.remove(promocion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocion> findPromocionEntities() {
        return findPromocionEntities(true, -1, -1);
    }

    public List<Promocion> findPromocionEntities(int maxResults, int firstResult) {
        return findPromocionEntities(false, maxResults, firstResult);
    }

    private List<Promocion> findPromocionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocion.class));
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

    public Promocion findPromocion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocion> rt = cq.from(Promocion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
