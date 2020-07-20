package com.example.blog.Data;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blog.R;
import com.example.blog.Utils.Blog;

import java.util.Date;
import java.util.List;

//import java.util.Date;

/**
 * Created by paulodichone on 4/17/17.
 */

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter( List<Blog> blogList) {
        //this.context = context;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Blog blog = blogList.get(position);
        String imageUrl = null;

        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDesc());


       java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
       String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());



        holder.timestamp.setText(formattedDate);

        Glide.with(context)
                .load(Uri.parse(blog.getImage()))
                .into(holder.image);
       // holder.image.setImageURI(Uri.parse(blog.getImage()));
        //holder.mItem = mValues.get(position);

       // imageUrl = blog.getImage();


        /*
        //Picasso.with(context)
          //      .load(imageUrl)
            //    .into(holder.image);*/



    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        String userid;

        public ViewHolder(View view) {
            super(view);



            title = (TextView) view.findViewById(R.id.posttitlelist);
            desc = (TextView) view.findViewById(R.id.postdesclist);
            image = (ImageView) view.findViewById(R.id.postimagelist);
            timestamp = (TextView) view.findViewById(R.id.posttimelist);

            userid = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // we can go to the next activity...

                }
            });

        }
    }
}

//package com.example.whatsapp;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import java.util.ArrayList;
//public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolderClass> {
//    ArrayList<ChatObject> chatList;
//    public ChatAdapter(ArrayList<ChatObject> charList) {
//        this.chatList = charList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchat,null,false);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutView.setLayoutParams(lp);
//        ViewHolderClass vhc=new ViewHolderClass(layoutView);
//        return vhc;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolderClass holder, final int position) {
//        holder.chatID.setText(chatList.get(position).getChatID());
//       holder.eachContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),ChatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("chatID",chatList.get(holder.getAdapterPosition()).getChatID());
//                intent.putExtras(bundle);
//                v.getContext().startActivity(intent);
//                //String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
//                //FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
//                //FirebaseDatabase.getInstance().getReference().child(userList.get(position).getUid()).child("chat").child(key).setValue(true);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() { return chatList.size();
//    }
//    public class ViewHolderClass extends RecyclerView.ViewHolder{
//        public TextView chatID;
//        public LinearLayout eachContact;
//        public ViewHolderClass( View itemView) {
//            super(itemView);
//            chatID = itemView.findViewById(R.id.chatID);
//            eachContact = itemView.findViewById(R.id.eachcontact);
//        }
//
//    }
//}