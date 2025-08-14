package ${PACKAGE_NAME}.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "${NAME.toLowerCase()}s")
data class ${NAME}Entity(
    @PrimaryKey val uid: String,
    val firstName: String,
    val email: String,
    val phoneNumber: String,
    val address: String
)
