package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

public class Question {

  /**
   * Object attributes.
   */
  private int questionId;
  private String questionText;
  private ArrayList<String> answers;
  private int correctAnswer;


  /**
   * Constructor.
   *
   * @param paQuestionId    The question id.
   * @param paQuestionText  The question text.
   * @param paAnswers       A list of answers for the questions.
   * @param paCorrectAnswer The index of the correct answer.
   */
  public Question(int paQuestionId,
                  String paQuestionText,
                  ArrayList<String> paAnswers,
                  int paCorrectAnswer) {

    this.questionId = paQuestionId;
    this.questionText = paQuestionText;
    this.answers = paAnswers;
    this.correctAnswer = paCorrectAnswer;
  }


  /**
   * A better look for the toString java method.
   *
   * @return Returns a String containing information from the object.
   */
  @Override
  public String toString() {
    return "Question{"
        + "questionId="
        + questionId
        + ", questionText='"
        + questionText
        + '\''
        + ", answers="
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
}