package com.raheelmalik.locationandstatesharing;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Raheel Malik on 1/30/2018.
 */

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot>mSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
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
    }

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder {
        TextView userName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public User getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(userName.class);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.chat_msg_row,parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.userName.getLayoutParams();
            convertView.setTag(holder);

         }

         final User message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String author = message.getAuthor();
        holder.userName.setText(author);



        return convertView;
    };





    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }
}
