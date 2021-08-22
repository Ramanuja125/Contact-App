package com.example.thecontactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class ViewContactActivity extends AppCompatActivity {

    TextView name ;
    TextView phone ;
    TextView email ;
    TextView age ;
    TextView gender ;
    TextView city ;
    TextView college ;

    String phoneNo;
    long idw;
    String nameContact;
    String emailC;
    String ageC;
    String  gebderC;
    String cityC;
    String collegeC;
    String idString;

    Contact contact;

    ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        viewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

         name = findViewById(R.id.contact_name);
         phone = findViewById(R.id.contact_phone);
         email = findViewById(R.id.contact_email);
         age = findViewById(R.id.contact_age);
         gender = findViewById(R.id.contact_gender);
         city = findViewById(R.id.contact_city);
         college = findViewById(R.id.contact_college);

        Intent i = getIntent();
        name.setText(i.getStringExtra("name"));
        phone.setText(i.getStringExtra("phone"));
        email.setText("Email : "+i.getStringExtra("email"));
        age.setText("Age : "+i.getStringExtra("age"));
        gender.setText("Gender : "+i.getStringExtra("gender"));
        city.setText("City : "+i.getStringExtra("city"));
        college.setText("College : "+i.getStringExtra("college"));
        idw=Long.parseLong(i.getStringExtra("id"));

        nameContact=i.getStringExtra("name");
        phoneNo =i.getStringExtra("phone");
        emailC=i.getStringExtra("email");
        ageC=i.getStringExtra("age");
        gebderC=i.getStringExtra("gender");
        cityC=i.getStringExtra("city");
        collegeC=i.getStringExtra("college");
        idString=i.getStringExtra("id");

        contact = new Contact(idw,nameContact,phoneNo,emailC,ageC,gebderC,cityC,collegeC);

        ImageView call = findViewById(R.id.call_button);
        ImageView chat = findViewById(R.id.chat_button);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+phoneNo));
                startActivity(call);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:"+phoneNo);
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_contact_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.delete:
                viewModel.deleteByName(nameContact);
                Toast.makeText(ViewContactActivity.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                //Snackbar.make(,nameContact+"Contact Deleted",Snackbar.LENGTH_LONG).setAction("Undo", viewModel.insert(new Contact(0L,nameContact,phoneNo,emailC,ageC,gebderC,cityC,collegeC));)
                finish();
                return true;

            case R.id.edit:
                Intent reply = new Intent(ViewContactActivity.this,EditContactActivity.class);
                reply.putExtra("name", nameContact);
                reply.putExtra("id",idString);
                reply.putExtra("phone",phoneNo);
                reply.putExtra("email",emailC);
                reply.putExtra("age",ageC);
                reply.putExtra("gender",gebderC);
                reply.putExtra("city",cityC);
                reply.putExtra("college",collegeC);
                startActivity(reply);
                finish();
                return true;

            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,nameContact);
                intent.putExtra(Intent.EXTRA_TEXT,nameContact+" "+phoneNo);
                startActivity(Intent.createChooser(intent,"Share Contact"));
                return true;
        }
        return false;
    }
}
