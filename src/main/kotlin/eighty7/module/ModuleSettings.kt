package eighty7.module

class StringSetting(val name: String, var value: String)

/*
val d = DoubleSetting("D", 0.0, 0.0..1.0)

d.range.start
d.range.endInclusive
*/

class DoubleSetting(val name: String, var value: Double, val range: ClosedFloatingPointRange<Double>)

class ArraySetting(val name: String, val value: ArrayList<String>)