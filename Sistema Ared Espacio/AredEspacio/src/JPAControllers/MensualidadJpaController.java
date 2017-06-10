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
import BaseDeDatos.Promocion;
import BaseDeDatos.Alumno;
import BaseDeDatos.Mensualidad;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
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
        if (mensualidad.getAlumnoList() == null) {
            mensualidad.setAlumnoList(new ArrayList<Alumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion IDPromocionM = mensualidad.getIDPromocionM();
            if (IDPromocionM != null) {
                IDPromocionM = em.getReference(IDPromocionM.getClass(), IDPromocionM.getIDPromocion());
                mensualidad.setIDPromocionM(IDPromocionM);
            }
            List<Alumno> attachedAlumnoList = new ArrayList<Alumno>();
            for (Alumno alumnoListAlumnoToAttach : mensualidad.getAlumnoList()) {
                alumnoListAlumnoToAttach = em.getReference(alumnoListAlumnoToAttach.getClass(), alumnoListAlumnoToAttach.getIDAlumno());
                attachedAlumnoList.add(alumnoListAlumnoToAttach);
            }
            mensualidad.setAlumnoList(attachedAlumnoList);
            em.persist(mensualidad);
            if (IDPromocionM != null) {
                IDPromocionM.getMensualidadList().add(mensualidad);
                IDPromocionM = em.merge(IDPromocionM);
            }
            for (Alumno alumnoListAlumno : mensualidad.getAlumnoList()) {
                Mensualidad oldIDMensualidadAOfAlumnoListAlumno = alumnoListAlumno.getIDMensualidadA();
                alumnoListAlumno.setIDMensualidadA(mensualidad);
                alumnoListAlumno = em.merge(alumnoListAlumno);
                if (oldIDMensualidadAOfAlumnoListAlumno != null) {
                    oldIDMensualidadAOfAlumnoListAlumno.getAlumnoList().remove(alumnoListAlumno);
                    oldIDMensualidadAOfAlumnoListAlumno = em.merge(oldIDMensualidadAOfAlumnoListAlumno);
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
            Promocion IDPromocionMOld = persistentMensualidad.getIDPromocionM();
            Promocion IDPromocionMNew = mensualidad.getIDPromocionM();
            List<Alumno> alumnoListOld = persistentMensualidad.getAlumnoList();
            List<Alumno> alumnoListNew = mensualidad.getAlumnoList();
            if (IDPromocionMNew != null) {
                IDPromocionMNew = em.getReference(IDPromocionMNew.getClass(), IDPromocionMNew.getIDPromocion());
                mensualidad.setIDPromocionM(IDPromocionMNew);
            }
            List<Alumno> attachedAlumnoListNew = new ArrayList<Alumno>();
            for (Alumno alumnoListNewAlumnoToAttach : alumnoListNew) {
                alumnoListNewAlumnoToAttach = em.getReference(alumnoListNewAlumnoToAttach.getClass(), alumnoListNewAlumnoToAttach.getIDAlumno());
                attachedAlumnoListNew.add(alumnoListNewAlumnoToAttach);
            }
            alumnoListNew = attachedAlumnoListNew;
            mensualidad.setAlumnoList(alumnoListNew);
            mensualidad = em.merge(mensualidad);
            if (IDPromocionMOld != null && !IDPromocionMOld.equals(IDPromocionMNew)) {
                IDPromocionMOld.getMensualidadList().remove(mensualidad);
                IDPromocionMOld = em.merge(IDPromocionMOld);
            }
            if (IDPromocionMNew != null && !IDPromocionMNew.equals(IDPromocionMOld)) {
                IDPromocionMNew.getMensualidadList().add(mensualidad);
                IDPromocionMNew = em.merge(IDPromocionMNew);
            }
            for (Alumno alumnoListOldAlumno : alumnoListOld) {
                if (!alumnoListNew.contains(alumnoListOldAlumno)) {
                    alumnoListOldAlumno.setIDMensualidadA(null);
                    alumnoListOldAlumno = em.merge(alumnoListOldAlumno);
                }
            }
            for (Alumno alumnoListNewAlumno : alumnoListNew) {
                if (!alumnoListOld.contains(alumnoListNewAlumno)) {
                    Mensualidad oldIDMensualidadAOfAlumnoListNewAlumno = alumnoListNewAlumno.getIDMensualidadA();
                    alumnoListNewAlumno.setIDMensualidadA(mensualidad);
                    alumnoListNewAlumno = em.merge(alumnoListNewAlumno);
                    if (oldIDMensualidadAOfAlumnoListNewAlumno != null && !oldIDMensualidadAOfAlumnoListNewAlumno.equals(mensualidad)) {
                        oldIDMensualidadAOfAlumnoListNewAlumno.getAlumnoList().remove(alumnoListNewAlumno);
                        oldIDMensualidadAOfAlumnoListNewAlumno = em.merge(oldIDMensualidadAOfAlumnoListNewAlumno);
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
            Promocion IDPromocionM = mensualidad.getIDPromocionM();
            if (IDPromocionM != null) {
                IDPromocionM.getMensualidadList().remove(mensualidad);
                IDPromocionM = em.merge(IDPromocionM);
            }
            List<Alumno> alumnoList = mensualidad.getAlumnoList();
            for (Alumno alumnoListAlumno : alumnoList) {
                alumnoListAlumno.setIDMensualidadA(null);
                alumnoListAlumno = em.merge(alumnoListAlumno);
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
