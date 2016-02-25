import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import org.junit.Rule;
import java.util.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Restaurant.all().size(), 0);
  }
  @Test
  public void equals_returnsTrueIfRestaurantsAretheSame() {
    Restaurant firstRestaurant = new Restaurant ("Lardo", 1);
    Restaurant secondRestaurant = new Restaurant ("Lardo", 1);
    assertTrue(firstRestaurant.equals(secondRestaurant));
  }

  @Test
  public void save_savesRestaurantToDatabase() {
    Restaurant newRestaurant = new Restaurant("Lardo", 1);
    newRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(newRestaurant));
  }


  @Test
  public void find_findsRestaurantInDatabase_true() {
    Restaurant newRestaurant = new Restaurant("Lardo", 1);
    newRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(newRestaurant.getId());
    assertEquals(savedRestaurant.getName(), "Lardo");
  }

  @Test
  public void update_updatesRestaurantInDatabased() {
    Restaurant newRestaurant = new Restaurant("Lrdo", 1);
    newRestaurant.save();
    newRestaurant.update("Lardo");
    assertEquals(newRestaurant.getName(), "Lardo");
  }

  @Test
  public void delete_deletesRestaurantFromDatabase() {
    Restaurant myRestaurant = new Restaurant ("Lardo", 1);
    myRestaurant.save();
    myRestaurant.delete();
    assertEquals(Restaurant.all().size(), 0);
  }
}
