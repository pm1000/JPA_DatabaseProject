package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.*;

public class Lab05Impl {

  Lab02EntityManager lab02;


  /**
   * Setter for entity manager.
   * @param lab02 The entity manager.
   */
  public void setLab02(Lab02EntityManager lab02) {
    this.lab02 = lab02;
  }


  /**
   * Starts the menu for all queries.
   * Interactive by the user.
   */
  public void startMenu () {

    // Open the entity manager.
    EntityManager em = lab02.getEntityManager();

    // Ask the user what to do.
    Scanner scn = new Scanner(System.in);
    String nextLine = "";
    do {
      System.out.println("\n######################################################################");
      System.out.println("Choose an option:");
      System.out.println("[1] Query 1");
      System.out.println("[2] Query 2");
      System.out.println("[3] Query 3");
      System.out.println("[4] Query 4");
      System.out.println("[0] Return to main menu");
      nextLine = scn.nextLine();

      // Check the input.
      long timeStart = System.currentTimeMillis();
      List<?> resultList = null;
      String headerString = "";
      if (nextLine.compareTo("1") == 0) {

        // Set values for this query.
        Timestamp end = new Timestamp(System.currentTimeMillis());
        Timestamp begin = new Timestamp(1612158720000L);

        // Query 1.
        Query query = em.createQuery("select distinct g.player.name from Game g " +
                                              "where g.gameBegin > :begin and " +
                                              "g.gameEnd < :end")
                        .setParameter("begin", begin)
                        .setParameter("end", end);
        headerString = "PlayerName";
        resultList = query.getResultList();



      } else if (nextLine.compareTo("2") == 0) {

        // Set values for this query.
        int playerId = 4194;

        // Query 2.
        Query q = em.createQuery("select g.gameId, g.gameBegin, g.gameEnd, count(key(p)), " +
                                   "sum(case when (q.correctAnswer = value(p)) then 1 else 0 end) " +
                                   "from Game g " +
                                   "join g.playerQuestionAnswer p " +
                                   "join Question q " +
                                   "where g.player.playerID = :player " +
                                   "and q.questionId = key(p).questionId " +
                                   "group by g.gameId, g.gameBegin, g.gameEnd")
                    .setParameter("player", playerId);
        resultList = q.getResultList();
        headerString = "gameId, gameBegin, gameEnd, countQuestions, countCorrectQuestions";



      } else if (nextLine.compareTo("3") == 0) {

        // Query 3.
        Query q = em.createQuery("select g.player.name, count(g) as anzahl " +
                                   "from Game g " +
                                   "group by g.player.name " +
                                   "order by anzahl desc");
        resultList = q.getResultList();
        headerString = "PlayerName, CountGames";


      } else if (nextLine.compareTo("4") == 0) {

        // Query 4.
        Query q = em.createQuery("select q.cat, count(key(p)) as anzahl " +
                                   "from Game g " +
                                   "join g.playerQuestionAnswer p " +
                                   "join Question q " +
                                   "where q = key(p) " +
                                   "group by q.cat " +
                                   "order by anzahl desc");
        resultList = q.getResultList();
        headerString = "CategoryName, countAsked";
      }

      // Display the time for this query.
      if (resultList != null) {
        System.out.println("Time for this query: " + (System.currentTimeMillis() - timeStart) + "ms.");
        System.out.println("Size of the result list: " + resultList.size() + ". Printing the first 10 rows.");

        System.out.println("Header: " + headerString);
        if (resultList.size() > 10) {
          for (int i = 0; i < 10; i++) {
            Object[] tmp = (resultList.get(i).getClass().isArray())
                             ? (Object[]) resultList.get(i) : new Object[]{resultList.get(i)};
            System.out.println("[" + i + "] " + Arrays.toString(tmp));
          }

        } else {
          for (int i = 0; i < resultList.size(); i++) {
            Object[] tmp = (resultList.get(i).getClass().isArray())
                             ? (Object[]) resultList.get(i) : new Object[]{resultList.get(i)};
            System.out.println("[" + i + "] " + Arrays.toString(tmp));
          }
        }
      }


    } while (nextLine.compareTo("0") != 0);

    // Close the entity manager.
    em.close();
  }
}
