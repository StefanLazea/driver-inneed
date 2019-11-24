package eu.ase.damapp.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.R;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Item> mechanics = getItemListFromJson(jsonObject.getJSONArray("mecanica"));
            List<Item> signs = getItemListFromJson(jsonObject.getJSONArray("semne rutiere"));
            List<Item> tickets = getItemListFromJson(jsonObject.getJSONArray("contraventii"));

            return new HttpResponse(mechanics, signs, tickets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Item> getItemListFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        List<Item> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Item item = getItemFromJson(array.getJSONObject(i));
            if (item != null) {
                results.add(item);
            }
        }
        return results;
    }

    private static Item getItemFromJson(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }

        String question = object.getString("question");
        String img = object.getString("img");
        Answer answer = getAnswerFromJson(object.getJSONObject("answer"));
        return new Item(question, img, answer);
    }

    private static Answer getAnswerFromJson(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }
        String firstAnswer = object.getString("answer 1");
        String secondAnswer = object.getString("answer 2");
        String thirdAnswer = object.getString("answer 3");
        String fourthAnswer = object.getString("answer 4");
        String correct = object.getString("correct");

        return new Answer(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, correct);
    }
}
