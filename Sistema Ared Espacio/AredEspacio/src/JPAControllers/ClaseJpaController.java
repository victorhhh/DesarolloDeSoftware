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
import BaseDeDatos.Maestro;
import BaseDeDatos.Asistencia;
import BaseDeDatos.Clase;
import java.util.ArrayList;
import java.util.List;
import BaseDeDatos.Grupo;
import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ossiel
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
        if (clase.getAsistenciaList() == null) {
            clase.setAsistenciaList(new ArrayList<Asistencia>());
        }
        if (clase.getGrupoList() == null) {
            clase.setGrupoList(new ArrayList<Grupo>());
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
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : clase.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            clase.setAsistenciaList(attachedAsistenciaList);
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : clase.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getIDGrupo());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            clase.setGrupoList(attachedGrupoList);
            em.persist(clase);
            if (IDMaestroC != null) {
                IDMaestroC.getClaseList().add(clase);
                IDMaestroC = em.merge(IDMaestroC);
            }
            for (Asistencia asistenciaListAsistencia : clase.getAsistenciaList()) {
                Clase oldIDClaseAsisOfAsistenciaListAsistencia = asistenciaListAsistencia.getIDClaseAsis();
                asistenciaListAsistencia.setIDClaseAsis(clase);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldIDClaseAsisOfAsistenciaListAsistencia != null) {
                    oldIDClaseAsisOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldIDClaseAsisOfAsistenciaListAsistencia = em.merge(oldIDClaseAsisOfAsistenciaListAsistencia);
                }
            }
            for (Grupo grupoListGrupo : clase.getGrupoList()) {
                Clase oldIDClaseGOfGrupoListGrupo = grupoListGrupo.getIDClaseG();
                grupoListGrupo.setIDClaseG(clase);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldIDClaseGOfGrupoListGrupo != null) {
                    oldIDClaseGOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldIDClaseGOfGrupoListGrupo = em.merge(oldIDClaseGOfGrupoListGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getIDClase());
            Maestro IDMaestroCOld = persistentClase.getIDMaestroC();
            Maestro IDMaestroCNew = clase.getIDMaestroC();
            List<Asistencia> asistenciaListOld = persistentClase.getAsistenciaList();
            List<Asistencia> asistenciaListNew = clase.getAsistenciaList();
            List<Grupo> grupoListOld = persistentClase.getGrupoList();
            List<Grupo> grupoListNew = clase.getGrupoList();
            List<String> illegalOrphanMessages = null;
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its IDClaseAsis field is not nullable.");
                }
            }
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoListOldGrupo + " since its IDClaseG field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDMaestroCNew != null) {
                IDMaestroCNew = em.getReference(IDMaestroCNew.getClass(), IDMaestroCNew.getIDMaestro());
                clase.setIDMaestroC(IDMaestroCNew);
            }
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getIDAsistencia());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            clase.setAsistenciaList(asistenciaListNew);
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getIDGrupo());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            clase.setGrupoList(grupoListNew);
            clase = em.merge(clase);
            if (IDMaestroCOld != null && !IDMaestroCOld.equals(IDMaestroCNew)) {
                IDMaestroCOld.getClaseList().remove(clase);
                IDMaestroCOld = em.merge(IDMaestroCOld);
            }
            if (IDMaestroCNew != null && !IDMaestroCNew.equals(IDMaestroCOld)) {
                IDMaestroCNew.getClaseList().add(clase);
                IDMaestroCNew = em.merge(IDMaestroCNew);
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Clase oldIDClaseAsisOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getIDClaseAsis();
                    asistenciaListNewAsistencia.setIDClaseAsis(clase);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldIDClaseAsisOfAsistenciaListNewAsistencia != null && !oldIDClaseAsisOfAsistenciaListNewAsistencia.equals(clase)) {
                        oldIDClaseAsisOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldIDClaseAsisOfAsistenciaListNewAsistencia = em.merge(oldIDClaseAsisOfAsistenciaListNewAsistencia);
                    }
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Clase oldIDClaseGOfGrupoListNewGrupo = grupoListNewGrupo.getIDClaseG();
                    grupoListNewGrupo.setIDClaseG(clase);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldIDClaseGOfGrupoListNewGrupo != null && !oldIDClaseGOfGrupoListNewGrupo.equals(clase)) {
                        oldIDClaseGOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldIDClaseGOfGrupoListNewGrupo = em.merge(oldIDClaseGOfGrupoListNewGrupo);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Asistencia> asistenciaListOrphanCheck = clase.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clase (" + clase + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable IDClaseAsis field.");
            }
            List<Grupo> grupoListOrphanCheck = clase.getGrupoList();
            for (Grupo grupoListOrphanCheckGrupo : grupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clase (" + clase + ") cannot be destroyed since the Grupo " + grupoListOrphanCheckGrupo + " in its grupoList field has a non-nullable IDClaseG field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Maestro IDMaestroC = clase.getIDMaestroC();
            if (IDMaestroC != null) {
                IDMaestroC.getClaseList().remove(clase);
                IDMaestroC = em.merge(IDMaestroC);
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
