package com.dlucci.businesscard;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class CardActivity extends Activity implements OnClickListener {

    private EditText firstName, lastName, email, phoneNumber;
    private Button start, stop;

    private static String fName, lName, pEmail, pNumber;

    private static Notification.Builder builder;
    private static NotificationManager notification;

    private static PendingIntent pIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        start = (Button)findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        builder = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Business Card")
                .setContentText("Send information")
                .addAction(R.drawable.ic_launcher, "Text", pIntent)
                .addAction(R.drawable.ic_launcher, "Email", pIntent)
                .setOngoing(true);
        notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == start.getId()){
            fName = firstName.getText().toString();
            lName = lastName.getText().toString();
            pEmail = email.getText().toString();
            pNumber = phoneNumber.getText().toString();
            notification.notify(1, builder.build());
        } else if(id == stop.getId()){
            notification.cancel(1);
        }

    }

    //private class
}
