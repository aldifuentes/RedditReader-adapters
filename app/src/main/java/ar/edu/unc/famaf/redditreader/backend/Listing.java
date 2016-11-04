package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 29/10/2016.
 */
public class Listing {
    private List<PostModel> postModelList;
    private int before;
    private int after;

    public Listing() {
        this.postModelList = new ArrayList<PostModel>();
        this.before = 0;
        this.after = 0;
    }

    public Listing(List<PostModel> postModelList, int before, int after) {
        this.postModelList = postModelList;
        this.before = before;
        this.after = after;
    }

    public List<PostModel> getPostModelList() {
        return postModelList;
    }

    public void setPostModelList(List<PostModel> postModelList) {
        this.postModelList = postModelList;
    }

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public void add (PostModel postModel){
        if (postModel!= null) {
            this.postModelList.add(postModel);
        }
    }
}