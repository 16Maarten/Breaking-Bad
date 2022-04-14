package com.example.breakingbad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CharacterActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final static String SAVED_INSTANCE_OF_CHARACTER ="character";
    private TextView mCharacterName;
    private TextView mNickname;
    private TextView mStatus;
    private TextView mBirthday;
    private ImageView mImage;
    private TextView mOccupation;
    private TextView mAppearance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Oncreate aangeroepen");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        mCharacterName = (TextView) findViewById(R.id.character_detail_characterName);
        mNickname = (TextView) findViewById(R.id.character_detail_nickname);
        mStatus = (TextView) findViewById(R.id.character_detail_status);
        mImage = (ImageView) findViewById(R.id.character_detail_image);
        mBirthday = (TextView) findViewById(R.id.character_detail_birthday);
        mOccupation = (TextView) findViewById(R.id.character_detail_occupation);
        mAppearance = (TextView) findViewById(R.id.character_detail_appearance);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
                Character characterDetails = (Character)intentThatStartedThisActivity.getSerializableExtra(SAVED_INSTANCE_OF_CHARACTER);
                Log.d(TAG,characterDetails.getNickname());
                mCharacterName.setText(characterDetails.getCharacterName());
                mNickname.append(": "+characterDetails.getNickname());
                mStatus.append(": "+characterDetails.getStatus());
                mBirthday.append(": "+characterDetails.getBirthday());
                ArrayList<String> occupations = characterDetails.getOccupation();
                if(occupations != null) {
                    String occupation = occupations.get(0);
                    for (int i = 1; i < occupations.size(); i++) {
                        occupation = occupation + ", " + occupations.get(i);
                    }
                    mOccupation.append(": " + occupation);
                }
            ArrayList<Integer> appearances = characterDetails.getAppearance();
            if(appearances != null) {
                String appearance = appearances.get(0).toString();
                for (int i = 1; i < appearances.size(); i++) {
                    appearance = appearance + ", " + appearances.get(i);
                }
                mAppearance.append(": " + appearance);
            }
                Picasso.get().load(characterDetails.getImg()).into(mImage);
        }
    }
    }
