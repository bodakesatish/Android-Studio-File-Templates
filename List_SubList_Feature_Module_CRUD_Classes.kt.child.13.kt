package ${PACKAGE_NAME}.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import ${PACKAGE_NAME}.databinding.ListRowSub${NAME}Binding

class Sub${NAME}Adapter(
    private val onItemClick: (Sub${NAME}) -> Unit,
    private val onDeleteItemClick: (Sub${NAME}) -> Unit
) : ListAdapter<Sub${NAME}, Sub${NAME}Adapter.Sub${NAME}ViewHolder>(Sub${NAME}DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Sub${NAME}ViewHolder {
        val binding =
            ListRowSub${NAME}Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Sub${NAME}ViewHolder(binding, onItemClick, onDeleteItemClick)
    }

    override fun onBindViewHolder(holder: Sub${NAME}ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class Sub${NAME}ViewHolder(
        private val binding: ListRowSub${NAME}Binding,
        private val onItemClick: (Sub${NAME}) -> Unit,
        private val onDeleteItemClick: (Sub${NAME}) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sub${NAME}: Sub${NAME}) {
            binding.textViewName.text = sub${NAME}.name
            binding.textViewPrice.text = sub${NAME}.price.toString()

            binding.root.setOnClickListener {
                onItemClick(sub${NAME})
            }
            binding.buttonDelete.setOnClickListener {
                onDeleteItemClick(sub${NAME})
            }
        }
    }

    class Sub${NAME}DiffCallback : DiffUtil.ItemCallback<Sub${NAME}>() {
        override fun areItemsTheSame(oldItem: Sub${NAME}, newItem: Sub${NAME}): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Sub${NAME}, newItem: Sub${NAME}): Boolean {
            return oldItem == newItem
        }
    }
}