package com.dysen.paging3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dysen.paging3.http.res.Res
import com.me.optionbarview.OptionBarView


/**
 * @author dysen
 * dy.sen@qq.com     4/6/21 3:24 PM
 *
 * Info：
 */
class RepoAdapter : PagingDataAdapter<Res.Item, RepoAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Res.Item>() {
            override fun areItemsTheSame(oldItem: Res.Item, newItem: Res.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Res.Item, newItem: Res.Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val opv1: OptionBarView = itemView.findViewById(R.id.opv1)
        val opv2: OptionBarView = itemView.findViewById(R.id.opv2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.projects_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.opv1.setLeftText(repo.name)
            holder.opv1.setLeftText(repo.description)
            holder.opv2.setLeftText(repo.stargazersCount)
        }
    }

}

