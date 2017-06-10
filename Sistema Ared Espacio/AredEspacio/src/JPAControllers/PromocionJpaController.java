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
import java.util.List;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Promocion;
import JPAControllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
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
        if (promocion.getInscripcionList() == null) {
            promocion.setInscripcionList(new ArrayList<Inscripcion>());
        }
        if (promocion.getMensualidadList() == null) {
            promocion.setMensualidadList(new ArrayList<Mensualidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Inscripcion> attachedInscripcionList = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListInscripcionToAttach : promocion.getInscripcionList()) {
                inscripcionListInscripcionToAttach = em.getReference(inscripcionListInscripcionToAttach.getClass(), inscripcionListInscripcionToAttach.getIDInscripcion());
                attachedInscripcionList.add(inscripcionListInscripcionToAttach);
            }
            promocion.setInscripcionList(attachedInscripcionList);
            List<Mensualidad> attachedMensualidadList = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListMensualidadToAttach : promocion.getMensualidadList()) {
                mensualidadListMensualidadToAttach = em.getReference(mensualidadListMensualidadToAttach.getClass(), mensualidadListMensualidadToAttach.getIDMensualidad());
                attachedMensualidadList.add(mensualidadListMensualidadToAttach);
            }
            promocion.setMensualidadList(attachedMensualidadList);
            em.persist(promocion);
            for (Inscripcion inscripcionListInscripcion : promocion.getInscripcionList()) {
                Promocion oldIDPromocionIOfInscripcionListInscripcion = inscripcionListInscripcion.getIDPromocionI();
                inscripcionListInscripcion.setIDPromocionI(promocion);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
                if (oldIDPromocionIOfInscripcionListInscripcion != null) {
                    oldIDPromocionIOfInscripcionListInscripcion.getInscripcionList().remove(inscripcionListInscripcion);
                    oldIDPromocionIOfInscripcionListInscripcion = em.merge(oldIDPromocionIOfInscripcionListInscripcion);
                }
            }
            for (Mensualidad mensualidadListMensualidad : promocion.getMensualidadList()) {
                Promocion oldIDPromocionMOfMensualidadListMensualidad = mensualidadListMensualidad.getIDPromocionM();
                mensualidadListMensualidad.setIDPromocionM(promocion);
                mensualidadListMensualidad = em.merge(mensualidadListMensualidad);
                if (oldIDPromocionMOfMensualidadListMensualidad != null) {
                    oldIDPromocionMOfMensualidadListMensualidad.getMensualidadList().remove(mensualidadListMensualidad);
                    oldIDPromocionMOfMensualidadListMensualidad = em.merge(oldIDPromocionMOfMensualidadListMensualidad);
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
            List<Inscripcion> inscripcionListOld = persistentPromocion.getInscripcionList();
            List<Inscripcion> inscripcionListNew = promocion.getInscripcionList();
            List<Mensualidad> mensualidadListOld = persistentPromocion.getMensualidadList();
            List<Mensualidad> mensualidadListNew = promocion.getMensualidadList();
            List<Inscripcion> attachedInscripcionListNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListNewInscripcionToAttach : inscripcionListNew) {
                inscripcionListNewInscripcionToAttach = em.getReference(inscripcionListNewInscripcionToAttach.getClass(), inscripcionListNewInscripcionToAttach.getIDInscripcion());
                attachedInscripcionListNew.add(inscripcionListNewInscripcionToAttach);
            }
            inscripcionListNew = attachedInscripcionListNew;
            promocion.setInscripcionList(inscripcionListNew);
            List<Mensualidad> attachedMensualidadListNew = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListNewMensualidadToAttach : mensualidadListNew) {
                mensualidadListNewMensualidadToAttach = em.getReference(mensualidadListNewMensualidadToAttach.getClass(), mensualidadListNewMensualidadToAttach.getIDMensualidad());
                attachedMensualidadListNew.add(mensualidadListNewMensualidadToAttach);
            }
            mensualidadListNew = attachedMensualidadListNew;
            promocion.setMensualidadList(mensualidadListNew);
            promocion = em.merge(promocion);
            for (Inscripcion inscripcionListOldInscripcion : inscripcionListOld) {
                if (!inscripcionListNew.contains(inscripcionListOldInscripcion)) {
                    inscripcionListOldInscripcion.setIDPromocionI(null);
                    inscripcionListOldInscripcion = em.merge(inscripcionListOldInscripcion);
                }
            }
            for (Inscripcion inscripcionListNewInscripcion : inscripcionListNew) {
                if (!inscripcionListOld.contains(inscripcionListNewInscripcion)) {
                    Promocion oldIDPromocionIOfInscripcionListNewInscripcion = inscripcionListNewInscripcion.getIDPromocionI();
                    inscripcionListNewInscripcion.setIDPromocionI(promocion);
                    inscripcionListNewInscripcion = em.merge(inscripcionListNewInscripcion);
                    if (oldIDPromocionIOfInscripcionListNewInscripcion != null && !oldIDPromocionIOfInscripcionListNewInscripcion.equals(promocion)) {
                        oldIDPromocionIOfInscripcionListNewInscripcion.getInscripcionList().remove(inscripcionListNewInscripcion);
                        oldIDPromocionIOfInscripcionListNewInscripcion = em.merge(oldIDPromocionIOfInscripcionListNewInscripcion);
                    }
                }
            }
            for (Mensualidad mensualidadListOldMensualidad : mensualidadListOld) {
                if (!mensualidadListNew.contains(mensualidadListOldMensualidad)) {
                    mensualidadListOldMensualidad.setIDPromocionM(null);
                    mensualidadListOldMensualidad = em.merge(mensualidadListOldMensualidad);
                }
            }
            for (Mensualidad mensualidadListNewMensualidad : mensualidadListNew) {
                if (!mensualidadListOld.contains(mensualidadListNewMensualidad)) {
                    Promocion oldIDPromocionMOfMensualidadListNewMensualidad = mensualidadListNewMensualidad.getIDPromocionM();
                    mensualidadListNewMensualidad.setIDPromocionM(promocion);
                    mensualidadListNewMensualidad = em.merge(mensualidadListNewMensualidad);
                    if (oldIDPromocionMOfMensualidadListNewMensualidad != null && !oldIDPromocionMOfMensualidadListNewMensualidad.equals(promocion)) {
                        oldIDPromocionMOfMensualidadListNewMensualidad.getMensualidadList().remove(mensualidadListNewMensualidad);
                        oldIDPromocionMOfMensualidadListNewMensualidad = em.merge(oldIDPromocionMOfMensualidadListNewMensualidad);
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
            List<Inscripcion> inscripcionList = promocion.getInscripcionList();
            for (Inscripcion inscripcionListInscripcion : inscripcionList) {
                inscripcionListInscripcion.setIDPromocionI(null);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
            }
            List<Mensualidad> mensualidadList = promocion.getMensualidadList();
            for (Mensualidad mensualidadListMensualidad : mensualidadList) {
                mensualidadListMensualidad.setIDPromocionM(null);
                mensualidadListMensualidad = em.merge(mensualidadListMensualidad);
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
