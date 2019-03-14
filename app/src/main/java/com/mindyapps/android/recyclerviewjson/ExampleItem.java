package com.mindyapps.android.recyclerviewjson;

public class ExampleItem {

    private String mImageUrl;
    private String mText;
    private int mLikes;

    public ExampleItem (String imageUrl, String text, int likes){
        mImageUrl = imageUrl;
        mText = text;
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        return mText;
    }

    public int getLikeCount() {
        return mLikes;
    }


}
