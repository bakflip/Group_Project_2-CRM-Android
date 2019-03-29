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

public class DeleteCustomerAccountActivity extends AppCompatActivity {

    AllUsers allUsers;
    private EditText dcuserID,dcpassword; // user input for signin
    private Button deletecusconfirm; //button to delete password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer_account);

        final Context context = this;
        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");

        dcuserID = findViewById(R.id.delete_customer_user_name);
        dcpassword = findViewById(R.id.delete_customer_password);
        deletecusconfirm = findViewById(R.id.delete_customer_confrim);

        deletecusconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting string input from edit text
                String dsUserid = dcuserID.getText().toString();
                String dosPassword = dcpassword.getText().toString();

                //check user account is successfully deleted
                boolean isAccountDeleted = allUsers.DeleteCaccount(dsUserid,dosPassword);
                //if yes
                if(isAccountDeleted) {
                    //write the updated information to the file
                    try {
                        writeToFile(context,allUsers);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                    //as user is no longer have account, application force user to log out and send the user to main activity.
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast.makeText(getBaseContext(),"Account Successfully deleted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //wrong password so account is not deleted.
                    Toast.makeText(getBaseContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
