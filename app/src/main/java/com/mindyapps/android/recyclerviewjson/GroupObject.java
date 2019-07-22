package com.mindyapps.android.recyclerviewjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupObject {

    @SerializedName("response")
    private List<GroupResponse> response;

    public GroupObject(List<GroupResponse> response) {
        this.response = response;
    }

    public String getName(){
        return response.get(0).getName();
    }

    public String getPic(){
        return response.get(0).getPic();
    }
}

class GroupResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("photo_200")
    private String photoUrl;

    public GroupResponse(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName(){
        return name;
    }

    public String getPic(){
        return photoUrl;
    }

}
