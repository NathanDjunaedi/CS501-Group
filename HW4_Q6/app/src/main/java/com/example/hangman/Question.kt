package com.example.hangman

import kotlin.properties.Delegates

class Question{
    var word: String = ""
    private var hint: String = ""
    var length: Int = 0
    var tries: Int = 6
    private var letters: MutableMap<Char, MutableList<Int>> = mutableMapOf()
    var revealed: MutableMap<Int, Char> = mutableMapOf()

    fun reset() {
        tries = 6
        revealed.replaceAll {_,_ -> '_'}
    }

    fun setPars(str: String, hintWord: String) {
        word = str
        hint = hintWord
        length = str.length
        var index = 0
        for (char in str) {
            if (char in letters) {
                letters[char]?.add(index)
            }
            else { letters[char] = mutableListOf(index) }
            revealed[index] = '_'
            index ++
        }
    }
    fun tryChar(char: Char): Boolean {
        if (char in letters) {
            for (places in letters[char]!!) {
                revealed[places] = char
            }
            return true
        }
        tries--
        return false
    }
    fun showHint(): String {
        return hint
    }

}
