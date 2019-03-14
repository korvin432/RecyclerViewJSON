package com.mindyapps.android.recyclerviewjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemObject {

    @SerializedName("response")
    private Response response;

    public ItemObject(Response response) {
        this.response = response;
    }

    public String getImageUrl(int i){
        return response.getSomeSize(i);
    }

    public String getText(int i){
        return response.getText(i);
    }

    public int getLikes(int i){
        return response.getLikes(i);
    }

}

class Likes {
    @SerializedName("count")
    private int likesCount;

    public Likes(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getLikesCount(){
        return likesCount;
    }
}

class Photo {

    @SerializedName("sizes")
    List<Sizes> sizes;

    public Photo(List<Sizes> sizes) {
        this.sizes = sizes;
    }

    public String getSomeSize(){
        return sizes.get(sizes.size() - 1).getUrl();
    }
}

class Post {

    @SerializedName("text")
    private String mText;
    @SerializedName("likes")
    private Likes likes;
    @SerializedName("attachments")
    private List<Attachment> attachment;

    public Post(String mText, Likes likes, List<Attachment> attachment) {
        this.mText = mText;
        this.likes = likes;
        this.attachment = attachment;
    }

    public String getSomeSize(){
        if (this.attachment != null && attachment.get(0).getPhoto() != null){
            return attachment.get(0).getPhoto().getSomeSize();
        }
        else {
            return null;
        }
    }

    public String getText(){
        return this.mText;
    }

    public int getLikes(){
        return this.likes.getLikesCount();
    }
}

class Response {

    @SerializedName("count")
    private int count;
    @SerializedName("items")
    private List<Post> mItems;

    public Response(List<Post> mItems, int count) {
        this.mItems = mItems;
        this.count = count;
    }

    public String getSomeSize(int i){
        return mItems.get(i).getSomeSize();
    }

    public String getText(int i){
        return mItems.get(i).getText();
    }

    public int getLikes(int i){
        return mItems.get(i).getLikes();
    }
}

class Sizes {

    @SerializedName("url")
    private String url;

    public Sizes(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}

class Attachment {

    @SerializedName("photo")
    private Photo photo;

    public Attachment(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto(){
        return this.photo;
    }
}


