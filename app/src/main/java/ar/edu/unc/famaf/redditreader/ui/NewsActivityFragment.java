package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news, container, false);

        List<PostModel> tmp = Backend.getInstance().getTopPosts();



        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        PostAdapter adapter = new PostAdapter(this.getContext(), R.layout.listview_item_row, tmp);

        ListView postsLV = (ListView) v.findViewById(R.id.postsLV);
        postsLV.setAdapter(adapter);

        return v;
    }

    // Conectar Adapter con Fragment

/*
    PostAdapter adapter = new PostAdapter(this.getContext(), R.layout.listview_item_row, Backend.getInstance().getTopPosts() );
        View v = getActivity().findViewById(R.id.postsLV);
        ListView postsLV = (ListView) v;
        postsLV.setAdapter(adapter);
    * */


}
