package com.example.blog.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.Data.BlogRecyclerAdapter;
import com.example.blog.R;
import com.example.blog.Utils.Blog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private List<Blog> blogList;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Blog> bloglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MBlog");
        mDatabaseReference.keepSynced(true);


        bloglist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("mBlog");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("HomePgae.this","startttttttttttt");

                    collectDetails((Map<String, Object>) dataSnapshot.getValue());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        initializeRecyclerView();

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


    private void initializeRecyclerView() {
        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BlogRecyclerAdapter(bloglist);
        recyclerView.setAdapter(adapter);
    }
    private void collectDetails(Map<String, Object> posts) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : posts.entrySet()) {

            //Get user map
            Map singlePost = (Map) entry.getValue();
            //Get phone field and append to list
           // String title = singlePost.get("title");
          //  phoneNumbers.add((Long) singleUser.get("phone"));
            Blog blog = new Blog();
            blog.setImage(singlePost.get("image").toString());
            blog.setTitle(singlePost.get("title").toString());
            blog.setDesc(singlePost.get("desc").toString());
            blog.setTimestamp(singlePost.get("timestamp").toString());
            blog.setUserid(singlePost.get("userid").toString());

            bloglist.add(blog);
            Log.d("HomePgae.this","valllllllllllllll"+singlePost.get("title").toString());
            adapter.notifyDataSetChanged();
        }

    }
}

