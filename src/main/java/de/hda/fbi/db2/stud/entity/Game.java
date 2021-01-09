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

  public Game(Date begin, Player player) {
    this.gameBegin = begin;
    this.gameEnd = null;
    this.player = player;
  }

  public Game() {

  }


  public void setEnd(Date end) {
    this.gameEnd = end;
  }

  public List<GameAnswer> getAnswerList() {
    return answerList;
  }

  public void setAnswerList(List<GameAnswer> answerList) {
    this.answerList = answerList;
  }
}
