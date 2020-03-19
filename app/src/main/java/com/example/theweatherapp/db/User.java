package com.example.theweatherapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName="users")
public class User {

    public User(int id, String name, String unitsPref, String verbosityPref, ArrayList<Double> tempPref) {
        this.id = id;
        this.name = name;
        this.unitsPref = unitsPref;
        this.verbosityPref = verbosityPref;
        this.tempPref = tempPref;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "unitsPref")
    public String unitsPref;

    @ColumnInfo(name = "verbosityPref")
    public String verbosityPref;

    @ColumnInfo(name = "tempPref")
    public ArrayList<Double> tempPref;
}