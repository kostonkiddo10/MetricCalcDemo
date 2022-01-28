package com.example.metriccalcdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.metriccalcdemo.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    /*
    1. Visibility modifiers not necessary when public?
    2. There are unused parameters in onItemSelected, but AndroidStudio does not
       warn about this. Does this mean that they are used implicitly somehow? Or
       is it because this is an overridden function therefore it needs those parameters
       despite not actually needing to use them all?
    3. onItemSelected uses the position parameter on Units.values() instead of
       the list_of_units variable we created. Why is that?
     */

    private val metricCalculator : Calculator = Calculator()
    private lateinit var binding : ActivityMainBinding
    private val TAG = "Main Activity"
    var list_of_units = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        for(unit in Units.values()) {
            list_of_units.add(unit.name)
        }

        binding.spinner.setOnItemSelectedListener(this)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_units)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = aa

        setContentView(binding.root)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(arg0 : AdapterView<*>, arg1 : View, position : Int, id : Long) {
        Log.d(TAG, "On Item Selected")
        Log.d(TAG, "selected item: $position")

        metricCalculator.setMode(Units.values()[position])
        binding.result.text = "----------"
    }

    fun convertData(view : View) {
        if(binding.inputValue.text.isNotEmpty()) {
            try {
                val inputValue = binding.inputValue.text.toString().toDouble()
                val metricValue = metricCalculator.convert(inputValue)
                binding.result.text = String.format("%.2f", metricValue)
            } catch (e : NumberFormatException) {
                binding.result.text = "Enter valid number"
            }
        }
    }
}