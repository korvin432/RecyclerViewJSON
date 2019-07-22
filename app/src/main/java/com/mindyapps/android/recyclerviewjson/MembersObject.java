package com.mindyapps.android.recyclerviewjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MembersObject {

    @SerializedName("response")
    private MembersResponse response;

    public MembersObject(MembersResponse response) {
        this.response = response;
    }

    public List<Integer> getIds(){
        return response.getIds();
    }

    public int getCount(){
        return response.getCount();
    }

}


class MembersResponse {

    @SerializedName("count")
    private int count;
    @SerializedName("items")
    private List<Integer> mItems;

    public MembersResponse(List<Integer> mItems, int count) {
        this.mItems = mItems;
        this.count = count;
    }

    public List<Integer> getIds(){
        return mItems;
    }

    public int getCount(){
        return count;
    }

}
