package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
  private Player player;
  @OneToMany(mappedBy = "game")
  private List<GameAnswer> answerList;

  public Game(Player player) {
    this.gameBegin = new Date(System.currentTimeMillis());;
    this.gameEnd = null;
    this.player = player;
  }

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
