package ${PACKAGE_NAME}.data.source.mapper

import ${PACKAGE_NAME}.domain.model.${NAME}
import ${PACKAGE_NAME}.data.source.local.database.entity.${NAME}Entity

object ${NAME}Mapper : BaseMapper<${NAME}Entity, ${NAME}> {
    override fun ${NAME}.toEntity(): ${NAME}Entity {
        val entity = ${NAME}Entity(
            uid = uid,
            firstName = firstName,
            email = email,
            phoneNumber = phoneNumber,
            address = address
        )
        return entity
    }

    override fun ${NAME}Entity.toDomain(): ${NAME} {
        val domain = ${NAME}(
            uid = uid,
            firstName = firstName,
            email = email,
            phoneNumber = phoneNumber,
            address = address
        )
        return domain
    }

    fun List<${NAME}Entity>.toDomainList(): List<${NAME}> {
        return this.map { it.toDomain() } // Calls the extension within the object's scope
    }

    fun List<${NAME}>.toEntityList(): List<${NAME}Entity> {
        return this.map { it.toEntity() } // Calls the extension within the object's scope
    }

}