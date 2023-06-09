package uz.gita.book_app_io.presentation.screens.main.pages.saved

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.book_app_io.R
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.databinding.ListItemSavedBinding
import uz.gita.book_app_io.utils.inflate

class SavedAdapter : ListAdapter<BookEntity, SavedAdapter.ViewHolder>(itemBookCallback) {

    private var itemClickListener: ((BookEntity) -> Unit)? = null

    fun setItemClickListener(block: (BookEntity) -> Unit) {
        itemClickListener = block
    }


    inner class ViewHolder(private val binding: ListItemSavedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBind() {

            val data = getItem(absoluteAdapterPosition)
            binding.apply {
                tvBookAuthor.text = data.author
                tvBookName.text = data.name
                tvBookName.setSingleLine()
                tvBookPage.text = "Page: ${data.pages}"
                tvBookReadPage.text = "Read page: ${data.currentPage}"
            }
            Glide.with(binding.imageBook.context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_group)
                .into(binding.imageBook)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemSavedBinding.bind(parent.inflate(R.layout.list_item_saved))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

}

private val itemBookCallback = object : DiffUtil.ItemCallback<BookEntity>() {
    override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean =
        oldItem.pages == newItem.pages &&
                oldItem.author == newItem.author &&
                oldItem.name == newItem.name &&
                oldItem.currentPage == newItem.currentPage &&
                oldItem.download == newItem.download

}
