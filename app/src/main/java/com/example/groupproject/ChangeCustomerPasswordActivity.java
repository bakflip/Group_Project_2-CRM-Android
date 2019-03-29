package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import Model.AllUsers;

import static Controller.IO.writeToFile;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ChangeCustomerPasswordActivity extends AppCompatActivity {

    AllUsers allUsers;
    private EditText cuserID,opassword,npassword; // user input for signin
    private Button confirm; //button to change password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer_password);

        final Context context = this;

        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");

        cuserID = findViewById(R.id.cust_cuser_name);
        opassword = findViewById(R.id.cust_old_password);
        npassword = findViewById(R.id.cust_new_password);
        confirm = findViewById(R.id.cust_change_confrim);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accepting user string from edit textbox
                //Userid, oldpassword newpassword
                String sUserid = cuserID.getText().toString();
                String osPassword = opassword.getText().toString();
                String snPassword = npassword.getText().toString();
                //call changepassword function. If user password successfully change, return true, else false
                boolean isPasswordChange = allUsers.ChangeCpass(sUserid,osPassword,snPassword);
                //if user change password successfully
                if(isPasswordChange)
                {
                    //write the new password to the file
                    try {
                        writeToFile(context,allUsers);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                    //as user change password, application force user to log out and send the user to main activity to signing in.
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast.makeText(getBaseContext(),"Password Successfully Changed. Signing out...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Wrong Password.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
