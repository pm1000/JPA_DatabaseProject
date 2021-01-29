package de.hda.fbi.db2.stud.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Game", schema = "db2")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int gameID;
  @Temporal(TemporalType.TIMESTAMP)
  private Date gameBegin;
  @Temporal(TemporalType.TIMESTAMP)
  private Date gameEnd;
  @OneToOne(cascade = CascadeType.PERSIST)
  private Player player;
  @OneToMany(mappedBy = "game")
  private List<GameAnswer> answerList;


  /**
   * Constructor.
   *
   * @param player The player object.
   */
  public Game(Player player) {
    this.gameBegin = new Date(System.currentTimeMillis());;
    this.gameEnd = null;
    this.player = player;
  }


  /**
   * Constructor.
   */
  public Game() {

  }


  public void setEnd() {
    this.gameEnd = new Date(System.currentTimeMillis());;
  }

  public List<GameAnswer> getAnswerList() {
    return answerList;
  }

  public void setAnswerList(List<GameAnswer> answerList) {
    this.answerList = answerList;
  }
}
