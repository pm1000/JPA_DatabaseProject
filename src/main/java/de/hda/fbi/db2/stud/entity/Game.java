package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Game", schema = "db2")
public class Game {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int gameID;
    @Column(nullable = false)
    private Date begin;
    private Date end;
    @Column(nullable = false)
    private Player player;

    private Game(int gameID, Date begin, Player player){
        this.gameID = gameID;
        this.begin = begin;
        this.end = null;
        this.player = player;
    }

    private Game(){

    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
