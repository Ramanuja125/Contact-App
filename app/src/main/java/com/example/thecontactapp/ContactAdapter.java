package com.example.thecontactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    LayoutInflater mInflater;
    List<Contact> mContacts;
    //private static Clicklistener clicklistener;


    ContactAdapter(Context context){ mInflater=LayoutInflater.from(context);}

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        if(mContacts!=null){
        Contact contact = mContacts.get(position);
        holder.phoneTextView.setText(contact.getPhone());
        holder.nameTextView.setText(contact.getName());
    }
        else {
            holder.nameTextView.setText("No Name");
            holder.phoneTextView.setText("No Phone");
        }
    }

    void setContacts(List<Contact> contacts){
        mContacts=contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mContacts!=null)
            return mContacts.size();
        return 0;
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView phoneTextView;

        private ContactHolder(View view){
            super(view);
            nameTextView=view.findViewById(R.id.name);
            phoneTextView=view.findViewById(R.id.phone);
        }

    }

    public Contact getContactAtPosition(int position){
         return mContacts.get(position);
    }


}

