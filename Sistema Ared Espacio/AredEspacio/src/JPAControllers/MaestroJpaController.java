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
import BaseDeDatos.Pagoegreso;
import java.util.ArrayList;
import java.util.Collection;
import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) {
        if (maestro.getPagoegresoCollection() == null) {
            maestro.setPagoegresoCollection(new ArrayList<Pagoegreso>());
        }
        if (maestro.getClaseCollection() == null) {
            maestro.setClaseCollection(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pagoegreso> attachedPagoegresoCollection = new ArrayList<Pagoegreso>();
            for (Pagoegreso pagoegresoCollectionPagoegresoToAttach : maestro.getPagoegresoCollection()) {
                pagoegresoCollectionPagoegresoToAttach = em.getReference(pagoegresoCollectionPagoegresoToAttach.getClass(), pagoegresoCollectionPagoegresoToAttach.getIDEgreso());
                attachedPagoegresoCollection.add(pagoegresoCollectionPagoegresoToAttach);
            }
            maestro.setPagoegresoCollection(attachedPagoegresoCollection);
            Collection<Clase> attachedClaseCollection = new ArrayList<Clase>();
            for (Clase claseCollectionClaseToAttach : maestro.getClaseCollection()) {
                claseCollectionClaseToAttach = em.getReference(claseCollectionClaseToAttach.getClass(), claseCollectionClaseToAttach.getIDClase());
                attachedClaseCollection.add(claseCollectionClaseToAttach);
            }
            maestro.setClaseCollection(attachedClaseCollection);
            em.persist(maestro);
            for (Pagoegreso pagoegresoCollectionPagoegreso : maestro.getPagoegresoCollection()) {
                Maestro oldIDMaestroPEOfPagoegresoCollectionPagoegreso = pagoegresoCollectionPagoegreso.getIDMaestroPE();
                pagoegresoCollectionPagoegreso.setIDMaestroPE(maestro);
                pagoegresoCollectionPagoegreso = em.merge(pagoegresoCollectionPagoegreso);
                if (oldIDMaestroPEOfPagoegresoCollectionPagoegreso != null) {
                    oldIDMaestroPEOfPagoegresoCollectionPagoegreso.getPagoegresoCollection().remove(pagoegresoCollectionPagoegreso);
                    oldIDMaestroPEOfPagoegresoCollectionPagoegreso = em.merge(oldIDMaestroPEOfPagoegresoCollectionPagoegreso);
                }
            }
            for (Clase claseCollectionClase : maestro.getClaseCollection()) {
                Maestro oldIDMaestroCOfClaseCollectionClase = claseCollectionClase.getIDMaestroC();
                claseCollectionClase.setIDMaestroC(maestro);
                claseCollectionClase = em.merge(claseCollectionClase);
                if (oldIDMaestroCOfClaseCollectionClase != null) {
                    oldIDMaestroCOfClaseCollectionClase.getClaseCollection().remove(claseCollectionClase);
                    oldIDMaestroCOfClaseCollectionClase = em.merge(oldIDMaestroCOfClaseCollectionClase);
                }
            }
            em.getTransaction().commit();
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
            Collection<Pagoegreso> pagoegresoCollectionOld = persistentMaestro.getPagoegresoCollection();
            Collection<Pagoegreso> pagoegresoCollectionNew = maestro.getPagoegresoCollection();
            Collection<Clase> claseCollectionOld = persistentMaestro.getClaseCollection();
            Collection<Clase> claseCollectionNew = maestro.getClaseCollection();
            Collection<Pagoegreso> attachedPagoegresoCollectionNew = new ArrayList<Pagoegreso>();
            for (Pagoegreso pagoegresoCollectionNewPagoegresoToAttach : pagoegresoCollectionNew) {
                pagoegresoCollectionNewPagoegresoToAttach = em.getReference(pagoegresoCollectionNewPagoegresoToAttach.getClass(), pagoegresoCollectionNewPagoegresoToAttach.getIDEgreso());
                attachedPagoegresoCollectionNew.add(pagoegresoCollectionNewPagoegresoToAttach);
            }
            pagoegresoCollectionNew = attachedPagoegresoCollectionNew;
            maestro.setPagoegresoCollection(pagoegresoCollectionNew);
            Collection<Clase> attachedClaseCollectionNew = new ArrayList<Clase>();
            for (Clase claseCollectionNewClaseToAttach : claseCollectionNew) {
                claseCollectionNewClaseToAttach = em.getReference(claseCollectionNewClaseToAttach.getClass(), claseCollectionNewClaseToAttach.getIDClase());
                attachedClaseCollectionNew.add(claseCollectionNewClaseToAttach);
            }
            claseCollectionNew = attachedClaseCollectionNew;
            maestro.setClaseCollection(claseCollectionNew);
            maestro = em.merge(maestro);
            for (Pagoegreso pagoegresoCollectionOldPagoegreso : pagoegresoCollectionOld) {
                if (!pagoegresoCollectionNew.contains(pagoegresoCollectionOldPagoegreso)) {
                    pagoegresoCollectionOldPagoegreso.setIDMaestroPE(null);
                    pagoegresoCollectionOldPagoegreso = em.merge(pagoegresoCollectionOldPagoegreso);
                }
            }
            for (Pagoegreso pagoegresoCollectionNewPagoegreso : pagoegresoCollectionNew) {
                if (!pagoegresoCollectionOld.contains(pagoegresoCollectionNewPagoegreso)) {
                    Maestro oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso = pagoegresoCollectionNewPagoegreso.getIDMaestroPE();
                    pagoegresoCollectionNewPagoegreso.setIDMaestroPE(maestro);
                    pagoegresoCollectionNewPagoegreso = em.merge(pagoegresoCollectionNewPagoegreso);
                    if (oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso != null && !oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso.equals(maestro)) {
                        oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso.getPagoegresoCollection().remove(pagoegresoCollectionNewPagoegreso);
                        oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso = em.merge(oldIDMaestroPEOfPagoegresoCollectionNewPagoegreso);
                    }
                }
            }
            for (Clase claseCollectionOldClase : claseCollectionOld) {
                if (!claseCollectionNew.contains(claseCollectionOldClase)) {
                    claseCollectionOldClase.setIDMaestroC(null);
                    claseCollectionOldClase = em.merge(claseCollectionOldClase);
                }
            }
            for (Clase claseCollectionNewClase : claseCollectionNew) {
                if (!claseCollectionOld.contains(claseCollectionNewClase)) {
                    Maestro oldIDMaestroCOfClaseCollectionNewClase = claseCollectionNewClase.getIDMaestroC();
                    claseCollectionNewClase.setIDMaestroC(maestro);
                    claseCollectionNewClase = em.merge(claseCollectionNewClase);
                    if (oldIDMaestroCOfClaseCollectionNewClase != null && !oldIDMaestroCOfClaseCollectionNewClase.equals(maestro)) {
                        oldIDMaestroCOfClaseCollectionNewClase.getClaseCollection().remove(claseCollectionNewClase);
                        oldIDMaestroCOfClaseCollectionNewClase = em.merge(oldIDMaestroCOfClaseCollectionNewClase);
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
            Collection<Pagoegreso> pagoegresoCollection = maestro.getPagoegresoCollection();
            for (Pagoegreso pagoegresoCollectionPagoegreso : pagoegresoCollection) {
                pagoegresoCollectionPagoegreso.setIDMaestroPE(null);
                pagoegresoCollectionPagoegreso = em.merge(pagoegresoCollectionPagoegreso);
            }
            Collection<Clase> claseCollection = maestro.getClaseCollection();
            for (Clase claseCollectionClase : claseCollection) {
                claseCollectionClase.setIDMaestroC(null);
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
