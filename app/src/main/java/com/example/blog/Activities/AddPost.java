package com.example.blog.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPost extends AppCompatActivity {
    String downloadImageUrl;
    private Button b;
    public EditText title;
    public EditText desc;
    private ImageButton im;
    private DatabaseReference mRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

     private ProgressDialog progress;
     private Uri iuri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        b = (Button) findViewById(R.id.button2);
        title = (EditText) findViewById(R.id.editText3);
        desc = (EditText) findViewById(R.id.editText4);
        im = (ImageButton) findViewById(R.id.imageButton);


        progress = new ProgressDialog(this);


        mRef = FirebaseDatabase.getInstance().getReference().child("mBlog");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        Toast.makeText(AddPost.this, "added post", Toast.LENGTH_LONG).show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Posting to blog");
                progress.show();
                final String titlestring = title.getText().toString().trim();
                final String descstring = desc.getText().toString().trim();


                if (!TextUtils.isEmpty(titlestring) && !TextUtils.isEmpty(descstring) /*&& iuri != null*/) {
                    //Toast.makeText(AddPost.this, "one", Toast.LENGTH_SHORT).show();
                    // final StorageReference filepath = productImagesRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
                    //final UploadTask uploadTask = filepath.putFile(imageUri);

                    StorageReference filepath = mStorageRef.child("MBlog_images").child(iuri.getLastPathSegment());
              //  StorageReference filepath = mStorageRef.child("MBlog_images").child(iuri.getLastPathSegment()+".jpg");
                    filepath.putFile(iuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Toast.makeText(AdminAddNewProductActivity.this,"Image uploaded succesfully",Toast.LENGTH_SHORT).show();
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadImageUrl= uri.toString();

                                    DatabaseReference newpost = mRef.push();
                                    DatabaseReference userdetails = mRef.push();

                                    /*String mailid = mAuth.getCurrentUser().getEmail();
                                    Map<String, String> userdata = new HashMap<>();
                                    userdata.put("email", mailid.toString());
                                    userdetails.setValue(userdata);*/

                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", titlestring);
                                    data.put("desc", descstring);
                                   // Toast.makeText(AddPost.this, downloadImageUrl, Toast.LENGTH_SHORT).show();
                                    data.put("image", downloadImageUrl);

                                    data.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
                                    data.put("userid", mUser.getUid());
                                    newpost.setValue(data);
                                    progress.dismiss();
                                    startActivity(new Intent(AddPost.this,HomePage.class));


                                    //createNewPost(imageUrl);
                                }
                            });

                        }

                    });

                }




            }
        });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            iuri = data.getData();
            im.setImageURI(iuri);
        }
    }

    public void ddd(View view)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,1);
    }



 }


