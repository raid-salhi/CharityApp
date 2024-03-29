package com.example.charityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private TextView LoginText;
    private AutoCompleteTextView bloodType;
    private AutoCompleteTextView wilaya;
    private Button signUp;

    private DatabaseReference reference;
    private FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


       bloodType = findViewById(R.id.blood_group);
       wilaya = findViewById(R.id.wilaya);
        LoginText = findViewById(R.id.login_text);
        firstname = findViewById(R.id.first_name_edit_text);
        lastname = findViewById(R.id.last_name_edit_text);
        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        signUp = findViewById(R.id.sign_up_button);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

//        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                bloodType = parent.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        Wilayas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                wilaya = parent.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,R.array.blood_groups, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        bloodGroup.setAdapter(adapter);
//
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
//                this,R.array.wilayas, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Wilayas.setAdapter(adapter1);

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname = firstname.getText().toString();
                String Lastname = lastname.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Wilaya = wilaya.getText().toString();
                String BloodType = bloodType.getText().toString();
                if(TextUtils.isEmpty(Firstname) || TextUtils.isEmpty(Lastname) || TextUtils.isEmpty(Email)
                        || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Wilaya) || TextUtils.isEmpty(BloodType)){
                    Toast.makeText(SignUp.this, "Empty credentials !", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 6) {
                    Toast.makeText(SignUp.this, "Too short password", Toast.LENGTH_SHORT).show();
                }else {
                    registeruser(Firstname, Lastname, Email, Password, Wilaya,BloodType);
                }
            }
        });
    }
    private void registeruser(String Firstname,String Lastname, String Email,String Password,String wilaya, String bloodType){
        progressDialog.setMessage("Please Wait!");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String , Object> map = new HashMap<>();
                map.put("firstName" , Firstname);
                map.put("lastName" , Lastname);
                map.put("email" , Email);
                map.put("wilaya" , wilaya);
                map.put("bloodGroup" , bloodType);
                map.put("userId" , auth.getCurrentUser().getUid());
                reference.child("Users").child(auth.getCurrentUser().getUid()).setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "you have been registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });


    }
}