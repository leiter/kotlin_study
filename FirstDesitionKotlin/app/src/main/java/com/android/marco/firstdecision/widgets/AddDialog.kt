package com.android.marco.firstdecision.widgets

import android.app.Dialog
import android.view.WindowManager
import android.widget.EditText
import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.activities.MainActivity

/**
 * Created by gen on 22.08.15.

 */
class AddDialog(context: MainActivity) {

    private val addDialog: Dialog

    init {
        this.addDialog = Dialog(context)
        addDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        addDialog.setTitle("Am Abend ins")
        addDialog.setContentView(R.layout.dialog_add_item)
        addDialog.findViewById(R.id.bnt_ma_add).setOnClickListener(context)
        val titleDividerId = context.resources.getIdentifier("titleDivider", "id", "android")
        val titleDivider = addDialog.window!!.decorView.findViewById(titleDividerId)
        //        titleDivider.setBackgroundColor(ContextCompat.getColor(context, R.color.brown));

    }


    fun show() {
        addDialog.show()
    }

    fun dismiss() {
        val e = addDialog.findViewById(R.id.et_ma_set_hobby) as EditText
        e.setText("")
        addDialog.dismiss()
    }

    val text: String
        get() {
            val e = addDialog.findViewById(R.id.et_ma_set_hobby) as EditText
            return e.text.toString()
        }

}
