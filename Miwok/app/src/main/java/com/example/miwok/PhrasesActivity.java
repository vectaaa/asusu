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

public class PhrasesActivity extends AppCompatActivity {
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
        english.add(new Word("Welcome", "Nnọọ | Dalụ", R.raw.android_welcome));
        english.add(new Word("Hello", "Kedụ", R.raw.android_hello));
        english.add(new Word("How are you doing?", "Kedụ ka ịmere?", R.raw.android_howareyoudoing));
        english.add(new Word("I am fine.", "A di mụ mma", R.raw.android_iamfine));
        english.add(new Word("Long time no see", "Anya gị", R.raw.android_longtimenosee));
        english.add(new Word("Whats your name", "Kedu aha gị?", R.raw.android_whatsyourname));
        english.add(new Word("My name is..", "Aham bu ..\"say your name\" ", R.raw.android_mynameis));
        english.add(new Word("Where are you from?", "Ebee ka i si?", R.raw.android_whereareyoufrom));
        english.add(new Word("I'm from ...", "Esi m na ..", R.raw.android_iamfine));
        english.add(new Word("Pleased to meet you", "Ọ dị m obi ụtọ izute gị", R.raw.android_pleasedtomeetyou));
        english.add(new Word("Good afternoon", "Ehihie ọma", R.raw.android_goodafternoon));
        english.add(new Word("Good evening", "Mgbede ọma", R.raw.android_goodevening));
        english.add(new Word("Good night", "Ka chi fo", R.raw.android_goodnight));
        english.add(new Word("Goodbye", "Ka omesia", R.raw.android_goodbye));
        english.add(new Word("See you!", "Ka ọ dị", R.raw.android_seeyou));
        english.add(new Word("See you tomorrow", "Ka ọ dị echi ", R.raw.android_seeyoutommorrow));
        english.add(new Word("Goodluck", "Ihe oma diri", R.raw.android_goodluck));
        english.add(new Word("Have a nice day", "Daalụ nwee ọmarịcha ụbọchị!", R.raw.android_haveaniceday));
        english.add(new Word("Have a nice meal", "Rie nke ọma", R.raw.android_haveanicemeal));
        english.add(new Word("Have a good journey", "Ijeoma", R.raw.android_safetrip));
        english.add(new Word("Sorry", "Ndo", R.raw.android_sorry));
        english.add(new Word("Thank you", "Ịmela", R.raw.android_thankyou));
        english.add(new Word("Where's the toilet / bathroom?", "Ke ebe mkpochi di?", R.raw.android_bathroom));
        english.add(new Word("I miss you", "Agụụ gị na-agụ m", R.raw.andriod_imissyou));
        english.add(new Word("I love you", "A hụrụ m gị n’anya", R.raw.android_iloveyou));
        english.add(new Word("Get well soon", "Gbakee ngwa ngwa", R.raw.android_getwellsoon));
        english.add(new Word("Good morning", "Otutu oma", R.raw.android_goodmorning));

        WordAdapter adapter = new WordAdapter(PhrasesActivity.this, english);

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
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourceId());
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
