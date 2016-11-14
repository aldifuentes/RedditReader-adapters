package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {
    List<PostModel> postsList = new ArrayList<PostModel>();
    PostAdapter adapter;

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



        PostModel p1 = new PostModel();
        p1.setTitle("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        postsList.add(p1);




        adapter = new PostAdapter(getActivity(), R.layout.listview_item_row, postsList);
        ListView postsLV = (ListView) view.findViewById(R.id.postsLV);
        postsLV.setAdapter(adapter);
        postsLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                System.out.println("[*] onLoadMore -> " + page + " - " + totalItemsCount);
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                Backend.getInstance().getNextPosts(new PostsIteratorListener() {
                    @Override
                    public void nextPosts(List<PostModel> lst) {
                        System.out.println("[*] PostsIteratorListener->nextPosts");

                        postsList.addAll(lst);

                        adapter.notifyDataSetChanged();
                    }
                });



                //loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
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


    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`





        System.out.println("[*] loadNextDataFromApi -> " + offset);

        PostModel p1 = new PostModel();
        p1.setTitle("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        postsList.add(p1);postsList.add(p1);postsList.add(p1);postsList.add(p1);postsList.add(p1);

        adapter.notifyDataSetChanged();
    }


    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}





