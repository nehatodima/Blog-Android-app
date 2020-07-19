package com.example.blog.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.Data.BlogRecyclerAdapter;
import com.example.blog.R;
import com.example.blog.Utils.Blog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
public class HomePage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser muser;
    private FirebaseDatabase mdatabse;
    private DatabaseReference mref;
    private Button bu;
    private TextView t;
    private RecyclerView recyclerview;
    private BlogRecyclerAdapter blogRecyclerAdapter ;
    private List<Blog> bloglist;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toast.makeText(HomePage.this, "onee", Toast.LENGTH_SHORT).show();
     mAuth = FirebaseAuth.getInstance();
     muser = mAuth.getCurrentUser();
     mdatabse = FirebaseDatabase.getInstance();
     mref =  mdatabse.getReference().child("MBlog");
     mref.keepSynced(true);
     bloglist = new ArrayList<>();
     recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
     recyclerview.setHasFixedSize(true);
        LinearLayoutManager ly= new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(ly);
        blogRecyclerAdapter = new BlogRecyclerAdapter(HomePage.this , bloglist);
        recyclerview.setAdapter(blogRecyclerAdapter);
        blogRecyclerAdapter.notifyDataSetChanged();
//        recyclerview.smoothScrollToPosition(bloglist.size()-1);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signoutid)
        {
      mAuth.signOut();
            Toast.makeText(HomePage.this ,"signed out",Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomePage.this, MainActivity.class));
            //finish();
        }
        else if(item.getItemId() == R.id.addpostid)
        {

            try
            {
                //Try starting the Drawing activity
                startActivity(new Intent(getApplicationContext(), AddPost.class));

                Toast.makeText(HomePage.this,"add post",Toast.LENGTH_LONG).show();
            }
            catch(Exception ex)
            {
                //A problem occured, check Logcat
                ex.printStackTrace();
            }
            finally
            {
                //DrawingActivity successfully started
                //So, finish the MainActivity
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Blog blog =dataSnapshot.getValue(Blog.class);
                bloglist.add(blog);
               // Collections.reverse(bloglist);
                LinearLayoutManager ly= new LinearLayoutManager(getApplicationContext());
                recyclerview.setLayoutManager(ly);
                blogRecyclerAdapter = new BlogRecyclerAdapter(HomePage.this , bloglist);
                recyclerview.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();
                recyclerview.smoothScrollToPosition(bloglist.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}*/

public class HomePage extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> blogList;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MBlog");
        mDatabaseReference.keepSynced(true);


        blogList = new ArrayList<>();
        Blog blog = new Blog();
        blog.setImage("null");
        blog.setTitle("neha");
        blog.setDesc("desccc");
        blog.setTimestamp("hihggt");
        blog.setUserid("jhjh");

        blogList.add(blog);

        Collections.reverse(blogList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        blogRecyclerAdapter = new BlogRecyclerAdapter(HomePage.this,blogList);
        recyclerView.setAdapter(blogRecyclerAdapter);
        blogRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signoutid)
        {
            mAuth.signOut();
            Toast.makeText(HomePage.this ,"signed out",Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomePage.this, MainActivity.class));
            //finish();
        }
        else if(item.getItemId() == R.id.addpostid)
        {

            try
            {
                //Try starting the Drawing activity
                startActivity(new Intent(getApplicationContext(), AddPost.class));

                Toast.makeText(HomePage.this,"add post",Toast.LENGTH_LONG).show();
            }
            catch(Exception ex)
            {
                //A problem occured, check Logcat
                ex.printStackTrace();
            }
            finally
            {
                //DrawingActivity successfully started
                //So, finish the MainActivity
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               // Blog blog = dataSnapshot.getValue(Blog.class);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
