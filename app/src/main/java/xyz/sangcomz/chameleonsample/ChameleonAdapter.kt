package xyz.sangcomz.chameleonsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * Created by sangcomz on 27/03/2018.
 */
class ChameleonAdapter : RecyclerView.Adapter<ChameleonAdapter.ChameleonViewHolder>() {
    private var chameleonList: List<Chameleon> = arrayListOf()

    fun setChameleonList(chameleonList: List<Chameleon>) {
        this.chameleonList = chameleonList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChameleonViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chamelon, parent, false)
        return ChameleonViewHolder(view)
    }

    override fun getItemCount(): Int = chameleonList.size

    override fun onBindViewHolder(holder: ChameleonViewHolder, position: Int) {
        holder.setItem(chameleonList[position])
    }


    inner class ChameleonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItemChameleon: AppCompatImageView = itemView.findViewById(R.id.iv_item_chameleon)
        private val tvItemChameleon: AppCompatTextView = itemView.findViewById(R.id.tv_item_chameleon)

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