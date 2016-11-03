package ar.edu.unc.famaf.redditreader.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.utils.NetworkReceiver;

import static ar.edu.unc.famaf.redditreader.utils.NetworkReceiver.SHOW_DIALOG_ACTION;

public class NewsActivity extends AppCompatActivity {
    private NetworkReceiver checkConnectionReceiver = null;
    BroadcastReceiver showDialogReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
            TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
            textView.setText("User XXXX logged in");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
