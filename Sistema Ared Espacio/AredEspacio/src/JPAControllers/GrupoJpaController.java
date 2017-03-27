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
import BaseDeDatos.Clase;
import BaseDeDatos.Grupo;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno IDAlumnoG = grupo.getIDAlumnoG();
            if (IDAlumnoG != null) {
                IDAlumnoG = em.getReference(IDAlumnoG.getClass(), IDAlumnoG.getIDAlumno());
                grupo.setIDAlumnoG(IDAlumnoG);
            }
            Clase IDClaseG = grupo.getIDClaseG();
            if (IDClaseG != null) {
                IDClaseG = em.getReference(IDClaseG.getClass(), IDClaseG.getIDClase());
                grupo.setIDClaseG(IDClaseG);
            }
            em.persist(grupo);
            if (IDAlumnoG != null) {
                IDAlumnoG.getGrupoCollection().add(grupo);
                IDAlumnoG = em.merge(IDAlumnoG);
            }
            if (IDClaseG != null) {
                IDClaseG.getGrupoCollection().add(grupo);
                IDClaseG = em.merge(IDClaseG);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getIDGrupo());
            Alumno IDAlumnoGOld = persistentGrupo.getIDAlumnoG();
            Alumno IDAlumnoGNew = grupo.getIDAlumnoG();
            Clase IDClaseGOld = persistentGrupo.getIDClaseG();
            Clase IDClaseGNew = grupo.getIDClaseG();
            if (IDAlumnoGNew != null) {
                IDAlumnoGNew = em.getReference(IDAlumnoGNew.getClass(), IDAlumnoGNew.getIDAlumno());
                grupo.setIDAlumnoG(IDAlumnoGNew);
            }
            if (IDClaseGNew != null) {
                IDClaseGNew = em.getReference(IDClaseGNew.getClass(), IDClaseGNew.getIDClase());
                grupo.setIDClaseG(IDClaseGNew);
            }
            grupo = em.merge(grupo);
            if (IDAlumnoGOld != null && !IDAlumnoGOld.equals(IDAlumnoGNew)) {
                IDAlumnoGOld.getGrupoCollection().remove(grupo);
                IDAlumnoGOld = em.merge(IDAlumnoGOld);
            }
            if (IDAlumnoGNew != null && !IDAlumnoGNew.equals(IDAlumnoGOld)) {
                IDAlumnoGNew.getGrupoCollection().add(grupo);
                IDAlumnoGNew = em.merge(IDAlumnoGNew);
            }
            if (IDClaseGOld != null && !IDClaseGOld.equals(IDClaseGNew)) {
                IDClaseGOld.getGrupoCollection().remove(grupo);
                IDClaseGOld = em.merge(IDClaseGOld);
            }
            if (IDClaseGNew != null && !IDClaseGNew.equals(IDClaseGOld)) {
                IDClaseGNew.getGrupoCollection().add(grupo);
                IDClaseGNew = em.merge(IDClaseGNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getIDGrupo();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getIDGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            Alumno IDAlumnoG = grupo.getIDAlumnoG();
            if (IDAlumnoG != null) {
                IDAlumnoG.getGrupoCollection().remove(grupo);
                IDAlumnoG = em.merge(IDAlumnoG);
            }
            Clase IDClaseG = grupo.getIDClaseG();
            if (IDClaseG != null) {
                IDClaseG.getGrupoCollection().remove(grupo);
                IDClaseG = em.merge(IDClaseG);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
