package com.example.ecommerce.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.R;

public class ThankYouActivity extends AppCompatActivity {
    TextView bye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        bye=(TextView)findViewById(R.id.bye);

        bye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),CategoryActivity.class);
                startActivity(i);
            }
        });
    }
}
