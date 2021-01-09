package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.*;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

public class Lab03Impl extends de.hda.fbi.db2.api.Lab03Game {

  private javax.persistence.EntityManager em;


  /**
   * Constructor.
   */
  public Lab03Impl() {

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
      player = (Player) em.createNamedQuery("Player.findByName")
                          .setParameter("name", playerName).getSingleResult();
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

    // Prepare input variables.
    String name = "";

    try {
      // Prepare input.
      Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
      System.out.print("Bitte einen Spielernamen eingeben: ");

      // Read the name
      for (boolean isCorrect = false; !isCorrect; ) {
        name = scanner.nextLine();
        if (!name.isEmpty() || !name.isBlank()) {
          isCorrect = true;
        } else {
          System.out.print("Name nicht korrekt. Bitte erneut eingeben: ");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return getOrCreatePlayer(name);
  }


  /**
   * Get a random list of questions from the given categories and
   * the gn amount of questions per category.
   *
   * @param categories                   A list of categories to select questions from
   * @param amountOfQuestionsForCategory The amount of questions per category. If a category has
   *                                     less than this amount, then all questions of that category
   *                                     shall be selected.
   * @return A random list of questions.
   */
  @Override
  public java.util.List<?> getQuestions(java.util.List<?> categories,
                                        int amountOfQuestionsForCategory) {

    Random random = new Random();
    List<Question> questions = new ArrayList<>();

    for (Object obj : categories) {
      Category cat = (Category) obj;
      List<Question> catQuestions = cat.getQuestions();

      // Less questions than expected
      if (catQuestions.size() <= amountOfQuestionsForCategory) {
        questions.addAll(catQuestions);

        // Choose random questions
      } else {
        List<Question> tmpQuestionList = new ArrayList<>();

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
   * @return A list of questions.
   */
  @Override
  public java.util.List<?> interactiveGetQuestions() {

    // Prepare input variables.
    List<Category> categories = new ArrayList<>();
    int questionsPerCategory = 0;
    int amountCategories = 0;

    try {
      // Prepare input
      Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

      // Get the questions per category.
      System.out.print("Anzahl der Fragen pro Kategorie: ");
      boolean isCorrect = false;
      do {
        try {
          questionsPerCategory = scanner.nextInt();
          if (questionsPerCategory > 0) { // More than zero questions per category.
            isCorrect = true;
          } else {
            throw new Exception();
          }
        } catch (Exception e) {
          System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
        }
      } while (!isCorrect);


      // Get the amount of categories asked.
      System.out.print("Anzahl der Kategorien: ");
      isCorrect = false;
      do {
        try {
          amountCategories = scanner.nextInt();
          if (amountCategories > 1) { // More than 2 categories.
            isCorrect = true;
          } else {
            throw new Exception();
          }
        } catch (Exception e) {
          System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
        }
      } while (!isCorrect);

      // Get all categories available.
      List<?> allCategoriesList = em.createNamedQuery("Category.findAll").getResultList();

      // print all categories with the corresponding ids and fill up a hashmap for easy access.
      Map<Integer, Category> allCategoriesMap = new HashMap<>();
      for (Object obj : allCategoriesList) {
        Category cat = (Category) obj;
        System.out.println("[" + cat.getCatID() + "] " + cat.getCategoryName());
        allCategoriesMap.put(cat.getCatID(), cat);
      }

      // Get the categories from the user.
      for (int i = 0; i < amountCategories; i++) {
        System.out.print("Wähle deine " + (i + 1) + ". Kategorie per ID: ");

        // Check the input of the user.
        int catId = 0;
        isCorrect = false;
        do {
          try {
            catId = scanner.nextInt();
            isCorrect = true;
          } catch (Exception e) {
            System.out.print("Eingabe ist keine valide Zahl. Bitte Zahl eingeben: ");
          }
        } while (!isCorrect);

        // Check if the category is already chosen.
        if (!allCategoriesMap.containsKey(catId)
              || categories.contains(allCategoriesMap.get(catId))) {
          System.out.println("Kategorie nicht vorhanden oder schon ausgewählt.");
          i--;
        } else {
          categories.add(allCategoriesMap.get(catId));
        }
      }

      System.out.println("\n\n############################################################");
      System.out.println("Fragen pro Kategorie: " + questionsPerCategory);
      System.out.println("Anzahl Kategorien: " + amountCategories);
      System.out.println("Ausgewählte Kategorien: " + categories.toString());
      System.out.println("############################################################\n\n");

    } catch (Exception e) {
      e.printStackTrace();
    }
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
    Game game = new Game((Player) player);

    // Create GameAnswerList
    List<GameAnswer> gameAnswers = new ArrayList<>();
    for (Object question : questions) {
      gameAnswers.add(new GameAnswer(game, (Question) question));
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

    // Iterate trough each question and get the player answer.
    Game g = (Game) game;
    List<GameAnswer> gameAnswers = g.getAnswerList();

    for (GameAnswer gameAnswer : gameAnswers) {
      // Generate a random answer.
      int answer = (int) ((Math.random() * (4 - 1)) + 1);
      gameAnswer.setPlayerAnswer(answer);
    }
  }


  /**
   * Start playing the game with user interaction.
   *
   * @param game The Game Object which shall be played.
   */
  @Override
  public void interactivePlayGame(Object game) {


    try {
      // Prepare input
      Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

      // Iterate trough each question and get the player answer
      Game g = (Game) game;
      for (GameAnswer a : g.getAnswerList()) {
        System.out.print("\nFrage: " + a.getQuestion().getQuestionText()
                           + "\n Antwort 1: "
                           + a.getQuestion().getAnswers().get(0)
                           + "\n Antwort 2: "
                           + a.getQuestion().getAnswers().get(1)
                           + "\n Antwort 3: "
                           + a.getQuestion().getAnswers().get(2)
                           + "\n Antwort 4: "
                           + a.getQuestion().getAnswers().get(3)
                           + "\n Ihre Antwort: ");

        // get the user input and check his input
        int answer = 0;
        boolean correctInput = false;

        while (!correctInput) {
          try {
            answer = scanner.nextInt();
            if (answer < 1 || answer > 4) {
              throw new Exception();
            }
            correctInput = true;
          } catch (Exception e) {
            System.out.println("Bitte geben Sie eine Zahl zwischen 1 und 4 ein:");
          }
        }

        //needed to for db
        answer = answer - 1;

        a.setPlayerAnswer(answer);

        // Print the correect answer.
        if (a.getQuestion().getCorrectAnswer() == answer) {
          System.out.println("Die Antwort ist korrekt.");
        } else {
          System.out.println("Die Antwort ist nicht korrekt. Die korrekte Antwort ist "
                               + (a.getQuestion().getCorrectAnswer() + 1));
        }
      }

      //set end of the game
      g.setEnd();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Persist the game to the database.
   *
   * @param game The Game Object to be persisted.
   */
  @Override
  public void persistGame(Object game) {

    // Open transaction
    EntityTransaction et = null;

    try {
      et = em.getTransaction();
      et.begin();

      // First step: persist the game
      Game g = (Game) game;
      em.persist(g);

      // Persist all answers
      List<GameAnswer> gameAnswers = g.getAnswerList();
      for (GameAnswer gameAnswer : gameAnswers) {
        em.persist(gameAnswer);
      }

      et.commit();
    } catch (Exception e) {
      if (et != null && et.isActive()) {
        et.rollback();
      }
    }
  }
}
