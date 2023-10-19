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
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hangman.databinding.FragmentHangmanQuestionBinding
private const val TAG = "HangmanFragment"
class HangmanQuestionFragment : Fragment() {
    private lateinit var binding: FragmentHangmanQuestionBinding
    private lateinit var viewModel: HangmanViewModel
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
        updateHangman()
        val questionObserver = Observer<Question> {
            Log.d(TAG, "observed change in hangman")
            updateHangman()
        }
        viewModel.liveData.observe(viewLifecycleOwner, questionObserver)
    }

    private fun updateHangman() {
        viewModel.currentQuestion
        when {
            viewModel.currentQuestion?.tries!! == 6 -> {
                for (index in 0..5) {
                    val rid = hangmanIDs[index]
                    view?.findViewById<ImageView>(rid)?.isVisible = false
                }
            }
            viewModel.currentQuestion?.tries!! == 5 ->
                view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                    R.drawable.baseline_5_left)
            viewModel.currentQuestion?.tries!! == 4 ->
                view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                    R.drawable.baseline_4_left)
            viewModel.currentQuestion?.tries!! == 3 ->
                view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                    R.drawable.baseline_2_left)
            viewModel.currentQuestion?.tries!! == 2 ->
                view?.findViewById<ImageView>(R.id.head1)?.setImageResource(
                    R.drawable.baseline_0_left)
        }
        if (viewModel.currentQuestion?.tries!! < 6) {
            val unTries = 5 - viewModel.currentQuestion?.tries!!
            for (i in 0..unTries) {
                view?.findViewById<ImageView>(hangmanIDs[i])?.visibility = View.VISIBLE
            }
        }
        for ((index, letterViews) in letterIDs.withIndex()) {
            if (index < viewModel.currentQuestion!!.length) {

                if (view?.findViewById<TextView>(letterViews)?.visibility == TextView.INVISIBLE) {
                    view?.findViewById<TextView>(letterViews)?.visibility = TextView.VISIBLE
                }

                view?.findViewById<TextView>(letterViews)?.text =
                    viewModel.currentQuestion!!.revealed[index].toString() }
            else {
                view?.findViewById<TextView>(letterViews)?.visibility = TextView.INVISIBLE
            }
        }
    }
}