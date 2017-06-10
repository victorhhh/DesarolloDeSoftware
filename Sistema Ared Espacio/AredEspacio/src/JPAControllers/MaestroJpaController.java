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
import java.util.List;
import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
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
        if (maestro.getPagoegresoList() == null) {
            maestro.setPagoegresoList(new ArrayList<Pagoegreso>());
        }
        if (maestro.getClaseList() == null) {
            maestro.setClaseList(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pagoegreso> attachedPagoegresoList = new ArrayList<Pagoegreso>();
            for (Pagoegreso pagoegresoListPagoegresoToAttach : maestro.getPagoegresoList()) {
                pagoegresoListPagoegresoToAttach = em.getReference(pagoegresoListPagoegresoToAttach.getClass(), pagoegresoListPagoegresoToAttach.getIDEgreso());
                attachedPagoegresoList.add(pagoegresoListPagoegresoToAttach);
            }
            maestro.setPagoegresoList(attachedPagoegresoList);
            List<Clase> attachedClaseList = new ArrayList<Clase>();
            for (Clase claseListClaseToAttach : maestro.getClaseList()) {
                claseListClaseToAttach = em.getReference(claseListClaseToAttach.getClass(), claseListClaseToAttach.getIDClase());
                attachedClaseList.add(claseListClaseToAttach);
            }
            maestro.setClaseList(attachedClaseList);
            em.persist(maestro);
            for (Pagoegreso pagoegresoListPagoegreso : maestro.getPagoegresoList()) {
                Maestro oldIDMaestroPEOfPagoegresoListPagoegreso = pagoegresoListPagoegreso.getIDMaestroPE();
                pagoegresoListPagoegreso.setIDMaestroPE(maestro);
                pagoegresoListPagoegreso = em.merge(pagoegresoListPagoegreso);
                if (oldIDMaestroPEOfPagoegresoListPagoegreso != null) {
                    oldIDMaestroPEOfPagoegresoListPagoegreso.getPagoegresoList().remove(pagoegresoListPagoegreso);
                    oldIDMaestroPEOfPagoegresoListPagoegreso = em.merge(oldIDMaestroPEOfPagoegresoListPagoegreso);
                }
            }
            for (Clase claseListClase : maestro.getClaseList()) {
                Maestro oldIDMaestroCOfClaseListClase = claseListClase.getIDMaestroC();
                claseListClase.setIDMaestroC(maestro);
                claseListClase = em.merge(claseListClase);
                if (oldIDMaestroCOfClaseListClase != null) {
                    oldIDMaestroCOfClaseListClase.getClaseList().remove(claseListClase);
                    oldIDMaestroCOfClaseListClase = em.merge(oldIDMaestroCOfClaseListClase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestro maestro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getIDMaestro());
            List<Pagoegreso> pagoegresoListOld = persistentMaestro.getPagoegresoList();
            List<Pagoegreso> pagoegresoListNew = maestro.getPagoegresoList();
            List<Clase> claseListOld = persistentMaestro.getClaseList();
            List<Clase> claseListNew = maestro.getClaseList();
            List<String> illegalOrphanMessages = null;
            for (Pagoegreso pagoegresoListOldPagoegreso : pagoegresoListOld) {
                if (!pagoegresoListNew.contains(pagoegresoListOldPagoegreso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagoegreso " + pagoegresoListOldPagoegreso + " since its IDMaestroPE field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pagoegreso> attachedPagoegresoListNew = new ArrayList<Pagoegreso>();
            for (Pagoegreso pagoegresoListNewPagoegresoToAttach : pagoegresoListNew) {
                pagoegresoListNewPagoegresoToAttach = em.getReference(pagoegresoListNewPagoegresoToAttach.getClass(), pagoegresoListNewPagoegresoToAttach.getIDEgreso());
                attachedPagoegresoListNew.add(pagoegresoListNewPagoegresoToAttach);
            }
            pagoegresoListNew = attachedPagoegresoListNew;
            maestro.setPagoegresoList(pagoegresoListNew);
            List<Clase> attachedClaseListNew = new ArrayList<Clase>();
            for (Clase claseListNewClaseToAttach : claseListNew) {
                claseListNewClaseToAttach = em.getReference(claseListNewClaseToAttach.getClass(), claseListNewClaseToAttach.getIDClase());
                attachedClaseListNew.add(claseListNewClaseToAttach);
            }
            claseListNew = attachedClaseListNew;
            maestro.setClaseList(claseListNew);
            maestro = em.merge(maestro);
            for (Pagoegreso pagoegresoListNewPagoegreso : pagoegresoListNew) {
                if (!pagoegresoListOld.contains(pagoegresoListNewPagoegreso)) {
                    Maestro oldIDMaestroPEOfPagoegresoListNewPagoegreso = pagoegresoListNewPagoegreso.getIDMaestroPE();
                    pagoegresoListNewPagoegreso.setIDMaestroPE(maestro);
                    pagoegresoListNewPagoegreso = em.merge(pagoegresoListNewPagoegreso);
                    if (oldIDMaestroPEOfPagoegresoListNewPagoegreso != null && !oldIDMaestroPEOfPagoegresoListNewPagoegreso.equals(maestro)) {
                        oldIDMaestroPEOfPagoegresoListNewPagoegreso.getPagoegresoList().remove(pagoegresoListNewPagoegreso);
                        oldIDMaestroPEOfPagoegresoListNewPagoegreso = em.merge(oldIDMaestroPEOfPagoegresoListNewPagoegreso);
                    }
                }
            }
            for (Clase claseListOldClase : claseListOld) {
                if (!claseListNew.contains(claseListOldClase)) {
                    claseListOldClase.setIDMaestroC(null);
                    claseListOldClase = em.merge(claseListOldClase);
                }
            }
            for (Clase claseListNewClase : claseListNew) {
                if (!claseListOld.contains(claseListNewClase)) {
                    Maestro oldIDMaestroCOfClaseListNewClase = claseListNewClase.getIDMaestroC();
                    claseListNewClase.setIDMaestroC(maestro);
                    claseListNewClase = em.merge(claseListNewClase);
                    if (oldIDMaestroCOfClaseListNewClase != null && !oldIDMaestroCOfClaseListNewClase.equals(maestro)) {
                        oldIDMaestroCOfClaseListNewClase.getClaseList().remove(claseListNewClase);
                        oldIDMaestroCOfClaseListNewClase = em.merge(oldIDMaestroCOfClaseListNewClase);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Pagoegreso> pagoegresoListOrphanCheck = maestro.getPagoegresoList();
            for (Pagoegreso pagoegresoListOrphanCheckPagoegreso : pagoegresoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Pagoegreso " + pagoegresoListOrphanCheckPagoegreso + " in its pagoegresoList field has a non-nullable IDMaestroPE field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Clase> claseList = maestro.getClaseList();
            for (Clase claseListClase : claseList) {
                claseListClase.setIDMaestroC(null);
                claseListClase = em.merge(claseListClase);
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
