package org.myd.instagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import org.myd.instagramclone.adapters.SearchAdapter
import org.myd.instagramclone.databinding.FragmentSearchBinding
import org.myd.instagramclone.models.User
import org.myd.instagramclone.utils.USER_NODE

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()

            for (i in it.documents) {
                if (i.id.toString() == Firebase.auth.currentUser!!.uid.toString()) {
                    //do nothing
                } else {
                    var user: User = i.toObject<User>()!!
                    userList.add(user)
                }
            }

            userList.addAll(tempList)
            adapter.notifyDataSetChanged()

        }

        binding.searchButton.setOnClickListener {
            var text = binding.searchView.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get().addOnSuccessListener {

                var tempList = ArrayList<User>()
                userList.clear()

                if (it.isEmpty) {

                } else {
                    for (i in it.documents) {
                        if (i.id.toString() == Firebase.auth.currentUser!!.uid.toString()) {
                            //do nothing
                        } else {
                            var user: User = i.toObject<User>()!!
                            userList.add(user)
                        }
                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }

    companion object {

    }
}