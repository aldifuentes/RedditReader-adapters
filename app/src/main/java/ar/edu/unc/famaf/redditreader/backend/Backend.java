package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private List<PostModel> mListPostModel;

    private Backend() {

        mListPostModel = new ArrayList<>();

        PostModel p1 = new PostModel();
        p1.setTitle("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setImage(R.drawable.ic_launcher);

        PostModel p2 = new PostModel();
        p2.setTitle("It's official. Old and new Note 7 phones are to be powered down and stop being used.");
        p2.setAuthor("TheMcClaneShow");
        p2.setCreated("Hace 5h");
        p2.setSubreddit("/r/Android");
        p2.setComments("1148 comentarios");
        p2.setImage(R.drawable.ic_launcher);

        PostModel p3 = new PostModel();
        p3.setTitle("Chrome 55 will reduce RAM consumption as much as 50% compared to v53");
        p3.setAuthor("armando_rod");
        p3.setCreated("Hace 8h");
        p3.setSubreddit("/r/Android");
        p3.setComments("130 comentarios");
        p3.setImage(R.drawable.ic_launcher);

        PostModel p4 = new PostModel();
        p4.setTitle("VisualVM migrated to GitHub");
        p4.setAuthor("thesystemx");
        p4.setCreated("Hace 15h");
        p4.setSubreddit("/r/java");
        p4.setComments("13 comentarios");
        p4.setImage(R.drawable.ic_launcher);

        PostModel p5 = new PostModel();
        p5.setTitle("Moving old data to separate tablespace?");
        p5.setAuthor("RodDub75");
        p5.setCreated("Hace 16h");
        p5.setSubreddit("/r/oracle");
        p5.setComments("3 comentarios");
        p5.setImage(R.drawable.ic_launcher);

        mListPostModel.add(p1);
        mListPostModel.add(p2);
        mListPostModel.add(p3);
        mListPostModel.add(p4);
        mListPostModel.add(p5);

    }

    public List<PostModel> getTopPosts() {
        // TODO: implement me
        return mListPostModel;
    }
}
