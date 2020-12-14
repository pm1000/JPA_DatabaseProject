package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class Lab02Impl extends Lab02EntityManager {

    /**
     * Object attributes.
     */
    private EntityManagerFactory emFactory;


    /**
     * Override the persist data function. Implementation to save all objects to the database.
     */
    @Override
    public void persistData() {

        EntityManager em = getEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            ArrayList<?> cats = (ArrayList<?>) lab01Data.getCategories();
            ArrayList<?> questions = (ArrayList<?>) lab01Data.getQuestions();

            for (Object cat : cats) {
                Category tmp = (Category) cat;
                em.persist(tmp);
            }

            for (Object question : questions) {
                Question tmp = (Question) question;
                em.persist(tmp);
            }

            et.commit();

        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }

        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }


    /**
     * Get an EntityManager instance.
     *
     * @return A reference of an EntityManager object.
     */
    @Override
    public EntityManager getEntityManager() {
        try {
            return this.emFactory.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * Override the init method to initialize the function.
     */
    @Override
    public void init() {
        super.init();

        this.emFactory = Persistence.createEntityManagerFactory("fbi-postgresPU");
    }
}
