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

import Controller.SignIn;
import Controller.ChangePassword;
import Model.AllUsers;

import static Controller.IO.readFromFile;
import static Controller.IO.writeToFile;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class ChangeOwnerPasswordActivity extends AppCompatActivity {

    AllUsers allUsers;
    private EditText cuserID,opassword,npassword; // user input for signin
    private Button confirm; //button to change password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final Context context = this;

        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");

        cuserID = findViewById(R.id.cuser_name);
        opassword = findViewById(R.id.old_password);
        npassword = findViewById(R.id.new_password);
        confirm = findViewById(R.id.change_confrim);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accepting user string from edit textbox
                //Userid, oldpassword newpassword
                String sUserid = cuserID.getText().toString();
                String osPassword = opassword.getText().toString();
                String snPassword = npassword.getText().toString();
                //call changepassword function. If user password successfully change, return true, else false
                boolean isPasswordChange = allUsers.ChangeOpass(sUserid,osPassword,snPassword);
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
                    //Intent i = new Intent(getBaseContext(), MainActivity.class);
                    //i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(i);
                    intent.putExtra("AllUsers", allUsers);
                    setResult(RESULT_OK, intent);
                    finish();

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
