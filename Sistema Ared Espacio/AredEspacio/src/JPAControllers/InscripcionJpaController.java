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
import BaseDeDatos.Inscripcion;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
 */
public class InscripcionJpaController implements Serializable {

    public InscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscripcion inscripcion) {
        if (inscripcion.getAlumnoList() == null) {
            inscripcion.setAlumnoList(new ArrayList<Alumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion IDPromocionI = inscripcion.getIDPromocionI();
            if (IDPromocionI != null) {
                IDPromocionI = em.getReference(IDPromocionI.getClass(), IDPromocionI.getIDPromocion());
                inscripcion.setIDPromocionI(IDPromocionI);
            }
            List<Alumno> attachedAlumnoList = new ArrayList<Alumno>();
            for (Alumno alumnoListAlumnoToAttach : inscripcion.getAlumnoList()) {
                alumnoListAlumnoToAttach = em.getReference(alumnoListAlumnoToAttach.getClass(), alumnoListAlumnoToAttach.getIDAlumno());
                attachedAlumnoList.add(alumnoListAlumnoToAttach);
            }
            inscripcion.setAlumnoList(attachedAlumnoList);
            em.persist(inscripcion);
            if (IDPromocionI != null) {
                IDPromocionI.getInscripcionList().add(inscripcion);
                IDPromocionI = em.merge(IDPromocionI);
            }
            for (Alumno alumnoListAlumno : inscripcion.getAlumnoList()) {
                Inscripcion oldIDInscripcionAOfAlumnoListAlumno = alumnoListAlumno.getIDInscripcionA();
                alumnoListAlumno.setIDInscripcionA(inscripcion);
                alumnoListAlumno = em.merge(alumnoListAlumno);
                if (oldIDInscripcionAOfAlumnoListAlumno != null) {
                    oldIDInscripcionAOfAlumnoListAlumno.getAlumnoList().remove(alumnoListAlumno);
                    oldIDInscripcionAOfAlumnoListAlumno = em.merge(oldIDInscripcionAOfAlumnoListAlumno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscripcion inscripcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion persistentInscripcion = em.find(Inscripcion.class, inscripcion.getIDInscripcion());
            Promocion IDPromocionIOld = persistentInscripcion.getIDPromocionI();
            Promocion IDPromocionINew = inscripcion.getIDPromocionI();
            List<Alumno> alumnoListOld = persistentInscripcion.getAlumnoList();
            List<Alumno> alumnoListNew = inscripcion.getAlumnoList();
            if (IDPromocionINew != null) {
                IDPromocionINew = em.getReference(IDPromocionINew.getClass(), IDPromocionINew.getIDPromocion());
                inscripcion.setIDPromocionI(IDPromocionINew);
            }
            List<Alumno> attachedAlumnoListNew = new ArrayList<Alumno>();
            for (Alumno alumnoListNewAlumnoToAttach : alumnoListNew) {
                alumnoListNewAlumnoToAttach = em.getReference(alumnoListNewAlumnoToAttach.getClass(), alumnoListNewAlumnoToAttach.getIDAlumno());
                attachedAlumnoListNew.add(alumnoListNewAlumnoToAttach);
            }
            alumnoListNew = attachedAlumnoListNew;
            inscripcion.setAlumnoList(alumnoListNew);
            inscripcion = em.merge(inscripcion);
            if (IDPromocionIOld != null && !IDPromocionIOld.equals(IDPromocionINew)) {
                IDPromocionIOld.getInscripcionList().remove(inscripcion);
                IDPromocionIOld = em.merge(IDPromocionIOld);
            }
            if (IDPromocionINew != null && !IDPromocionINew.equals(IDPromocionIOld)) {
                IDPromocionINew.getInscripcionList().add(inscripcion);
                IDPromocionINew = em.merge(IDPromocionINew);
            }
            for (Alumno alumnoListOldAlumno : alumnoListOld) {
                if (!alumnoListNew.contains(alumnoListOldAlumno)) {
                    alumnoListOldAlumno.setIDInscripcionA(null);
                    alumnoListOldAlumno = em.merge(alumnoListOldAlumno);
                }
            }
            for (Alumno alumnoListNewAlumno : alumnoListNew) {
                if (!alumnoListOld.contains(alumnoListNewAlumno)) {
                    Inscripcion oldIDInscripcionAOfAlumnoListNewAlumno = alumnoListNewAlumno.getIDInscripcionA();
                    alumnoListNewAlumno.setIDInscripcionA(inscripcion);
                    alumnoListNewAlumno = em.merge(alumnoListNewAlumno);
                    if (oldIDInscripcionAOfAlumnoListNewAlumno != null && !oldIDInscripcionAOfAlumnoListNewAlumno.equals(inscripcion)) {
                        oldIDInscripcionAOfAlumnoListNewAlumno.getAlumnoList().remove(alumnoListNewAlumno);
                        oldIDInscripcionAOfAlumnoListNewAlumno = em.merge(oldIDInscripcionAOfAlumnoListNewAlumno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripcion.getIDInscripcion();
                if (findInscripcion(id) == null) {
                    throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.");
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
            Inscripcion inscripcion;
            try {
                inscripcion = em.getReference(Inscripcion.class, id);
                inscripcion.getIDInscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.", enfe);
            }
            Promocion IDPromocionI = inscripcion.getIDPromocionI();
            if (IDPromocionI != null) {
                IDPromocionI.getInscripcionList().remove(inscripcion);
                IDPromocionI = em.merge(IDPromocionI);
            }
            List<Alumno> alumnoList = inscripcion.getAlumnoList();
            for (Alumno alumnoListAlumno : alumnoList) {
                alumnoListAlumno.setIDInscripcionA(null);
                alumnoListAlumno = em.merge(alumnoListAlumno);
            }
            em.remove(inscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscripcion> findInscripcionEntities() {
        return findInscripcionEntities(true, -1, -1);
    }

    public List<Inscripcion> findInscripcionEntities(int maxResults, int firstResult) {
        return findInscripcionEntities(false, maxResults, firstResult);
    }

    private List<Inscripcion> findInscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscripcion.class));
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

    public Inscripcion findInscripcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscripcion> rt = cq.from(Inscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
