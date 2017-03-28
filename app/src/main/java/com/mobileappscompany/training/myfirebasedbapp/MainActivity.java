package com.mobileappscompany.training.myfirebasedbapp;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MY_JSON_NODE = "myPlace";
    public static final String CONTACTS_NODE = "Contacts";
    private EditText editName;
    private EditText editPhone;
    private Button buttonWrite;
    private Button buttonListen;
    private Button buttonListenPojo;
    private TextView textView;
    private List<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        buttonWrite = (Button) findViewById(R.id.buttonWrite);
        buttonListen = (Button) findViewById(R.id.buttonListen);
        buttonListenPojo = (Button) findViewById(R.id.buttonListenPojo);
        textView = (TextView) findViewById(R.id.text);

        people = new ArrayList<>();

        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });

        buttonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenData();
            }
        });

        buttonListenPojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenDataWithPojo();
            }
        });
    }

    private void writeData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(MY_JSON_NODE);
        databaseReference.setValue(editName.getText().toString());
    }

    private void listenData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(CONTACTS_NODE);

        databaseReference.child("name").setValue(editName.getText().toString());
        databaseReference.child("phone").setValue(editPhone.getText().toString());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameString = dataSnapshot.child("name").getValue(String.class);
                String phoneString = dataSnapshot.child("phone").getValue(String.class);

                textView.setText(nameString + " - " + phoneString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }

    private void listenDataWithPojo() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(CONTACTS_NODE);

//        databaseReference.child("name").setValue(editName.getText().toString());
//        databaseReference.child("phone").setValue(editPhone.getText().toString());
        Person person = new Person();
        person.setName(editName.getText().toString());
        person.setPhone(editPhone.getText().toString());

        String keyString = databaseReference.push().getKey();
        person.setKey(keyString);
        databaseReference.child(keyString).setValue(person);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder sb = new StringBuilder();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Person person = ds.getValue(Person.class);
                    sb.append(person.getName() + " - " + person.getPhone() + "\n");
                    people.add(person);
                }

                textView.setText(sb.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
