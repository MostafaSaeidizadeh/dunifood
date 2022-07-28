package ir.dunijet.dunifood

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.dunijet.dunifood.Room.Food
import ir.dunijet.dunifood.Room.FoodDao
import ir.dunijet.dunifood.Room.MyDatabase
import ir.dunijet.dunifood.databinding.ActivityMainBinding
import ir.dunijet.dunifood.databinding.DialogAddNewItemBinding
import ir.dunijet.dunifood.databinding.DialogDeleteItemBinding
import ir.dunijet.dunifood.databinding.DialogUpadateItemBinding

const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var myAdapter: FoodAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var foodDao: FoodDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = MyDatabase.getDatabase(this).foodDao
        val sharedPreferences = getSharedPreferences("dunifood", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run", true)) {
            firstRun()

            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }
        showAllData()
        binding.btnRemoveAllFoods.setOnClickListener {
            removeAllData()
        }


//        val foodListe = arrayListOf<Food>(
//            Food(
//                "Hamburger",
//                "15",
//                "3",
//                "Isfahan, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
//                20,
//                4.5f
//            ),
//            Food(
//                "Grilled fish",
//                "20",
//                "2.1",
//                "Tehran, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
//                10,
//                4f
//            ),
//            Food(
//                "Lasania",
//                "40",
//                "1.4",
//                "Isfahan, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
//                30,
//                2f
//            ),
//            Food(
//                "pizza",
//                "10",
//                "2.5",
//                "Zahedan, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
//                80,
//                1.5f
//            ),
//            Food(
//                "Sushi",
//                "20",
//                "3.2",
//                "Mashhad, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
//                200,
//                3f
//            ),
//            Food(
//                "Roasted Fish",
//                "40",
//                "3.7",
//                "Jolfa, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
//                50,
//                3.5f
//            ),
//            Food(
//                "Fried chicken",
//                "70",
//                "3.5",
//                "NewYork, USA",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
//                70,
//                2.5f
//            ),
//            Food(
//                "Vegetable salad",
//                "12",
//                "3.6",
//                "Berlin, Germany",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
//                40,
//                4.5f
//            ),
//            Food(
//                "Grilled chicken",
//                "10",
//                "3.7",
//                "Beijing, China",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
//                15,
//                5f
//            ),
//            Food(
//                "Baryooni",
//                "16",
//                "10",
//                "Ilam, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
//                28,
//                4.5f
//            ),
//            Food(
//                "Ghorme Sabzi",
//                "11.5",
//                "7.5",
//                "Karaj, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
//                27,
//                5f
//            ),
//            Food(
//                "Rice",
//                "12.5",
//                "2.4",
//                "Shiraz, Iran",
//                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
//                35,
//                2.5f
//            )
//        )

//        myAdapter = FoodAdapter(foodListe.clone() as ArrayList<Food>, this)
//
//        binding.recyclerMain.adapter = myAdapter
//        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//
//
        binding.btnAddNewFood.setOnClickListener {

            addNewfood()
        }


        binding.edtsearch.addTextChangedListener { editTextInput ->

            searchonDatabase(editTextInput!!.toString())

        }

    }

    private fun addNewfood() {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {
            if (
                dialogBinding.dialogEdtNameFood.length() > 0 &&
                dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                dialogBinding.dialogEdtFoodCity.length() > 0 &&
                dialogBinding.dialogEdtFoodDistance.length() > 0
            ) {
                val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                val txtRatingNumber: Int = (1..150).random()
                val ratingBarstar: Float = (1..5).random().toFloat()


                val randomForURL = (1 until 12).random()
                val UrlPic = BASE_URL_IMAGE + randomForURL.toShort() + ".jpg"


                val newFood = Food(
                    txtsubject = txtName,
                    txtprice = txtPrice,
                    txtdistance = txtDistance,
                    txtcity = txtCity,
                    urlImage = UrlPic,
                    numofRating = txtRatingNumber,
                    rating = ratingBarstar
                )
                myAdapter.addFood(newFood)
                foodDao.insertorupdate(newFood)
                dialog.dismiss()
                binding.recyclerMain.scrollToPosition(0)


            } else {
                Toast.makeText(this, " لطفا اطلاعات خود را تکمیل کنید", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun removeAllData() {
        foodDao.DeleteAllfoods()
        showAllData()

    }

    private fun firstRun() {

        val foodListe = arrayListOf<Food>(
            Food(
                txtsubject = "Hamburger",
                txtprice = "15",
                txtdistance = "3",
                txtcity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numofRating = 20,
                rating = 4.5f,
            ),
            Food(
                txtsubject = "Grilled fish",
                txtprice = "20",
                txtdistance = "2.1",
                txtcity = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numofRating = 10,
                rating = 4f
            ),
            Food(
                txtsubject = "Lasania",
                txtprice = "40",
                txtdistance = "1.4",
                txtcity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numofRating = 30,
                rating = 2f
            ),
            Food(
                txtsubject = "pizza",
                txtprice = "10",
                txtdistance = "2.5",
                txtcity = "Zahedan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numofRating = 80,
                rating = 1.5f
            ),
            Food(
                txtsubject = "Sushi",
                txtprice = "20",
                txtdistance = "3.2",
                txtcity = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numofRating = 200,
                rating = 3f
            ),
            Food(
                txtsubject = "Roasted Fish",
                txtprice = "40",
                txtdistance = "3.7",
                txtcity = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numofRating = 50,
                rating = 3.5f
            ),
            Food(
                txtsubject = "Fried chicken",
                txtprice = "70",
                txtdistance = "3.5",
                txtcity = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numofRating = 70,
                rating = 2.5f
            ),
            Food(
                txtsubject = "Vegetable salad",
                txtprice = "12",
                txtdistance = "3.6",
                txtcity = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numofRating = 40,
                rating = 4.5f
            ),
            Food(
                txtsubject = "Grilled chicken",
                txtprice = "10",
                txtdistance = "3.7",
                txtcity = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numofRating = 15,
                rating = 5f
            ),
            Food(
                txtsubject = "Baryooni",
                txtprice = "16",
                txtdistance = "10",
                txtcity = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numofRating = 28,
                rating = 4.5f
            ),
            Food(
                txtsubject = "Ghorme Sabzi",
                txtprice = "11.5",
                txtdistance = "7.5",
                txtcity = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numofRating = 27,
                rating = 5f
            ),
            Food(
                txtsubject = "Rice",
                txtprice = "12.5",
                txtdistance = "2.4",
                txtcity = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numofRating = 35,
                rating = 2.5f
            )
        )
        foodDao.insertAllfoods(foodListe)
    }

    private fun showAllData() {

        val foodData = foodDao.getAllfood()

        //این سه خط برای ست کردن اداپتور است  و همچنین ست کردن اداپتور در ریسایکلر ویو

        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        Log.v("testlog ", foodData.toString())

    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val UpdateDialogBinding = DialogUpadateItemBinding.inflate(layoutInflater)

        UpdateDialogBinding.dialogEdtNameFood.setText(food.txtsubject)
        UpdateDialogBinding.dialogEdtFoodCity.setText(food.txtcity)
        UpdateDialogBinding.dialogEdtFoodPrice.setText(food.txtprice)
        UpdateDialogBinding.dialogEdtFoodDistance.setText(food.txtdistance)

        dialog.setView(UpdateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        UpdateDialogBinding.dialogBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        UpdateDialogBinding.dialogBtnDone.setOnClickListener {
            if (
                UpdateDialogBinding.dialogEdtNameFood.length() > 0 &&
                UpdateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                UpdateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                UpdateDialogBinding.dialogEdtFoodDistance.length() > 0
            ) {


                val txtName = UpdateDialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = UpdateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtCity = UpdateDialogBinding.dialogEdtFoodCity.text.toString()
                val txtDistance = UpdateDialogBinding.dialogEdtFoodDistance.text.toString()


                val newFood = Food(
                    id = food.id,
                    txtsubject = txtName,
                    txtprice = txtPrice,
                    txtcity = txtCity,
                    txtdistance = txtDistance,
                    urlImage = food.urlImage,
                    numofRating = food.numofRating,
                    rating = food.rating
                )

                //update item in adapter =>
                myAdapter.UpdateFood(newFood, position)

                // update item in database =>
                foodDao.insertorupdate(newFood)

                dialog.dismiss()


            } else {
                Toast.makeText(this, "لطفا همه مقادیر را وارد نمایید", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onFoodLongClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this)
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()


        dialogDeleteBinding.dialogBtnDeletecancel.setOnClickListener {
            dialog.dismiss()

        }

        dialogDeleteBinding.dilogBtnDeletesurei.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food, position)
            foodDao.Deletefoods(food)


        }
    }


    private fun searchonDatabase(editTextInput: String) {
        if (editTextInput.isNotEmpty()) {
            //filter data
            val searchedData = foodDao.searchfood(editTextInput)
            myAdapter.setData(ArrayList(searchedData))


        } else {
            //show all data
            val data = foodDao.getAllfood()
            myAdapter.setData(ArrayList(data))

        }
    }


    private fun AlertDialog.Builder.dismiss() {

    }
}






