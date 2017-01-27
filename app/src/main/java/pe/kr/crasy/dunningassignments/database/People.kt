package pe.kr.crasy.dunningassignments.database

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**

 * Created by maybe on 16. 11. 24.
 */
@RealmClass
open class People : RealmObject() {
    var email: String? = null
    var name: String? = null
    var age: Int = 0
    var address: String? = null
    var phone: String? = null
}
