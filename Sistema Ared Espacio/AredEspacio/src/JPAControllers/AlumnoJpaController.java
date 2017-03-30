/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAControllers;

import BaseDeDatos.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BaseDeDatos.Inscripcion;
import BaseDeDatos.Grupo;
import java.util.ArrayList;
import java.util.Collection;
import BaseDeDatos.Pagoingreso;
import BaseDeDatos.Mensualidad;
import JPAControllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author yoresroy
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getGrupoCollection() == null) {
            alumno.setGrupoCollection(new ArrayList<Grupo>());
        }
        if (alumno.getPagoingresoCollection() == null) {
            alumno.setPagoingresoCollection(new ArrayList<Pagoingreso>());
        }
        if (alumno.getMensualidadCollection() == null) {
            alumno.setMensualidadCollection(new ArrayList<Mensualidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion IDInscripcionA = alumno.getIDInscripcionA();
            if (IDInscripcionA != null) {
                IDInscripcionA = em.getReference(IDInscripcionA.getClass(), IDInscripcionA.getIDInscripcion());
                alumno.setIDInscripcionA(IDInscripcionA);
            }
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : alumno.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getIDGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            alumno.setGrupoCollection(attachedGrupoCollection);
            Collection<Pagoingreso> attachedPagoingresoCollection = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionPagoingresoToAttach : alumno.getPagoingresoCollection()) {
                pagoingresoCollectionPagoingresoToAttach = em.getReference(pagoingresoCollectionPagoingresoToAttach.getClass(), pagoingresoCollectionPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollection.add(pagoingresoCollectionPagoingresoToAttach);
            }
            alumno.setPagoingresoCollection(attachedPagoingresoCollection);
            Collection<Mensualidad> attachedMensualidadCollection = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadCollectionMensualidadToAttach : alumno.getMensualidadCollection()) {
                mensualidadCollectionMensualidadToAttach = em.getReference(mensualidadCollectionMensualidadToAttach.getClass(), mensualidadCollectionMensualidadToAttach.getIDMensualidad());
                attachedMensualidadCollection.add(mensualidadCollectionMensualidadToAttach);
            }
            alumno.setMensualidadCollection(attachedMensualidadCollection);
            em.persist(alumno);
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoCollection().add(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            for (Grupo grupoCollectionGrupo : alumno.getGrupoCollection()) {
                Alumno oldIDAlumnoGOfGrupoCollectionGrupo = grupoCollectionGrupo.getIDAlumnoG();
                grupoCollectionGrupo.setIDAlumnoG(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldIDAlumnoGOfGrupoCollectionGrupo != null) {
                    oldIDAlumnoGOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldIDAlumnoGOfGrupoCollectionGrupo = em.merge(oldIDAlumnoGOfGrupoCollectionGrupo);
                }
            }
            for (Pagoingreso pagoingresoCollectionPagoingreso : alumno.getPagoingresoCollection()) {
                Alumno oldIDAlumnoPIOfPagoingresoCollectionPagoingreso = pagoingresoCollectionPagoingreso.getIDAlumnoPI();
                pagoingresoCollectionPagoingreso.setIDAlumnoPI(alumno);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
                if (oldIDAlumnoPIOfPagoingresoCollectionPagoingreso != null) {
                    oldIDAlumnoPIOfPagoingresoCollectionPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionPagoingreso);
                    oldIDAlumnoPIOfPagoingresoCollectionPagoingreso = em.merge(oldIDAlumnoPIOfPagoingresoCollectionPagoingreso);
                }
            }
            for (Mensualidad mensualidadCollectionMensualidad : alumno.getMensualidadCollection()) {
                Alumno oldIDAlumnoMOfMensualidadCollectionMensualidad = mensualidadCollectionMensualidad.getIDAlumnoM();
                mensualidadCollectionMensualidad.setIDAlumnoM(alumno);
                mensualidadCollectionMensualidad = em.merge(mensualidadCollectionMensualidad);
                if (oldIDAlumnoMOfMensualidadCollectionMensualidad != null) {
                    oldIDAlumnoMOfMensualidadCollectionMensualidad.getMensualidadCollection().remove(mensualidadCollectionMensualidad);
                    oldIDAlumnoMOfMensualidadCollectionMensualidad = em.merge(oldIDAlumnoMOfMensualidadCollectionMensualidad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIDAlumno());
            Inscripcion IDInscripcionAOld = persistentAlumno.getIDInscripcionA();
            Inscripcion IDInscripcionANew = alumno.getIDInscripcionA();
            Collection<Grupo> grupoCollectionOld = persistentAlumno.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = alumno.getGrupoCollection();
            Collection<Pagoingreso> pagoingresoCollectionOld = persistentAlumno.getPagoingresoCollection();
            Collection<Pagoingreso> pagoingresoCollectionNew = alumno.getPagoingresoCollection();
            Collection<Mensualidad> mensualidadCollectionOld = persistentAlumno.getMensualidadCollection();
            Collection<Mensualidad> mensualidadCollectionNew = alumno.getMensualidadCollection();
            if (IDInscripcionANew != null) {
                IDInscripcionANew = em.getReference(IDInscripcionANew.getClass(), IDInscripcionANew.getIDInscripcion());
                alumno.setIDInscripcionA(IDInscripcionANew);
            }
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getIDGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            alumno.setGrupoCollection(grupoCollectionNew);
            Collection<Pagoingreso> attachedPagoingresoCollectionNew = new ArrayList<Pagoingreso>();
            for (Pagoingreso pagoingresoCollectionNewPagoingresoToAttach : pagoingresoCollectionNew) {
                pagoingresoCollectionNewPagoingresoToAttach = em.getReference(pagoingresoCollectionNewPagoingresoToAttach.getClass(), pagoingresoCollectionNewPagoingresoToAttach.getIDIngreso());
                attachedPagoingresoCollectionNew.add(pagoingresoCollectionNewPagoingresoToAttach);
            }
            pagoingresoCollectionNew = attachedPagoingresoCollectionNew;
            alumno.setPagoingresoCollection(pagoingresoCollectionNew);
            Collection<Mensualidad> attachedMensualidadCollectionNew = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadCollectionNewMensualidadToAttach : mensualidadCollectionNew) {
                mensualidadCollectionNewMensualidadToAttach = em.getReference(mensualidadCollectionNewMensualidadToAttach.getClass(), mensualidadCollectionNewMensualidadToAttach.getIDMensualidad());
                attachedMensualidadCollectionNew.add(mensualidadCollectionNewMensualidadToAttach);
            }
            mensualidadCollectionNew = attachedMensualidadCollectionNew;
            alumno.setMensualidadCollection(mensualidadCollectionNew);
            alumno = em.merge(alumno);
            if (IDInscripcionAOld != null && !IDInscripcionAOld.equals(IDInscripcionANew)) {
                IDInscripcionAOld.getAlumnoCollection().remove(alumno);
                IDInscripcionAOld = em.merge(IDInscripcionAOld);
            }
            if (IDInscripcionANew != null && !IDInscripcionANew.equals(IDInscripcionAOld)) {
                IDInscripcionANew.getAlumnoCollection().add(alumno);
                IDInscripcionANew = em.merge(IDInscripcionANew);
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.setIDAlumnoG(null);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Alumno oldIDAlumnoGOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getIDAlumnoG();
                    grupoCollectionNewGrupo.setIDAlumnoG(alumno);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldIDAlumnoGOfGrupoCollectionNewGrupo != null && !oldIDAlumnoGOfGrupoCollectionNewGrupo.equals(alumno)) {
                        oldIDAlumnoGOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldIDAlumnoGOfGrupoCollectionNewGrupo = em.merge(oldIDAlumnoGOfGrupoCollectionNewGrupo);
                    }
                }
            }
            for (Pagoingreso pagoingresoCollectionOldPagoingreso : pagoingresoCollectionOld) {
                if (!pagoingresoCollectionNew.contains(pagoingresoCollectionOldPagoingreso)) {
                    pagoingresoCollectionOldPagoingreso.setIDAlumnoPI(null);
                    pagoingresoCollectionOldPagoingreso = em.merge(pagoingresoCollectionOldPagoingreso);
                }
            }
            for (Pagoingreso pagoingresoCollectionNewPagoingreso : pagoingresoCollectionNew) {
                if (!pagoingresoCollectionOld.contains(pagoingresoCollectionNewPagoingreso)) {
                    Alumno oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso = pagoingresoCollectionNewPagoingreso.getIDAlumnoPI();
                    pagoingresoCollectionNewPagoingreso.setIDAlumnoPI(alumno);
                    pagoingresoCollectionNewPagoingreso = em.merge(pagoingresoCollectionNewPagoingreso);
                    if (oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso != null && !oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso.equals(alumno)) {
                        oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso.getPagoingresoCollection().remove(pagoingresoCollectionNewPagoingreso);
                        oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso = em.merge(oldIDAlumnoPIOfPagoingresoCollectionNewPagoingreso);
                    }
                }
            }
            for (Mensualidad mensualidadCollectionOldMensualidad : mensualidadCollectionOld) {
                if (!mensualidadCollectionNew.contains(mensualidadCollectionOldMensualidad)) {
                    mensualidadCollectionOldMensualidad.setIDAlumnoM(null);
                    mensualidadCollectionOldMensualidad = em.merge(mensualidadCollectionOldMensualidad);
                }
            }
            for (Mensualidad mensualidadCollectionNewMensualidad : mensualidadCollectionNew) {
                if (!mensualidadCollectionOld.contains(mensualidadCollectionNewMensualidad)) {
                    Alumno oldIDAlumnoMOfMensualidadCollectionNewMensualidad = mensualidadCollectionNewMensualidad.getIDAlumnoM();
                    mensualidadCollectionNewMensualidad.setIDAlumnoM(alumno);
                    mensualidadCollectionNewMensualidad = em.merge(mensualidadCollectionNewMensualidad);
                    if (oldIDAlumnoMOfMensualidadCollectionNewMensualidad != null && !oldIDAlumnoMOfMensualidadCollectionNewMensualidad.equals(alumno)) {
                        oldIDAlumnoMOfMensualidadCollectionNewMensualidad.getMensualidadCollection().remove(mensualidadCollectionNewMensualidad);
                        oldIDAlumnoMOfMensualidadCollectionNewMensualidad = em.merge(oldIDAlumnoMOfMensualidadCollectionNewMensualidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIDAlumno();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
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
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIDAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            Inscripcion IDInscripcionA = alumno.getIDInscripcionA();
            if (IDInscripcionA != null) {
                IDInscripcionA.getAlumnoCollection().remove(alumno);
                IDInscripcionA = em.merge(IDInscripcionA);
            }
            Collection<Grupo> grupoCollection = alumno.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.setIDAlumnoG(null);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            Collection<Pagoingreso> pagoingresoCollection = alumno.getPagoingresoCollection();
            for (Pagoingreso pagoingresoCollectionPagoingreso : pagoingresoCollection) {
                pagoingresoCollectionPagoingreso.setIDAlumnoPI(null);
                pagoingresoCollectionPagoingreso = em.merge(pagoingresoCollectionPagoingreso);
            }
            Collection<Mensualidad> mensualidadCollection = alumno.getMensualidadCollection();
            for (Mensualidad mensualidadCollectionMensualidad : mensualidadCollection) {
                mensualidadCollectionMensualidad.setIDAlumnoM(null);
                mensualidadCollectionMensualidad = em.merge(mensualidadCollectionMensualidad);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}