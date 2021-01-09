package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
@Table (name = "GameAnswer", schema = "db2")
public class GameAnswer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int gameAnswerID;
    private int playerAnswer;
    @ManyToOne
    private Game game;
    @OneToOne
    private Question question;

    public GameAnswer(Game game, Question question){
        this.game = game;
        this.question = question;
    }

    public GameAnswer(){

    }

    public void setPlayerAnswer(int playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
