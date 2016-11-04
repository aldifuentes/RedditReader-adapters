package ar.edu.unc.famaf.redditreader.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by fuentald on 02/11/2016.
 */
public class NetworkReceiver extends BroadcastReceiver {
    public static final String SHOW_DIALOG_ACTION = "ar.edu.unc.famaf.redditreader.ShowDialog";

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager conn =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();

        if (networkInfo.isConnected() == false) {
            Intent showDialogIntent = new Intent(SHOW_DIALOG_ACTION);
            context.sendBroadcast(showDialogIntent);
        }
    }
}
