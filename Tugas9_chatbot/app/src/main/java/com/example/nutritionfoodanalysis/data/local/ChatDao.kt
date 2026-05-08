package com.example.nutritionfoodanalysis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Insert
    suspend fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllMessages()

    @Query("SELECT DISTINCT conversationId FROM chat_messages")
    fun getAllConversationIds(): Flow<List<String>>

    @Query("SELECT * FROM chat_messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessagesByConversation(conversationId: String): Flow<List<ChatMessage>>

    // Food Cache
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertFoodCache(cache: FoodAnalysisCache)

    @Query("SELECT * FROM food_analysis_cache WHERE foodName = :foodName")
    suspend fun getFoodCache(foodName: String): FoodAnalysisCache?
}
