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
import Model.AllUsers;
import Model.Customer;
import Model.Owner;

import static Controller.IO.readFromFile;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 2;
    private static final int ZERO_ACTIVITY_REQUEST_CODE = 0;
    private Button butSignIn;
    private Button butSignUp;
    private AllUsers allUsers;
    private EditText userID,password; // user input for signin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the list of users (load from file)
        Context context = this; //Context to access file system
        try {
            allUsers = readFromFile(context);
            Toast.makeText(MainActivity.this, "Loading data...", Toast.LENGTH_SHORT).show();

        } catch (ClassNotFoundException | IOException o) {
            allUsers = new AllUsers();
        }

        // get data from edit text
        userID = findViewById(R.id.user_name);
        password = findViewById(R.id.password);

        //Set up the buttons
        //Sign In
        butSignIn = findViewById(R.id.sign_in);
        butSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set up the userid and password as the string that get from edit text
                String sUserid = userID.getText().toString();
                String sPassword = password.getText().toString();

                // check account exist and check sing in as owner or customer
                Owner owner = allUsers.getOwnerBasedOnID(sUserid);
                Customer customer = allUsers.getCustomerBasedOnID(sUserid);

                if (owner != null && customer == null){
                    // sign in as owner
                    boolean isSignin = SignIn.Signin(sUserid,sPassword,allUsers);
                    if (isSignin == true){ // if isSignin is false, wrong password
                        // if sucessfully sing in, go to owner main menu
                        //allUsers.ChangeOpass(sUserid,sPassword,"zaw");
                        Intent intent = new Intent(MainActivity.this, OwnerMainMenuActivity.class);
                        intent.putExtra("ownerID",owner.getID());
                        intent.putExtra("alluser",allUsers);
                        startActivity(intent);
                    }
                    // in later, i like to show error msg when user put wrong password

                } else if (customer != null && owner == null){
                    // sign in as customer
                    boolean isSignin = SignIn.Signin(sUserid,sPassword,allUsers);
                    if (isSignin) {
                        // if sucessfully sing in, go to owner main menu
                        Intent intent = new Intent(MainActivity.this, CustomerMainMenuActivity.class);
                        intent.putExtra("customer",customer);
                        intent.putExtra("alluser",allUsers);
                        startActivity(intent);
                    }
                    // in later, i like to show error msg when user put wrong password
                }
                else {
                    Toast.makeText(getBaseContext(),"wrong user name or haven't sing up yet!",Toast.LENGTH_SHORT).show();

                }

            }
        });
        //Sign Up
        butSignUp = findViewById(R.id.sign_up);
        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                intent.putExtra("AllUsers", allUsers);
                startActivityForResult(intent, ZERO_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ZERO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                allUsers = (AllUsers)data.getSerializableExtra("AllUsers");
            }
        }
    }
}
