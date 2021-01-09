package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameAnswer;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;

import java.util.*;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.io.*;

public class Lab03Impl extends de.hda.fbi.db2.api.Lab03Game {

  private javax.persistence.EntityManager em;
  static private Scanner scanner = new Scanner(System.in);


  /**
   * Constructor.
   */
  public Lab03Impl () {

  }

  @Override
  public void init() {
    super.init();
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
    try {
      player = (Player) em.createNamedQuery("Player.findByName").setParameter("name", playerName).getSingleResult();
    } catch (NoResultException e) {
      EntityTransaction et = null;

      try {
        et = em.getTransaction();
        et.begin();
        player = new Player();
        player.setName(playerName);
        em.persist(player);

        et.commit();

      } catch (Exception ex) {
        if (et != null && et.isActive()) {
          et.rollback();
        }

      }
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
    String name = "";

    for (boolean isCorrect = false; !isCorrect; ) {

      name =  scanner.nextLine();
      if (!name.isEmpty() || !name.isBlank()) {
        isCorrect = true;
      } else {
        System.out.print("\nName nicht korrekt. Bitte erneut eingeben: ");
      }
    }


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

    Random random = new Random();
    List<Question> questions = new ArrayList<Question>();

    for (Object obj : categories) {
      Category cat = (Category) obj;
      List<Question> catQuestions = cat.getQuestions();

      // Less questions than expected
      if (catQuestions.size() <= amountOfQuestionsForCategory) {
        questions.addAll(catQuestions);

        // Choose random questions
      } else {
        List<Question> tmpQuestionList = new ArrayList<Question>();

        for (int i = 0; i < amountOfQuestionsForCategory; i++) {
          Question tmpQuestion;
          do {
            tmpQuestion = catQuestions.get(random.nextInt(catQuestions.size() - 1));
          } while (tmpQuestionList.contains(tmpQuestion));
          tmpQuestionList.add(tmpQuestion);
        }

        questions.addAll(tmpQuestionList);
      }
    }

    return questions;
  }


  /**
   * Call getQuestions() with a user interface.
   *
   * @return
   */
  @Override
  public java.util.List<?> interactiveGetQuestions() {

    // Get all categories available.
    List<Category> allCategoriesList = (List<Category>) em.createNamedQuery("Category.findAll").getResultList();

    // Prepare input variables.
    int questionsPerCategory = 0;
    int amountCategories = 0;
    List<Category> categories = new ArrayList<>();


    // Get the questions per category.
    System.out.println("Anzahl der Fragen pro Kategorie: ");
    boolean isCorrect = false;
    do {
      try {
        questionsPerCategory = scanner.nextInt();
        isCorrect = true;
      } catch (Exception e) {
        System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
      }
    } while (!isCorrect);


    // Get the amount of categories asked.
    System.out.print("Anzahl der Kategorien: ");
    isCorrect = false;
    do {
      try {
        amountCategories = Integer.parseInt(scanner.nextLine());
        isCorrect = true;
      } catch (Exception e) {
        System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
      }
    } while (!isCorrect);

    // print all categories with the corresponding ids and fill up a hashmap for easy access.
    Map<Integer, Category> allCategoriesMap = new HashMap<>();
    for (Category cat : allCategoriesList) {
      System.out.println("[" + cat.getCatID() + "] " + cat.getCategoryName());
      allCategoriesMap.put(cat.getCatID(), cat);
    }

    // Get the categories from the user.
    for (int i = 0; i < amountCategories; i++) {
      System.out.print("Wähle deine " + (i+1) + ". Kategorie per ID: ");

      // Check the input of the user.
      int catId = 0;
      isCorrect = false;
      do {
        try {
          catId = Integer.parseInt(scanner.nextLine());
          isCorrect = true;
        } catch (Exception e) {
          System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
        }
      } while (!isCorrect);

      // Check if the category is already chosen.
      if (!allCategoriesMap.containsKey(catId) || categories.contains(allCategoriesMap.get(catId))) {
        System.out.println("Kategorie nicht vorhanden oder schon ausgewählt.");
        i--;
      } else {
        categories.add(allCategoriesMap.get(catId));
      }
    }

    System.out.println("Fragen pro Kategorie: " + questionsPerCategory);
    System.out.println("Anzahl Kategorien: " + amountCategories);
    System.out.println("Ausgewählte Kategorien: " + categories.toString());
    
    return getQuestions(categories, questionsPerCategory);
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
    for (int i = 0; i < g.getAnswerList().size(); ++i){
      System.out.println("Frage Nr." + i + ": " + g.getAnswerList().get(i).getQuestion().getQuestionText() +
                         "\n Antwort 1: " + g.getAnswerList().get(i).getQuestion().getAnswers().get(0) +
                         "\n Antwort 2: " + g.getAnswerList().get(i).getQuestion().getAnswers().get(1) +
                         "\n Antwort 3: " + g.getAnswerList().get(i).getQuestion().getAnswers().get(2) +
                         "\n Antwort 4: " + g.getAnswerList().get(i).getQuestion().getAnswers().get(3) +
                         "\n Ihre Antwort: \n");

      // get the user input and check his input
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

      g.getAnswerList().get(i).setPlayerAnswer(answer - 1);
    }

    //set end of the game
    Date date = new Date(System.currentTimeMillis());
    g.setEnd(date);
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

    //scanner.close();
  }
}
