package ar.edu.unc.famaf.redditreader.model;


import android.graphics.Bitmap;

public class PostModel {
    private String mTitle;
    private String mAuthor;
    private String mCreated;
    private String mSubreddit;
    private String mComments;
    private int mImage;
    private String mUrl;

    public PostModel(String mTitle, String mAuthor, String mCreated, String mSubreddit, String mComments, int mImage) {
        super();
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mCreated = mCreated;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mImage = mImage;
    }

    public PostModel(String mTitle, String mAuthor, String mCreated, String mSubreddit, String mComments, String mUrl) {
        super();
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mCreated = mCreated;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mUrl = mUrl;
    }

    public PostModel() {
        super();
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        this.mCreated = created;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public void setSubreddit(String subreddit) {
        this.mSubreddit = subreddit;
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String Comments) {
        this.mComments = Comments;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }


    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

}
