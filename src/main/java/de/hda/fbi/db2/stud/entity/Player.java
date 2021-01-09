package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
@Table(name = "Player", schema = "db2")
@NamedQueries(
  {
    @NamedQuery(name = "Player.findByName", query = "select p from Player p where p.name = :name")
  }
)
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int playerID;
  @Column(unique = true)
  private String name;

  public Player(int paID, String name) {
    this.playerID = paID;
    this.name = name;
  }

  public Player() {

  }

  public void setName(String name) {
    this.name = name;
  }
}
