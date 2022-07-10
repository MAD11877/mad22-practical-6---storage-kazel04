package com.example.prac2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;


public class Logins extends AppCompatActivity {
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://adplz-cabe6-default-rtdb.firebaseio.com/");
        Auth = FirebaseAuth.getInstance();
        Button logins = findViewById(R.id.buttonLogin);
        logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editusernames = findViewById(R.id.Username);
                EditText editpasswords = findViewById(R.id.Password);

                String username = editusernames.getText().toString();
                String password = editpasswords.getText().toString();
                DatabaseReference myref = database.getReference("Users/" + username);

                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> map = (HashMap<String, Object>) snapshot.getValue();

                        String correctPassword = "";
                        try {
                            correctPassword = map.get("password").toString();
                        } catch (Exception e) {
                            Toast.makeText(Logins.this, "WRONG USERNAME", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (correctPassword.equals(password)) {
                            Intent intent = new Intent(Logins.this, ListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Logins.this, "WRONG PASSWORD", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });

            }
        });

    }
//Worked with ray zin for this practical
}
