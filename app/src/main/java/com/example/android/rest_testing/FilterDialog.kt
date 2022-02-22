package com.example.android.rest_testing

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.retrofit.JWT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterDialog(context:Context, jwtToken:JWT?, placesSearch: PlacesSearch):Dialog(context) {
    init {
        dialog = Dialog(context)
        token = jwtToken
        places = placesSearch
    }

    fun showDialog(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.filter_dialog, null, false)
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogView)

        val keyWordEditText = dialogView.findViewById<EditText>(R.id.keyWordEditText)
        val typeEditText = dialogView.findViewById<EditText>(R.id.typeEditText)

        keyWordEditText.setText(places?.keyWord)
        typeEditText.setText(places?.typeOfPlace)

        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            dismissDialog()
        }

        val filterButton = dialogView.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener {
            var keyWordText = keyWordEditText.text.toString()
            if(keyWordText != ""){
                places?.keyWord = keyWordText
            }
            else
                places?.keyWord = null
            var typeText = typeEditText.text.toString()
            if(typeText != ""){
                places?.typeOfPlace = typeText
            }
            else
                places?.typeOfPlace = null
            dismissDialog()
        }

        dialog?.show()
    }

    fun dismissDialog() = dialog?.let { dialog!!.dismiss() }

    companion object{
        var dialog:Dialog? = null
        var token:JWT? = null
        var places:PlacesSearch? = null
    }
}