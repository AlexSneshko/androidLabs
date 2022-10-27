package space.sneshko.unitconverter

import java.math.BigDecimal

val unitsMap = mapOf(
    "Length" to mapOf(
        "Metres" to mapOf(
            "Metres" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Feet" to fun(x: Double): BigDecimal = (x * 3.281).toBigDecimal(),
            "Miles" to fun(x: Double): BigDecimal = (x / 1609).toBigDecimal()
        ),
        "Feet" to mapOf(
            "Metres" to fun(x: Double): BigDecimal = (x / 3.281).toBigDecimal(),
            "Feet" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Miles" to fun(x: Double): BigDecimal = (x / 5280).toBigDecimal()
        ),
        "Miles" to mapOf(
            "Metres" to fun(x: Double): BigDecimal = (x * 1609).toBigDecimal(),
            "Feet" to fun(x: Double): BigDecimal = (x * 5280).toBigDecimal(),
            "Miles" to fun(x: Double): BigDecimal = x.toBigDecimal()
        )
    ),
    "Temperature" to mapOf(
        "Celsius" to mapOf(
            "Celsius" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Fahrenheit" to fun(x: Double): BigDecimal = (x * 9 / 5 + 32).toBigDecimal(),
            "Kelvin" to fun(x: Double): BigDecimal = (x + 273.15).toBigDecimal()
        ),
        "Fahrenheit" to mapOf(
            "Celsius" to fun(x: Double): BigDecimal = ((x - 32) * 5 / 9).toBigDecimal(),
            "Fahrenheit" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Kelvin" to fun(x: Double): BigDecimal = ((x - 32) * 5 / 9 + 273.15).toBigDecimal()
        ),
        "Kelvin" to mapOf(
            "Celsius" to fun(x: Double): BigDecimal = (x - 273.15).toBigDecimal(),
            "Fahrenheit" to fun(x: Double): BigDecimal = ((x - 273.15) * 9 / 5 + 32).toBigDecimal(),
            "Kelvin" to fun(x: Double): BigDecimal = x.toBigDecimal()
        )
    ),
    "Mass" to mapOf(
        "Kilograms" to mapOf(
            "Kilograms" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Pounds" to fun(x: Double): BigDecimal = (x * 2.205).toBigDecimal(),
            "Ounces" to fun(x: Double): BigDecimal = (x * 35.274).toBigDecimal()
        ),
        "Pounds" to mapOf(
            "Kilograms" to fun(x: Double): BigDecimal = (x / 2.205).toBigDecimal(),
            "Pounds" to fun(x: Double): BigDecimal = x.toBigDecimal(),
            "Ounces" to fun(x: Double): BigDecimal = (x * 16).toBigDecimal()
        ),
        "Ounces" to mapOf(
            "Kilograms" to fun(x: Double): BigDecimal = (x / 35.274).toBigDecimal(),
            "Pounds" to fun(x: Double): BigDecimal = (x / 16).toBigDecimal(),
            "Ounces" to fun(x: Double): BigDecimal = x.toBigDecimal()
        )
    )
)