package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.Listing;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {
    List<PostModel> postsList = new ArrayList<PostModel>();
    PostAdapter adapter;

    OnPostItemSelectedListener mCallback;

    public interface OnPostItemSelectedListener{
        void onPostItemPicked(PostModel post);
    }

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        RedditDBHelper db = RedditDBHelper.getInstance(this.getContext());
        db.deleteAllPosts();

        /*
        Backend.getInstance().getTopPosts(new TopPostIterator() {
            @Override
            public void nextPosts(List<PostModel> postsList) {
                PostAdapter adapter = new PostAdapter(getActivity(), R.layout.listview_item_row, postsList);
                ListView postsLV = (ListView) getView().findViewById(R.id.postsLV);
                postsLV.setAdapter(adapter);
            }
        });
        */


        /*
        PostModel p1 = new PostModel();
        p1.setTitle("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        postsList.add(p1);
        */
        Backend.getInstance().getNextPosts(new PostsIteratorListener() {
            @Override
            public void nextPosts(List<PostModel> lst) {
                System.out.println("[*] PostsIteratorListener->nextPosts");
                postsList.addAll(lst);
                adapter.notifyDataSetChanged();
            }
        });



        adapter = new PostAdapter(getActivity(), R.layout.listview_item_row, postsList);
        final ListView postsLV = (ListView) view.findViewById(R.id.postsLV);
        postsLV.setAdapter(adapter);
        postsLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                System.out.println("[*] onLoadMore -> " + page + " - " + totalItemsCount);


                Backend.getInstance().getNextPosts(new PostsIteratorListener() {
                    @Override
                    public void nextPosts(List<PostModel> lst) {
                        System.out.println("[*] PostsIteratorListener->nextPosts");

                        postsList.addAll(lst);

                        adapter.notifyDataSetChanged();
                    }
                });

                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        postsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override


           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               PostModel post = (PostModel) postsLV.getItemAtPosition(position);
               System.out.println("[*] item -> "+ position);

               mCallback.onPostItemPicked(post);
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

        return view;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = getActivity();

        try {
            mCallback = (OnPostItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPostItemSelectedListener");
        }
    }



    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}





