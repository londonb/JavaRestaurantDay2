import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;
import java.util.*;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines", (request, response) -> {
      HashMap<String, Object>model= new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model,layout);
    }, new VelocityTemplateEngine());

    post("/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String cuisineType = request.queryParams("cuisineType");
      Cuisine newCuisine = new Cuisine (cuisineType);
      newCuisine.save();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:id" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id =Integer.parseInt(request.params(":id"));
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      List<Restaurant> restaurants = cuisine.viewRestaurant();
      model.put("id", id);
      model.put("cuisine", cuisine);
      model.put("restaurants", restaurants);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants", (request, response ) -> {
      HashMap<String, Object>model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/restaurants.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String restaurantName = request.queryParams("restaurantName");
      int cuisineId = Integer.parseInt(request.queryParams("cuisineId"));
      Restaurant newRestaurant = new Restaurant(restaurantName, cuisineId);
      newRestaurant.save();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/restaurants.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/:id" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id =Integer.parseInt(request.params(":id"));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      model.put("id", id);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants/:id/updated", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      // int id =Integer.parseInt(request.params(":id"));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      // Cuisine cuisine = Cuisine.find(restaurant.getId());
      String newName = request.queryParams("update");
      restaurant.update(newName);
      // model.put("id", id);
      model.put("restaurant", newName);
      model.put("template", "templates/update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants/:id/deleted", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      restaurant.delete();
      // model.put("restaurant", restaurant);
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
