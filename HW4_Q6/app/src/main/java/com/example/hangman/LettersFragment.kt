package com.example.hangman

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.databinding.FragmentLettersBinding

class LettersFragment : Fragment() {
    private lateinit var binding: FragmentLettersBinding
    private lateinit var viewModel: HangmanViewModel
    private var currentQuestion: Question? = null
    private val letterButtons = listOf(
        R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j, R.id.k,
        R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u, R.id.v,
        R.id.w, R.id.x, R.id.y, R.id.z, )
    private var buttonListeners : MutableMap<Int, Any?> = mutableMapOf()
    private var alphaSet = (97..123).toSet()
    private var hintPressed = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLettersBinding.inflate(
            layoutInflater,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity())[HangmanViewModel::class.java]
        return binding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        currentQuestion = viewModel.currentQuestion

        for ((index, button) in letterButtons.withIndex()) {
            val buttonView = getView()?.findViewById<Button>(button)
            if (buttonView != null) {
                buttonListeners[button] = buttonView.setOnClickListener{
                    if (currentQuestion?.tries!! > 1) {
                        currentQuestion?.tryChar((97 + index).toChar())
                        alphaSet = alphaSet - (97 +index)
                        viewModel.updateData()
                        buttonView.isEnabled = false
                        buttonView.isClickable = false
                    }
                    else if (currentQuestion?.tries == 1) {
                        currentQuestion?.tryChar((97 + index).toChar())
                        alphaSet = alphaSet - (97 +index)
                        viewModel.updateData()
                        disableAllButtons()
                    }
                }
            }
        }
        if (isLandscape) {
            getView()?.findViewById<Button>(R.id.hintButton)?.setOnClickListener {
                when (hintPressed) {
                    0 -> getView()?.findViewById<TextView>(R.id.hintBox)?.text =
                        currentQuestion?.showHint()
                    1 -> {
                        if(currentQuestion?.tries!! > 1) {
                            disableHalf()
                            currentQuestion!!.tries --
                            viewModel.updateData()
                        } else {
                            getView()?.findViewById<Button>(R.id.hintButton)?.isEnabled = false
                            getView()?.findViewById<Button>(R.id.hintButton)?.isClickable = false
                        } }
                    2 -> {
                        if(currentQuestion?.tries!! > 1) {
                            showVowels()
                            currentQuestion!!.tries --
                            viewModel.updateData()
                        } else {
                            getView()?.findViewById<Button>(R.id.hintButton)?.isEnabled = false
                            getView()?.findViewById<Button>(R.id.hintButton)?.isClickable = false
                        }
                    }
                }
                hintPressed ++
                viewModel.updateData()
            }
        }
    }

    private fun disableAllButtons() {
        for (button in letterButtons) {
            val buttonView = view?.findViewById<Button>(button)
            if (buttonView != null) {
                buttonView.isEnabled = false
                buttonView.isClickable = false
            }
        }
    }

    private fun disableHalf(){
        val charSet = currentQuestion?.word?.toSet()!!.map{ char -> char.code }.toSet()
        val finalSet = (alphaSet - charSet).map{ elem -> elem - 97 }.toList().shuffled()
        val finalHalf = finalSet.subList(0, finalSet.size / 2)
        disableButton(finalHalf)
    }

    private fun showVowels() {
        val charSet = currentQuestion?.word?.toSet()!!.map{ char -> char.code }.toSet()
        val vowelSet = charSet - (charSet - setOf<Int>(97,101,105,111,117))
        val finalCharSet = vowelSet.map { elem -> elem.toChar() }
        val vowelButtons = vowelSet.map { elem -> elem - 97 }
        disableButton(vowelButtons)
        for (char in finalCharSet) {
            currentQuestion?.tryChar(char)
        }
    }

    private fun disableButton(vowelButtons: List<Int>) {
        for (index in vowelButtons) {
            val currentId = letterButtons[index]
            val buttonView = view?.findViewById<Button>(currentId)
            if (buttonView != null) {
                buttonView.isEnabled = false
                buttonView.isClickable = false
            }
        }
    }
}

