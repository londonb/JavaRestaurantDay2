import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Best Restaurant");
  }

  @Test
  public void cuisineIsCreated() {
    goTo ("http://localhost:4567/");
    fill("#cuisineType").with("Mexican");
    submit("a", withText("Add Cuisine"));
    assertThat(pageSource()).contains("Mexican");
  }

    @Test
    public void restaurantIsCreated() {
      Cuisine myCuisine = new Cuisine("Mexican");
      myCuisine.save();
      Restaurant firstRestaurant = new Restaurant("Taco Bell", myCuisine.getId());
      firstRestaurant.save();
      Restaurant secondRestaurant = new Restaurant("Mazatlan", myCuisine.getId());
      secondRestaurant.save();
      String categoryPath = String.format("http://localhost:4567/");
      goTo(categoryPath);
      assertThat(pageSource()).contains("Taco Bell");
      assertThat(pageSource()).contains("Mazatlan");
  }

    @Test
    public void update_updatesRestaurantInDatabased() {
      Restaurant newRestaurant = new Restaurant("Lrdo", 1);
      newRestaurant.save();
      newRestaurant.update("Lardo");
      String categoryPath = String.format("http://localhost:4567/", newRestaurant.getId());
      goTo(categoryPath);
      assertThat(pageSource()).contains("Lardo");
    }

    @Test
    public void delete_deletesRestaurantsFromDatabase() {
      Restaurant myRestaurant = new Restaurant ("American", 1);
      myRestaurant.save();
      Restaurant myRestaurant2 = new Restaurant ("Mexican", 1);
      myRestaurant2.save();
      myRestaurant.delete();
      String categoryPath = String.format("http://localhost:4567/");
      goTo(categoryPath);
      assertThat(!(pageSource()).contains("American"));
      assertThat(pageSource()).contains("Mexican");
    }

    @Test
    public void find_restaurantHasAPageForItself() {
      Restaurant newRestaurant = new Restaurant("Lardo", 1);
      newRestaurant.save();
      Restaurant savedRestaurant = Restaurant.find(newRestaurant.getId());
      String restaurantPath = String.format("http://localhost:4567/restaurants/%d", newRestaurant.getId());
      goTo (restaurantPath);
      assertThat(pageSource()).contains("Lardo");
    }
}
