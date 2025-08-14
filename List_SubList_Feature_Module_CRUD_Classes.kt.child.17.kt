package ${PACKAGE_NAME}.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ${PACKAGE_NAME}.domain.model.${NAME}
import ${PACKAGE_NAME}.databinding.ListRow${NAME}Binding

class ${NAME}Adapter(
    private val onItemClick: (${NAME}) -> Unit,
    private val onItemLongClick: (${NAME}) -> Unit,
    private val onDeleteItemClick: (${NAME}) -> Unit
) : ListAdapter<${NAME}, ${NAME}Adapter.${NAME}ViewHolder>(${NAME}DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ${NAME}ViewHolder {
        val binding = ListRow${NAME}Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ${NAME}ViewHolder(binding, onItemClick, onItemLongClick, onDeleteItemClick)
    }

    override fun onBindViewHolder(holder: ${NAME}ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ${NAME}ViewHolder(
        private val binding: ListRow${NAME}Binding,
        private val onItemClick: (${NAME}) -> Unit,
        private val onItemLongClick: (${NAME}) -> Unit,
        private val onDeleteItemClick: (${NAME}) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(${NAME.toLowerCase()}: ${NAME}) {
            binding.textViewName.text = ${NAME.toLowerCase()}.name

            binding.root.setOnClickListener {
                onItemClick(${NAME.toLowerCase()})
            }
            binding.root.setOnLongClickListener {
                onItemLongClick(${NAME.toLowerCase()})
                true
            }
            binding.buttonDelete.setOnClickListener {
                onDeleteItemClick(${NAME.toLowerCase()})
            }
        }
    }

    class ${NAME}DiffCallback : DiffUtil.ItemCallback<${NAME}>() {
        override fun areItemsTheSame(oldItem: ${NAME}, newItem: ${NAME}): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: ${NAME}, newItem: ${NAME}): Boolean {
            return oldItem == newItem
        }
    }
}