package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameAnswer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab04Impl extends Lab04MassData {

  /**
   * Class attributes.
   */
  private final Random r = new Random();


  /**
   * Constructor.
   */
  public Lab04Impl() {

  }


  /**
   * Method call to create the mass data.
   */
  @Override
  public void createMassData() {

    // Get an entityManager.
    EntityManager em = lab02EntityManager.getEntityManager();
    EntityTransaction et = null;

    // Logging.
    long allTime = System.currentTimeMillis();
    long playerTime = System.currentTimeMillis();
    System.out.println("Start Transaction.");

    try {
      // Start the transaction.
      et = em.getTransaction();
      et.begin();

      // Load the categories.
      List<?> allCategoriesList = em.createNamedQuery("Category.findAll").getResultList();

      // 10.000 Player.
      for (int i = 0; i < 10000; i++) {

        // Get the player
        Object player = lab03Game.getOrCreatePlayer("User_" + i);

        // For every player play 100 games.
        for (int o = 0; o < 100; o++) {

          // Get random categories
          List<Category> categories = getRandomCategoriesAsList(allCategoriesList);

          // Get the questions
          List<?> questions = lab03Game.getQuestions(categories, 3);

          // Create the game
          Game game = (Game) lab03Game.createGame(player, questions);
          lab03Game.playGame(game);

          // Persist everything.
          em.persist(game);
          for (GameAnswer gameAnswer : game.getAnswerList()) {
            em.persist(gameAnswer);
          }
        }


        // Flush after 1000 player
        if (i % 1000 == 999) {
          System.out.println("End MassData after " + (System.currentTimeMillis() - playerTime) + "ms.");
          playerTime = System.currentTimeMillis();
          long flushTime = System.currentTimeMillis();
          em.flush();
          em.clear();
          System.out.println("End Flush after " + (System.currentTimeMillis() - flushTime) + "ms.");
        }
      }

      // Commit the transaction.
      et.commit();

    } catch (Exception e) {
      if (et != null && et.isActive()) {
        et.rollback();
      }
      e.printStackTrace();
    } finally {

      // Logging.
      System.out.println("End Transaction after " +  (System.currentTimeMillis() - allTime) + "ms.");

      // Close the entity manager
      em.close();
    }
  }


  /**
   * Return a list of random categories.
   *
   * @return A list of random categories.
   */
  private List<Category> getRandomCategoriesAsList(List<?> allCategories) {
    // Create the return list.
    List<Category> cats = new ArrayList<>();
    Category c;

    // Add the given number of categories.
    for (int i = 0; i < 6; i++) {
      // Check for this category.
      do {
        c = (Category) allCategories.get(r.nextInt(allCategories.size() - 1));
      } while (cats.contains(c));
      cats.add(c);
    }

    return cats;
  }
}
