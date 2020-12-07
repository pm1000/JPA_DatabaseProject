package de.hda.fbi.db2.stud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category", schema = "db2")
public class Category {

  /**
   * Object attributes.
   */
  @Id
  private String categoryName;
  @OneToMany(mappedBy = "cat")
  private ArrayList<Question> questionList;

  public Category(){}

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


  /**
   * Getter for the categoryName.
   * @return Returns the category name.
   */
  public String getCategoryName() {
    return categoryName;
  }

  @Override
  public int hashCode() {
    return this.categoryName.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    Category tmp = (Category) obj;
    if (this.categoryName.compareTo(tmp.getCategoryName()) == 0)
      return true;
    return false;
  }
}
