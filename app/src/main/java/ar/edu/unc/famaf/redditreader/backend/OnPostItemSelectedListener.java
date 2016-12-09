package ar.edu.unc.famaf.redditreader.backend;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 17/11/2016.
 */
public interface OnPostItemSelectedListener{
    void onPostItemPicked(PostModel post);
}