package com.mikeshehadeh.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewContact extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mLastnameEditText;
    private EditText mphoneEditText;
    private Button mAddBtn;
    private MyDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        mNameEditText = (EditText) findViewById(R.id.contactLastname);
        mLastnameEditText = (EditText) findViewById(R.id.contactName);
        mphoneEditText = (EditText) findViewById(R.id.contactPhone);
        mAddBtn = (Button)findViewById(R.id.addNewContactButton);
        mAddBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveContact();
            }
        });
    }

    private void saveContact() {
        String name = mNameEditText.getText().toString().trim();
        String lastname = mLastnameEditText.getText().toString().trim();
        String phone = mphoneEditText.getText().toString().trim();
        dbHelper = new MyDBHelper(this);
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
        Contact c = new Contact(name,lastname,phone);
        dbHelper.saveNewContact(c, this);
        finish();
    }
}
