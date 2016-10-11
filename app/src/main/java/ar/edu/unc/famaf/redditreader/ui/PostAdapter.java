package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 06/10/2016.
 */
public class PostAdapter extends ArrayAdapter {

    private List<PostModel> mListPostModel;

    public PostAdapter(Context context, int resource, List<PostModel> list) {
        super(context, resource);
        mListPostModel = list;
    }

    @Override
    public int getCount(){
        return mListPostModel.size();
    }

    @Override
    public PostModel getItem(int position){
        return mListPostModel.get(position);
    }

    public int getPosition (PostModel item){
        return  mListPostModel.indexOf(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        PostHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_item_row, null);

            holder = new PostHolder();
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            holder.subreddit = (TextView)convertView.findViewById(R.id.subreddit);
            holder.hour = (TextView)convertView.findViewById(R.id.hour);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.comments = (TextView)convertView.findViewById(R.id.comments);

            convertView.setTag(holder);
        } else{
            holder = (PostHolder) convertView.getTag();
        }
            //ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
            //TextView subreddit = (TextView)convertView.findViewById(R.id.subreddit);
            //TextView hour = (TextView)convertView.findViewById(R.id.hour);
            //TextView title = (TextView)convertView.findViewById(R.id.title);
            //TextView comments = (TextView)convertView.findViewById(R.id.comments);

            PostModel pm = mListPostModel.get(position);

            holder.icon.setImageResource(pm.getImage());
            holder.subreddit.setText(pm.getSubreddit());
            holder.hour.setText(pm.getCreated());
            holder.title.setText(pm.getTitle());
            holder.comments.setText(pm.getComments());

        return convertView;
    }

    static class PostHolder {
        ImageView icon;
        TextView subreddit;
        TextView hour;
        TextView title;
        TextView comments;
    }


    @Override
    public boolean isEmpty(){
        return mListPostModel.isEmpty();
    }



}
