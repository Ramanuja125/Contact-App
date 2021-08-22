package com.example.thecontactapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private LiveData<List<Contact>> mAllContact;
    private ContactRepository mContactRepository;

    public ContactViewModel(Application application){
        super(application);
        mContactRepository = new ContactRepository(application);
        mAllContact = mContactRepository.getAllContacts();
    }

    LiveData<List<Contact>> getAllContact()  { return mAllContact; }

    public void insert(Contact contact){ mContactRepository.insert(contact);}

    public void deleteAll(){ mContactRepository.deleteAll();}

    public void delete(Contact contact){ mContactRepository.insert(contact);}

    public void deleteByName(String name){ mContactRepository.deleteByName(name);}

    public void update(Contact contact){ mContactRepository.update(contact);}

    public void contactUpdate(String name){ mContactRepository.update(name);}
}
