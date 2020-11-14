package de.hda.fbi.db2.stud.entity;

import java.util.List;

public class Question {
    private int id;
    private String question;
    private List<String> answerOptions;
    private int correctAnswer;

    public Question(int id, String question, List<String> answerOptions, int correctAnswer){
        this.id = id;
        this.question = question;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
