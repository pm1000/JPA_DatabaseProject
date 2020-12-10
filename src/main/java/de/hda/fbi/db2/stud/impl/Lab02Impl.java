package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.util.ArrayList;

public class Lab02Impl extends Lab02EntityManager {
    private static EntityManagerFactory emFactory;

    public Lab02Impl(){
        this.emFactory = Persistence.createEntityManagerFactory("fbi-postgresPU");

    }

    @Override
    public void persistData() {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        ArrayList<Category> cats = (ArrayList<Category>) lab01Data.getCategories();
        ArrayList<Question> questions = (ArrayList<Question>) lab01Data.getQuestions();
        
        for (int i = 0; i < cats.size(); i++){
            em.persist(cats.get(i));
        }

        for (int i = 0; i < questions.size(); i++){
            em.persist(questions.get(i));
        }

        em.getTransaction().commit();
        em.close();

    }

    @Override
    public EntityManager getEntityManager() {
        return this.emFactory.createEntityManager();
    }
}
