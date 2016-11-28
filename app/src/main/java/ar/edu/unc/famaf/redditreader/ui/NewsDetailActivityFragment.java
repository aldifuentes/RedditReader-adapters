package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailActivityFragment extends Fragment {

    OnLinkClickedListener mCallback;

    public interface OnLinkClickedListener{
        void onLinkClicked(String link);
    }


    public NewsDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        System.out.println("[*] NewsDetailActivityFragment->OnCreateView");


        PostModel post = (PostModel) getArguments().getSerializable("post");

        System.out.println("[*] NewsDetailActivityFragment->OnCreateView --> " + post.getTitle());



        TextView subreddit = (TextView) view.findViewById(R.id.detail_subreddit);
        subreddit.setText(post.getSubreddit());

        TextView date = (TextView) view.findViewById(R.id.detail_date);
        date.setText(post.getCreated());

        TextView title = (TextView) view.findViewById(R.id.detail_title);
        title.setText(post.getTitle());

        TextView author = (TextView) view.findViewById(R.id.detail_author);
        author.setText(post.getAuthor());

        TextView link = (TextView) view.findViewById(R.id.detail_link);
        link.setText(post.getLink());
        link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                System.out.println("[*] onClick -> cargando link");
                TextView link = (TextView) view;
                mCallback.onLinkClicked(link.getText().toString());
            }

        });


        RedditDBHelper db = RedditDBHelper.getInstance(null);
        Bitmap bmp = db.getImage(post.getTitle());
        ImageView preview = (ImageView) view.findViewById(R.id.detail_preview);
        preview.setImageBitmap(bmp);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = getActivity();

        try {
            mCallback = (OnLinkClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPostItemSelectedListener");
        }
    }

}
