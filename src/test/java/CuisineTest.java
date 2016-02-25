import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.*;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void equals_checksIfCuisineTheSame() {
    Cuisine firstCuisine = new Cuisine ("American");
    Cuisine secondCuisine = new Cuisine ("American");
    assertTrue(firstCuisine.equals(secondCuisine));

  }

  @Test
  public void save_savesCuisineToDatabase() {
    Cuisine newCuisine = new Cuisine ("American");
    newCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(newCuisine));
  }

  @Test
  public void find_findsCuisineToDatabase() {
    Cuisine myCuisine = new Cuisine ("American");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.find(myCuisine.getId());
    assertEquals(savedCuisine.getType(), "American");
  }
  @Test
  public void update_updateCuisineInDatabase() {
    Cuisine myCuisine = new Cuisine ("America");
    myCuisine.save();
    myCuisine.update("American");
    assertEquals(myCuisine.getType(), "American");
  }

  @Test
  public void delete_deletesCuisineFromDatabase() {
    Cuisine myCuisine = new Cuisine ("American");
    myCuisine.save();
    myCuisine.delete();
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void viewRestaurant_returnsAllRestaurantsForCuisineType() {
    Cuisine myCuisine = new Cuisine ("American");
    myCuisine.save();
    Restaurant newRestaurant = new Restaurant ("American", myCuisine.getId());
    Restaurant newRestaurant2 = new Restaurant ("Mexican", myCuisine.getId());
    newRestaurant.save();
    newRestaurant2.save();
    Restaurant[] restaurants = new Restaurant[] { newRestaurant , newRestaurant2};
    assertTrue(myCuisine.viewRestaurant().containsAll(Arrays.asList(restaurants)));
  }
}
