package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Question", schema = "db2")
public class Question {

  /**
   * Object attributes.
   */
  @Id
  private int questionId;
  private String questionText;
  @ElementCollection
  @OrderColumn(name="answerOrder")
  private ArrayList<String> answers;
  private int correctAnswer;
  @ManyToOne
  private Category cat;

  public Question(){}
  /**
   * Constructor.
   *
   * @param paQuestionId    The question id.
   * @param paQuestionText  The question text.
   * @param paAnswers       A list of answers for the questions.
   * @param paCorrectAnswer The index of the correct answer.
   */
  public Question(Category paCat,
                  int paQuestionId,
                  String paQuestionText,
                  ArrayList<String> paAnswers,
                  int paCorrectAnswer) {

    this.questionId = paQuestionId;
    this.questionText = paQuestionText;
    this.answers = paAnswers;
    this.correctAnswer = paCorrectAnswer;
    this.cat = paCat;
  }


  /**
   * A better look for the toString java method.
   *
   * @return Returns a String containing information from the object.
   */
  @Override
  public String toString() {
    return "Question ("
        + questionId
        + "): "
        + questionText
        + "; answers:"
        + answers
        + ", correctAnswer="
        + correctAnswer
        + '}';
  }


  /**
   * Getter for the question id.
   *
   * @return Returns the question id.
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   * Getter for the question text.
   *
   * @return Returns the question text
   */
  public String getQuestionText() {
    return questionText;
  }


  /**
   * Getter for the answers.
   *
   * @return Returns a list of possible answers.
   */
  public ArrayList<String> getAnswers() {
    return answers;
  }


  /**
   * Getter for the correctAnswer.
   *
   * @return Returns the correct answer index.
   */
  public int getCorrectAnswer() {
    return correctAnswer;
  }



  /**
   * Getter for the category.
   *
   * @return Returns the category.
   */
  public Category getCategory() {
    return cat;
  }

  @Override
  public int hashCode() {
    return this.questionId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && Question.class.isInstance(obj) == true){
      Question tmp = (Question) obj;
      if (this.questionId == tmp.getQuestionId())
        return true;
    }
    return false;
  }
}