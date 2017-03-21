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
import basededatos.Maestro;
import basededatos.Pagoegreso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author victor
 */
public class PagoegresoJpaController implements Serializable {

    public PagoegresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoegreso pagoegreso) throws PreexistingEntityException, Exception {
        if (pagoegreso.getMaestroCollection() == null) {
            pagoegreso.setMaestroCollection(new ArrayList<Maestro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Maestro> attachedMaestroCollection = new ArrayList<Maestro>();
            for (Maestro maestroCollectionMaestroToAttach : pagoegreso.getMaestroCollection()) {
                maestroCollectionMaestroToAttach = em.getReference(maestroCollectionMaestroToAttach.getClass(), maestroCollectionMaestroToAttach.getIDMaestro());
                attachedMaestroCollection.add(maestroCollectionMaestroToAttach);
            }
            pagoegreso.setMaestroCollection(attachedMaestroCollection);
            em.persist(pagoegreso);
            for (Maestro maestroCollectionMaestro : pagoegreso.getMaestroCollection()) {
                Pagoegreso oldIDPagoEgresoOfMaestroCollectionMaestro = maestroCollectionMaestro.getIDPagoEgreso();
                maestroCollectionMaestro.setIDPagoEgreso(pagoegreso);
                maestroCollectionMaestro = em.merge(maestroCollectionMaestro);
                if (oldIDPagoEgresoOfMaestroCollectionMaestro != null) {
                    oldIDPagoEgresoOfMaestroCollectionMaestro.getMaestroCollection().remove(maestroCollectionMaestro);
                    oldIDPagoEgresoOfMaestroCollectionMaestro = em.merge(oldIDPagoEgresoOfMaestroCollectionMaestro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoegreso(pagoegreso.getIDEgreso()) != null) {
                throw new PreexistingEntityException("Pagoegreso " + pagoegreso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoegreso pagoegreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoegreso persistentPagoegreso = em.find(Pagoegreso.class, pagoegreso.getIDEgreso());
            Collection<Maestro> maestroCollectionOld = persistentPagoegreso.getMaestroCollection();
            Collection<Maestro> maestroCollectionNew = pagoegreso.getMaestroCollection();
            List<String> illegalOrphanMessages = null;
            for (Maestro maestroCollectionOldMaestro : maestroCollectionOld) {
                if (!maestroCollectionNew.contains(maestroCollectionOldMaestro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maestro " + maestroCollectionOldMaestro + " since its IDPagoEgreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Maestro> attachedMaestroCollectionNew = new ArrayList<Maestro>();
            for (Maestro maestroCollectionNewMaestroToAttach : maestroCollectionNew) {
                maestroCollectionNewMaestroToAttach = em.getReference(maestroCollectionNewMaestroToAttach.getClass(), maestroCollectionNewMaestroToAttach.getIDMaestro());
                attachedMaestroCollectionNew.add(maestroCollectionNewMaestroToAttach);
            }
            maestroCollectionNew = attachedMaestroCollectionNew;
            pagoegreso.setMaestroCollection(maestroCollectionNew);
            pagoegreso = em.merge(pagoegreso);
            for (Maestro maestroCollectionNewMaestro : maestroCollectionNew) {
                if (!maestroCollectionOld.contains(maestroCollectionNewMaestro)) {
                    Pagoegreso oldIDPagoEgresoOfMaestroCollectionNewMaestro = maestroCollectionNewMaestro.getIDPagoEgreso();
                    maestroCollectionNewMaestro.setIDPagoEgreso(pagoegreso);
                    maestroCollectionNewMaestro = em.merge(maestroCollectionNewMaestro);
                    if (oldIDPagoEgresoOfMaestroCollectionNewMaestro != null && !oldIDPagoEgresoOfMaestroCollectionNewMaestro.equals(pagoegreso)) {
                        oldIDPagoEgresoOfMaestroCollectionNewMaestro.getMaestroCollection().remove(maestroCollectionNewMaestro);
                        oldIDPagoEgresoOfMaestroCollectionNewMaestro = em.merge(oldIDPagoEgresoOfMaestroCollectionNewMaestro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoegreso.getIDEgreso();
                if (findPagoegreso(id) == null) {
                    throw new NonexistentEntityException("The pagoegreso with id " + id + " no longer exists.");
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
            Pagoegreso pagoegreso;
            try {
                pagoegreso = em.getReference(Pagoegreso.class, id);
                pagoegreso.getIDEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoegreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Maestro> maestroCollectionOrphanCheck = pagoegreso.getMaestroCollection();
            for (Maestro maestroCollectionOrphanCheckMaestro : maestroCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pagoegreso (" + pagoegreso + ") cannot be destroyed since the Maestro " + maestroCollectionOrphanCheckMaestro + " in its maestroCollection field has a non-nullable IDPagoEgreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pagoegreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagoegreso> findPagoegresoEntities() {
        return findPagoegresoEntities(true, -1, -1);
    }

    public List<Pagoegreso> findPagoegresoEntities(int maxResults, int firstResult) {
        return findPagoegresoEntities(false, maxResults, firstResult);
    }

    private List<Pagoegreso> findPagoegresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagoegreso.class));
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

    public Pagoegreso findPagoegreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagoegreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoegresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagoegreso> rt = cq.from(Pagoegreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
