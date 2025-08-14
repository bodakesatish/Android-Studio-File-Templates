package ${PACKAGE_NAME}.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub${NAME.toLowerCase()}")
data class Sub${NAME}Entity(
    @PrimaryKey val uid: String,
    val ${NAME.toLowerCase()}Id: String,
    val name: String,
    val price: Double
)