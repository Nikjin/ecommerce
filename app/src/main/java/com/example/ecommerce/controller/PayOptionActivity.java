package com.example.ecommerce.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ecommerce.R;

public class PayOptionActivity extends AppCompatActivity {

    RadioGroup radiogroup;
    RadioButton rb1,rb2;
    String radio2 ,radio1,selectoption;
    Button done;
    int final_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_option);

        Intent i =getIntent();
        final_total = i.getIntExtra("final_total", 0);
        Log.d("final_total", Integer.toString(final_total));


        rb1 = (RadioButton)findViewById(R.id.radiocard);
        rb2 = (RadioButton)findViewById(R.id.radiocod);
        done=(Button)findViewById(R.id.btnpayoption);
        radiogroup = (RadioGroup)findViewById(R.id.radiogrouppay);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rd=(RadioButton)group.findViewById(checkedId);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb2.isChecked())
                {
                    selectoption = rb2.getText().toString();
                    Log.d("selectoption in p= ", selectoption);
                    Intent i = new Intent(getApplicationContext(),ShippingActivity.class);
                    i.putExtra("selectoption", selectoption);
                    i.putExtra("final_total", final_total);

                    startActivity(i);


                }else{
                    if(rb1.isChecked()){
                        selectoption = rb1.getText().toString();
                        Intent i1 = new Intent(getApplicationContext(),PaymentActivity.class);
                        i1.putExtra("selectoption", selectoption);
                        i1.putExtra("final_total", final_total);
                        Log.d("selectoption = ", selectoption);
                        startActivity(i1);
                    }
                }
            }
        });




    }
}
