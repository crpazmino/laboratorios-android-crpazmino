package com.example.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepositoryAdapter(
    private val repos: MutableList<Repository>,
    private val onEdit: (Repository) -> Unit,
    private val onDelete: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvRepoName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvRepoDescription)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.tvName.text = repo.name
        holder.tvDescription.text = repo.description ?: "Sin descripción"
        holder.btnEdit.setOnClickListener { onEdit(repo) }
        holder.btnDelete.setOnClickListener { onDelete(repo) }
    }

    override fun getItemCount(): Int = repos.size

    fun updateData(newRepos: List<Repository>) {
        repos.clear()
        repos.addAll(newRepos)
        notifyDataSetChanged()
    }
}
