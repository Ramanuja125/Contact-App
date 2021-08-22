package com.example.thecontactapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final String NAME = "name";

    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert(final Contact contact) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.insert(contact);
            }
        });
    }

    public void delete(final Contact contact) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.delete(contact);
            }
        });
    }

    public void deleteByName(final String name) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.deleteContact(name);
            }
        });
    }

    public void update(final Contact contact){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.update(contact);
            }
        });
    }

    public void update(final String name){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.updateContact(name);
            }
        });
    }




    public void deleteAll() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mContactDao.deleteAll();
            }
        });
    }

    public class findContactAsync extends AsyncTask<String, Void, Contact> {
        private String name;
        private ContactDao dao;
        private ContactRepository repository;

        findContactAsync(ContactDao dao, String name) {
            this.dao = dao;
            this.name = name;
        }

        @Override
        protected Contact doInBackground(String... strings) {

            Contact contact = dao.findByName(strings[0]);
            return contact;
        }
    }
}
