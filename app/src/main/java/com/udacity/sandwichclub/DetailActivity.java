package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        populateTextViewWithList(alsoKnownTv, alsoKnownAsList);

        TextView placeOfOriginTv = findViewById(R.id.place_of_origin_tv);
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        populateTextViewWithList(ingredientsTv, ingredientsList);

        TextView description_tv = findViewById(R.id.description_tv);
        description_tv.setText(sandwich.getDescription());

    }

    private void populateTextViewWithList(TextView textView, List<String> listOfStrings) {
        for (int i = 0; i < listOfStrings.size(); i++) {
            String alsoKnowAs = listOfStrings.get(i);
            if (i > 0 ) {
                textView.append(", ");
            }
            textView.append(alsoKnowAs);
        }
    }
}
