package com.example.theweatherapp.db;

import android.os.AsyncTask;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database( entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomdb extends RoomDatabase {

    public interface UserListener {
        void onUserreturn(User user);

    }

    public abstract UserDao userDao(); // Singleton Pattern

    {
    }}}


    public static void insert (User user) {
    new AsyncTask <user, void, void>() {
        protected void doInBackground(User... user) {
            INSTANCE.userDao.insert(user);
        }.execute(user);


    }
