package com.bup.foodyv2.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bup.foodyv2.models.Result
import com.bup.foodyv2.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)