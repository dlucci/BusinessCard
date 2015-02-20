package com.dlucci.businesscard;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
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

    private static String fName, lName, pEmail, pNumber, emailSubject, messageBody;

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

        emailSubject = "We met at the conference";

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

            messageBody = "My name is " + fName + " " + lName + ".  My phone number is " + pNumber + " and my email is " + pEmail;

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            //email.putExtra(Intent.EXTRA_EMAIL, new String[]{"a@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            email.putExtra(Intent.EXTRA_TEXT, messageBody);

            PendingIntent eIntent = PendingIntent.getActivity(this, 0, Intent.createChooser(email, "Sending email"), PendingIntent.FLAG_UPDATE_CURRENT);

            Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
            sms.putExtra("sms_body", messageBody);

            PendingIntent tIntent = PendingIntent.getActivity(this, 0, sms, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Business Card")
                    .setContentText("Send information")
                    .addAction(R.drawable.ic_action_chat, "Text", tIntent)
                    .addAction(R.drawable.ic_action_email, "Email", eIntent)
                    .setOngoing(true);
            notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notification.notify(1, builder.build());
        } else if(id == stop.getId()){
            if(notification != null)
                notification.cancel(1);
        }

    }
}
