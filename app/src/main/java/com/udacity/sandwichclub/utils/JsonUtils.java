package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

  private static final String NAME = "name";
  private static final String MAIN_NAME = "mainName";
  private static final String ALSO_KNOWN_AS = "alsoKnownAs";
  private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
  private static final String DESCRIPTION = "description";
  private static final String IMAGE = "image";
  private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
      JSONObject sandwichJson = new JSONObject(json);

      JSONObject nameJson = sandwichJson.getJSONObject(NAME);
      String mainName = nameJson.optString(MAIN_NAME);
      List<String> alsoKnownAs = new ArrayList<>();
      JSONArray alsoKnownAsArray = nameJson.getJSONArray(ALSO_KNOWN_AS);
      for (int i = 0; i < alsoKnownAsArray.length(); i++) {
        alsoKnownAs.add(alsoKnownAsArray.optString(i));
      }

      String placeOfOrigin = sandwichJson.optString(PLACE_OF_ORIGIN);
      String description = sandwichJson.optString(DESCRIPTION);
      String image = sandwichJson.optString(IMAGE);

      List<String> ingredients = new ArrayList<>();
      JSONArray ingredientsArray = sandwichJson.getJSONArray(INGREDIENTS);
      for (int j = 0; j < ingredientsArray.length(); j++) {
        ingredients.add(ingredientsArray.optString(j));
      }

      return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,image, ingredients);
    }
}
