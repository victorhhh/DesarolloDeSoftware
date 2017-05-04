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
import BaseDeDatos.Inscripcion;
import java.util.ArrayList;
import java.util.Collection;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Promocion;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class PromocionJpaController implements Serializable {

    public PromocionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocion promocion) {
        if (promocion.getInscripcionCollection() == null) {
            promocion.setInscripcionCollection(new ArrayList<Inscripcion>());
        }
        if (promocion.getMensualidadCollection() == null) {
            promocion.setMensualidadCollection(new ArrayList<Mensualidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Inscripcion> attachedInscripcionCollection = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionInscripcionToAttach : promocion.getInscripcionCollection()) {
                inscripcionCollectionInscripcionToAttach = em.getReference(inscripcionCollectionInscripcionToAttach.getClass(), inscripcionCollectionInscripcionToAttach.getIDInscripcion());
                attachedInscripcionCollection.add(inscripcionCollectionInscripcionToAttach);
            }
            promocion.setInscripcionCollection(attachedInscripcionCollection);
            Collection<Mensualidad> attachedMensualidadCollection = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadCollectionMensualidadToAttach : promocion.getMensualidadCollection()) {
                mensualidadCollectionMensualidadToAttach = em.getReference(mensualidadCollectionMensualidadToAttach.getClass(), mensualidadCollectionMensualidadToAttach.getIDMensualidad());
                attachedMensualidadCollection.add(mensualidadCollectionMensualidadToAttach);
            }
            promocion.setMensualidadCollection(attachedMensualidadCollection);
            em.persist(promocion);
            for (Inscripcion inscripcionCollectionInscripcion : promocion.getInscripcionCollection()) {
                Promocion oldIDPromocionIOfInscripcionCollectionInscripcion = inscripcionCollectionInscripcion.getIDPromocionI();
                inscripcionCollectionInscripcion.setIDPromocionI(promocion);
                inscripcionCollectionInscripcion = em.merge(inscripcionCollectionInscripcion);
                if (oldIDPromocionIOfInscripcionCollectionInscripcion != null) {
                    oldIDPromocionIOfInscripcionCollectionInscripcion.getInscripcionCollection().remove(inscripcionCollectionInscripcion);
                    oldIDPromocionIOfInscripcionCollectionInscripcion = em.merge(oldIDPromocionIOfInscripcionCollectionInscripcion);
                }
            }
            for (Mensualidad mensualidadCollectionMensualidad : promocion.getMensualidadCollection()) {
                Promocion oldIDPromocionMOfMensualidadCollectionMensualidad = mensualidadCollectionMensualidad.getIDPromocionM();
                mensualidadCollectionMensualidad.setIDPromocionM(promocion);
                mensualidadCollectionMensualidad = em.merge(mensualidadCollectionMensualidad);
                if (oldIDPromocionMOfMensualidadCollectionMensualidad != null) {
                    oldIDPromocionMOfMensualidadCollectionMensualidad.getMensualidadCollection().remove(mensualidadCollectionMensualidad);
                    oldIDPromocionMOfMensualidadCollectionMensualidad = em.merge(oldIDPromocionMOfMensualidadCollectionMensualidad);
                }
            }
            em.getTransaction().commit();
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
            Collection<Inscripcion> inscripcionCollectionOld = persistentPromocion.getInscripcionCollection();
            Collection<Inscripcion> inscripcionCollectionNew = promocion.getInscripcionCollection();
            Collection<Mensualidad> mensualidadCollectionOld = persistentPromocion.getMensualidadCollection();
            Collection<Mensualidad> mensualidadCollectionNew = promocion.getMensualidadCollection();
            Collection<Inscripcion> attachedInscripcionCollectionNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionNewInscripcionToAttach : inscripcionCollectionNew) {
                inscripcionCollectionNewInscripcionToAttach = em.getReference(inscripcionCollectionNewInscripcionToAttach.getClass(), inscripcionCollectionNewInscripcionToAttach.getIDInscripcion());
                attachedInscripcionCollectionNew.add(inscripcionCollectionNewInscripcionToAttach);
            }
            inscripcionCollectionNew = attachedInscripcionCollectionNew;
            promocion.setInscripcionCollection(inscripcionCollectionNew);
            Collection<Mensualidad> attachedMensualidadCollectionNew = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadCollectionNewMensualidadToAttach : mensualidadCollectionNew) {
                mensualidadCollectionNewMensualidadToAttach = em.getReference(mensualidadCollectionNewMensualidadToAttach.getClass(), mensualidadCollectionNewMensualidadToAttach.getIDMensualidad());
                attachedMensualidadCollectionNew.add(mensualidadCollectionNewMensualidadToAttach);
            }
            mensualidadCollectionNew = attachedMensualidadCollectionNew;
            promocion.setMensualidadCollection(mensualidadCollectionNew);
            promocion = em.merge(promocion);
            for (Inscripcion inscripcionCollectionOldInscripcion : inscripcionCollectionOld) {
                if (!inscripcionCollectionNew.contains(inscripcionCollectionOldInscripcion)) {
                    inscripcionCollectionOldInscripcion.setIDPromocionI(null);
                    inscripcionCollectionOldInscripcion = em.merge(inscripcionCollectionOldInscripcion);
                }
            }
            for (Inscripcion inscripcionCollectionNewInscripcion : inscripcionCollectionNew) {
                if (!inscripcionCollectionOld.contains(inscripcionCollectionNewInscripcion)) {
                    Promocion oldIDPromocionIOfInscripcionCollectionNewInscripcion = inscripcionCollectionNewInscripcion.getIDPromocionI();
                    inscripcionCollectionNewInscripcion.setIDPromocionI(promocion);
                    inscripcionCollectionNewInscripcion = em.merge(inscripcionCollectionNewInscripcion);
                    if (oldIDPromocionIOfInscripcionCollectionNewInscripcion != null && !oldIDPromocionIOfInscripcionCollectionNewInscripcion.equals(promocion)) {
                        oldIDPromocionIOfInscripcionCollectionNewInscripcion.getInscripcionCollection().remove(inscripcionCollectionNewInscripcion);
                        oldIDPromocionIOfInscripcionCollectionNewInscripcion = em.merge(oldIDPromocionIOfInscripcionCollectionNewInscripcion);
                    }
                }
            }
            for (Mensualidad mensualidadCollectionOldMensualidad : mensualidadCollectionOld) {
                if (!mensualidadCollectionNew.contains(mensualidadCollectionOldMensualidad)) {
                    mensualidadCollectionOldMensualidad.setIDPromocionM(null);
                    mensualidadCollectionOldMensualidad = em.merge(mensualidadCollectionOldMensualidad);
                }
            }
            for (Mensualidad mensualidadCollectionNewMensualidad : mensualidadCollectionNew) {
                if (!mensualidadCollectionOld.contains(mensualidadCollectionNewMensualidad)) {
                    Promocion oldIDPromocionMOfMensualidadCollectionNewMensualidad = mensualidadCollectionNewMensualidad.getIDPromocionM();
                    mensualidadCollectionNewMensualidad.setIDPromocionM(promocion);
                    mensualidadCollectionNewMensualidad = em.merge(mensualidadCollectionNewMensualidad);
                    if (oldIDPromocionMOfMensualidadCollectionNewMensualidad != null && !oldIDPromocionMOfMensualidadCollectionNewMensualidad.equals(promocion)) {
                        oldIDPromocionMOfMensualidadCollectionNewMensualidad.getMensualidadCollection().remove(mensualidadCollectionNewMensualidad);
                        oldIDPromocionMOfMensualidadCollectionNewMensualidad = em.merge(oldIDPromocionMOfMensualidadCollectionNewMensualidad);
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
            Collection<Inscripcion> inscripcionCollection = promocion.getInscripcionCollection();
            for (Inscripcion inscripcionCollectionInscripcion : inscripcionCollection) {
                inscripcionCollectionInscripcion.setIDPromocionI(null);
                inscripcionCollectionInscripcion = em.merge(inscripcionCollectionInscripcion);
            }
            Collection<Mensualidad> mensualidadCollection = promocion.getMensualidadCollection();
            for (Mensualidad mensualidadCollectionMensualidad : mensualidadCollection) {
                mensualidadCollectionMensualidad.setIDPromocionM(null);
                mensualidadCollectionMensualidad = em.merge(mensualidadCollectionMensualidad);
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
