

package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab01Impl extends Lab01Data {
    private HashMap <String, Category> categoryList;

    public Lab01Impl(){

    }

    @Override
    public List<Question> getQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        for (Map.Entry<String, Category> entry : this.categoryList.entrySet()){
            allQuestions.addAll(entry.getValue().getQuestionList());
        }
        return allQuestions;
    }

    @Override
    public List<Category> getCategories() {
        return new ArrayList<Category>(this.categoryList.values());
    }

    @Override
    public void loadCsvFile(List<String[]> additionalCsvLines) {
        this.categoryList = new HashMap<>();
        for (int x = 0; x < additionalCsvLines.size(); ++x){
            try {
                int id = Integer.parseInt(additionalCsvLines.get(x)[0]);
                String question = additionalCsvLines.get(x)[1];
                List<String> answerOptions = new ArrayList<>();
                answerOptions.add(additionalCsvLines.get(x)[2]);
                answerOptions.add(additionalCsvLines.get(x)[3]);
                answerOptions.add(additionalCsvLines.get(x)[4]);
                answerOptions.add(additionalCsvLines.get(x)[5]);
                int correctAnswer = Integer.parseInt(additionalCsvLines.get(x)[6]) - 1;
                String categoryName = additionalCsvLines.get(x)[7];

                Category cat = this.categoryList.get(categoryName);

                //add new category if it doesn't exist yet
                if (cat == null) {
                    cat = new Category(categoryName);
                    this.categoryList.put(categoryName, cat);
                }

                cat.addQuestion(new Question(id, question, answerOptions, correctAnswer, cat));



            } catch (NumberFormatException e) {
            }
        }

        System.out.println("Num of Categories: " + this.categoryList.size() + "\tNum of Questions: " + getQuestions().size());
    }
}