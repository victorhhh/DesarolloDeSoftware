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
import BaseDeDatos.Alumno;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Pagoingreso;
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
        if (mensualidad.getPagoingresoCollection() == null) {
            mensualidad.setPagoingresoCollection(new ArrayList<Pagoingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno IDAlumnoM = mensualidad.getIDAlumnoM();
            if (IDAlumnoM != null) {
                IDAlumnoM = em.getReference(IDAlumnoM.getClass(), IDAlumnoM.getIDAlumno());
                mensualidad.setIDAlumnoM(IDAlumnoM);
            }
            Collection<Pagoingreso> attachedPagoingresoCollection = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionPagoingresoToAttach : mensualidad.getPagoingresoCollection()) {
                pagoingresoCollectionPagoingresoToAttach = em.getReference(pagoingresoCollectionPagoingresoToAttach.getClass(), pagoingresoCollectionPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollection.add(pagoingresoCollectionPagoingresoToAttach);
            }
            mensualidad.setPagoingresoCollection(attachedPagoingresoCollection);
            em.persist(mensualidad);
            if (IDAlumnoM != null) {
                IDAlumnoM.getMensualidadCollection().add(mensualidad);
                IDAlumnoM = em.merge(IDAlumnoM);
            }
            for (Pagoingreso pagoingresoCollectionPagoingreso : mensualidad.getPagoingresoCollection()) {
                Mensualidad oldIDMensualidadOfPagoingresoCollectionPagoingreso = pagoingresoCollectionPagoingreso.getIDMensualidad();
                pagoingresoCollectionPagoingreso.setIDMensualidad(mensualidad);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
                if (oldIDMensualidadOfPagoingresoCollectionPagoingreso != null) {
                    oldIDMensualidadOfPagoingresoCollectionPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionPagoingreso);
                    oldIDMensualidadOfPagoingresoCollectionPagoingreso = em.merge(oldIDMensualidadOfPagoingresoCollectionPagoingreso);
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
            Alumno IDAlumnoMOld = persistentMensualidad.getIDAlumnoM();
            Alumno IDAlumnoMNew = mensualidad.getIDAlumnoM();
            Collection<Pagoingreso> pagoingresoCollectionOld = persistentMensualidad.getPagoingresoCollection();
            Collection<Pagoingreso> pagoingresoCollectionNew = mensualidad.getPagoingresoCollection();
            if (IDAlumnoMNew != null) {
                IDAlumnoMNew = em.getReference(IDAlumnoMNew.getClass(), IDAlumnoMNew.getIDAlumno());
                mensualidad.setIDAlumnoM(IDAlumnoMNew);
            }
            Collection<Pagoingreso> attachedPagoingresoCollectionNew = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionNewPagoingresoToAttach : pagoingresoCollectionNew) {
                pagoingresoCollectionNewPagoingresoToAttach = em.getReference(pagoingresoCollectionNewPagoingresoToAttach.getClass(), pagoingresoCollectionNewPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollectionNew.add(pagoingresoCollectionNewPagoingresoToAttach);
            }
            pagoingresoCollectionNew = attachedPagoingresoCollectionNew;
            mensualidad.setPagoingresoCollection(pagoingresoCollectionNew);
            mensualidad = em.merge(mensualidad);
            if (IDAlumnoMOld != null && !IDAlumnoMOld.equals(IDAlumnoMNew)) {
                IDAlumnoMOld.getMensualidadCollection().remove(mensualidad);
                IDAlumnoMOld = em.merge(IDAlumnoMOld);
            }
            if (IDAlumnoMNew != null && !IDAlumnoMNew.equals(IDAlumnoMOld)) {
                IDAlumnoMNew.getMensualidadCollection().add(mensualidad);
                IDAlumnoMNew = em.merge(IDAlumnoMNew);
            }
            for (Pagoingreso pagoingresoCollectionOldPagoingreso : pagoingresoCollectionOld) {
                if (!pagoingresoCollectionNew.contains(pagoingresoCollectionOldPagoingreso)) {
                    pagoingresoCollectionOldPagoingreso.setIDMensualidad(null);
                    pagoingresoCollectionOldPagoingreso = em.merge(pagoingresoCollectionOldPagoingreso);
                }
            }
            for (Pagoingreso pagoingresoCollectionNewPagoingreso : pagoingresoCollectionNew) {
                if (!pagoingresoCollectionOld.contains(pagoingresoCollectionNewPagoingreso)) {
                    Mensualidad oldIDMensualidadOfPagoingresoCollectionNewPagoingreso = pagoingresoCollectionNewPagoingreso.getIDMensualidad();
                    pagoingresoCollectionNewPagoingreso.setIDMensualidad(mensualidad);
                    pagoingresoCollectionNewPagoingreso = em.merge(pagoingresoCollectionNewPagoingreso);
                    if (oldIDMensualidadOfPagoingresoCollectionNewPagoingreso != null && !oldIDMensualidadOfPagoingresoCollectionNewPagoingreso.equals(mensualidad)) {
                        oldIDMensualidadOfPagoingresoCollectionNewPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionNewPagoingreso);
                        oldIDMensualidadOfPagoingresoCollectionNewPagoingreso = em.merge(oldIDMensualidadOfPagoingresoCollectionNewPagoingreso);
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
            Alumno IDAlumnoM = mensualidad.getIDAlumnoM();
            if (IDAlumnoM != null) {
                IDAlumnoM.getMensualidadCollection().remove(mensualidad);
                IDAlumnoM = em.merge(IDAlumnoM);
            }
            Collection<Pagoingreso> pagoingresoCollection = mensualidad.getPagoingresoCollection();
            for (Pagoingreso pagoingresoCollectionPagoingreso : pagoingresoCollection) {
                pagoingresoCollectionPagoingreso.setIDMensualidad(null);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
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
