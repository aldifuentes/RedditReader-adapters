package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 03/11/2016.
 */
public interface TopPostIterator {
    void nextPosts(List<PostModel> lst);
}
