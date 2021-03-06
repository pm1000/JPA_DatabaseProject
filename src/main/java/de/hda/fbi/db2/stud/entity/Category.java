package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "Category", schema = "db2")
@NamedQueries(
    {
        @NamedQuery(name = "Category.findAll", query = "select c from Category c")
    }
)
public class Category {

  /**
   * Object attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int catID;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "cat", fetch = FetchType.EAGER)
  private List<Question> questionList;


  /**
   * Constructor.
   */
  public Category() {

  }


  /**
   * Constructor.
   */
  public Category(String paName) {

    this.name = paName;
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
    return name;
  }


  /**
   * Getter for the categoryName.
   *
   * @return Returns the category name.
   */
  public String getCategoryName() {
    return name;
  }


  /**
   * Get the category id.
   *
   * @return The category id.
   */
  public int getCatID() {
    return this.catID;
  }


  /**
   * Generate a hash code for this object.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return this.catID;
  }


  /**
   * Check the equality of the object.
   *
   * @param obj The object to compare with
   * @return true, if the object equals the given one.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Category) {
      Category tmp = (Category) obj;
      return this.catID == tmp.getCatID();
    }

    return false;
  }
}
