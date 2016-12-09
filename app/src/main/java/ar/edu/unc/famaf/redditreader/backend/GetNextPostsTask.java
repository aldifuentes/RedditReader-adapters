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
        URL url = null;

        String category = params[0];
        String baseUrl = "https://www.reddit.com/"+category+"/.json?limit=50&after=";
        System.out.println("[*] doInBackground -> category: " + category);


        String afterPostName = params[1];


        System.out.println("[*] doInBackground -> " + afterPostName);

        try {
            url = new URL(baseUrl + afterPostName);
            System.out.println("[*] doInBackground -> " + url.toString());

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

