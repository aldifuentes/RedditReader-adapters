package ar.edu.unc.famaf.redditreader.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import ar.edu.unc.famaf.redditreader.R;

public class LinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        String link = getIntent().getStringExtra("link");
        System.out.println("[*] LinkActivity->onCreate -> " + link );

        WebView webView = (WebView) findViewById(R.id.link_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

}
