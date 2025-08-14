package ${PACKAGE_NAME}.domain.model

data class Sub${NAME}(
    val uid: String,
    val ${NAME.toLowerCase()}Id: String,
    val name: String,
    val price: Double
) {

    constructor() : this("", "", "", 0.0)
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = uid
        hashMap["${NAME.toLowerCase()}Id"] = ${NAME.toLowerCase()}Id
        hashMap["name"] = name
        hashMap["price"] = price
        return hashMap
    }
}