/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package projekt.htlgrieskirchen.at.notodoslist;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

/**
 * PingService creates a notification that includes 2 buttons: one to snooze the
 * notification, and one to dismiss it.
 */
public class PingService extends IntentService {

    private NotificationManager mNotificationManager;
    private String mMessage;
    private int mMillis;
    private Notification.Builder notification;
    int notification_id =0;
    Uri todoUri=null;
    public static final String TAG = MainActivity.TAG;
    public PingService() {

        // The super call is required. The background thread that IntentService
        // starts is labeled with the string argument you pass.
        super("projekt.htlgrieskirchen.at.notodoslist");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // The reminder message the user set.
        if(intent.getExtras()!=null)
        {
            Log.d(TAG,"!null");
            todoUri =  intent.getParcelableExtra(TodoContentProvider.CONTENT_ITEM_TYPE);

        }
        else
        {
            Log.d(TAG, "null");

        }
        Log.d(TAG,"todoUri");

        if(todoUri!=null) {
            Log.d(TAG,"todoUri2");
            Cursor cursor = getContentResolver().query(todoUri, TodosTbl.ALL_COLUMNS, null, null,
                    null);

            if (cursor != null) {
                Log.d(TAG,"todoUri3");
                cursor.moveToFirst();
                notification_id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Log.d(TAG," "+notification_id);
            }
            cursor.close();
        }

        Log.d(TAG,"id ");
        if(intent.getIntExtra("id",0)!=0)
        {

            notification_id=intent.getIntExtra("id",0);
            Log.d(TAG," "+notification_id);
        }
        mMessage = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
        // The timer duration the user set. The default is 10 seconds.
        mMillis = intent.getIntExtra(CommonConstants.EXTRA_TIMER,
                CommonConstants.DEFAULT_TIMER_DURATION);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        String action = intent.getAction();
        // This section handles the 3 possible actions:
        // ping, snooze, and dismiss.
        if(action.equals(CommonConstants.ACTION_PING)) {
            issueNotification(intent, mMessage);
        } else if (action.equals(CommonConstants.ACTION_SNOOZE)) {
            nm.cancel(notification_id);
            Log.d(CommonConstants.DEBUG_TAG, getString(R.string.snoozing));
            // Sets a snooze-specific "done snoozing" message.
           Intent newTodo =new Intent(this,New_Todo.class);
            Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + notification_id);
            newTodo.putExtra(TodoContentProvider.CONTENT_ITEM_TYPE, uri);
            newTodo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newTodo);

        } else if (action.equals(CommonConstants.ACTION_DISMISS)) {

            nm.cancel(notification_id);
            Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + notification_id);
            ContentValues vals = new ContentValues();

                vals.put("Done","true");


            getContentResolver().update(uri, vals, null, null);


            Intent i=new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void issueNotification(Intent intent, String msg) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // Sets up the Snooze and Dismiss action buttons that will appear in the
        // expanded view of the notification.
        Intent dismissIntent = new Intent(this, PingService.class);
        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        dismissIntent.putExtra("id", notification_id);
        PendingIntent piDismiss = PendingIntent.getService(this,notification_id, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, PingService.class);
        snoozeIntent.putExtra("id",notification_id);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);

        PendingIntent piSnooze = PendingIntent.getService(this,notification_id, snoozeIntent, 0);

        // Constructs the Builder object.
        notification =
                new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(msg)
                .setContentText("Do not forget!")
                .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                /*
                 * Sets the big view "big text" style and supplies the
                 * text (the user's reminder message) that will be displayed
                 * in the detail area of the expanded notification.
                 * These calls are ignored by the support library for
                 * pre-4.1 devices.
                 */

                .addAction(android.R.drawable.ic_delete,
                        "Erledigt", piDismiss)
                .addAction(android.R.drawable.sym_def_app_icon,
                        "Verschieben", piSnooze);

        /*
         * Clicking the notification itself displays ResultActivity, which provides
         * UI for snoozing or dismissing the notification.
         * This is available through either the normal view or big view.
         */
         Intent resultIntent = new Intent(this, ResultActivity.class);
         resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE, msg);
         resultIntent.putExtra("id",notification_id);
         resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

         // Because clicking the notification opens a new ("special") activity, there's
         // no need to create an artificial back stack.
         PendingIntent resultPendingIntent =
                 PendingIntent.getActivity(
                 this,
                 notification_id,
                 resultIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );

         notification.setContentIntent(resultPendingIntent);
         startTimer( mMillis);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void issueNotification(Notification.Builder builder) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Including the notification ID allows you to update the notification later on.
        mNotificationManager.notify(notification_id, builder.build());
    }

 // Starts the timer according to the number of seconds the user specified.
    private void startTimer(int millis) {
        Log.d(CommonConstants.DEBUG_TAG, getString(R.string.timer_start));
        try {
            Thread.sleep(millis);

        } catch (InterruptedException e) {
            Log.d(CommonConstants.DEBUG_TAG, getString(R.string.sleep_error));
        }
        Log.d(CommonConstants.DEBUG_TAG, getString(R.string.timer_finished));
        issueNotification(notification);
    }
}
