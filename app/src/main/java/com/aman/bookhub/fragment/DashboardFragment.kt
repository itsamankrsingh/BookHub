package com.aman.bookhub.fragment


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aman.bookhub.R
import com.aman.bookhub.adapter.DashboardRecyclerAdapter
import com.aman.bookhub.model.Book
import com.aman.bookhub.util.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var btnCheckInternet: Button

    val bookInfoList= arrayListOf<Book>()

    /*val bookInfoList = arrayListOf<Book>(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book(
            "The Adventures of Huckleberry Finn",
            "Mark Twain",
            "Rs. 699",
            "4.5",
            R.drawable.adventures_finn
        ),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
    )*/


    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        btnCheckInternet = view.findViewById(R.id.btnCheckInternet)
        btnCheckInternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok") { text, listener ->

                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                }

                dialog.create()
                dialog.show()

            } else {

                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Ok") { text, listener ->

                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                }

                dialog.create()
                dialog.show()

            }
        }

        layoutManager = LinearLayoutManager(activity)



        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_books/"

        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val success=it.getBoolean("success")

                if (success){
                    val data=it.getJSONArray("data")
                    for (i in 0 until data.length()){
                        val bookJsonObject=data.getJSONObject(i)
                        val bookObject=Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image")

                        )
                        bookInfoList.add(bookObject)
                        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager

                        recyclerDashboard.addItemDecoration(
                            DividerItemDecoration(
                                recyclerDashboard.context,
                                (layoutManager as LinearLayoutManager).orientation
                            )
                        )
                    }
                }else{
                    Toast.makeText(activity as Context,"Some error occured!!!",Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {

                println("Error is $it")
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "a840f2b6fab166"
                    return headers
                }
            }

        queue.add(jsonObjectRequest)

        return view
    }


}
