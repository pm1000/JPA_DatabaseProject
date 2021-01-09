package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
@Table(name = "Player", schema = "db2")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int playerID;
    @Column(unique = true, nullable = false)
    private String name;

    public Player(int pID, String name){
        this.playerID = pID;
        this.name = name;
    }

    public Player(){

    }
}
