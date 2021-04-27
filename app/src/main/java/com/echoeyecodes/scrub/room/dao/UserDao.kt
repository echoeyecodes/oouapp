package com.echoeyecodes.scrub.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.echoeyecodes.scrub.models.UserModel

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(models: UserModel)

    @Query("DELETE FROM users")
    abstract suspend fun deleteUsers()

    @Query("SELECT * FROM users LIMIT 1")
    abstract fun getUserLiveData(): LiveData<UserModel?>

    @Query("SELECT * FROM users LIMIT 1")
    abstract suspend fun getUser(): UserModel?

}