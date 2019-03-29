package com.example.groupproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Model.AllUsers;
import Model.Owner;

public class ManageOwnerAccountActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    AllUsers allUsers;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_owner_account);
        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");
        Button chaPassword = findViewById(R.id.change_password);

        //call changepassword activity on click.
        chaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageOwnerAccountActivity.this, ChangeOwnerPasswordActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                intent.putExtra("alluser",allUsers);
                startActivity(intent);
                //startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        Button delaccount = findViewById(R.id.delete_account);
        //call delete account activity on click
        delaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageOwnerAccountActivity.this, DeleteOwnerAccountActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                //intent.putExtra("id",owner.getID());
                intent.putExtra("alluser",allUsers);
                startActivity(intent);
            }
        });


        //back to previous activity
        Button manDone = findViewById(R.id.done);
        manDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("AllUsers", allUsers);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                allUsers = (AllUsers)data.getSerializableExtra("AllUsers");
            }
        }
    }
}
