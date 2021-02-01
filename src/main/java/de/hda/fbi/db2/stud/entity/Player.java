package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
@Table(name = "Player", schema = "db2")
@NamedQueries(
    {
        @NamedQuery(name = "Player.findByName",
          query = "select p from Player p where p.name = :name")
    }
)
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int playerID;
  @Column(unique = true)
  private String name;



  /**
   * Constructor.
   *
   * @param paID The playerId.
   * @param name The players name.
   */
  public Player(int paID, String name) {
    this.playerID = paID;
    this.name = name;
  }


  /**
   * Constructor.
   */
  public Player() {

  }



  /**
   * Setter for the name.
   * @param name The name of this person.
   */
  public void setName(String name) {
    this.name = name;
  }



  /**
   * Getter for the playerid.
   * @return An int containing the playerID.
   */
  public int getPlayerID() {
    return playerID;
  }



  /**
   * Generate a hash code for this object.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return this.playerID;
  }



  /**
   * Check the equality of the object.
   *
   * @param obj The object to compare with
   * @return true, if the object equals the given one.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Player) {
      Player tmp = (Player) obj;
      return this.playerID == tmp.getPlayerID();
    }

    return false;
  }
}
