package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {

  /**
   * Object attributes.
   */
  private String categoryName;
  private ArrayList<Question> questionList;


  /**
   * Constructor.
   */
  public Category(String paName) {

    this.categoryName = paName;
    this.questionList = new ArrayList<>();
  }


  /**
   * Add q question to the list.
   *
   * @param paQuestion The question that should be added.
   */
  public void addQuestion(Question paQuestion) {

    questionList.add(paQuestion);
  }


  /**
   * Return a list of all questions for this category.
   *
   * @return Returns a list of all questions.
   */
  public List<Question> getQuestions() {

    return questionList;
  }


  /**
   * A better way to get information about this category.
   *
   * @return A string containing information about the object.
   */
  @Override
  public String toString() {
    return "Category{"
        + "categoryName='"
        + categoryName
        + '\''
        + '}';
  }
}
