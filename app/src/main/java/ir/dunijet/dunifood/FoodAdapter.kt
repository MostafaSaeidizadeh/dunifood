package ir.dunijet.dunifood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.dunijet.dunifood.Room.Food
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data :ArrayList<Food>, private val foodEvents: FoodEvents) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View , private val context:Context): RecyclerView.ViewHolder(itemView) {


        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtsubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtprice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtcity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtdistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val txtRatingBar = itemView.findViewById<TextView>(R.id.item_txt_rating)

        fun bindData(position: Int){
            txtsubject.text = data[position].txtsubject
            txtcity.text = data[position].txtcity
            txtprice.text = "$" +  data[position].txtprice +  "VIP"
            txtdistance.text = data[position].txtdistance +  "miles from you"
            txtRatingBar.text = "( " + data[position].numofRating.toString() + "Rating )"
            ratingBar.rating = data[position].rating


            Glide
                .with(context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16  , 4))
                .into(imgMain)



            itemView.setOnClickListener {
                foodEvents.onFoodClicked(data[adapterPosition] , adapterPosition)


            }
            itemView.setOnLongClickListener {
                foodEvents.onFoodLongClicked(data[adapterPosition] , adapterPosition)
                true
            }


            }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.itemfood , parent , false)
        return FoodViewHolder(view , parent.context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
       return data.size
    }


    fun  addFood(newFood: Food){
        data.add(0,newFood)
        notifyItemInserted(0)
    }


    fun removeFood(oldfood : Food, oldPosition : Int){
        data.remove(oldfood)
        notifyItemRemoved(oldPosition)


    }

     interface FoodEvents {
         fun onFoodClicked(food: Food, position: Int)
         fun onFoodLongClicked(food: Food, position: Int)

     }
    fun UpdateFood(newFood: Food, position: Int){
        data.set(position , newFood)
        notifyItemChanged(position)
    }
    fun setData(newList:ArrayList<Food>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }



    }