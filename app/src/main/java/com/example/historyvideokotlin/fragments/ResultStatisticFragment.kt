package com.example.historyvideokotlin.fragments

import android.graphics.Color
import android.graphics.Typeface
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentResultStatisticBinding
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.ResultStatisticViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import java.util.*
import kotlin.collections.ArrayList

class ResultStatisticFragment(val onItemClickListener: OnItemClickListener) :
    BaseFragment<ResultStatisticViewModel, FragmentResultStatisticBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_result_statistic
    }

    override fun getViewModel(): ResultStatisticViewModel =
        ViewModelProvider(requireActivity()).get(ResultStatisticViewModel::class.java)

    

    override fun initData() {
        val choosedAnswerList = arguments?.getStringArrayList(CHOOSED_ANSWER_LIST_KEY) as List<String>
        val correctList = arguments?.getStringArrayList(CORRECT_ANSWER_LIST_KEY) as List<String>

        setPieChart(checkAnswer(correctList, choosedAnswerList))

        binding.btnReturn.setOnClickListener {
            onItemClickListener.onReturn()
        }

        binding.btnWatchAnswer.setOnClickListener {
            onItemClickListener.onWatchAnswer()
        }

        binding.ivBack.setOnClickListener {
            onItemClickListener.onBack()
        }
    }

    private fun checkAnswer(
        correctList: List<String>,
        choosedAnswerList: List<String>
    ): ArrayList<Float> {
        val arrayList = ArrayList<Float>()
        var correctAnswerCount = 0
        var inCorrectAnswerCount = 0
        var notAnsweredCount = 0
        MyLog.e("choosedAnswerList",choosedAnswerList.toString())


        for (i in 0 until correctList.size) {
            val correct = correctList[i]
            if (choosedAnswerList[i].equals("")) {
                notAnsweredCount++
                continue
            }
            if (choosedAnswerList[i].equals(correct)) {
                correctAnswerCount++
            } else {
                inCorrectAnswerCount++
            }
        }
        arrayList.add(correctAnswerCount / choosedAnswerList.size * 100f)
        arrayList.add(inCorrectAnswerCount / choosedAnswerList.size * 100f)
        arrayList.add(notAnsweredCount / choosedAnswerList.size * 100f)

        return arrayList
    }

    private fun setPieChart(arrayList: ArrayList<Float>) {
        binding.run {
            pieChart.setUsePercentValues(true)
            pieChart.getDescription().setEnabled(false)
            pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

            // on below line we are setting drag for our pie chart
            pieChart.setDragDecelerationFrictionCoef(0.95f)

            // on below line we are setting hole
            // and hole color for pie chart
            pieChart.setDrawHoleEnabled(true)
            pieChart.setHoleColor(Color.WHITE)

            // on below line we are setting circle color and alpha
            pieChart.setTransparentCircleColor(Color.WHITE)
            pieChart.setTransparentCircleAlpha(110)

            // on  below line we are setting hole readius
            pieChart.setHoleRadius(58f)
            pieChart.setTransparentCircleRadius(61f)

            // on below line we are setting center text
            pieChart.setDrawCenterText(true)

            // on below line we are setting
            // rotation for our pie chart
            pieChart.setRotationAngle(0f)

            // enable rotation of the pieChart by touch
            pieChart.setRotationEnabled(true)
            pieChart.setHighlightPerTapEnabled(true)

            // on below line we are setting animation for our pie chart
            pieChart.animateY(1400, Easing.EaseInOutQuad)

            // on below line we are disabling our legend for pie chart
            pieChart.legend.isEnabled = false
            pieChart.setEntryLabelColor(Color.WHITE)
            pieChart.setEntryLabelTextSize(12f)

            // on below line we are creating array list and
            // adding data to it to display in pie chart
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(arrayList[0]))
            entries.add(PieEntry(arrayList[1]))
            entries.add(PieEntry(arrayList[2]))

            // on below line we are setting pie data set
            val dataSet = PieDataSet(entries, "Mobile OS")

            // on below line we are setting icons.
            dataSet.setDrawIcons(false)

            // on below line we are setting slice for pie
            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            // add a lot of colors to list
            val colors: ArrayList<Int> = ArrayList()
            colors.add(resources.getColor(R.color.green))
            colors.add(resources.getColor(R.color.red))
            colors.add(resources.getColor(R.color.yellow))

            // on below line we are setting colors.
            dataSet.colors = colors

            // on below line we are setting pie data set
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)
            pieChart.setData(data)

            // undo all highlights
            pieChart.highlightValues(null)

            // loading chart
            pieChart.invalidate()
        }
    }

    

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        const val CHOOSED_ANSWER_LIST_KEY = "CHOOSE_ANSWER_LIST_KEY"
        const val CORRECT_ANSWER_LIST_KEY = "CORRECT_ANSWER_LIST_KEY"

        @JvmStatic
        fun newInstance(choosedAnswerList: List<String>, correctList: List<String>, onItemClickListener: OnItemClickListener) =
            ResultStatisticFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    CHOOSED_ANSWER_LIST_KEY to choosedAnswerList,
                    CORRECT_ANSWER_LIST_KEY to correctList
                )
            }
    }

    interface OnItemClickListener {
        fun onReturn()
        fun onWatchAnswer()
        fun onBack()
    }
}