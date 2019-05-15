package com.mikeshehadeh.lab6;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyDBHelper dbHelper;
    private MyRecyclerAdapter adapter;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLayoutManager);
        populateRecyclerView(filter);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUserActivity();
            }
        });
    }

    @Override
    protected void onResume () {
        super.onResume();
        adapter.updateList(dbHelper.contactList(filter));
        Log.d("Tag", "Resume");
    }
    private void goToAddUserActivity() {
        Intent intent = new Intent(MainActivity.this, AddNewContact.class);
        startActivity(intent);
    }

    private void populateRecyclerView(String filter) {
        dbHelper = new MyDBHelper(this);
        adapter = new MyRecyclerAdapter(dbHelper.contactList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
