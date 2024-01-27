package com.nt.recipeapp_010

/*
https://www.themealdb.com/api/json/v1/1/categories.php
*/

data class Category
    (val idCategory: Int,
     val strCategory: String,
     val strCategoryThumb: String,
     val strCategoryDescription: String)


data class Categories(val categories: List<Category>)
