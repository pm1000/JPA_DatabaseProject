package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

        em.getTransaction().begin();
        ArrayList<Category> cats = (ArrayList<Category>) lab01Data.getCategories();
        ArrayList<Question> questions = (ArrayList<Question>) lab01Data.getQuestions();

        for (Category cat : cats) {
            em.persist(cat);
        }

        for (Question question : questions) {
            em.persist(question);
        }

        em.getTransaction().commit();
        em.close();
    }


    /**
     * Get an EntityManager instance.
     *
     * @return A reference of an EntityManager object.
     */
    @Override
    public EntityManager getEntityManager() {
        return this.emFactory.createEntityManager();
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
