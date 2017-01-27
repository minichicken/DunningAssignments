package pe.kr.crasy.dunningassignments.database

import java.util.Date

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**

 * Created by maybe on 16. 11. 24.
 */
open class Assignments : RealmObject() {
    @Required
    var title: String? = null
    @Required
    var location: String? = null
    var startLine: Date? = null
    @Required
    var deadLine: Date? = null
    var people: RealmList<People>? = null
    var metaData: String? = null
}
