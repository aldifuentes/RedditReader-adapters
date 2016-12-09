package ar.edu.unc.famaf.redditreader.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.OnPostItemSelectedListener;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.utils.NetworkReceiver;

import static ar.edu.unc.famaf.redditreader.utils.NetworkReceiver.SHOW_DIALOG_ACTION;

public class NewsActivity extends AppCompatActivity implements OnPostItemSelectedListener{
    public static final int CATEGORY_HOT = 0;
    public static final int CATEGORY_NEW = 1;
    public static final int CATEGORY_TOP = 2;

    private NetworkReceiver checkConnectionReceiver = null;
    BroadcastReceiver showDialogReceiver = null;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private Fragment frHot;
    private Fragment frTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RedditDBHelper db = RedditDBHelper.getInstance(this);
        db.deleteAllPosts();

        //////////
        String[] mCategories = new String[3];
        mCategories[CATEGORY_HOT] = "Hot";
        mCategories[CATEGORY_NEW] = "New";
        mCategories[CATEGORY_TOP] = "Top";
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mCategories));

        frHot = new HotActivityFragment();
        frTop = new TopActivityFragment();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("[*] Drawer->onItemClick -> id = " + id );

                if (id==CATEGORY_HOT) {
                    toolbar.setTitle("HOT");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frHot).commit();
                }
                if (id==CATEGORY_NEW) {
                    toolbar.setTitle("NEW");
                }
                if (id==CATEGORY_TOP) {
                    toolbar.setTitle("TOP");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frTop).commit();
                }



                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
        /////////



        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        checkConnectionReceiver = new NetworkReceiver();
        this.registerReceiver(checkConnectionReceiver, filter);


        IntentFilter showDialogFilter = new IntentFilter(SHOW_DIALOG_ACTION);
        showDialogReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                showDialog();
            }
        };
        this.registerReceiver(showDialogReceiver, showDialogFilter);

    }

    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(NewsActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("No hay conexion a Internet.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (checkConnectionReceiver != null) {
            this.unregisterReceiver(checkConnectionReceiver);
            this.unregisterReceiver(showDialogReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            //TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
            //textView.setText("User XXXX logged in");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostItemPicked(PostModel post) {
        System.out.println("[*] onPostItemPicked title -> "+ post.getTitle());

        Intent i = new Intent(this, NewsDetailActivity.class);
        i.putExtra("POST", post);
        startActivity(i);
    }
}
