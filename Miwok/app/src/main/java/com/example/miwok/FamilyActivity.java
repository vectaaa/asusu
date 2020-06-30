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

public class FamilyActivity extends AppCompatActivity {
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
        english.add(new Word("father", "nna", R.drawable.family_father, R.raw.android_father));
        english.add(new Word("mother", "nne", R.drawable.family_mother, R.raw.android_mother));
        english.add(new Word("brother", "nwannem nwoke", R.drawable.family_son, R.raw.android_brother));
        english.add(new Word("sister", "nwannem nwanyi", R.drawable.family_daughter,R.raw.android_sister));
        english.add(new Word("nephew", "nwa nwoke nwanne", R.drawable.family_younger_brother, R.raw.android_nephew));
        english.add(new Word("niece", "nwa nwanyi nwanne", R.drawable.family_younger_sister, R.raw.android_niece));
        english.add(new Word("cousin", "nwanne", R.drawable.family_son, R.raw.android_cousin));
        english.add(new Word("uncle", "nwanne nna", R.drawable.family_older_brother,R.raw.android_uncle));
        english.add(new Word("aunty", "nwanne nne", R.drawable.family_older_sister,R.raw.android_aunty));
        english.add(new Word("grand-ma", "nne ukwu", R.drawable.family_grandmother,R.raw.android_grandma));
        english.add(new Word("grand-pa", "nna ukwu", R.drawable.family_grandfather, R.raw.android_grandpa));



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
                mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionLister);
            }}
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
