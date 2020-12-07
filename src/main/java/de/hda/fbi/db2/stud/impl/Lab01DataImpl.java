package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lab01DataImpl extends Lab01Data {

    /**
     * Hold a list of categories containing its questions.
     */
    private HashMap<String, Category> categoryList;


    /**
     * Constructor.
     */
    public Lab01DataImpl() {

        categoryList = new HashMap<>();
    }


    /**
     * Get all questions in all categories.
     *
     * @return Returns a list of all questions.
     */
    @Override
    public List<?> getQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();

        categoryList.forEach((s, category) -> questionList.addAll(category.getQuestions()));

        return questionList;
    }


    /**
     * Get all categories.
     *
     * @return Returns a list of categories.
     */
    @Override
    public List<?> getCategories() {

        ArrayList<Category> tmp = new ArrayList<>();
        for (Map.Entry<String, Category> cat : this.categoryList.entrySet()) {
            tmp.add(cat.getValue());
        }
        return tmp;
    }


    /**
     * Generate category and question objects and make them fit.
     * CSV file format: ID;_frage;_antwort_1;_antwort_2;_antwort_3;_antwort_4;_loesung;_kategorie.
     *
     * @param additionalCsvLines Csv data as list.
     */
    @Override
    public void loadCsvFile(List<String[]> additionalCsvLines) {

        // Init the new list
        this.categoryList = new HashMap<>();

        // For each line from the csv file
        for (int i = 1; i < additionalCsvLines.size(); i++) {
            String[] tmp = additionalCsvLines.get(i);

            try {
                // Get or create a new category for this question.
                Category cat = categoryList.get(tmp[7]);
                if (cat == null) {
                    cat = new Category(tmp[7]);
                    this.categoryList.put(tmp[7], cat);
                }

                // Create a new question an add the question to the category
                int questionId = Integer.parseInt(tmp[0]);
                int correctAnswer = Integer.parseInt(tmp[6]) - 1;
                ArrayList<String> answers = new ArrayList<>();
                answers.add(tmp[2]);
                answers.add(tmp[3]);
                answers.add(tmp[4]);
                answers.add(tmp[5]);
                Question qes = new Question(cat, questionId, tmp[1], answers, correctAnswer);

                cat.addQuestion(qes);

            } catch (Exception e) {
                System.err.println("Error with csv-entry: " + Arrays.toString(tmp));
                e.printStackTrace();
            }
        }


        System.out.println("Finished adding categories and questions.");

        System.out.println("Alle Fragen:");

        for (Question q : (ArrayList<Question>) getQuestions()) {
            System.out.println(q.toString());
        }

        System.out.println("There are "
          + categoryList.size()
          + " categories and "
          + getQuestions().size()
          + " questions.");
    }
}
