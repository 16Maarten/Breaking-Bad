package com.example.breakingbad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> implements Filterable, Parcelable {

    private ArrayList<Character> characterlist;
    private ArrayList<Character> characterlistAll;
    private final String TAG = getClass().getSimpleName();
    private static final String ALIVE = "Alive";
    private static final String PRESUMED_DEAD = "Presumed dead";
    private static final String DECEASED = "Deceased";
    private CharacterAdapterOnClickHandler mClickHandler;

    public CharacterAdapter(ArrayList<Character> characterlist, CharacterAdapterOnClickHandler clickHandler) {
        Log.d(TAG,"Constructor aangeroepen");
        this.characterlist = characterlist;
        mClickHandler = clickHandler;
    }

    public void setCharacterlistAll(ArrayList<Character> characterlistFull){
            this.characterlistAll = characterlistFull;
        Log.d(TAG, "Zoveel characters zitten in de charachterlist: " + characterlistAll.size());
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Character> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(characterlistAll);
            } else if(constraint == ALIVE){
                filteredList = filterLoop(ALIVE);
            } else if(constraint == PRESUMED_DEAD){
                filteredList = filterLoop(PRESUMED_DEAD);
            } else if(constraint == DECEASED){
                filteredList = filterLoop(DECEASED);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Character character: characterlistAll){
                    if(character.getCharacterName().toLowerCase().contains(filterPattern)){
                        filteredList.add(character);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            Log.d(TAG,"Aantal characters in filter: " + filteredList.size());
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d(TAG,"Publish result aangeroepen");
            characterlist.clear();
            characterlist.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    private ArrayList<Character> filterLoop(String filter){
        ArrayList<Character> filteredList = new ArrayList<>();
        for(Character character: characterlistAll){
            if(character.getStatus().equals(filter)){
                filteredList.add(character);
            }
        }
        Log.d(TAG,"Characters in filterList: "+ filteredList.size());
        return filteredList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public interface CharacterAdapterOnClickHandler {
        void onClick(Character characterDetails);
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreate aangeroepen");

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.character_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Log.d(TAG,"onBindViewholder aangeroepen");
        Character character = characterlist.get(position);
        holder.mCharacterNameTextView.setText(character.getCharacterName());
        holder.mStatusTextView.setText(character.getStatus());
        holder.mNicknameTextView.setText(character.getNickname());
        Picasso.get().load(character.getImg()).into(holder.mImageView);
        holder.mImageView.setOnClickListener((l) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(character.getImg()));
            holder.mImageView.getContext().startActivity(browserIntent);
            Log.d(TAG, "onClick was called from image");
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"Grote lijst:"+characterlist.size());
        return characterlist.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mCharacterNameTextView;
        public TextView mNicknameTextView;
        public TextView mStatusTextView;
        public ImageView mImageView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            mCharacterNameTextView = (TextView) itemView.findViewById(R.id.character_characterName);
            mNicknameTextView = (TextView) itemView.findViewById(R.id.character_nickname);
            mStatusTextView = (TextView) itemView.findViewById(R.id.character_status);
            mImageView = (ImageView) itemView.findViewById(R.id.character_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Character character = characterlist.get(adapterPosition);
            mClickHandler.onClick(character);
        }
    }

}
