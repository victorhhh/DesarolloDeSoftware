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
import basededatos.Pagoegreso;
import basededatos.Clase;
import basededatos.Maestro;
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
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) throws PreexistingEntityException, Exception {
        if (maestro.getClaseCollection() == null) {
            maestro.setClaseCollection(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoegreso IDPagoEgreso = maestro.getIDPagoEgreso();
            if (IDPagoEgreso != null) {
                IDPagoEgreso = em.getReference(IDPagoEgreso.getClass(), IDPagoEgreso.getIDEgreso());
                maestro.setIDPagoEgreso(IDPagoEgreso);
            }
            Collection<Clase> attachedClaseCollection = new ArrayList<Clase>();
            for (Clase claseCollectionClaseToAttach : maestro.getClaseCollection()) {
                claseCollectionClaseToAttach = em.getReference(claseCollectionClaseToAttach.getClass(), claseCollectionClaseToAttach.getIDClase());
                attachedClaseCollection.add(claseCollectionClaseToAttach);
            }
            maestro.setClaseCollection(attachedClaseCollection);
            em.persist(maestro);
            if (IDPagoEgreso != null) {
                IDPagoEgreso.getMaestroCollection().add(maestro);
                IDPagoEgreso = em.merge(IDPagoEgreso);
            }
            for (Clase claseCollectionClase : maestro.getClaseCollection()) {
                Maestro oldIDMaestroOfClaseCollectionClase = claseCollectionClase.getIDMaestro();
                claseCollectionClase.setIDMaestro(maestro);
                claseCollectionClase = em.merge(claseCollectionClase);
                if (oldIDMaestroOfClaseCollectionClase != null) {
                    oldIDMaestroOfClaseCollectionClase.getClaseCollection().remove(claseCollectionClase);
                    oldIDMaestroOfClaseCollectionClase = em.merge(oldIDMaestroOfClaseCollectionClase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaestro(maestro.getIDMaestro()) != null) {
                throw new PreexistingEntityException("Maestro " + maestro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestro maestro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getIDMaestro());
            Pagoegreso IDPagoEgresoOld = persistentMaestro.getIDPagoEgreso();
            Pagoegreso IDPagoEgresoNew = maestro.getIDPagoEgreso();
            Collection<Clase> claseCollectionOld = persistentMaestro.getClaseCollection();
            Collection<Clase> claseCollectionNew = maestro.getClaseCollection();
            if (IDPagoEgresoNew != null) {
                IDPagoEgresoNew = em.getReference(IDPagoEgresoNew.getClass(), IDPagoEgresoNew.getIDEgreso());
                maestro.setIDPagoEgreso(IDPagoEgresoNew);
            }
            Collection<Clase> attachedClaseCollectionNew = new ArrayList<Clase>();
            for (Clase claseCollectionNewClaseToAttach : claseCollectionNew) {
                claseCollectionNewClaseToAttach = em.getReference(claseCollectionNewClaseToAttach.getClass(), claseCollectionNewClaseToAttach.getIDClase());
                attachedClaseCollectionNew.add(claseCollectionNewClaseToAttach);
            }
            claseCollectionNew = attachedClaseCollectionNew;
            maestro.setClaseCollection(claseCollectionNew);
            maestro = em.merge(maestro);
            if (IDPagoEgresoOld != null && !IDPagoEgresoOld.equals(IDPagoEgresoNew)) {
                IDPagoEgresoOld.getMaestroCollection().remove(maestro);
                IDPagoEgresoOld = em.merge(IDPagoEgresoOld);
            }
            if (IDPagoEgresoNew != null && !IDPagoEgresoNew.equals(IDPagoEgresoOld)) {
                IDPagoEgresoNew.getMaestroCollection().add(maestro);
                IDPagoEgresoNew = em.merge(IDPagoEgresoNew);
            }
            for (Clase claseCollectionOldClase : claseCollectionOld) {
                if (!claseCollectionNew.contains(claseCollectionOldClase)) {
                    claseCollectionOldClase.setIDMaestro(null);
                    claseCollectionOldClase = em.merge(claseCollectionOldClase);
                }
            }
            for (Clase claseCollectionNewClase : claseCollectionNew) {
                if (!claseCollectionOld.contains(claseCollectionNewClase)) {
                    Maestro oldIDMaestroOfClaseCollectionNewClase = claseCollectionNewClase.getIDMaestro();
                    claseCollectionNewClase.setIDMaestro(maestro);
                    claseCollectionNewClase = em.merge(claseCollectionNewClase);
                    if (oldIDMaestroOfClaseCollectionNewClase != null && !oldIDMaestroOfClaseCollectionNewClase.equals(maestro)) {
                        oldIDMaestroOfClaseCollectionNewClase.getClaseCollection().remove(claseCollectionNewClase);
                        oldIDMaestroOfClaseCollectionNewClase = em.merge(oldIDMaestroOfClaseCollectionNewClase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maestro.getIDMaestro();
                if (findMaestro(id) == null) {
                    throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.");
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
            Maestro maestro;
            try {
                maestro = em.getReference(Maestro.class, id);
                maestro.getIDMaestro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.", enfe);
            }
            Pagoegreso IDPagoEgreso = maestro.getIDPagoEgreso();
            if (IDPagoEgreso != null) {
                IDPagoEgreso.getMaestroCollection().remove(maestro);
                IDPagoEgreso = em.merge(IDPagoEgreso);
            }
            Collection<Clase> claseCollection = maestro.getClaseCollection();
            for (Clase claseCollectionClase : claseCollection) {
                claseCollectionClase.setIDMaestro(null);
                claseCollectionClase = em.merge(claseCollectionClase);
            }
            em.remove(maestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestro> findMaestroEntities() {
        return findMaestroEntities(true, -1, -1);
    }

    public List<Maestro> findMaestroEntities(int maxResults, int firstResult) {
        return findMaestroEntities(false, maxResults, firstResult);
    }

    private List<Maestro> findMaestroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestro.class));
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

    public Maestro findMaestro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestro> rt = cq.from(Maestro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
