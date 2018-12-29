package gautham.agjs.institute;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private DatabaseReference databaseReference;
    String personName;
    String currentDateTimeString;
    ProgressDialog mprogress;
    long date = System.currentTimeMillis();
    private TextView d ;
    private TextView a;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    String dateString = sdf.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Date d2 = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        currentDateTimeString = sdf2.format(d2);
        a = findViewById(R.id.time_cal);
        a.setText(currentDateTimeString);


        databaseReference = FirebaseDatabase.getInstance().getReference().child(dateString);
        mprogress = new ProgressDialog(this);
        mprogress.setMessage("Adding Your Entry");

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
        }
        d = findViewById(R.id.name_display);
        d.setText(personName);


    }


    public void update_db(View v){

        AddData();

    }

    public void viewData(View v){
        Intent intent = new Intent(MainActivity.this,DataView.class);
        startActivity(intent);
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
        String Time = currentDateTimeString;
        mprogress.show();
        UpdateData updateData = new UpdateData(Name, Time);
        databaseReference.push().setValue(updateData);
        mprogress.dismiss();
        Toast.makeText(MainActivity.this,"Updated Your Entry", Toast.LENGTH_SHORT).show();
    }


}
