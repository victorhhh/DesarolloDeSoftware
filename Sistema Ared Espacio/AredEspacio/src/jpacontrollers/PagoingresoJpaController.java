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
import basededatos.Inscripcion;
import basededatos.Mensualidad;
import basededatos.Promocion;
import basededatos.Alumno;
import basededatos.Pagoingreso;
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
public class PagoingresoJpaController implements Serializable {

    public PagoingresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoingreso pagoingreso) throws PreexistingEntityException, Exception {
        if (pagoingreso.getAlumnoCollection() == null) {
            pagoingreso.setAlumnoCollection(new ArrayList<Alumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion IDInscripcion = pagoingreso.getIDInscripcion();
            if (IDInscripcion != null) {
                IDInscripcion = em.getReference(IDInscripcion.getClass(), IDInscripcion.getIDInscripcion());
                pagoingreso.setIDInscripcion(IDInscripcion);
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
            Collection<Alumno> attachedAlumnoCollection = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionAlumnoToAttach : pagoingreso.getAlumnoCollection()) {
                alumnoCollectionAlumnoToAttach = em.getReference(alumnoCollectionAlumnoToAttach.getClass(), alumnoCollectionAlumnoToAttach.getIDMatricula());
                attachedAlumnoCollection.add(alumnoCollectionAlumnoToAttach);
            }
            pagoingreso.setAlumnoCollection(attachedAlumnoCollection);
            em.persist(pagoingreso);
            if (IDInscripcion != null) {
                IDInscripcion.getPagoingresoCollection().add(pagoingreso);
                IDInscripcion = em.merge(IDInscripcion);
            }
            if (IDMensualidad != null) {
                IDMensualidad.getPagoingresoCollection().add(pagoingreso);
                IDMensualidad = em.merge(IDMensualidad);
            }
            if (IDPromocion != null) {
                IDPromocion.getPagoingresoCollection().add(pagoingreso);
                IDPromocion = em.merge(IDPromocion);
            }
            for (Alumno alumnoCollectionAlumno : pagoingreso.getAlumnoCollection()) {
                Pagoingreso oldIDPagoOfAlumnoCollectionAlumno = alumnoCollectionAlumno.getIDPago();
                alumnoCollectionAlumno.setIDPago(pagoingreso);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
                if (oldIDPagoOfAlumnoCollectionAlumno != null) {
                    oldIDPagoOfAlumnoCollectionAlumno.getAlumnoCollection().remove(alumnoCollectionAlumno);
                    oldIDPagoOfAlumnoCollectionAlumno = em.merge(oldIDPagoOfAlumnoCollectionAlumno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoingreso(pagoingreso.getIDIngreso()) != null) {
                throw new PreexistingEntityException("Pagoingreso " + pagoingreso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoingreso pagoingreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoingreso persistentPagoingreso = em.find(Pagoingreso.class, pagoingreso.getIDIngreso());
            Inscripcion IDInscripcionOld = persistentPagoingreso.getIDInscripcion();
            Inscripcion IDInscripcionNew = pagoingreso.getIDInscripcion();
            Mensualidad IDMensualidadOld = persistentPagoingreso.getIDMensualidad();
            Mensualidad IDMensualidadNew = pagoingreso.getIDMensualidad();
            Promocion IDPromocionOld = persistentPagoingreso.getIDPromocion();
            Promocion IDPromocionNew = pagoingreso.getIDPromocion();
            Collection<Alumno> alumnoCollectionOld = persistentPagoingreso.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = pagoingreso.getAlumnoCollection();
            List<String> illegalOrphanMessages = null;
            for (Alumno alumnoCollectionOldAlumno : alumnoCollectionOld) {
                if (!alumnoCollectionNew.contains(alumnoCollectionOldAlumno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alumno " + alumnoCollectionOldAlumno + " since its IDPago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDInscripcionNew != null) {
                IDInscripcionNew = em.getReference(IDInscripcionNew.getClass(), IDInscripcionNew.getIDInscripcion());
                pagoingreso.setIDInscripcion(IDInscripcionNew);
            }
            if (IDMensualidadNew != null) {
                IDMensualidadNew = em.getReference(IDMensualidadNew.getClass(), IDMensualidadNew.getIDMensualidad());
                pagoingreso.setIDMensualidad(IDMensualidadNew);
            }
            if (IDPromocionNew != null) {
                IDPromocionNew = em.getReference(IDPromocionNew.getClass(), IDPromocionNew.getIDPromocion());
                pagoingreso.setIDPromocion(IDPromocionNew);
            }
            Collection<Alumno> attachedAlumnoCollectionNew = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionNewAlumnoToAttach : alumnoCollectionNew) {
                alumnoCollectionNewAlumnoToAttach = em.getReference(alumnoCollectionNewAlumnoToAttach.getClass(), alumnoCollectionNewAlumnoToAttach.getIDMatricula());
                attachedAlumnoCollectionNew.add(alumnoCollectionNewAlumnoToAttach);
            }
            alumnoCollectionNew = attachedAlumnoCollectionNew;
            pagoingreso.setAlumnoCollection(alumnoCollectionNew);
            pagoingreso = em.merge(pagoingreso);
            if (IDInscripcionOld != null && !IDInscripcionOld.equals(IDInscripcionNew)) {
                IDInscripcionOld.getPagoingresoCollection().remove(pagoingreso);
                IDInscripcionOld = em.merge(IDInscripcionOld);
            }
            if (IDInscripcionNew != null && !IDInscripcionNew.equals(IDInscripcionOld)) {
                IDInscripcionNew.getPagoingresoCollection().add(pagoingreso);
                IDInscripcionNew = em.merge(IDInscripcionNew);
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
            for (Alumno alumnoCollectionNewAlumno : alumnoCollectionNew) {
                if (!alumnoCollectionOld.contains(alumnoCollectionNewAlumno)) {
                    Pagoingreso oldIDPagoOfAlumnoCollectionNewAlumno = alumnoCollectionNewAlumno.getIDPago();
                    alumnoCollectionNewAlumno.setIDPago(pagoingreso);
                    alumnoCollectionNewAlumno = em.merge(alumnoCollectionNewAlumno);
                    if (oldIDPagoOfAlumnoCollectionNewAlumno != null && !oldIDPagoOfAlumnoCollectionNewAlumno.equals(pagoingreso)) {
                        oldIDPagoOfAlumnoCollectionNewAlumno.getAlumnoCollection().remove(alumnoCollectionNewAlumno);
                        oldIDPagoOfAlumnoCollectionNewAlumno = em.merge(oldIDPagoOfAlumnoCollectionNewAlumno);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Alumno> alumnoCollectionOrphanCheck = pagoingreso.getAlumnoCollection();
            for (Alumno alumnoCollectionOrphanCheckAlumno : alumnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pagoingreso (" + pagoingreso + ") cannot be destroyed since the Alumno " + alumnoCollectionOrphanCheckAlumno + " in its alumnoCollection field has a non-nullable IDPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inscripcion IDInscripcion = pagoingreso.getIDInscripcion();
            if (IDInscripcion != null) {
                IDInscripcion.getPagoingresoCollection().remove(pagoingreso);
                IDInscripcion = em.merge(IDInscripcion);
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
