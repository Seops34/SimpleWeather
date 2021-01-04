package com.seosh.simpleweather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seosh.simpleweather.R
import com.seosh.simpleweather.databinding.AdapterItemCityBinding
import com.seosh.simpleweather.repository.data.CityItem
import com.seosh.simpleweather.viewmodel.CityAdapterViewModel

class CityAdapter(private val cityAdapterViewModel: CityAdapterViewModel)
    : ListAdapter<CityItem, CityAdapter.CityViewHolder>(CityDiffUtils()), Filterable {

    companion object {
        private const val TAG = "[CityAdapter]"
    }

    private var originalList : List<CityItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(AdapterItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CityViewHolder(private val binding: AdapterItemCityBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = cityAdapterViewModel
        }

        fun bind(item: CityItem) {
            binding.cityItem = item

            binding.imageFavorite.setOnClickListener {
                binding.imageFavorite.setImageResource(
                    when (item.isFavorite) {
                        true -> R.drawable.ic_non_favorite
                        false -> R.drawable.ic_favorite
                    }
                )

                cityAdapterViewModel.onItemSaved(item)
            }

            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        if(originalList == null) {
            originalList = currentList
        }

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val text = constraint.toString()

                val filteredList = when(text.isEmpty()) {
                    true -> {
                        originalList!!
                    }

                    false -> {
                        originalList!!.filter {
                            // Log.d("seosh", "${TAG} [getFilter] Compare ${it.name} with ${text} / Result : ${it.name.contains(text, true)}")
                            it.name.startsWith(text, true)
                        }
                    }
                }

                // Log.d("seosh", "${TAG} [getFilter] Filtered List - ${filteredList.toString()}")

                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as List<CityItem>)
            }
        }
    }
}

class CityDiffUtils : DiffUtil.ItemCallback<CityItem>() {
    override fun areItemsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return (oldItem.name == newItem.name) && (oldItem.isFavorite == newItem.isFavorite)
    }

    override fun areContentsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return oldItem == newItem
    }
}