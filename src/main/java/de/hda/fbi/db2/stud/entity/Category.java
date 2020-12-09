package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category", schema = "db2",
        uniqueConstraints = @UniqueConstraint(columnNames = {"categoryName"}))
public class Category {

  /**
   * Object attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="xyz.id_cat")
  private int catID;
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

  public int getCatID(){return this.catID;}

  @Override
  public int hashCode() {
    return this.catID;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && Category.class.isInstance(obj) == true){
      Category tmp = (Category) obj;
      if (this.catID == tmp.getCatID())
        return true;
    }

    return false;
  }
}
