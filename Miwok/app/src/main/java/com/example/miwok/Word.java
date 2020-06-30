package com.example.miwok;

/**
 *{@link Word} is the word that will be translated
 */
public class Word {
    /** English translation of the words */
    private String mEngTranslation;

    /** Igbo translation of words */
    private String mIgboTranslation;

    // Drawable resource ID
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourceId;
    /**
     * Create  a new word object
     * @param EngTranslation is the word the user is familiar with already such as English.
     * @param IgboTranslation is the word the user is trying to learn or get used to.
     */
    public Word(String EngTranslation, String IgboTranslation, int ImageResourceId, int AudioResourceId) {
        mEngTranslation = EngTranslation;
        mIgboTranslation = IgboTranslation;
        mImageResourceId = ImageResourceId;
        mAudioResourceId = AudioResourceId;

    }

    public Word(String EngTranslation, String IgboTranslation, int AudioResourceId) {
        mEngTranslation = EngTranslation;
        mIgboTranslation = IgboTranslation;
        mAudioResourceId = AudioResourceId;

    }


    /**Get Eng translation */
    public String getEngTranslation() {
        return mEngTranslation;
    }
    /**Get Igbo translation */
    public String getIgboTranslation() {
        return mIgboTranslation;
    }
    /**Get Image view */
    public int getImageResourceId() { return mImageResourceId; }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId(){return mAudioResourceId;};
}
