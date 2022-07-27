package com.example.navdrawerlist.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE uid =:userID")
    User getUser(int userID);

    @Insert
    void insertUser(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}

