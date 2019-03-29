package com.example.groupproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        final Intent intent = getIntent();
        boolean dName = intent.getBooleanExtra("dName", false);
        boolean dCredit = intent.getBooleanExtra("dCredit", false);

        //Set the checkboxes to the values indicated by the booleans
        final CheckBox checkName = findViewById(R.id.check_name);
        checkName.setChecked(dName);
        CheckBox checkCredit = findViewById(R.id.check_credit);
        checkCredit.setChecked(dCredit);

        // Done Button
        Button butDone = findViewById(R.id.button_done);
        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the values based on the checkboxes
                intent.putExtra("dName", checkName.isChecked());
                intent.putExtra("dCredit", checkName.isChecked());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
