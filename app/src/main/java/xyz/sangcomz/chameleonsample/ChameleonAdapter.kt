package xyz.sangcomz.chameleonsample

import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by sangcomz on 27/03/2018.
 */
class ChameleonAdapter(private val chameleonList: List<Chameleon>) : RecyclerView.Adapter<ChameleonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_chamelon, parent ,false)
        )
    }

    override fun getItemCount(): Int = chameleonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(chameleonList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItemChameleon = itemView.findViewById<AppCompatImageView>(R.id.iv_item_chameleon)
        private val tvItemChameleon = itemView.findViewById<AppCompatTextView>(R.id.tv_item_chameleon)

        fun setItem(chameleon: Chameleon) {
            Picasso.get()
                    .load(chameleon.drawableId)
                    .fit()
                    .centerCrop()
                    .into(ivItemChameleon)
            tvItemChameleon.text = chameleon.petName
        }

    }
}