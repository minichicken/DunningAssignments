package pe.kr.crasy.dunningassignments.edit

import java.util.ArrayList
import java.util.Date

import io.realm.Realm
import io.realm.RealmList
import pe.kr.crasy.dunningassignments.database.Assignments
import pe.kr.crasy.dunningassignments.database.People

/**

 * Created by maybe on 16. 12. 29.
 */

class EditPresenter(private val view: EditPresenter.PView) {
    private val realm: Realm = Realm.getDefaultInstance()
    private val model: EditModel = EditModel()

    fun onTitleUpdate(title: String) {
        model.title = title
    }

    fun onLocationUpdate(location: String) {
        model.location = location
    }

    fun onMetaDataUpdate(metaData: String) {
        model.metaData = metaData
    }

    fun closeActivity() {
        if (!isValid) {
            view.onCloseActivity(false)
            return
        }
        realm.executeTransaction { realm1 ->
            val assignments = realm1.createObject(Assignments::class.java)
            assignments.title = model.title
            assignments.location = model.location
            assignments.startLine = model.startLine
            assignments.deadLine = model.deadLine
            assignments.metaData = model.metaData

            val peoples = RealmList<People>()
            for (i in people.indices) {
                val person = People()
                person.name = people[i]
                peoples.add(person)
            }
            assignments.people = peoples
        }
        realm.close()
        view.onCloseActivity(true)
    }

    fun onDateUpdate(date: Date, title: String) {
        if (title == "startLine") {
            model.startLine = date
            view.onStartLineText(date)
        } else {
            model.deadLine = date
            view.onDeadLineText(date)
        }
    }

    private val people = ArrayList<String>()

    fun onNewMember(memberName: String, position: Int) {
        if (position >= people.size) {
            people.add(memberName)
        } else {
            people[position] = memberName
        }
        model.people = people
    }

    val isValid: Boolean
        get() = model.title != null && model.location != null && model.deadLine != null

    interface PView {
        fun onCloseActivity(isSaved: Boolean)
        fun onStartLineText(date: Date)
        fun onDeadLineText(date: Date)
    }
}
