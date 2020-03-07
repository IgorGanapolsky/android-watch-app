package com.igorganapolsky.vibratingwatchapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ITimersDao {

    @get:Query("SELECT * FROM timers")
    val all: LiveData<List<TimerEntity>>

    @Insert
    fun insert(entity: TimerEntity)

    @Update
    fun update(entity: TimerEntity)

    @Query("SELECT * FROM timers WHERE id = :id")
    fun observeById(id: Int): LiveData<TimerEntity>

    @Query("SELECT * FROM timers WHERE id = :id")
    fun getById(id: Int): TimerEntity

    @Query("DELETE FROM timers WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE timers SET state =:timerState")
    fun disableAll(timerState: String)

    @Query("UPDATE timers SET state =:timerState WHERE id =:timerId")
    fun updateTimerState(timerId: Int, timerState: String)
}
