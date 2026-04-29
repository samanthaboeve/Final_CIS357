package edu.gvsu.cis.cis357final.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "starred_fruits")
data class StarredFruit(
    @PrimaryKey val name: String
)

@Dao
interface FruitDao {

    @Query("SELECT * FROM starred_fruits")
    fun getAllStarred(): Flow<List<StarredFruit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun star(fruit: StarredFruit)

    @Delete
    suspend fun unstar(fruit: StarredFruit)
}

@Database(entities = [StarredFruit::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): FruitDao
}