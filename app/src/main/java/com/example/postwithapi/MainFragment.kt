package com.example.postwithapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.postwithapi.adapter.PostAdapter
import com.example.postwithapi.adapter.PostData
import com.example.postwithapi.databinding.FragmentMainBinding
import org.json.JSONArray

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter: PostAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var postList: MutableList<PostData>
    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        recyclerView = binding.postsRv
        val requestQueue = Volley.newRequestQueue(requireContext())
        postList = mutableListOf()
        adapter = PostAdapter(postList, object : PostAdapter.MyInterface {
            override fun onClick(postData: PostData) {

            }
        })

        recyclerView.adapter = adapter
        val request = JsonArrayRequest(
            "https://jsonplaceholder.org/posts",
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    for (i in 0 until response!!.length()) {
                        val obj = response.getJSONObject(i)
                        val title = obj.getString("title")
                        val img = obj.getString("image")
                        val cont = obj.getString("content")
                        val data = obj.getString("publishedAt")
                        postList.add(PostData(cont, img, data, title))
                        adapter.notifyDataSetChanged()
                    }

                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("BBB", "onResponse: $error")
                }
            })
        Log.d("AAA", "${postList.size}")
        requestQueue.add(request)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}