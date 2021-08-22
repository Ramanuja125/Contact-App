package com.example.thecontactapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static ContactViewModel mContactViewModel;
    private int NEW_CONTACT_ACTIVITY = 1;
    private int VIEW_CONTACT_ACTIVITY = 1;
    RecyclerView recyclerView;

    Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        final ContactAdapter adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        mContactViewModel.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY);
            }
        });
        CoordinatorLayout layout = findViewById(R.id.main_view);
        final Snackbar snackbar = Snackbar.make(layout,"Contact Deleted", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContactViewModel.insert(contact);
                    }
                });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        contact = adapter.getContactAtPosition(position);
                        mContactViewModel.deleteByName(contact.getName());
                        snackbar.show();
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerClickListener(this, recyclerView, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        //Toast.makeText(MainActivity.this,"Clicked success",Toast.LENGTH_SHORT).show();
                        Contact contact = adapter.getContactAtPosition(position);
                        Intent reply = new Intent(MainActivity.this, ViewContactActivity.class);
                        reply.putExtra("name", contact.getName());
                        String idS = String.valueOf(contact.getId());
                        reply.putExtra("id", idS);
                        reply.putExtra("phone", contact.getPhone());
                        reply.putExtra("email", contact.getEmail());
                        reply.putExtra("age", contact.getAge());
                        reply.putExtra("gender", contact.getGender());
                        reply.putExtra("city", contact.getCity());
                        reply.putExtra("college", contact.getCollege());

                        startActivity(reply);
                    }
                })
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all) {
            mContactViewModel.deleteAll();
            Toast.makeText(this, "Clearing the contacts", Toast.LENGTH_LONG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY && resultCode == RESULT_OK) {
            String nameIntent = data.getStringExtra("name");
            String phoneIntent = data.getStringExtra("phone");

            Contact contact = new Contact(0L, nameIntent, phoneIntent, data.getStringExtra("email"), data.getStringExtra("age"), data.getStringExtra("gender"), data.getStringExtra("city"), data.getStringExtra("college"));
            mContactViewModel.insert(contact);
        } else {
            Toast.makeText(this, getString(R.string.not_saved), Toast.LENGTH_LONG).show();
        }
    }
}
