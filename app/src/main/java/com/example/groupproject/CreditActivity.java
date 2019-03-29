package com.example.groupproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import Model.AllUsers;
import Model.Customer;
import Model.Owner;

import static Controller.IO.writeToFile;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CreditActivity extends AppCompatActivity {
    AllUsers allUsers;
    Customer customer;
    Owner owner;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("alluser");
        String Userid = (String) intent.getSerializableExtra("id");
        final Context context = this;

        owner = allUsers.getOwnerBasedOnID(Userid);
        customer = allUsers.getCustomerBasedOnID(Userid);
        String m_Text;
        final EditText result;

        TextView textView = (TextView) findViewById(R.id.credit_num);
        if(owner != null)
            textView.setText(Double.toString(owner.getCredit()));
        else
            textView.setText(Double.toString(customer.getCredit()));

        //beginning of deposit
        Button deposit = findViewById(R.id.deposit_but);
        result = (EditText) findViewById(R.id.editTextResult);

        deposit.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.popup, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            result.setText(userInput.getText());
                                            amount = Double.parseDouble(result.getText().toString());
                                            if(owner!= null)
                                            {
                                                owner.addCredit(amount);
                                                TextView textView = (TextView) findViewById(R.id.credit_num);
                                                textView.setText(Double.toString(owner.getCredit()));

                                                textView = (TextView) findViewById(R.id.owner_credits);
                                                textView.setText(Double.toString(owner.getCredit()));

                                            }
                                            else
                                            {
                                                customer.addCredit(amount);
                                                TextView textView = (TextView) findViewById(R.id.credit_num);
                                                textView.setText(Double.toString(customer.getCredit()));

                                                textView = (TextView) findViewById(R.id.customer_credits);
                                                textView.setText(Double.toString(customer.getCredit()));

                                            }


                                            try {
                                                writeToFile(context,allUsers);
                                            } catch (IOException e) {
                                                e.getStackTrace();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();


            }
        }); //end of deposit

        //beginning of withdraw
        Button withdraw = findViewById(R.id.withdraw_but);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result.setText(userInput.getText());
                                        amount = Double.parseDouble(result.getText().toString());
                                        if(owner!= null)
                                        {
                                            owner.subCredit(amount);
                                            TextView textView = (TextView) findViewById(R.id.credit_num);
                                            textView.setText(Double.toString(owner.getCredit()));

                                            textView = (TextView) findViewById(R.id.owner_credits);
                                            textView.setText(Double.toString(owner.getCredit()));
                                        }
                                        else
                                        {
                                            customer.subCredit(amount);
                                            TextView textView = (TextView) findViewById(R.id.credit_num);
                                            textView.setText(Double.toString(customer.getCredit()));

                                            textView = (TextView) findViewById(R.id.customer_credits);
                                            textView.setText(Double.toString(customer.getCredit()));
                                        }
                                        try {
                                            writeToFile(context,allUsers);
                                        } catch (IOException e) {
                                            e.getStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


        }
        }); //end of withdraw

        Button send = findViewById(R.id.send_but);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); //end of send for owner

        //cancel button
        Button butCancel = findViewById(R.id.return_but);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send data back
                intent.putExtra("AllUsers", allUsers);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
