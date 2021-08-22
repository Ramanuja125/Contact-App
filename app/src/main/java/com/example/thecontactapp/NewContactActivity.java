package com.example.thecontactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

public class NewContactActivity extends AppCompatActivity  {
    public static final String EXTRA_REPLY =
            "com.example.android.TheContactApp.REPLY";
    public static final String EXTRA_REPLY_1 =
            "com.example.android.TheContactApp.REPLY";
    TextInputEditText name;
    TextInputEditText phone;
    TextInputEditText email;
    TextInputEditText age;
    String gender;
    TextInputEditText city;
    TextInputEditText college;
    ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        viewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        name = findViewById(R.id.name_text);
        phone = findViewById(R.id.phone_text);
        email=findViewById(R.id.email_text);
        age=findViewById(R.id.age_text);
        city=findViewById(R.id.city_text);
        college=findViewById(R.id.college_text);
        final RadioGroup radioGroup = findViewById(R.id.gender_radio_group);
        radioGroup.clearCheck();

        final RadioButton male = (RadioButton) radioGroup.findViewById(R.id.male_radio_button);
        RadioButton female = (RadioButton) male.findViewById(R.id.female_radio_button);

        // TODO: radio buttons not working


        Button save_button = findViewById(R.id.save_button);
        Log.e("abc",gender+" ");



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ids = radioGroup.getCheckedRadioButtonId();
                if(ids==male.getId())
                    gender="Male";
                else
                    gender="Female";

                Intent reply = new Intent();
                if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(phone.getText())){
                    setResult(RESULT_CANCELED,reply);
                }
                else {
                    //String[] contact = new String[]{name.getText().toString(),phone.getText().toString()};
                    //Bundle b = new Bundle();
                    //b.putStringArray("reply", contact);
                    //reply.putExtras(b);
                    reply.putExtra("name", name.getText().toString());
                    reply.putExtra("phone",phone.getText().toString());
                    reply.putExtra("email",email.getText().toString());
                    reply.putExtra("age",age.getText().toString());
                    reply.putExtra("gender",gender);
                    reply.putExtra("city",city.getText().toString());
                    reply.putExtra("college",college.getText().toString());
                    setResult(RESULT_OK, reply);
                    Toast.makeText(NewContactActivity.this, "Saving Contact",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
