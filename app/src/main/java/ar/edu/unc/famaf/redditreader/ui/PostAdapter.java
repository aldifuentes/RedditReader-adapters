package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 06/10/2016.
 */
public class PostAdapter extends ArrayAdapter {

    private List<PostModel> mListPostModel;

    private List<PostModel> mListHotPostModel = new ArrayList<PostModel>();
    private List<PostModel> mListNewPostModel = new ArrayList<PostModel>();;
    private List<PostModel> mListTopPostModel = new ArrayList<PostModel>();;

    public PostAdapter(Context context, int resource, List<PostModel> list) {
        super(context, resource);
        mListPostModel = list;
    }

    public void xxxHot() {
        PostModel p1 = new PostModel();
        p1.setTitle("Hot");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        mListHotPostModel.add(p1);
        mListPostModel.clear();
        mListPostModel.addAll(mListHotPostModel);
        notifyDataSetChanged();
    }

    public void xxxNew() {
        PostModel p1 = new PostModel();
        p1.setTitle("New");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        mListNewPostModel.add(p1);
        mListPostModel.clear();
        mListPostModel.addAll(mListNewPostModel);
        notifyDataSetChanged();
    }

    public void xxxTop() {
        PostModel p1 = new PostModel();
        p1.setTitle("Top");
        p1.setAuthor("Autor1");
        p1.setCreated("Hace 4h");
        p1.setSubreddit("/r/today");
        p1.setComments("2112 comentarios");
        p1.setUrl("http://japanlover.me/goodies/kawaii/02-icons/nomnom-01.png");
        mListTopPostModel.add(p1);
        mListPostModel.clear();
        mListPostModel.addAll(mListTopPostModel);
        notifyDataSetChanged();
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
            holder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

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

        try {
            URL[] urlArray = new URL[1];
            urlArray[0] = new URL(pm.getUrl());
            holder.position = position;
            DownloadImageAsyncTask imageAsyncTask = new DownloadImageAsyncTask(holder, position);
            imageAsyncTask.execute(urlArray);
        }catch(Exception e){
            e.printStackTrace();
        }


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
        int position;
        ProgressBar progressBar;
    }



    @Override
    public boolean isEmpty(){
        return mListPostModel.isEmpty();
    }

    protected class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        PostHolder mHolder;
        int mPosition;

        public DownloadImageAsyncTask(PostHolder holder, int position){
            mHolder = holder;
            mPosition = position;
        }

        @Override
        protected Bitmap doInBackground(URL... urls){
            URL url = urls[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is,null,null);
            } catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            mHolder.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap result){
            RedditDBHelper db = RedditDBHelper.getInstance(null);

            int res = db.updateImage(mHolder.title.getText().toString(), result);

            if (mHolder.position == mPosition) {
                Bitmap tmp = db.getImage(mHolder.title.getText().toString());
                mHolder.icon.setImageBitmap(tmp);
            }
            mHolder.progressBar.setVisibility(View.GONE);
        }


    }

}