package ${PACKAGE_NAME}.domain.model

data class ${NAME}(
    val uid: String,
    val firstName: String,
    val email: String,
    val phoneNumber: String,
    val address: String
) {

    constructor() : this("", "", "","","")
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = uid
        hashMap["firstName"] = firstName
        hashMap["email"] = email
        hashMap["phoneNumber"] = phoneNumber
        hashMap["address"] = address
        return hashMap
    }
}