package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fuentald on 12/11/2016.
 */
public class GetNextPostsTask extends AsyncTask<String, Integer, Listing> {

    public GetNextPostsTask() {
    }

    @Override
    protected Listing doInBackground(String... params){
        Listing response = new Listing();
        String baseUrl = "https://www.reddit.com/top/.json?limit=50&after=";
        URL url = null;

        String afterPostName = params[0];


        System.out.println("[*] doInBackground -> " + afterPostName);

        try {
            url = new URL(baseUrl + afterPostName);

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

