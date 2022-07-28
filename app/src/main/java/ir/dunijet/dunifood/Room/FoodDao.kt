package ir.dunijet.dunifood.Room

import androidx.room.*


@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertorupdate(food: Food)

    // @Insert
    // fun Insertfood(food: Food)

      @Insert
     fun insertAllfoods(Data:List<Food>)

    //@Update
    // fun Updatefood(food: Food)

    // برای برگرداندن غذا ها است
    @Query("SELECT * From table_food")
    fun getAllfood(): List<Food>

    @Delete
    fun Deletefoods(food: Food)

    //برای پاک کردن یک لیست استفاده می شود
    @Query("SELECT * From table_food")
    fun DeleteAllfoods(): List<Food>

    //برای عملیات سرچ استفاده می شود
    @Query("SELECT * From table_food WHERE txtsubject LIKE '%' || :searching || '%'")
    fun searchfood(searching: String): List<Food>

}