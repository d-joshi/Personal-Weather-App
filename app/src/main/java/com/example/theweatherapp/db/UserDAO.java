package com.example.theweatherapp.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users ORDER BY rowid")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE rowid = :userId")
    User getById(int userId);

    @Insert
    void insert(User... users);

    @Update
    void update(User... user);

    @Delete
    void delete(User... user);

    @Query("DELETE FROM users WHERE rowid = :userId")
    void delete(int userId);
}
