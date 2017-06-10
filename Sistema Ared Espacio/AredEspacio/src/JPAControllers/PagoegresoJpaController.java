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
import BaseDeDatos.Pagoegreso;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
 */
public class PagoegresoJpaController implements Serializable {

    public PagoegresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoegreso pagoegreso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro IDMaestroPE = pagoegreso.getIDMaestroPE();
            if (IDMaestroPE != null) {
                IDMaestroPE = em.getReference(IDMaestroPE.getClass(), IDMaestroPE.getIDMaestro());
                pagoegreso.setIDMaestroPE(IDMaestroPE);
            }
            em.persist(pagoegreso);
            if (IDMaestroPE != null) {
                IDMaestroPE.getPagoegresoList().add(pagoegreso);
                IDMaestroPE = em.merge(IDMaestroPE);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoegreso pagoegreso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoegreso persistentPagoegreso = em.find(Pagoegreso.class, pagoegreso.getIDEgreso());
            Maestro IDMaestroPEOld = persistentPagoegreso.getIDMaestroPE();
            Maestro IDMaestroPENew = pagoegreso.getIDMaestroPE();
            if (IDMaestroPENew != null) {
                IDMaestroPENew = em.getReference(IDMaestroPENew.getClass(), IDMaestroPENew.getIDMaestro());
                pagoegreso.setIDMaestroPE(IDMaestroPENew);
            }
            pagoegreso = em.merge(pagoegreso);
            if (IDMaestroPEOld != null && !IDMaestroPEOld.equals(IDMaestroPENew)) {
                IDMaestroPEOld.getPagoegresoList().remove(pagoegreso);
                IDMaestroPEOld = em.merge(IDMaestroPEOld);
            }
            if (IDMaestroPENew != null && !IDMaestroPENew.equals(IDMaestroPEOld)) {
                IDMaestroPENew.getPagoegresoList().add(pagoegreso);
                IDMaestroPENew = em.merge(IDMaestroPENew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Maestro IDMaestroPE = pagoegreso.getIDMaestroPE();
            if (IDMaestroPE != null) {
                IDMaestroPE.getPagoegresoList().remove(pagoegreso);
                IDMaestroPE = em.merge(IDMaestroPE);
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
