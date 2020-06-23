package com.igorganapolsky.vibratingwatchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
class TimerEntity {

    @PrimaryKey(autoGenerate = true)
    var timerId: Int = 0

    @ColumnInfo(name = "time")
    var milliseconds: Long = 0

    @ColumnInfo(name = "buzz_type")
    var buzzType: String? = null

    @ColumnInfo(name = "buzz_count")
    var buzzCount: Int = 0

    @ColumnInfo(name = "buzz_time")
    var buzzTime: Int = 0

    @ColumnInfo(name = "repeat")
    var repeat: Int = 0

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    @ColumnInfo(name = "state")
    var state: String? = null

    companion object {
        val TIMER_ID = "timer_id"
    }
}
