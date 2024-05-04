package com.pinedeveloper.homework.presentation.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pinedeveloper.homework.R
import com.pinedeveloper.homework.databinding.CardRickyBinding
import com.pinedeveloper.homework.entity.Result

class RickAdapterPage: PagingDataAdapter<Result, RickViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickViewHolder {
        return RickViewHolder(
            CardRickyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: RickViewHolder, position: Int) {
        val result = getItem(position)

        with(holder.binding){
            //textRover.text = "${holder.binding.root.context.getString(R.string.label_rover)} ${photo?.rover?.name}"
            //textSol.text = "${holder.binding.root.context.getString(R.string.label_sol)} ${photo?.sol}"
            //textCamera.text = "${holder.binding.root.context.getString(R.string.label_camera)} ${photo?.camera?.name}"
            //textDate.text = "${holder.binding.root.context.getString(R.string.label_date)} ${photo?.earthDate}"

            lName.text = result?.name
            lStatus.text = result?.status
            lLocation.text = result?.location?.name

            when(result?.status){
                "Alive" -> imgStatus.setImageResource(R.drawable.circle_green)
                "Dead" -> imgStatus.setImageResource(R.drawable.circle_red)
                else -> imgStatus.setImageResource(R.drawable.cirle_orange)
            }

            Glide.with(holder.binding.root)
                .load(result?.image ?: "")
                .centerCrop()
                .error(R.drawable.image_error)
                .into(imageView)
        }
    }

}

class RickViewHolder(val binding: CardRickyBinding): RecyclerView.ViewHolder(binding.root) {

}

class DiffUtilCallback: DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem == newItem

}