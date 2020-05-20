package com.aman.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aman.bookhub.R
import com.aman.bookhub.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyler_dashboard_single_row.view.*

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashBoardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyler_dashboard_single_row, parent, false)
        return DashBoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
        //holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).into(holder.imgBookImage)

        holder.llContent.setOnClickListener {
            Toast.makeText(context, "Clicked on ${holder.txtBookName.text}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    class DashBoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val txtBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val imgBookImage: ImageView = view.findViewById(R.id.imgBookImage)

        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

}




