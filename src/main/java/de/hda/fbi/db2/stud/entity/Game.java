package de.hda.fbi.db2.stud.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

@Entity
@Table(name = "Game", schema = "db2")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int gameId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date gameBegin;

  @Temporal(TemporalType.TIMESTAMP)
  private Date gameEnd;

  @OneToOne(cascade = CascadeType.PERSIST)
  private Player player;

  @ElementCollection
  @CollectionTable(schema = "db2", name = "gameAnswers")
  @MapKeyJoinColumn(name = "question_questionId")
  private Map<Question, Integer> playerQuestionAnswer = new HashMap<>();


  /**
   * Constructor.
   *
   * @param player The player object.
   */
  public Game(Player player) {
    this.gameBegin = new Date(System.currentTimeMillis());
    this.gameEnd = null;
    this.player = player;
  }



  /**
   * Constructor.
   */
  public Game() {

  }


  /**
   * Set the end of the game.
   * @param timeInMillis The timestamp in milliseconds.
   */
  public void setEnd(long timeInMillis) {
    this.gameEnd = new Date(timeInMillis);
  }


  /**
   * Set the start of the game.
   * @param timeInMillis The timestamp in milliseconds.
   */
  public void setStart(long timeInMillis) {
    this.gameBegin = new Date(timeInMillis);
  }


  /**
   * Get the list of questions for this game.
   * -1 as value means that the player has not given an answer yet.
   * @return A map of Question keys and Integer values with the questions.
   */
  public Map<Question, Integer> getAnswerList() {
    return playerQuestionAnswer;
  }


  /**
   * Set the answer map for this game.
   * @param questionIntegerMap The prefilled map with questions.
   */
  public void setAnswerList(Map<Question, Integer> questionIntegerMap) {
    this.playerQuestionAnswer = questionIntegerMap;
  }


  /**
   * Return the gameId.
   * @return An int containing the gameID.
   */
  public int getGameId() {
    return gameId;
  }



  /**
   * Generate a hash code for this object.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return this.gameId;
  }


  /**
   * Check the equality of the object.
   *
   * @param obj The object to compare with
   * @return true, if the object equals the given one.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Game) {
      Game tmp = (Game) obj;
      return this.gameId == tmp.getGameId();
    }

    return false;
  }
}
