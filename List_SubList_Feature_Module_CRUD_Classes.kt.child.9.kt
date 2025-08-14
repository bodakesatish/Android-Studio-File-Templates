package ${PACKAGE_NAME}.data.source.mapper

import ${PACKAGE_NAME}.data.source.mapper.BaseMapper
import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import ${PACKAGE_NAME}.data.source.local.database.entity.${NAME}Entity

object Sub${NAME}Mapper : BaseMapper<Sub${NAME}Entity, Sub${NAME}> {
    override fun Sub${NAME}.toEntity(): Sub${NAME}Entity {
        val entity = Sub${NAME}Entity(
            uid = uid,
            ${NAME.toLowerCase()}Id = ${NAME.toLowerCase()}Id,
            name = name,
            price = price
        )
        return entity
    }

    override fun Sub${NAME}Entity.toDomain(): Sub${NAME} {
        val domain = Sub${NAME}(
            uid = uid,
            ${NAME.toLowerCase()}Id = ${NAME.toLowerCase()}Id,
            name = name,
            price = price
        )
        return domain
    }

    fun List<Sub${NAME}Entity>.toDomainList(): List<Sub${NAME}> {
        return this.map { it.toDomain() } // Calls the extension within the object's scope
    }

    fun List<Sub${NAME}>.toEntityList(): List<Sub${NAME}Entity> {
        return this.map { it.toEntity() } // Calls the extension within the object's scope
    }

}