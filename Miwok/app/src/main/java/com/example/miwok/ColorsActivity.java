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

public class ColorsActivity extends AppCompatActivity {
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
    private MediaPlayer.OnCompletionListener mCompletionLister = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp){
            releaseMediaPlayer();
        }
    };
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Create an ArrayList of english translated words
        final ArrayList<Word> english = new ArrayList<Word>();
        english.add(new Word("black", "ojii", R.drawable.color_black, R.raw.android_black));
        english.add(new Word("blue", "acha anụnụ anụnụ", R.drawable.color_blue, R.raw.android_blue));
        english.add(new Word("brown", "aja aja", R.drawable.color_brown, R.raw.android_brown));
        english.add(new Word("green", "akwụkwọ ndụ akwụkwọ ndụ", R.drawable.color_green, R.raw.android_green));
        english.add(new Word("grey", "isi awọ", R.drawable.color_gray, R.raw.android_gray));
        english.add(new Word("orange", "oroma", R.drawable.color_orange, R.raw.android_orange));
        english.add(new Word("pink", "uhie ocha", R.drawable.color_pink, R.raw.android_pink));
        english.add(new Word("purple", "odo odo", R.drawable.color_purple, R.raw.android_purple));
        english.add(new Word("red", "acha ọbara ọbara", R.drawable.color_red, R.raw.andriod_red));
        english.add(new Word("violet", "ododo", R.drawable.color_violet, R.raw.android_violet));
        english.add(new Word("white", "ọcha", R.drawable.color_white, R.raw.android_white));
        english.add(new Word("yellow", "edo edo", R.drawable.color_yellow, R.raw.android_yellow));


        WordAdapter adapter = new WordAdapter(this, english);

        ListView listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = english.get(position);

                //Reuest audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Reuest parmanent focus
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have audio focus now


                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionLister);
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

