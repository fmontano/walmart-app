package com.walmart.app.ui.countrylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.walmart.app.R

class CountryListAdapter : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private var countryList: MutableList<CountryUIModel> = mutableListOf()

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.countryNameAndRegion)
        private val code: TextView = view.findViewById(R.id.countryCode)
        private val subtitle: TextView = view.findViewById(R.id.itemCaption)
        private val flagImage: ImageView = view.findViewById(R.id.flagImage)

        fun bind(country: CountryUIModel) {
            title.text = country.title
            code.text = country.code
            subtitle.isVisible = country.subtitle.isNotBlank()
            subtitle.text = country.subtitle
            flagImage.load(country.imageUrl)
        }
    }

    fun addItems(addItems: List<CountryUIModel>) {
        val rangeStart = itemCount
        countryList.addAll(addItems)
        notifyItemRangeInserted(rangeStart, addItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_country, parent, false)

        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(country = countryList[position])

    override fun getItemCount(): Int = countryList.size
}
