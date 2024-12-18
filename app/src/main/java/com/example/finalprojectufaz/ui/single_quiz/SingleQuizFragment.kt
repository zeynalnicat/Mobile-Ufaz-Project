package com.example.finalprojectufaz.ui.single_quiz

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.FragmentSingleQuizBinding
import com.example.finalprojectufaz.ui.single_quiz.adapter.QuizPagerAdapter
import com.example.finalprojectufaz.ui.single_quiz.factory.SingleQuizFactory
import com.example.finalprojectufaz.ui.single_quiz.viewmodel.SingleQuizViewModel


class SingleQuizFragment : Fragment(),QuizPagerAdapter.ActionCallBac {
    private lateinit var binding:FragmentSingleQuizBinding
    private val args:SingleQuizFragmentArgs by navArgs()
    private lateinit var quizDao: QuizDao
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis = 0L
    private lateinit var mActivity:MainActivity
    private val viewModel:SingleQuizViewModel by viewModels { SingleQuizFactory(quizDao) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleQuizBinding.inflate(layoutInflater)
        mActivity = requireActivity() as MainActivity
        mActivity.hideBottomNav(true)
        quizDao = RoomDB.accessDB(requireContext())!!.quizDao()
        binding.viewPager.isUserInputEnabled = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.questions.observe(viewLifecycleOwner,{
              if(it!=null){
                  val adapter = QuizPagerAdapter(it,this)
                  binding.viewPager.adapter=adapter
                  timeLeftInMillis = it.timer.toLong()
                  binding.circularProgressBar.max = it.timer
                  timer = object : CountDownTimer(it.timer*1000.toLong(), 1000) {
                      override fun onTick(millisUntilFinished: Long) {
                          timeLeftInMillis = millisUntilFinished
                          val progress = (millisUntilFinished * 100 / it.timer).toInt()
                          binding.circularProgressBar.progress = progress
                          binding.timerTextView.text = (millisUntilFinished/1000).toString()
                      }

                      override fun onFinish() {
                          binding.circularProgressBar.progress = 0
                          binding.timerTextView.text = "0"
                          mActivity.hideBottomNav(false)
                          if(isAdded){
                              findNavController().navigate(
                                  SingleQuizFragmentDirections.actionSingleQuizToQuizResultFragment(args.id)
                              )
                          }

                      }
                  }.start()
              }

        })

        viewModel.fetchQuizQuestions(args.id)

    }

    override fun nav() {
        findNavController().popBackStack()
        mActivity.hideBottomNav(false)
    }

    override fun next() {
        val currentItem = binding.viewPager.currentItem
        val itemCount = binding.viewPager.adapter?.itemCount ?: 0
        if (currentItem < itemCount - 1) {
            binding.viewPager.setCurrentItem(currentItem + 1, true)
        }else{
            mActivity.hideBottomNav(false)
            findNavController().navigate(SingleQuizFragmentDirections.actionSingleQuizToQuizResultFragment(args.id))
        }
    }

    override fun back() {
        val currentItem = binding.viewPager.currentItem
        if(currentItem!=0){
            binding.viewPager.setCurrentItem(currentItem-1,true)
        }
    }


}