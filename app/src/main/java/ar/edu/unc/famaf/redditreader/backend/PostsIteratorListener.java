package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 12/11/2016.
 */
public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);
}
