package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class Lab01Impl extends Lab01Data {
    private List<Category> categoryList;

    public Lab01Impl(){
        this.categoryList = new ArrayList<>();
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        for (int i = 0; i < this.categoryList.size(); ++i){
            allQuestions.addAll(categoryList.get(i).getQuestionList());
        }
        return allQuestions;
    }

    @Override
    public List<Category> getCategories() {
        return this.categoryList;
    }

    @Override
    public void loadCsvFile(List<String[]> additionalCsvLines) {
        for (int x = 0; x < additionalCsvLines.size(); ++x){
            try {
                int id = Integer.parseInt(additionalCsvLines.get(x)[0]);
                String question = additionalCsvLines.get(x)[1];
                List<String> answerOptions = new ArrayList<>();
                answerOptions.add(additionalCsvLines.get(x)[2]);
                answerOptions.add(additionalCsvLines.get(x)[3]);
                answerOptions.add(additionalCsvLines.get(x)[4]);
                answerOptions.add(additionalCsvLines.get(x)[5]);
                int correctAnswer = Integer.parseInt(additionalCsvLines.get(x)[6]);
                String categoryName = additionalCsvLines.get(x)[7];

                boolean categoryFound = false;
                for (int i = 0; i < this.categoryList.size(); ++i){
                    if (this.categoryList.get(i).getName().equals(categoryName)){
                        this.categoryList.get(i).addQuestion(new Question(id, question, answerOptions, correctAnswer));
                        categoryFound = true;
                        break;
                    }
                }

                if (!categoryFound){
                    this.categoryList.add(new Category(categoryName, new Question(id, question, answerOptions, correctAnswer)));
                }


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Num of Categories: " + this.categoryList.size() + "\tNum of Questions: " + getQuestions().size());
    }
}
