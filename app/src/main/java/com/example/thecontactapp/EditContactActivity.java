package com.example.thecontactapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

public class EditContactActivity extends AppCompatActivity  {
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

    String phoneNo;
    Long idnow;
    String nameContact;
    String emailC;
    String ageC;
    String  gebderC="male";
    String cityC;
    String collegeC;
    String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        viewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        name = findViewById(R.id.name_text);
        phone = findViewById(R.id.phone_text);
        email=findViewById(R.id.email_text);
        age=findViewById(R.id.age_text);
        city=findViewById(R.id.city_text);
        college=findViewById(R.id.college_text);
        final RadioGroup radioGroup = findViewById(R.id.gender_radio_group);
        final RadioButton male = (RadioButton) radioGroup.findViewById(R.id.male_radio_button);
        RadioButton female = (RadioButton) male.findViewById(R.id.female_radio_button);

        final Intent i =getIntent();
        nameContact=i.getStringExtra("name");
        phoneNo =i.getStringExtra("phone");
        emailC=i.getStringExtra("email");
        ageC=i.getStringExtra("age");
        gebderC=i.getStringExtra("gender");
        cityC=i.getStringExtra("city");
        collegeC=i.getStringExtra("college");
        idString=i.getStringExtra("id");
        idnow=Long.parseLong(idString);

        if(gebderC.equals("Male")||gebderC.equals("male"))
            radioGroup.check(R.id.male_radio_button);
        else
            radioGroup.check(R.id.female_radio_button);


        //filling fields
        name.setText(nameContact);
        phone.setText(phoneNo);
        email.setText(emailC);
        age.setText(ageC);
        city.setText(cityC);
        college.setText(collegeC);
        /*if(gebderC.equalsIgnoreCase("Male")||gebderC.equalsIgnoreCase("male"))
            male.setChecked(true);
        else
            female.setChecked(true);*/

        Button save_button = findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ids = radioGroup.getCheckedRadioButtonId();
                if(ids==male.getId())
                    gender="Male";
                else
                    gender="Female";
                //viewModel.deleteByName(nameContact);
                Intent reply = new Intent(EditContactActivity.this,MainActivity.class);
                if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(phone.getText())){
                   Toast.makeText(EditContactActivity.this,"Name or Phone is Empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    Contact contact = new Contact(idnow,name.getText().toString(),phone.getText().toString(),email.getText().toString(),age.getText().toString(),gender,city.getText().toString(),college.getText().toString());

                    viewModel.update(contact);
                    Toast.makeText(EditContactActivity.this,"Updating",Toast.LENGTH_SHORT).show();
                    //Bundle b = new Bundle();
                    //b.putStringArray("reply", contact);
                    //reply.putExtras(b);
                    /*reply.putExtra("name", name.getText().toString());
                    reply.putExtra("phone",phone.getText().toString());
                    reply.putExtra("email",email.getText().toString());
                    reply.putExtra("age",age.getText().toString());
                    reply.putExtra("gender",gender);
                    reply.putExtra("city",city.getText().toString());
                    reply.putExtra("college",college.getText().toString());
                    setResult(RESULT_OK, reply);*/
                    //startActivity(reply);
                }
                finish();
            }
        });

    }
}
