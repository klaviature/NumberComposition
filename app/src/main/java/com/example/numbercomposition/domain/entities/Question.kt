package com.example.numbercomposition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) : Parcelable