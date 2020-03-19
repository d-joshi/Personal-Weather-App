package com.example.theweatherapp.db;

import android.os.AsyncTask;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;


@Database( entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomdb extends RoomDatabase {

    public interface UserListener {
        void onUserReturned(User user);

        void onUserReturned();
    }

    public abstract UserDAO userDAO(); // Singleton Pattern

    private static UserRoomdb INSTANCE;

    public static synchronized UserRoomdb getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UserRoomdb.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserRoomdb.class, "User_databse")
                    .addCallback(createUserDatabaseCallback)
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createUserDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createUserTable();
        }
    };

    private static void createUserTable(){
        insert(new User(0, "John Doe", "F", "brief", new ArrayList<Double>(5)));
    }

    public static void getUser(int id, final UserListener listener){
        new AsyncTask<Integer, Void, User>(){
            protected User doInBackground(Integer... ids){
                return INSTANCE.userDAO().getById(ids[0]);
            }

            protected void onPostExecute(User user){
                super.onPostExecute(user);
                listener.onUserReturned();
            }
        }.execute(id);
    }

    public static void insert(User user) {
        new AsyncTask<User, Void, Void> () {
            protected Void doInBackground(User... users) {
                INSTANCE.userDAO().insert(users);
                return null;
            }
        }.execute(user);
    }

    public static void delete(int userId) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.userDAO().delete(ids[0]);
                return null;
            }
        }.execute(userId);
    }


    public static void update(User user) {
        new AsyncTask<User, Void, Void> () {
            protected Void doInBackground(User... users) {
                INSTANCE.userDAO().update(users);
                return null;
            }
        }.execute(user);
    }
}
