package com.example.helloworldkotlin

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.io.Serializable
import java.net.URL

private const val CHARACTER_DATA_API = "https://chargen-api.herokuapp.com"

private fun <T> List<T>.rand() = shuffled().first()

private fun Int.roll() = (0 until this)
    .map { (1..6).toList().rand() }
    .sum()
    .toString()

private val firstName = listOf("Eli", "Alex", "Sophie")
private val lastName = listOf("Lightweaver", "GreatFoot", "Oakenfeld")

private val myVars1 = listOf("Apple", "Orange", "Strawberry")
private val myVars2 = listOf("Acer", "Asus", "Lenovo", "HP")
private val myVars3 = listOf("C++", "Python", "1C", "Kotlin", "Java")

object CharacterGenerator {
    data class CharacterData(
        val name: String,
        val race: String,
        val dex: String,
        val wis: String,
        val str: String
    ) : Serializable

    private fun name() = "${firstName.rand()} ${lastName.rand()}"
    private fun race() = listOf("dwarf", "elf", "human", "halfing").rand()
    private fun dex() = 4.roll()
    private fun wis() = 3.roll()
    private fun str() = 5.roll()
    fun generate() =
        CharacterData(name = name(), race = race(), dex = dex(), wis = wis(), str = str())

    fun fromApiData(apiData: String): CharacterData {
        val (race, name, dex, wis, str) = apiData.split(",")
        return CharacterData(race = race, name = name, dex = dex, wis = wis, str = str)
    }

    data class myVarsData(
        val myVar1: String,
        val myVar2: String,
        val myVar3: String
    ) : Serializable

    private fun myVar1() = myVars1.rand()
    private fun myVar2() = myVars2.rand()
    private fun myVar3() = myVars3.rand()
    fun getMyDatas() = myVarsData(myVar1(), myVar2(), myVar3())
}

fun fetchCharacterData(): Deferred<CharacterGenerator.CharacterData> {
    return async {
        val apiData = URL(CHARACTER_DATA_API).readText()
        CharacterGenerator.fromApiData(apiData)
    }
}