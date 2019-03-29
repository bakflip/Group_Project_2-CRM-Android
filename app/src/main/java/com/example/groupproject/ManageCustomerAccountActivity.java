package com.example.groupproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Model.AllUsers;
import Model.Owner;

public class ManageCustomerAccountActivity extends AppCompatActivity {

    AllUsers allUsers;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer_account);

        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");

        Button chaCustpassword = findViewById(R.id.change_customer_password);
        //call changepassword activity on click.
        chaCustpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageCustomerAccountActivity.this, ChangeCustomerPasswordActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                //intent.putExtra("id",owner.getID());
                intent.putExtra("alluser",allUsers);
                startActivity(intent);
            }
        });

        Button delcusaccount = findViewById(R.id.delete_customer_account);
        //call delete account activity on click
        delcusaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageCustomerAccountActivity.this, DeleteCustomerAccountActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                //intent.putExtra("id",owner.getID());
                intent.putExtra("alluser",allUsers);
                startActivity(intent);
            }
        });


        //back to previous activity
        Button manCustdone = findViewById(R.id.done_customer);
        manCustdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("AllUsers", allUsers);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
