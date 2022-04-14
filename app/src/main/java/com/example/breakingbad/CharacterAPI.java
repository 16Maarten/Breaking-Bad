package com.example.breakingbad;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterAPI extends AsyncTask<String ,Void , List<Character>> {


    private final String TAG = getClass().getSimpleName();
    private final String JSON_CHARID = "char_id";
    private final String JSON_CHARACTERNAME = "name";
    private final String JSON_BIRTHDAY = "birthday";
    private final String JSON_OCCUPATION = "occupation";
    private final String JSON_IMG = "img";
    private final String JSON_STATUS = "status";
    private final String JSON_NICKNAME = "nickname";
    private final String JSON_APPEARANCE = "appearance";
    private CharacterListener listener = null;

    public CharacterAPI(CharacterListener listener) {
        this.listener = listener;
    }
    protected List<Character> doInBackground(String... params) {

        //url maken
        String characterUrlString = params[0];

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(characterUrlString);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String response = scanner.next();
                Log.d(TAG, "response: " + response);

                ArrayList<Character> resultList = convertJSONToArrayList(response);
                return resultList;
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        // Request bouwen en versturen
        // Response afwachten en verwerken
        // ArrayList maken.

        return null;
    }
    //hulp functie
    private ArrayList<Character> convertJSONToArrayList(String response) {
        ArrayList<Character> resultList = new ArrayList<>();
        try {
            JSONArray characters = new JSONArray(response);
            //json naar java variabelen
            for (int i = 0; i < characters.length(); i++) {
                JSONObject character = characters.getJSONObject(i);
                int charId = character.getInt(JSON_CHARID);
                String characterName = character.getString(JSON_CHARACTERNAME);
                String birthday = character.getString(JSON_BIRTHDAY);
                JSONArray occupation = character.getJSONArray(JSON_OCCUPATION);
                Log.d(TAG,occupation.toString());
                ArrayList<String> occupationList = new ArrayList<String>();;
                for (int j = 0; j < occupation.length(); j++) {
                   occupationList.add(occupation.getString(j));
                }
                String img = character.getString(JSON_IMG);
                String status = character.getString(JSON_STATUS);
                String nickname = character.getString(JSON_NICKNAME);
                JSONArray appearance = character.getJSONArray(JSON_APPEARANCE);
                ArrayList<Integer> appearanceList = new ArrayList<Integer>();
                for (int k=0; k< appearance.length(); k++) {
                    appearanceList.add(appearance.getInt(k));
                }
                resultList.add(new Character(charId, characterName, birthday, occupationList , img, status, nickname,  appearanceList));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Returning " + resultList.size() + " characters");
        return resultList;
    }

    @Override
    protected void onPostExecute(List<Character> characters) {
        Log.d(TAG, "Characters in onPostExecute: " + characters.size() + " characters");
        super.onPostExecute(characters);

        listener.onAvailableCharacters(characters);
    }

    public interface CharacterListener {
        public void onAvailableCharacters(List<Character> characters);
    }

}
