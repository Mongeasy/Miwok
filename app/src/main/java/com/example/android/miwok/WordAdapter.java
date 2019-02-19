package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link WordAdapter}
 * responsible for dynamically generating corresponding resources for each item on layout
 */
public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * Color resource ID for each category
     */
    private int mColorResourceId;

    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // instantiate Word class object
        Word currentWord = getItem(position);

        assert currentWord != null;

        // find text view in xml layout
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        // set text using Word class object
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // find text view in xml layout
        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        // set text using Word class object
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // find text view in xml layout
        ImageView imageResourceId = listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            // set text using Word class object
            imageResourceId.setImageResource(currentWord.getImageResourceId());
            // make sure the image is visible
            imageResourceId.setVisibility(View.VISIBLE);
        } else {
            // make the image disappear without taking up space on the layout
            // using View.INVISIBLE uses up view space and memory
            imageResourceId.setVisibility(View.GONE);
        }

        // find view in list_item.xml layout
        View textContainer = listItemView.findViewById(R.id.text_container);
        // change color of background dynamically
        textContainer.setBackgroundColor(mColorResourceId);

        // find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        // inflate list
        return listItemView;
    }
}
