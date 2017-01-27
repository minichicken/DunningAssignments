package pe.kr.crasy.dunningassignments.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.edit_activity.*

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import java.util.Date

import kotlinx.android.synthetic.main.edit_content.*

import pe.kr.crasy.dunningassignments.R

class EditActivity : AppCompatActivity(), EditPresenter.PView {
    private var presenter: EditPresenter = EditPresenter(this)
    private var params: LinearLayout.LayoutParams? = null
    private var format: SimpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        setSupportActionBar(toolbar)

        params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)

        val memberList = ArrayList<EditText>()

        editAddMember.setOnClickListener { view ->
            val addPeople = EditText(applicationContext)
            addPeople.layoutParams = params
            addPeople.hint = "add member"
            addPeople.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i0: Int, i1: Int, i2: Int) {
                    for (i in 0..editPeople.childCount - 1) {
                        if (editPeople.getChildAt(i).id == addPeople.id) {
                            presenter.onNewMember(charSequence.toString(), i)
                        }
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            memberList.add(addPeople)
            editPeople.addView(addPeople)
        }

        editStartLine.isFocusable = false
        editStartLine.isFocusableInTouchMode = false
        editDeadLine.isFocusable = false
        editDeadLine.isFocusableInTouchMode = false

        editTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                presenter.onTitleUpdate(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        editLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                presenter.onLocationUpdate(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        editMeta.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                presenter.onMetaDataUpdate(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        editStartLine.setOnClickListener { view ->
            val cal: Calendar = Calendar.getInstance()
            DatePickerDialog(MainActivity@this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(year, month, day)
                TimePickerDialog(MainActivity@this, TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, min)
                    presenter.onDateUpdate(cal.time, "startLine")
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false).show()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        editDeadLine.setOnClickListener { view ->
            val cal: Calendar = Calendar.getInstance()
            DatePickerDialog(MainActivity@this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(year, month, day)
                TimePickerDialog(MainActivity@this, TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, min)
                    presenter.onDateUpdate(cal.time, "deadLine")
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false).show()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.closeActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            presenter.closeActivity()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCloseActivity(isSaved: Boolean) {
        if (isSaved) {
            Toast.makeText(applicationContext, "저장되었습니다.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "저장할 데이터가 부족합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStartLineText(date: Date) {
        editStartLine.setText(format.format(date))
    }

    override fun onDeadLineText(date: Date) {
        editDeadLine.setText(format.format(date))
    }
}
