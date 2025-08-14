package ${PACKAGE_NAME}.data.source.mapper

interface BaseMapper<Entity, Domain> {
    fun Domain.toEntity(): Entity
    fun Entity.toDomain(): Domain
}