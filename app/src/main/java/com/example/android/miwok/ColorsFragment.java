package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    /**
     * Handles media playback of all audio files
     */
    private MediaPlayer mMediaPlayer;

    /**
     * Handles audio when playing a sound file
     */
    private AudioManager mAudioManager;

    /**
     * Audio Manager focus, handles audio focus listener as it changes states
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT state means we've lost focus for a short
                        // amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_MAY_DUCK state means that
                        // our app is allowed to continue to play audio sounds but at a lower volume.
                        // We'll treat both states in the same way since our app will be playing
                        // short sound files.

                        // Pause playback and seek to the very beginning of the audio file once we
                        // resume the app to restart the word.
                        // i.e. for a phone call
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        mMediaPlayer.start();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIO_FOCUS state means that we have lost focus and
                        // stop playback and cleanup resources
                        releaseMediaPlayer();
                    }
                }
            };

    /**
     * This listener gets triggered when Media Player has completed playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompleteListener
            = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // after completion of playing the audio file
            // release the media player resources.
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // set up and create Audio Manager to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create custom ArrayList to store translations of the word
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("red", "weṭeṭṭi",
                R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki",
                R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki",
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi",
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli",
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli",
                R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // ArrayAdapter inflating custom list
        WordAdapter adapter =
                new WordAdapter(getActivity(), words, R.color.category_colors);

        // List view
        ListView listView = rootView.findViewById(R.id.list);

        // Put adapter onto list view object
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // release the media player if it exists because we are about to
                // play a different sound file.
                releaseMediaPlayer();

                // create word object and get its position
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // we have audio focus now.

                    // create media player object and associate it to the
                    // relevant word object
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // start audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompleteListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // when the activity is stopped, release the media player resources
        // because we won't be playing any sounds in this  current state
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
