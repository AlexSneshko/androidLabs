package space.sneshko.unitconverter

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal


class Converter{
    private val unitsConversion: Map<String, Map<String, Map<String, (Double)->BigDecimal>>> = unitsMap



    fun convertUnit(unitCategory: String, from: String, to: String, unit: String): String{
        if (unit.isEmpty()) return ""
        return unitsConversion[unitCategory]!![from]!![to]?.invoke(unit.toDouble())!!.toPlainString()
    }
}