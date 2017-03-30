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
import BaseDeDatos.Promocion;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class PagoingresoJpaController implements Serializable {

    public PagoingresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoingreso pagoingreso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno IDAlumnoPI = pagoingreso.getIDAlumnoPI();
            if (IDAlumnoPI != null) {
                IDAlumnoPI = em.getReference(IDAlumnoPI.getClass(), IDAlumnoPI.getIDAlumno());
                pagoingreso.setIDAlumnoPI(IDAlumnoPI);
            }
            Mensualidad IDMensualidad = pagoingreso.getIDMensualidad();
            if (IDMensualidad != null) {
                IDMensualidad = em.getReference(IDMensualidad.getClass(), IDMensualidad.getIDMensualidad());
                pagoingreso.setIDMensualidad(IDMensualidad);
            }
            Promocion IDPromocion = pagoingreso.getIDPromocion();
            if (IDPromocion != null) {
                IDPromocion = em.getReference(IDPromocion.getClass(), IDPromocion.getIDPromocion());
                pagoingreso.setIDPromocion(IDPromocion);
            }
            em.persist(pagoingreso);
            if (IDAlumnoPI != null) {
                IDAlumnoPI.getPagoingresoCollection().add(pagoingreso);
                IDAlumnoPI = em.merge(IDAlumnoPI);
            }
            if (IDMensualidad != null) {
                IDMensualidad.getPagoingresoCollection().add(pagoingreso);
                IDMensualidad = em.merge(IDMensualidad);
            }
            if (IDPromocion != null) {
                IDPromocion.getPagoingresoCollection().add(pagoingreso);
                IDPromocion = em.merge(IDPromocion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoingreso pagoingreso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoingreso persistentPagoingreso = em.find(Pagoingreso.class, pagoingreso.getIDIngreso());
            Alumno IDAlumnoPIOld = persistentPagoingreso.getIDAlumnoPI();
            Alumno IDAlumnoPINew = pagoingreso.getIDAlumnoPI();
            Mensualidad IDMensualidadOld = persistentPagoingreso.getIDMensualidad();
            Mensualidad IDMensualidadNew = pagoingreso.getIDMensualidad();
            Promocion IDPromocionOld = persistentPagoingreso.getIDPromocion();
            Promocion IDPromocionNew = pagoingreso.getIDPromocion();
            if (IDAlumnoPINew != null) {
                IDAlumnoPINew = em.getReference(IDAlumnoPINew.getClass(), IDAlumnoPINew.getIDAlumno());
                pagoingreso.setIDAlumnoPI(IDAlumnoPINew);
            }
            if (IDMensualidadNew != null) {
                IDMensualidadNew = em.getReference(IDMensualidadNew.getClass(), IDMensualidadNew.getIDMensualidad());
                pagoingreso.setIDMensualidad(IDMensualidadNew);
            }
            if (IDPromocionNew != null) {
                IDPromocionNew = em.getReference(IDPromocionNew.getClass(), IDPromocionNew.getIDPromocion());
                pagoingreso.setIDPromocion(IDPromocionNew);
            }
            pagoingreso = em.merge(pagoingreso);
            if (IDAlumnoPIOld != null && !IDAlumnoPIOld.equals(IDAlumnoPINew)) {
                IDAlumnoPIOld.getPagoingresoCollection().remove(pagoingreso);
                IDAlumnoPIOld = em.merge(IDAlumnoPIOld);
            }
            if (IDAlumnoPINew != null && !IDAlumnoPINew.equals(IDAlumnoPIOld)) {
                IDAlumnoPINew.getPagoingresoCollection().add(pagoingreso);
                IDAlumnoPINew = em.merge(IDAlumnoPINew);
            }
            if (IDMensualidadOld != null && !IDMensualidadOld.equals(IDMensualidadNew)) {
                IDMensualidadOld.getPagoingresoCollection().remove(pagoingreso);
                IDMensualidadOld = em.merge(IDMensualidadOld);
            }
            if (IDMensualidadNew != null && !IDMensualidadNew.equals(IDMensualidadOld)) {
                IDMensualidadNew.getPagoingresoCollection().add(pagoingreso);
                IDMensualidadNew = em.merge(IDMensualidadNew);
            }
            if (IDPromocionOld != null && !IDPromocionOld.equals(IDPromocionNew)) {
                IDPromocionOld.getPagoingresoCollection().remove(pagoingreso);
                IDPromocionOld = em.merge(IDPromocionOld);
            }
            if (IDPromocionNew != null && !IDPromocionNew.equals(IDPromocionOld)) {
                IDPromocionNew.getPagoingresoCollection().add(pagoingreso);
                IDPromocionNew = em.merge(IDPromocionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoingreso.getIDIngreso();
                if (findPagoingreso(id) == null) {
                    throw new NonexistentEntityException("The pagoingreso with id " + id + " no longer exists.");
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
            Pagoingreso pagoingreso;
            try {
                pagoingreso = em.getReference(Pagoingreso.class, id);
                pagoingreso.getIDIngreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoingreso with id " + id + " no longer exists.", enfe);
            }
            Alumno IDAlumnoPI = pagoingreso.getIDAlumnoPI();
            if (IDAlumnoPI != null) {
                IDAlumnoPI.getPagoingresoCollection().remove(pagoingreso);
                IDAlumnoPI = em.merge(IDAlumnoPI);
            }
            Mensualidad IDMensualidad = pagoingreso.getIDMensualidad();
            if (IDMensualidad != null) {
                IDMensualidad.getPagoingresoCollection().remove(pagoingreso);
                IDMensualidad = em.merge(IDMensualidad);
            }
            Promocion IDPromocion = pagoingreso.getIDPromocion();
            if (IDPromocion != null) {
                IDPromocion.getPagoingresoCollection().remove(pagoingreso);
                IDPromocion = em.merge(IDPromocion);
            }
            em.remove(pagoingreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagoingreso> findPagoingresoEntities() {
        return findPagoingresoEntities(true, -1, -1);
    }

    public List<Pagoingreso> findPagoingresoEntities(int maxResults, int firstResult) {
        return findPagoingresoEntities(false, maxResults, firstResult);
    }

    private List<Pagoingreso> findPagoingresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagoingreso.class));
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

    public Pagoingreso findPagoingreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagoingreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoingresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagoingreso> rt = cq.from(Pagoingreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
