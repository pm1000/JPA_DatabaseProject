package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
@Table (name = "GameAnswer", schema = "db2")
public class GameAnswer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int gameAnswerID;
    @Column(nullable = false)
    private int playerAnswer;
    @Column(nullable = false)
    private Game game;
    @Column(nullable = false)
    private Question question;

    public GameAnswer(int gameAnswerID, int playerAnswer, Game game, Question question){
        this.gameAnswerID = gameAnswerID;
        this.playerAnswer = playerAnswer;
        this.game = game;
        this.question = question;
    }

    public GameAnswer(){

    }
}
