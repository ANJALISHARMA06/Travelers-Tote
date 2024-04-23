package com.example.travelerstote.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.travelerstote.Models.Items;

import java.util.List;

/** @noinspection ALL*/
@Dao
public interface ItemsDao {

    @Insert(onConflict = REPLACE)
    void saveItem(Items items);

    @Query("SELECT * FROM items WHERE category=:category ORDER BY ID ASC")
    List<Items> getAll(String category);

    @Delete
    void delete(Items items);

    @Query("UPDATE items SET checked=:checked WHERE ID=:id")
    void checkUncheck(int id, boolean checked);

    @Query("SELECT COUNT(*) FROM items")
    Integer getItemsCount();

    @Query("DELETE FROM items WHERE addedby=:addedBy")
    Integer deleteAllSystemItems(String addedBy);

    @Query("DELETE FROM items WHERE category=:category")
    Integer deleteAllByCategory(String category);

    @Query("DELETE FROM items WHERE category=:category AND addedby=:addedBy")
    Integer deleteAllByCategoryAndAddedBy(String category, String addedBy);

    @Query("SELECT * FROM items WHERE checked=:checked ORDER BY ID ASC")
    List<Items> getAllSelected(Boolean checked);
}

