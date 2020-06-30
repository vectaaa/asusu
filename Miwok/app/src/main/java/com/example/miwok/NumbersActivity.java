package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    //We declare this globally so that it becomes a once time function for all recycle view items.
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //AUDIOFOCUS_GAIN means the mediaplayer has regained focus and can actually resume.
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //AUDIOFOCUS_LOSS means we have lost audio focus and stop playback and cleanup resources
                        releaseMediaPlayer();
                    }
                }
            };





    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
// Create
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Create an ArrayList of english translated words
        final ArrayList<Word> english = new ArrayList<Word>();
        english.add(new Word("one", "otu", R.drawable.number_one, R.raw.android_one));
        english.add(new Word("two", "abuo", R.drawable.number_two, R.raw.android_two));
        english.add(new Word("three", "ato", R.drawable.number_three, R.raw.android_three));
        english.add(new Word("four", "ano", R.drawable.number_four, R.raw.android_four));
        english.add(new Word("five", "ise", R.drawable.number_five, R.raw.android_five));
        english.add(new Word("six", "isii", R.drawable.number_six, R.raw.android_six));
        english.add(new Word("seven", "asaa", R.drawable.number_seven, R.raw.android_seven));
        english.add(new Word("eight", "asaato", R.drawable.number_eight, R.raw.android_eight));
        english.add(new Word("nine", "itoolu", R.drawable.number_nine, R.raw.android_nine));
        english.add(new Word("ten", "iri", R.drawable.number_ten, R.raw.android_ten));




        WordAdapter adapter = new WordAdapter(NumbersActivity.this, english);

        ListView listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = english.get(position);
                //Released the media player file if it currently exist because we are about to play
                //a different media/sound file

                //Reuest audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Reuest parmanent focus
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //We have audio focus now


                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    //Set up a listener on the media file so that we can stop and release
                    // the media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        //if the mediaplayer isn't null it may currently be playing a sound.

        if (mMediaPlayer !=null) {
            //Regardless of the current state of media player, release its resources.
            //all because we no longer need it.
            mMediaPlayer.release();
            //set the media player back to null. For our code, we've decided that
            //setting the media player to null is an easy way to tell that the media player is not configured
            //to play an audio file at the moment.
            mMediaPlayer = null;
            //Regardless oF whether or not Audio Focus is granted, abandon it. This also unregisters
            // the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }




    }


}