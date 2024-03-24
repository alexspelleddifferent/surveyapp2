package com.example.surveyapp

import androidx.lifecycle.ViewModel

class CountsViewModel: ViewModel() {
    private var leftCount = 0
    private var rightCount = 0

    fun getLeftCount(): Int {
        return leftCount
    }

    fun setLeftCount(count: Int) {
        leftCount = count
    }

    fun getRightCount(): Int {
        return rightCount
    }

    fun setRightCount(count: Int) {
        rightCount = count
    }

    fun updateLeft() {
        leftCount += 1
    }

    fun updateRight() {
        rightCount += 1
    }

    fun reset() {
        leftCount = 0
        rightCount = 0

    }
}