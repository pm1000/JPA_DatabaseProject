package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Game", schema = "db2")
public class Game {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int gameID;
    private Date begin;
    private Date end;
    private Player player;
    @OneToMany(mappedBy = "game")
    private List<GameAnswer> answerList;

    public Game(Date begin, Player player){
        this.begin = begin;
        this.end = null;
        this.player = player;
    }

    public Game(){

    }


    public void setEnd(Date end) {
        this.end = end;
    }

    public void setAnswerList(List<GameAnswer> answerList) {
        this.answerList = answerList;
    }

    public List<GameAnswer> getAnswerList() {
        return answerList;
    }
}
