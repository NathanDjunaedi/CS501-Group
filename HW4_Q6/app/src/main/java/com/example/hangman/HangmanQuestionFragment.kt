package com.example.hangman

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hangman.databinding.FragmentHangmanQuestionBinding
private const val TAG = "HangmanFragment"
class HangmanQuestionFragment : Fragment() {
    private lateinit var binding: FragmentHangmanQuestionBinding
    private lateinit var viewModel: HangmanViewModel
    private var currentQuestion: Question? = null
    private var hangmanIDs = listOf(
        R.id.head1, R.id.body2, R.id.arm3, R.id.arm4, R.id.leg5, R.id.leg6)
    private var letterIDs = listOf(
        R.id.userAnswer, R.id.userAnswer2, R.id.userAnswer3,
        R.id.userAnswer4, R.id.userAnswer5, R.id.userAnswer6 )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHangmanQuestionBinding.inflate(
            layoutInflater,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity())[HangmanViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentQuestion = viewModel.currentQuestion
        updateHangman()
        val questionObserver = Observer<Question> {
            Log.d(TAG, "observed change in hangman")
            updateHangman()
        }
        viewModel.liveData.observe(viewLifecycleOwner, questionObserver)
    }

    private fun updateHangman() {
        if (currentQuestion?.tries != 6) {
            when {
                currentQuestion?.tries!! == 5 ->
                    view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                        R.drawable.baseline_5_left)
                currentQuestion?.tries!! == 4 ->
                    view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                        R.drawable.baseline_4_left)
                currentQuestion?.tries!! == 3 ->
                    view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                        R.drawable.baseline_2_left)
                currentQuestion?.tries!! == 2 ->
                    view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                        R.drawable.baseline_0_left)
            }
            val unTries = 5 - currentQuestion?.tries!!
            for (i in 0..unTries) {
                view?.findViewById<ImageView>(hangmanIDs[i])?.visibility = View.VISIBLE
            }
        }
        for ((index, letterViews) in letterIDs.withIndex()) {
            if (index < currentQuestion!!.length) {
                view?.findViewById<TextView>(letterViews)?.text =
                    currentQuestion!!.revealed[index].toString() }
            else {
                view?.findViewById<TextView>(letterViews)?.visibility = TextView.INVISIBLE
            }
        }
    }
}