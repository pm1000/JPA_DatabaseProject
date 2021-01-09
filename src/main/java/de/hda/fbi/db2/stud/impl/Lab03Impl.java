package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.Player;

import java.util.Scanner;

public class Lab03Impl extends de.hda.fbi.db2.api.Lab03Game {

  private javax.persistence.EntityManager em;


  /**
   * Constructor.
   */
  public Lab03Impl () {
    em = lab02EntityManager.getEntityManager();
  }

  /**
   * Get the player from the database or create a new one.
   *
   * @param playerName The name for the new Player.
   * @return The player object.
   */
  @Override
  public Object getOrCreatePlayer(String playerName) {

    // Try to get the player from the database.
    Player player;
    player = (Player) em.createNamedQuery("Player.findByName").setParameter("name", playerName).getSingleResult();

    // If the player is not in the database create a new one.
    if (player == null) {
      player = new Player();
      player.setName(playerName);
      em.persist(player);
    }

    return player;
  }



  /**
   * Call getOrCreatePlayer() with a user interface.
   *
   * @return The player object.
   */
  @Override
  public Object interactiveGetOrCreatePlayer() {

    System.out.print("Bitte einen Spielernamen eingeben: ");
    Scanner scn = new Scanner(System.in);
    String name = "";

    for (boolean isCorrect = false; !isCorrect; ) {

      name =  scn.nextLine();
      if (!name.isEmpty() || !name.isBlank()) {
        isCorrect = true;
      } else {
        System.out.print("\nName nicht korrekt. Bitte erneut eingeben: ");
      }
    }

    scn.close();
    return getOrCreatePlayer(name);
  }



  /**
   * Get a random list of questions from the given categories and the gn amount of questions per category.
   *
   * @param categories                   A list of categories to select questions from
   * @param amountOfQuestionsForCategory The amount of questions per category. If a category has
   *                                     less than this amount, then all questions of that category
   *                                     shall be selected.
   * @return A random list of questions.
   */
  @Override
  public java.util.List<?> getQuestions(java.util.List<?> categories, int amountOfQuestionsForCategory) {
    return null;
  }


  /**
   * Call getQuestions() with a user interface.
   *
   * @return
   */
  @Override
  public java.util.List<?> interactiveGetQuestions() {
    return null;
  }


  /**
   * Create a new game object.
   *
   * @param player    The Player which shall play the game.
   * @param questions The Questions which shall be asked during the game.
   * @return The game object.
   */
  @Override
  public Object createGame(Object player, java.util.List<?> questions) {
    return null;
  }


  /**
   * Play the game automatically. Without user interaction.
   *
   * @param game The Game Object which shall be played.
   */
  @Override
  public void playGame(Object game) {

  }


  /**
   * Start playing the game with user interaction.
   *
   * @param game The Game Object which shall be played.
   */
  @Override
  public void interactivePlayGame(Object game) {

  }


  /**
   * Persist the game to the database.
   *
   * @param game The Game Object to be persisted.
   */
  @Override
  public void persistGame(Object game) {

  }
}
