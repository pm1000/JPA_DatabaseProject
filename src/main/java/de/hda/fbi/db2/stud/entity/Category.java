package de.hda.fbi.db2.stud.entity;

import java.util.List;

public class Category {
    private String name;
    private List<Question> questionList;

    public Category(String name, Question question){
        this.name = name;
        this.questionList.add(question);
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
