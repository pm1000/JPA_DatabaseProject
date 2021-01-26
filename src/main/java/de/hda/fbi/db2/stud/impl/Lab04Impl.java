package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameAnswer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.Instant;
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

    try {
      // Start the transaction.
      et = em.getTransaction();
      et.begin();
      System.out.println("Start of transaction. " + Instant.now());

      // Load the categories.
      List<?> allCategoriesList = em.createNamedQuery("Category.findAll").getResultList();

      // 10.000 Player.
      for (int i = 0; i < 10000; i++) {

        // Get the player
        Object player = lab03Game.getOrCreatePlayer("User_" + i);
        System.out.println("Start of player" + i + ". " + Instant.now());

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
      }

      // Commit the transaction.
      et.commit();
      System.out.println("Start of Commit. " + Instant.now());

    } catch (Exception e) {
      if (et != null && et.isActive()) {
        et.rollback();
      }
    } finally {

      // Close the entity manager
      em.close();
      System.out.println("Finished. " + Instant.now());
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
