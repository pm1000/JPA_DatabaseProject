package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameAnswer;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;

import javax.persistence.EntityTransaction;
import java.util.*;

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
    Player player = null;
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
    // get current time
    Date date = new Date(System.currentTimeMillis());
    Game game = new Game(date, (Player) player);

    //create GameAnswerList
    List<GameAnswer> gameAnswers = new ArrayList<GameAnswer>();
    for (int i = 0; i < questions.size(); ++i){
      gameAnswers.add(new GameAnswer(game, (Question) questions.get(i)));
    }
    game.setAnswerList(gameAnswers);

    return game;
  }


  /**
   * Play the game automatically. Without user interaction.
   *
   * @param game The Game Object which shall be played.
   */
  @Override
  public void playGame(Object game) {
    // iterate trough each question and get the player answer
    Game g = (Game) game;
    List<GameAnswer> gameAnswers = g.getAnswerList();
    for (int i = 0; i < gameAnswers.size(); ++i){
      //generate a random answer
      int answer = (int) ((Math.random() * (4 - 1)) + 1);
      gameAnswers.get(i).setPlayerAnswer(answer);
    }
  }


  /**
   * Start playing the game with user interaction.
   *
   * @param game The Game Object which shall be played.
   */
  @Override
  public void interactivePlayGame(Object game) {
    // iterate trough each question and get the player answer
    Game g = (Game) game;
    List<GameAnswer> gameAnswers = g.getAnswerList();
    for (int i = 0; i < gameAnswers.size(); ++i){
      System.out.print("Frage Nr." + i + ": " + gameAnswers.get(i).getQuestion().getQuestionText() +
                        "\n Antwort 1: " + gameAnswers.get(i).getQuestion().getAnswers().get(0) +
                        "\n Antwort 2: " + gameAnswers.get(i).getQuestion().getAnswers().get(1) +
                        "\n Antwort 3: " + gameAnswers.get(i).getQuestion().getAnswers().get(2) +
                        "\n Antwort 4: " + gameAnswers.get(i).getQuestion().getAnswers().get(3) +
                        "\n Ihre Antwort: \n");

      // get the user input and check his input
      Scanner scanner = new Scanner(System.in);
      int answer = 0;
      boolean correctInput = false;

      while (!correctInput) {
        try {
          answer = scanner.nextInt();
          if (answer < 1 || answer > 4)
            throw new Exception();
          correctInput = true;
          } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bitte geben Sie eine Zahl zwischen 1 und 4 ein!");
        }
      }
      scanner.close();

      gameAnswers.get(i).setPlayerAnswer(answer);

    }
  }


  /**
   * Persist the game to the database.
   *
   * @param game The Game Object to be persisted.
   */
  @Override
  public void persistGame(Object game) {
    //open transaction
    EntityTransaction et = null;

    try {
      et = em.getTransaction();
      et.begin();

      //first step: persist the game
      Game g = (Game) game;
      em.persist(g);

      //persist all answers
      List<GameAnswer> gameAnswers = g.getAnswerList();
      for (int i = 0; i < gameAnswers.size(); ++i) {
        em.persist(gameAnswers.get(i));
      }
    }catch (Exception e) {
      if (et != null && et.isActive()) {
        et.rollback();
      }

    } finally {
      if (em.isOpen()) {
        em.close();
      }
    }

  }
}
