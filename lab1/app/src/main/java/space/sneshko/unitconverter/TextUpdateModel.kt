package space.sneshko.unitconverter

import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextUpdateModel : ViewModel() {
    private var userInput = MutableLiveData<String>()
    private var cursorPosition = MutableLiveData<Int>()
    var textChanging = MutableLiveData<Int>()
    private var unitCategory = MutableLiveData<String>()
    private var userMessage = MutableLiveData<String>()
    private var copiedInput = MutableLiveData<String>()

    init {
        userInput.value = ""
        textChanging.value = 0
    }

    fun getCursorPosition() = cursorPosition

    fun setCursorPosition(pos: Int){
        cursorPosition.value = pos
    }

    fun setUnitCategory(cat: String){
        unitCategory.value = cat
    }

    fun getText() = userInput

    fun setText(text: String) {
        userInput.value = text
    }

    fun getUserMessage() = userMessage

    fun pasteUserInput() : Boolean{
        if (copiedInput.value.toString().length > 15) {
            userMessage.value = "Max length is 17"
            return false
        }

        return if (copiedInput.value?.isNotEmpty() == true) {
            userInput.value = copiedInput.value
            true
        } else {
            userMessage.value = "No copied value"
            false
        }
    }

    fun copyUserInput(copied: String) {
        copiedInput.value = copied
    }

    fun updateText(strToAdd: String){
        if (userInput.value.toString().length > 15 && !(userInput.value.toString().endsWith(".") && cursorPosition.value == 15)) {
            Log.i("CUR", cursorPosition.value.toString())
            userMessage.value = "Max length is 17"
            return
        }
        if (strToAdd == "." && "." in userInput.value!!) {
            userMessage.value = "Dot already exist"
            return
        }
        if (userInput.value!!.isNotEmpty() && userInput.value!!.toDouble() < 0 && cursorPosition.value == 1) {
            userMessage.value = "Not available before minus"
            return
        }

        textChanging.value = textChanging.value?.plus(1)

        val oldStr = userInput.value


        val leftStr = oldStr?.substring(0, cursorPosition.value!!)
        val rightStr = oldStr?.substring(cursorPosition.value!!)

        val newText = String.format("%s%s%s", leftStr, strToAdd, rightStr)

        if (newText.toDoubleOrNull() == null)
            return
        if (newText.startsWith("00")) {
            userMessage.value = "Can't print double zeros"
            return
        }

        userInput.value = newText
        cursorPosition.value = cursorPosition.value!! + strToAdd.length
    }

    fun clearText(full: Boolean){
        if (full) {
            userInput.value = ""
        }
        else {
            textChanging.value = textChanging.value?.plus(1)
            val cursorPos = cursorPosition.value

            if (cursorPos != 0 && userInput.value!!.isNotEmpty()){
                userInput.value = userInput.value.toString().replaceRange(cursorPos!! - 1, cursorPos, "")
                cursorPosition.value = cursorPos - 1
            }
        }
    }

    fun changeSign(){
        if (unitCategory.value == "Temperature" && userInput.value!!.isNotEmpty()){
            val num = userInput.value!!.toBigDecimal()

            userInput.value = (-num).toString()
        }
        else {
            userMessage.value = "This unit can't be negative"
        }
    }
}