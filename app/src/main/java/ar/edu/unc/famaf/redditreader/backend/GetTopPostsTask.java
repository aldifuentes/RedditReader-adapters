package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 20/10/2016.
 */
public class GetTopPostsTask extends AsyncTask<String, Integer, Listing>{

    public GetTopPostsTask() {
    }

    @Override
    protected Listing doInBackground(String... reqUrl){
            Listing response = new Listing();

            try {
                URL url = new URL(reqUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                Parser parser = new Parser();

                response = parser.readJsonStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
    }

}
