package com.igorganapolsky.vibratingwatchapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.igorganapolsky.vibratingwatchapp.common.Mappers
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel
import java.util.*
import java.util.concurrent.ExecutorService

class WatchRepository(private val timerDb: TimersDatabase, private val executor: ExecutorService) :
    ITimersRepository {

    init {
        disableAllTimers()
    }

    override fun getTimerById(timerId: Int): TimerModel {
        val timer = timerDb.timersDao().getById(timerId)
        return Mappers.mapToTimerModel(timer)
    }

    override fun getAllTimers(): LiveData<List<TimerModel>> {
        return Transformations.map(timerDb.timersDao().all) { list ->
            val mappedList = ArrayList<TimerModel>(list.size)
            for (timer in list) {
                mappedList.add(Mappers.mapToTimerModel(timer))
            }
            mappedList
        }
    }

    override fun updateTimer(timer: TimerModel) {
        executor.execute {
            val timerEntity = Mappers.mapToTimerEntity(timer)
            timerEntity.timerId = timer.id
            timerDb.timersDao().update(timerEntity)
        }
    }

    override fun updateTimerState(timerId: Int, newState: TimerModel.State) {
        executor.execute { timerDb.timersDao().updateTimerState(timerId, newState.name) }
    }

    override fun saveTimer(timer: TimerModel) {
        executor.execute { timerDb.timersDao().insert(Mappers.mapToTimerEntity(timer)) }
    }

    override fun deleteTimer(timerId: Int) {
        executor.execute { timerDb.timersDao().deleteById(timerId) }
    }

    private fun disableAllTimers() {
        executor.execute { timerDb.timersDao().disableAll(TimerModel.State.FINISHED.name) }
    }

}
