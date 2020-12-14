package de.hda.fbi.db2.stud.entity;

import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
  @CollectionTable(schema = "db2")
  @OrderColumn(name = "answerOrder")
  private List<String> answers;   // Hier noch Annotation für PK, wir wissen aber nicht wie :/
  private int correctAnswer;

  @ManyToOne
  private Category cat;


  /**
   * Constructor.
   */
  public Question(){

  }



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
  public List<String> getAnswers() {
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



  /**
   * Generate a hash code for this object.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return this.questionId;
  }



  /**
   * Check the equality of the object.
   *
   * @param obj The object to compare with
   * @return true, if the object equals the given one.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Question) {
      Question tmp = (Question) obj;
      return this.questionId == tmp.getQuestionId();
    }
    return false;
  }
}