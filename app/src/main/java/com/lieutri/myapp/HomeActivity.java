package com.lieutri.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lieutri.myapp.components.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;
    private EditText etUsername, etPassword;
    private TextView tvShow;
    private Button btnLogout;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    public static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvShow = findViewById(R.id.tvShow);
        btnLogout = findViewById(R.id.btnLogout);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // "https://myapp-lieutri-default-rtdb.firebaseio.com/"
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("users");

        btnSubmit.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void WriteData(String username, String password){
        User user = new User(username, password);
//        myRef.child(currentUser.getUid()).setValue(user);


        // Add a new document with a generated ID
        collectionReference.document(currentUser.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void ReadData(){
        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//
//                    tvShow.setText(user.getUsername()+"\n"+user.getPassword());
//
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        collectionReference.document(currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Map<String, Object> user;
                        assert documentSnapshot != null;
                        user = documentSnapshot.getData();

                        assert user != null;
                        tvShow.setText(Objects.requireNonNull(user.get("username")).toString()+"\n"+ Objects.requireNonNull(user.get("password")).toString());

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Logged out",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.btnSubmit:
                WriteData(etUsername.getText().toString(), etPassword.getText().toString());
                ReadData();
        }
    }
}