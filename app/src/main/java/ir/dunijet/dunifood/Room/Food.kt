package ir.dunijet.dunifood.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_food")

data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val txtsubject: String,
    val txtprice: String,
    val txtdistance: String,
    val txtcity: String,
   // @ColumnInfo(name = "url")
    val urlImage: String,
    val numofRating: Int,
    val rating: Float
    )