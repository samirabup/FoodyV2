package com.bup.foodyv2.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bup.foodyv2.models.FoodJoke
import com.bup.foodyv2.util.Constants.Companion.FOOD_JOKE_TABLE


@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}