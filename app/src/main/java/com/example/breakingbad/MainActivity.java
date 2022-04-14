package com.example.breakingbad;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAPI.CharacterListener, CharacterAdapter.CharacterAdapterOnClickHandler {

    private ArrayList<Character> characters = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();
    private RecyclerView characterRecyclerView;
    private CharacterAdapter characterAdapter;
    private final static String SAVED_INSTANCE_OF_ADAPTER ="Adapter";
    private final static String SAVED_INSTANCE_OF_CHARACTER ="character";
    private static final String ALIVE = "Alive";
    private static final String PRESUMED_DEAD = "Presumed dead";
    private static final String DECEASED = "Deceased";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"oncreate aangeroepen");
        characterRecyclerView = (RecyclerView) findViewById(R.id.character_recycler_view);
        //layoutmanagers toegewezen
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            characterRecyclerView.setLayoutManager(horizontalLayoutManager);
        } else {
            characterRecyclerView.setLayoutManager(verticalLayoutManager);
        }
        //adapter geinstantieerd
        if(savedInstanceState == null) {
            characterAdapter = new CharacterAdapter(characters, (CharacterAdapter.CharacterAdapterOnClickHandler) this);
            //Adapter toegewezen aan recyclerview
            characterRecyclerView.setAdapter(characterAdapter);
            String[] params = {"https://www.breakingbadapi.com/api/characters" };
            //Async task
            new CharacterAPI(this).execute(params);
        } else {
            //Adapter toegewezen aan recyclerview
            characterAdapter = savedInstanceState.getParcelable(SAVED_INSTANCE_OF_ADAPTER);
            characterRecyclerView.setAdapter(characterAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreate option menu aangeroepen");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //characterAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                characterAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onAvailableCharacters(List<Character> characters) {
        Log.d(TAG, "Number of characters in onAvailableCharacters: "+ characters.size());
        this.characters.clear();
        this.characters.addAll(characters);
        ArrayList<Character> charactersFull = new ArrayList<>(characters);
        this.characterAdapter.setCharacterlistAll(charactersFull);
        this.characterAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Number of characters: "+ characters.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Character characterDetails) {
        Context context = this;
        Class destinationClass = CharacterActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(SAVED_INSTANCE_OF_CHARACTER,  characterDetails);
        Toast.makeText(this, "character: "+ characterDetails.getCharacterName(), Toast.LENGTH_SHORT).show();
        startActivity(intentToStartDetailActivity);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putParcelable(SAVED_INSTANCE_OF_ADAPTER, characterAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume");
    }

    public void PresumedDeadFilter(MenuItem item) {
        Log.d(TAG, "Filter op presumed dead");
        characterAdapter.getFilter().filter(PRESUMED_DEAD);
    }

    public void AliveFilter(MenuItem item) {
        characterAdapter.getFilter().filter(ALIVE);
        Log.d(TAG, "Filter op alive");
    }

    public void DeacesedFilter(MenuItem item) {
        characterAdapter.getFilter().filter(DECEASED);
        Log.d(TAG, "Filter op deceased");
    }

    public void NoFilter(MenuItem item) {
        Log.d(TAG, "Geen filter waarde");
        characterAdapter.getFilter().filter("");
    }
}

