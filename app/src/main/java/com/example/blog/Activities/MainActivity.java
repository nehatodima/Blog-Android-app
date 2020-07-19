 package com.example.blog.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blog.R;
import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;


//import android.util.Log;
import android.view.Menu;
        import android.view.MenuItem;

 public class MainActivity extends AppCompatActivity {
     private FirebaseDatabase database;
     private  DatabaseReference myRef;
     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;
     private Button login;
     private Button create;
     private EditText emaill;
     private EditText passwordd;


     private static final String TAG = "MainActivity";
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         login = (Button)findViewById(R.id.button);
         create = (Button)findViewById(R.id.button3);
         emaill=(EditText)findViewById(R.id.editText);
         passwordd = (EditText)findViewById(R.id.editText2);


         mAuth = FirebaseAuth.getInstance();


         // Write a message to the database
         database = FirebaseDatabase.getInstance();
        // myRef = database.getReference("message");
         // myRef.setValue("neha data!");




         mAuthListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user = mAuth.getCurrentUser();
                 if(user!= null){
                     String s ="User Aldready logged in";
                     Intent intent = new Intent(MainActivity.this,HomePage.class);
                     startActivity(intent);

                     Toast.makeText(MainActivity.this, s,Toast.LENGTH_LONG).show();
                 }
                /* else
                 {
                     String st ="User logged out";
                     Toast.makeText(MainActivity.this, st,Toast.LENGTH_LONG).show();
                 }*/
             }
         };



         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, HomePage.class);
                 startActivity(intent);
                 final String email = emaill.getText().toString();
                 String password = passwordd.getText().toString();
                 if ((email != null) && (password != null)) {
                     mAuth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information
                                         Intent intent = new Intent(MainActivity.this,HomePage.class);
                                         startActivity(intent);
                                         Toast.makeText(MainActivity.this, "Successfully Logged In.", Toast.LENGTH_LONG).show();
                                         //FirebaseUser user = mAuth.getCurrentUser();
                                         //updateUI(user);
                                     } else {
                                         // If sign in fails, display a message to the user.
                                         Toast.makeText(MainActivity.this, "LogIn failed.", Toast.LENGTH_LONG).show();
                                         //updateUI(null);
                                     }
                                 }
                             });
                 }
             }

         });

         create.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String email = emaill.getText().toString();
                 String password = passwordd.getText().toString();
                 if ((email != null) && (password != null)) {

                     mAuth.createUserWithEmailAndPassword(email, password)
                             .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information
                                         Toast.makeText(MainActivity.this, " Created Account Successfully", Toast.LENGTH_LONG).show();
                                         //FirebaseUser user = mAuth.getCurrentUser();
                                         //updateUI(user);
                                     } else {
                                         // If sign in fails, display a message to the user.
                                         Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                         Toast.makeText(MainActivity.this, "Account Creation failed.",Toast.LENGTH_LONG).show();
                                         //updateUI(null);
                                     }
                                 }
                             });
                 }
             }
         });
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId()== R.id.signoutid)
         {
             Toast.makeText(MainActivity.this,"signed out",Toast.LENGTH_LONG).show();
             mAuth.signOut();

         }
         return super.onOptionsItemSelected(item);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu,menu);
         return super.onCreateOptionsMenu(menu);
     }




     @Override
     public void onStart() {
         super.onStart();
         mAuth.addAuthStateListener(mAuthListener);
         // Check if user is signed in (non-null) and update UI accordingly.
         //FirebaseUser currentUser = mAuth.getCurrentUser();
         //updateUI(currentUser);
     }

     @Override
     public void onStop(){
         super.onStop();
         if(mAuthListener != null){
             mAuth.removeAuthStateListener(mAuthListener);
         }
     }
 }
