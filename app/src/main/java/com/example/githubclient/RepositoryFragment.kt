package com.example.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RepositoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RepositoryAdapter(
            mutableListOf(),
            onEdit = { repo ->
                (activity as MainActivity).onEditRepository(repo)
            },
            onDelete = { repo ->
                (activity as MainActivity).onDeleteRepository(repo)
            }
        )
        recyclerView.adapter = adapter
    }

    fun updateList(repos: List<Repository>) {
        adapter.updateData(repos)
    }
}