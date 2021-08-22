package com.example.thecontactapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao  {
    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * from contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("DELETE from contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table LIMIT 1")
    Contact[] getAnyContact();

    @Query("DELETE FROM contact_table WHERE name = :name")
    void deleteContact(String name);

    @Query("UPDATE contact_table SET name = :name")
    void updateContact(String name);



    @Query("SELECT * FROM contact_table WHERE name = :name")
    Contact findByName(String name);

    @Update
    void update(Contact contact);
}
