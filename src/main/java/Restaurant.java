import org.sql2o.*;
import java.util.List;
import java.util.*;

public class Restaurant {
  private int id;
  private String name;
  private  int cuisine_id;

  public Restaurant (String name, int cuisine_id) {
    this.name = name;
    this.cuisine_id=cuisine_id;
  }
  //
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int cuisine_id() {
    return cuisine_id;
  }
  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }
//
  //CREATE
  public void save() {
    String sql = "INSERT INTO restaurants (name, cuisine_id) VALUES (:name, :cuisine_id)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true).addParameter("name", name).addParameter("cuisine_id", cuisine_id).executeUpdate().getKey();
      /******************************************************
        Students: TODO: Display all restaurants on main page
      *******************************************************/
    }
  }

  //READ
  public static List<Restaurant> all() {
    String sql = "SELECT * FROM restaurants ORDER BY name ASC";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

//
  //UPDATE
  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name =:name WHERE id =:id";
      con.createQuery(sql).addParameter("name", newName).addParameter("id", id).executeUpdate();
      }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();

    }
  }
  public static Restaurant find(int id) {
    String sql = "SELECT * FROM restaurants WHERE id=:id";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Restaurant.class);
      }
  }
//   /******************************************************
//     Students:
//     TODO: Create find method- done
//     TODO: Create method to get cuisine type
//   *******************************************************/
//
}
