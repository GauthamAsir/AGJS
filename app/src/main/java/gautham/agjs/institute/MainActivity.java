package gautham.agjs.institute;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private DatabaseReference databaseReference;
    String personName;
    String currentDateTimeString;
    ProgressDialog mprogress;
    long date = System.currentTimeMillis();
    public TextView d ;

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
        mprogress = new ProgressDialog(this);
        mprogress.setMessage("Adding Your Entry");

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
        }


    }


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(dateString);
    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                String db_name = ds.child("name").getValue(String.class);
                String db_time = ds.child("time").getValue(String.class);
                Log.d("TAG", db_name + " / " + db_time);
                //temp_dis = (TextView)findViewById(R.id.temp);
                //temp_dis.setText(db_name + db_time);
                Toast.makeText(MainActivity.this,db_name,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
        String Time = currentDateTimeString.trim();
        mprogress.show();
        UpdateData updateData = new UpdateData(Name, Time);
        databaseReference.push().setValue(updateData);
        mprogress.dismiss();
        Toast.makeText(MainActivity.this,"Updated Your Entry", Toast.LENGTH_SHORT).show();
    }


}
