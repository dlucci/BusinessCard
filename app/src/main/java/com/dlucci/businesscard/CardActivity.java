package com.dlucci.businesscard;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;


public class CardActivity extends Activity {

    @InjectViews({R.id.firstName, R.id.lastName, R.id.phoneNumber, R.id.email, R.id.subject})
    List<EditText> views;

    @InjectViews({R.id.start, R.id.stop})
    List<Button> notifyButtons;

    private static String fName, lName, pEmail, pNumber, emailSubject, messageBody;

    private static Notification.Builder builder;
    private static NotificationManager notification;

    private static PendingIntent pIntent;

    private static boolean notified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.inject(this);

        setFields();
    }

    private void setFields() {
        views.get(0).setText(fName);
        views.get(1).setText(lName);
        views.get(2).setText(pNumber);
        views.get(3).setText(pEmail);
        views.get(4).setText(emailSubject);
     }


    @OnClick({R.id.start, R.id.stop})
    public void click() {
        if(!notified){
            fName = views.get(0).getText().toString();
            lName = views.get(1).getText().toString();
            pNumber = views.get(2).getText().toString();
            pEmail = views.get(3).getText().toString();
            emailSubject = views.get(4).getText().toString();

            messageBody = "My name is " + fName + " " + lName + ".  My phone number is " + pNumber + " and my email is " + pEmail;

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            email.putExtra(Intent.EXTRA_TEXT, messageBody);

            PendingIntent eIntent = PendingIntent.getActivity(this, 0, Intent.createChooser(email, "Sending email"), PendingIntent.FLAG_UPDATE_CURRENT);

            Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
            sms.putExtra("sms_body", messageBody);

            PendingIntent tIntent = PendingIntent.getActivity(this, 0, sms, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent clickIntent = new Intent(this, CardActivity.class);
            PendingIntent pendingClickIntent = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Business Card")
                    .setContentText("Send information")
                    .addAction(R.drawable.ic_action_chat, "Text", tIntent)
                    .addAction(R.drawable.ic_action_email, "Email", eIntent)
                    .setContentIntent(pendingClickIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setOngoing(true);
            notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notification.notify(1, builder.build());
            notified = true;
        } else if(notified){
            if(notification != null) {
                notification.cancel(1);
                notified = false;
            }
        }

    }
}
