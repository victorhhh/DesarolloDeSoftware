/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAControllers;

import BaseDeDatos.Clase;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BaseDeDatos.Maestro;
import BaseDeDatos.Grupo;
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
public class ClaseJpaController implements Serializable {

    public ClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) {
        if (clase.getGrupoCollection() == null) {
            clase.setGrupoCollection(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro IDMaestroC = clase.getIDMaestroC();
            if (IDMaestroC != null) {
                IDMaestroC = em.getReference(IDMaestroC.getClass(), IDMaestroC.getIDMaestro());
                clase.setIDMaestroC(IDMaestroC);
            }
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : clase.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getIDGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            clase.setGrupoCollection(attachedGrupoCollection);
            em.persist(clase);
            if (IDMaestroC != null) {
                IDMaestroC.getClaseCollection().add(clase);
                IDMaestroC = em.merge(IDMaestroC);
            }
            for (Grupo grupoCollectionGrupo : clase.getGrupoCollection()) {
                Clase oldIDClaseGOfGrupoCollectionGrupo = grupoCollectionGrupo.getIDClaseG();
                grupoCollectionGrupo.setIDClaseG(clase);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldIDClaseGOfGrupoCollectionGrupo != null) {
                    oldIDClaseGOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldIDClaseGOfGrupoCollectionGrupo = em.merge(oldIDClaseGOfGrupoCollectionGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getIDClase());
            Maestro IDMaestroCOld = persistentClase.getIDMaestroC();
            Maestro IDMaestroCNew = clase.getIDMaestroC();
            Collection<Grupo> grupoCollectionOld = persistentClase.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = clase.getGrupoCollection();
            if (IDMaestroCNew != null) {
                IDMaestroCNew = em.getReference(IDMaestroCNew.getClass(), IDMaestroCNew.getIDMaestro());
                clase.setIDMaestroC(IDMaestroCNew);
            }
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getIDGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            clase.setGrupoCollection(grupoCollectionNew);
            clase = em.merge(clase);
            if (IDMaestroCOld != null && !IDMaestroCOld.equals(IDMaestroCNew)) {
                IDMaestroCOld.getClaseCollection().remove(clase);
                IDMaestroCOld = em.merge(IDMaestroCOld);
            }
            if (IDMaestroCNew != null && !IDMaestroCNew.equals(IDMaestroCOld)) {
                IDMaestroCNew.getClaseCollection().add(clase);
                IDMaestroCNew = em.merge(IDMaestroCNew);
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.setIDClaseG(null);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Clase oldIDClaseGOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getIDClaseG();
                    grupoCollectionNewGrupo.setIDClaseG(clase);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldIDClaseGOfGrupoCollectionNewGrupo != null && !oldIDClaseGOfGrupoCollectionNewGrupo.equals(clase)) {
                        oldIDClaseGOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldIDClaseGOfGrupoCollectionNewGrupo = em.merge(oldIDClaseGOfGrupoCollectionNewGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clase.getIDClase();
                if (findClase(id) == null) {
                    throw new NonexistentEntityException("The clase with id " + id + " no longer exists.");
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
            Clase clase;
            try {
                clase = em.getReference(Clase.class, id);
                clase.getIDClase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clase with id " + id + " no longer exists.", enfe);
            }
            Maestro IDMaestroC = clase.getIDMaestroC();
            if (IDMaestroC != null) {
                IDMaestroC.getClaseCollection().remove(clase);
                IDMaestroC = em.merge(IDMaestroC);
            }
            Collection<Grupo> grupoCollection = clase.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.setIDClaseG(null);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            em.remove(clase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clase> findClaseEntities() {
        return findClaseEntities(true, -1, -1);
    }

    public List<Clase> findClaseEntities(int maxResults, int firstResult) {
        return findClaseEntities(false, maxResults, firstResult);
    }

    private List<Clase> findClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clase.class));
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

    public Clase findClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clase.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clase> rt = cq.from(Clase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}