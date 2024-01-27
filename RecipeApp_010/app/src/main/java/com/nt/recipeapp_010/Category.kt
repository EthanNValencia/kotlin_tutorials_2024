package com.nt.recipeapp_010

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
https://www.themealdb.com/api/json/v1/1/categories.php
*/

@Parcelize
data class Category
    (val idCategory: Int,
     val strCategory: String,
     val strCategoryThumb: String,
     val strCategoryDescription: String): Parcelable


data class Categories(val categories: List<Category>)
