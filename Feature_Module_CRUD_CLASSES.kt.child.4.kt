package ${PACKAGE_NAME}.data.source.local

import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.flow.Flow
import ${PACKAGE_NAME}.data.source.local.database.entity.${NAME}Entity
import ${PACKAGE_NAME}.data.source.local.database.dao.${NAME}Dao
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toDomain
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toEntity
import kotlinx.coroutines.flow.map
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toEntityList
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toDomainList
import javax.inject.Inject

class ${NAME}LocalDataSourceImpl @Inject constructor(
    private val ${NAME.toLowerCase()}Dao: ${NAME}Dao
) : ${NAME}LocalDataSource {

    override suspend fun createOrUpdateLocal${NAME}(${NAME.toLowerCase()}: ${NAME}): Long {
        return ${NAME.toLowerCase()}Dao.insertOrUpdate${NAME}(${NAME.toLowerCase()}.toEntity())
    }

    override suspend fun saveLocal${NAME}List(${NAME.toLowerCase()}List: List<${NAME}>): List<Long> {
        return ${NAME.toLowerCase()}Dao.insertOrUpdate${NAME}List(${NAME.toLowerCase()}List.toEntityList())
    }

    override suspend fun deleteLocal${NAME}(uid: String): Int {
        return ${NAME.toLowerCase()}Dao.delete${NAME}ById(uid)
    }

    override suspend fun deleteAllLocal${NAME}(): Int {
        return ${NAME.toLowerCase()}Dao.deleteAll${NAME}s()
    }

    override suspend fun getLocal${NAME}ById(uid: String): ${NAME}? {
        return ${NAME.toLowerCase()}Dao.get${NAME}ById(uid)?.toDomain()
    }

    override fun observeLocal${NAME}ById(uid: String): Flow<${NAME}?> {
        return ${NAME.toLowerCase()}Dao.observe${NAME}ById(uid)
            .map { it?.toDomain() }
    }

    override fun observeLocal${NAME}s(): Flow<List<${NAME}>> {
        return ${NAME.toLowerCase()}Dao.observeAll${NAME}s()
            .map { ${NAME.toLowerCase()}Entities -> ${NAME.toLowerCase()}Entities.toDomainList() }
    }

    override suspend fun getLocal${NAME}s(): List<${NAME}> {
        return ${NAME.toLowerCase()}Dao.getAll${NAME}s().toDomainList()
    }

    override suspend fun isLocalCacheEmpty(): Boolean {
        return ${NAME.toLowerCase()}Dao.get${NAME}Count() == 0
    }

}