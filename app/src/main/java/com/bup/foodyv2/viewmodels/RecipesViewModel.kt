package com.bup.foodyv2.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bup.foodyv2.util.Constants
import com.bup.foodyv2.util.Constants.Companion.API_KEY
import com.bup.foodyv2.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.bup.foodyv2.util.Constants.Companion.QUERY_API_KEY
import com.bup.foodyv2.util.Constants.Companion.QUERY_DIET
import com.bup.foodyv2.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.bup.foodyv2.util.Constants.Companion.QUERY_NUMBER
import com.bup.foodyv2.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel


class RecipesViewModel(application: Application) : AndroidViewModel(application){
     fun applyQueries():HashMap<String, String> {
        val queries: HashMap<String,String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "gluten free"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}