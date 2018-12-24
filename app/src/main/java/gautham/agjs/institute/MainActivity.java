package gautham.agjs.institute;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.Attributes;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    DatabaseReference databaseReference;
    String personName;
    String currentDateTimeString;

    long date = System.currentTimeMillis();

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    String dateString = sdf.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        TextView a = (TextView)findViewById(R.id.time_cal);
        a.setText(currentDateTimeString);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(dateString);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
        }

    }

    public void update_db(View v){

        AddData();

    }


    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            finish();
            moveTaskToBack(true);
        }


        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    public void AddData(){

        String Name = personName;
        String Time = currentDateTimeString.trim();

        UpdateData updateData = new UpdateData(Name, Time);
        databaseReference.push().setValue(updateData);
    }


}
