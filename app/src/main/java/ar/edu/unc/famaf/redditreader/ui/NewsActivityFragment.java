package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.AlertDialog;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.backend.TopPostIterator;
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

        final View v = inflater.inflate(R.layout.fragment_news, container, false);

        RedditDBHelper db = RedditDBHelper.getInstance(this.getContext());

        Backend.getInstance().getTopPosts(new TopPostIterator() {
            @Override
            public void nextPosts(List<PostModel> lst) {
                PostAdapter adapter = new PostAdapter(getActivity(), R.layout.listview_item_row, lst);
                ListView postsLV = (ListView) getView().findViewById(R.id.postsLV);
                postsLV.setAdapter(adapter);
            }
        });

//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            PostAdapter adapter = new PostAdapter(this.getContext(), R.layout.listview_item_row, tmp);
//
//            ListView postsLV = (ListView) v.findViewById(R.id.postsLV);
//            postsLV.setAdapter(adapter);

        //System.out.println("isOnline" + isOnline());

        return v;

    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}





