package space.sneshko.unitconverter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import java.text.FieldPosition
import kotlin.system.measureNanoTime

class InformationFragment : Fragment() {
    private lateinit var unitCategories: RadioGroup
    private lateinit var firstUnit: Spinner
    private lateinit var secondUnit: Spinner
    private lateinit var firstField: EditText
    private lateinit var secondField: EditText
    private val converter = Converter()
    private lateinit var copyButton: ImageButton
    private lateinit var pasteButton: ImageButton
    private lateinit var reverseButton: ImageButton

    private val textModel: TextUpdateModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_information, container, false)

        firstField = view.findViewById(R.id.firstField)
        firstField.showSoftInputOnFocus = false
        secondField = view.findViewById(R.id.secondField)
        secondField.showSoftInputOnFocus = false
        unitCategories = view.findViewById(R.id.unitCategories)

        firstUnit = view.findViewById<Spinner>(R.id.firstUnits)
        secondUnit = view.findViewById<Spinner>(R.id.secondUnits)
        copyButton = view.findViewById(R.id.buttonCopy)
        pasteButton = view.findViewById(R.id.buttonPaste)
        reverseButton = view.findViewById(R.id.buttonReverse)


        unitCategories.setOnCheckedChangeListener { radioGroup, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                val newUnits: Int = when (text) {
                    "Length" -> R.array.lengthUnits
                    "Temperature" -> R.array.temperatureUnits
                    "Mass" -> R.array.massUnits
                    else -> 0
                }

                val firstUnitAdapter = activity?.let {
                    ArrayAdapter.createFromResource(
                        it, newUnits,
                        android.R.layout.simple_spinner_item)
                }
                firstUnitAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                firstUnit.adapter = firstUnitAdapter

                val secondUnitAdapter = activity?.let {
                    ArrayAdapter.createFromResource(
                        it, newUnits,
                        android.R.layout.simple_spinner_item)
                }
                secondUnitAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                secondUnit.adapter = secondUnitAdapter

                view.findViewById<RadioButton>(checkedId)?.apply {
                    textModel.setUnitCategory(text.toString())

                }
                firstField.setText("")
                secondField.setText("")
                textModel.clearText(true)
            }
        }



        textModel.textChanging.observe(viewLifecycleOwner, Observer {
            textModel.setCursorPosition(firstField.selectionStart)
        })

        textModel.getText().observe(viewLifecycleOwner, Observer { text ->
            firstField.setText(text)

                val selectedUnitsCategory: RadioButton = view.findViewById(unitCategories.checkedRadioButtonId)

                secondField.setText(converter.convertUnit(selectedUnitsCategory.text.toString(), firstUnit.selectedItem.toString(),
                    secondUnit.selectedItem.toString(), text))

        })

        textModel.getCursorPosition().observe(viewLifecycleOwner, Observer { curPosition ->
            firstField.setSelection(curPosition)
        })

        textModel.getUserMessage().observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })


        copyButton.setOnClickListener{
            textModel.copyUserInput(secondField.text.toString())
            Toast.makeText(activity, "Number was copied", Toast.LENGTH_SHORT).show()
        }

        pasteButton.setOnClickListener{
            if (textModel.pasteUserInput()) {
                Toast.makeText(activity, "Number was pasted", Toast.LENGTH_SHORT).show()
            }
        }

        firstUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedUnitsCategory: RadioButton = view.findViewById(unitCategories.checkedRadioButtonId)
                secondField.setText(converter.convertUnit(selectedUnitsCategory.text.toString(), firstUnit.selectedItem.toString(),
                    secondUnit.selectedItem.toString(), firstField.text.toString()))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        secondUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedUnitsCategory: RadioButton = view.findViewById(unitCategories.checkedRadioButtonId)
                secondField.setText(converter.convertUnit(selectedUnitsCategory.text.toString(), firstUnit.selectedItem.toString(),
                    secondUnit.selectedItem.toString(), firstField.text.toString()))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        reverseButton.setOnClickListener {
            val firstUnitSave = firstUnit.selectedItemId.toInt()
            firstUnit.setSelection(secondUnit.selectedItemId.toInt())
            secondUnit.setSelection(firstUnitSave)

            textModel.setText(secondField.text.toString())


        }

        return view
    }
}
