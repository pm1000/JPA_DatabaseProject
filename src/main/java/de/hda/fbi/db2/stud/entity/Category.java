package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Question> questionList = new ArrayList<>();

    public Category(String name){
        this.name = name;
        this.questionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question){
        this.questionList.add(question);
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}
