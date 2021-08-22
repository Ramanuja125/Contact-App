package com.example.thecontactapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, exportSchema = false, version = 2)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static ContactRoomDatabase INSTANCE;

    public static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized ((ContactRoomDatabase.class)) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDatabase.class, "contact_list")
                            .addCallback(sRoomDatabase)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback sRoomDatabase = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDb(INSTANCE).execute();
        }
    };

    public static class PopulateDb extends AsyncTask<Void, Void, Void> {

        private final ContactDao mDao;
        Contact contact = new Contact(0L,"Rama", "9045358945","Rajesh@gmail.com","32","Male","California","Greendale");
        Contact con = new Contact(0L,"Rohan", "849298483","Prarthana@vvce.ac.in","21","Female","California","Greendale");

        PopulateDb(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mDao.getAnyContact().length < 1) {
                mDao.insert(contact);
                mDao.insert(con);

            }
            return null;
        }
    }
}


