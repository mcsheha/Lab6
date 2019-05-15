package com.mikeshehadeh.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mLastnameEditText;
    private EditText mphoneEditText;
    private Button mUpdateBtn;
    private MyDBHelper dbHelper;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        mNameEditText = (EditText) findViewById(R.id.contactLastname);
        mLastnameEditText = (EditText) findViewById(R.id.contactName);
        mphoneEditText = (EditText) findViewById(R.id.contactPhone);
        mUpdateBtn = (Button)findViewById(R.id.updateButton);
        dbHelper = new MyDBHelper(this);
        try {
            contactId = getIntent().getLongExtra("CONTACT_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contact c = dbHelper.getContact(contactId);
        mNameEditText.setText(c.getName());
        mLastnameEditText.setText(c.getLastname());
        mphoneEditText.setText(c.getPhone_number());
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateContact();
            }
        });

    }

    private void updateContact() {
        String name = mNameEditText.getText().toString().trim();
        String lastname = mLastnameEditText.getText().toString().trim();
        String phone = mphoneEditText.getText().toString().trim();
        if(name.isEmpty()) {
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastname.isEmpty()) {
            Toast.makeText(this, "You must enter a last name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(phone.isEmpty()) {
            Toast.makeText(this, "You must enter a phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        Contact updatedContact = new Contact(name, lastname, phone);
        dbHelper.updateContact(contactId, this, updatedContact);
        finish();
    }
}
