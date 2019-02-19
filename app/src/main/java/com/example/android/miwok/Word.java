package com.example.android.miwok;


/**
 * {@link Word} represents vocabulary word that the user who wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    /**
     * Constant field
     */
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Default translation for the word fields
     */
    private String mDefaultTranslation;

    /**
     * Miwok translation for the word
     */
    private String mMiwokTranslation;

    /**
     * Image resource ID for each word
     */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /**
     * Audio ID for each word/phrase
     */
    private int mAudioResourceId = 0;

    /**
     * Constructor has 3 params (NO IMAGE)
     *
     * @param defaultTranslation for default translation (e.g English)
     * @param miwokTranslation   for Miwok translation
     * @param audioResourceId    for pronouncing each corresponding word
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Constructor has 4 params (with image)
     *
     * @param defaultTranslation for default translation (e.g English)
     * @param miwokTranslation   for Miwok translation
     * @param imageResourceId    for image
     * @param audioResourceId    for pronouncing each corresponding word
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Method to check whether there's an image associated with translated word
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /* get default translation of the word */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /* get Miwok translation of the word */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /* get image resource ID for the word */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
