package space.sneshko.unitconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class KeyboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var numberButtons: List<Button>
    private lateinit var backSpaceButton: ImageButton
    private lateinit var clearButton: Button
    private lateinit var plusMinusButton: Button
    private val textModel: TextUpdateModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keyboard, container, false)



        numberButtons = listOf<Button>(
            view.findViewById(R.id.button0),
            view.findViewById(R.id.button1),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4),
            view.findViewById(R.id.button5),
            view.findViewById(R.id.button6),
            view.findViewById(R.id.button7),
            view.findViewById(R.id.button8),
            view.findViewById(R.id.button9),
            view.findViewById(R.id.buttonPoint)
        )
        backSpaceButton = view.findViewById(R.id.buttonBackspace)
        clearButton = view.findViewById(R.id.buttonClear)
        plusMinusButton = view.findViewById(R.id.buttonPlusMinus)

        for(button in numberButtons) {
            button.setOnClickListener {
                textModel.updateText(button.text.toString())
            }
        }

        backSpaceButton.setOnClickListener{
            textModel.clearText(false)
        }
        clearButton.setOnClickListener{
            textModel.clearText(true)
        }
        plusMinusButton.setOnClickListener{
            textModel.changeSign()
        }

        return view
    }




}