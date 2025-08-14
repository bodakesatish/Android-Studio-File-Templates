package ${PACKAGE_NAME}.domain.model

data class ${NAME}(
    val uid: String,
    val name: String
) {

    constructor() : this("", "")
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = uid
        hashMap["name"] = name
        return hashMap
    }
}