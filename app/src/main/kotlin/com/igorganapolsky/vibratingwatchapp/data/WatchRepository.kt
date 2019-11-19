package com.igorganapolsky.vibratingwatchapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.igorganapolsky.vibratingwatchapp.domain.model.Mappers
import com.igorganapolsky.vibratingwatchapp.domain.model.model.TimerModel
import java.util.*
import java.util.concurrent.ExecutorService

class WatchRepository(private val timerDb: TimersDatabase, private val executor: ExecutorService) :
    IRepository {
    init {
        disableAll()
    }

    override fun getTimerById(id: Int): TimerModel {
        val timer = timerDb.timersDao().getById(id)
        return Mappers.mapToTimerModel(timer)
    }

    override fun getAll(): LiveData<List<TimerModel>> {
        return Transformations.map(timerDb.timersDao().all) { list ->
            val mappedList = ArrayList<TimerModel>(list.size)
            for (timer in list) {
                mappedList.add(Mappers.mapToTimerModel(timer))
            }
            mappedList
        }
    }

    override fun updateTimer(model: TimerModel) {
        executor.execute {
            val timerEntity = Mappers.mapToTimerEntity(model)
            timerEntity.id = model.id
            timerDb.timersDao().update(timerEntity)
        }
    }

    override fun saveTimer(model: TimerModel) {
        executor.execute { timerDb.timersDao().insert(Mappers.mapToTimerEntity(model)) }
    }

    override fun deleteTimer(id: Int) {
        executor.execute { timerDb.timersDao().deleteById(id) }
    }

    override fun updateTimerState(timerId: Int, newState: TimerModel.State) {
        executor.execute { timerDb.timersDao().updateTimerState(timerId, newState.name) }
    }

    private fun disableAll() {
        executor.execute { timerDb.timersDao().disableAll(TimerModel.State.FINISHED.name) }
    }
}
