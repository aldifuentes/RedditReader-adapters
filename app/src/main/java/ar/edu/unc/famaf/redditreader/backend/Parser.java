package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 25/10/2016.
 */
public class Parser {

    public Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            Listing l = readJson(reader);
            return l;
        } finally {
            reader.close();
        }
    }

    public Listing readJson(JsonReader reader) throws IOException {
        Listing listPostModel = new Listing();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                listPostModel = readData(reader);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listPostModel;
    }

    public Listing readData(JsonReader reader) throws IOException {
        Listing listPostModel = new Listing();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("children")) {
                listPostModel = readChildren(reader);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listPostModel;

    }

    public Listing readChildren(JsonReader reader) throws IOException {
        Listing listPostModel = new Listing();

        reader.beginArray();
        while (reader.hasNext()) {
            listPostModel.add(readArray(reader));
        }
        reader.endArray();

        return listPostModel;
    }

    public PostModel readArray(JsonReader reader) throws IOException {
        PostModel postModel = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                postModel = readPost(reader);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return postModel;
    }

    public PostModel readPost(JsonReader reader) throws IOException {
        PostModel post = new PostModel();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "title":
                    post.setTitle(reader.nextString());
                    break;
                case "author":
                    post.setAuthor(reader.nextString());
                    break;
                case "created":
                    String tmp = reader.nextString();
                    tmp = tmp.substring(0, tmp.length() - 2);
                    long timeStamp = Long.parseLong(tmp) * 1000;
                    DateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm");
                    Date netDate = (new Date(timeStamp));
                    post.setCreated(sdf.format(netDate));
                    break;
                case "subreddit":
                    post.setSubreddit("/r/"+reader.nextString());
                    break;
                case "num_comments":
                    post.setComments(reader.nextString());
                    break;
                case "thumbnail":
                    post.setUrl(reader.nextString());
                    //post.setImage(post.getBitmapFromString(reader.nextString()));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return post;
    }


}
