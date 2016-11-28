package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsDetailActivity extends AppCompatActivity implements NewsDetailActivityFragment.OnLinkClickedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("[*] NewsDetailActivity->onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        PostModel post = (PostModel) getIntent().getSerializableExtra("POST");


        Bundle arg = new Bundle();
        arg.putSerializable("post", post);
        NewsDetailActivityFragment fragment = new NewsDetailActivityFragment();
        fragment.setArguments(arg);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, fragment).commit();
    }


    @Override
    public void onLinkClicked(String link) {
        System.out.println("[*] onLinkClicked link -> "+ link);

        Intent i = new Intent(this, LinkActivity.class);
        i.putExtra("link", link);
        startActivity(i);
    }
}
