package ${PACKAGE_NAME}.data.source.local

import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import ${PACKAGE_NAME}.Sub${NAME}Mapper.toDomain
import ${PACKAGE_NAME}.Sub${NAME}Mapper.toDomainList
import ${PACKAGE_NAME}.Sub${NAME}Mapper.toEntity
import ${PACKAGE_NAME}.Sub${NAME}Mapper.toEntityList
import kotlinx.coroutines.flow.Flow
import ${PACKAGE_NAME}.data.source.local.database.entity.Sub${NAME}Entity
import ${PACKAGE_NAME}.data.source.local.database.dao.Sub${NAME}Dao
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toDomain
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toEntity
import kotlinx.coroutines.flow.map
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toEntityList
import ${PACKAGE_NAME}.data.source.mapper.${NAME}Mapper.toDomainList
import javax.inject.Inject

class Sub${NAME}LocalDataSourceImpl @Inject constructor(
    private val sub${NAME}Dao: Sub${NAME}Dao
) : Sub${NAME}LocalDataSource {

    override suspend fun createOrUpdateLocalSub${NAME}(sub${NAME}: Sub${NAME}): Long {
        return sub${NAME}Dao.insertOrUpdateSub${NAME}(sub${NAME}.toEntity())
    }

    override suspend fun saveLocalSub${NAME}List(sub${NAME}List: List<Sub${NAME}>): List<Long> {
        return sub${NAME}Dao.insertOrUpdateSub${NAME}List(sub${NAME}List.toEntityList())
    }

    override suspend fun deleteLocalSub${NAME}(uid: String): Int {
        return sub${NAME}Dao.deleteSub${NAME}ById(uid)
    }

    override suspend fun deleteAllLocalSub${NAME}(${NAME.toLowerCase()}Id: String): Int {
        return sub${NAME}Dao.deleteAllSub${NAME}s(${NAME.toLowerCase()}Id)
    }

    override suspend fun getLocalSub${NAME}ById(uid: String): Sub${NAME}? {
        return sub${NAME}Dao.getSub${NAME}ById(uid)?.toDomain()
    }

    override fun observeLocalSub${NAME}ById(uid: String): Flow<Sub${NAME}?> {
        return sub${NAME}Dao.observeSub${NAME}ById(uid)
            .map { it?.toDomain() }
    }

    override fun observeLocalSub${NAME}s(${NAME.toLowerCase()}Id: String): Flow<List<Sub${NAME}>> {
        return sub${NAME}Dao.observeAllSub${NAME}s(${NAME.toLowerCase()}Id)
            .map { sub${NAME}Entities -> sub${NAME}Entities.toDomainList() }
    }

    override suspend fun getLocalSub${NAME}s(): List<Sub${NAME}> {
        return sub${NAME}Dao.getAllSub${NAME}s().toDomainList()
    }

    override suspend fun isLocalCacheEmpty(${NAME.toLowerCase()}Id: String): Boolean {
        return sub${NAME}Dao.getSub${NAME}Count(${NAME.toLowerCase()}Id) == 0
    }

}