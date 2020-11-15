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
  public Question(int paQuestionId, String paQuestionText, ArrayList<String> paAnswers, int paCorrectAnswer) {

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
        + "questionText='"
        + questionText
        + '\''
        + ", answers="
        + answers
        + '}';
  }
}
