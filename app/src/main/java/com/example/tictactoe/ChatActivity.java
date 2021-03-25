package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    Button btnSendMsg;
    EditText etMsg,etMsg2;

    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    String UserName, SelectedTopic, user_msg_key;

    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        etMsg = (EditText) findViewById(R.id.etMessage);
        etMsg2=(EditText)(findViewById(R.id.etMessage1));

        lvDiscussion = (ListView) findViewById(R.id.lvConversation);
        arrayAdpt = new ArrayAdapter(this,  R.layout.list_view_text_colors, R.id.list_content, listConversation);
        lvDiscussion.setAdapter(arrayAdpt);


        UserName = getIntent().getExtras().get("user_name").toString();
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        setTitle("Topic : " + SelectedTopic);

        dbr = FirebaseDatabase.getInstance().getReference().child(SelectedTopic);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);

                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                String tm=etMsg2.getText().toString()+"!"+etMsg.getText().toString();
                map2.put("msg", tm);
                map2.put("user", UserName);
                dbr2.updateChildren(map2);

                etMsg.setText("");
                etMsg2.setText("");
            }
        });
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateConversation(DataSnapshot dataSnapshot){
        String total,description,title, user, conversation,tmp5;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            total = (String) ((DataSnapshot)i.next()).getValue();
            user = (String) ((DataSnapshot)i.next()).getValue();
            int pos=total.indexOf('!');
            title=total.substring(0,pos);
            description=total.substring(pos+1,total.length());
            tmp5="Title : "+title+'\n'+"Description : "+description;
            conversation = user + ": " + tmp5;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }

    }


}