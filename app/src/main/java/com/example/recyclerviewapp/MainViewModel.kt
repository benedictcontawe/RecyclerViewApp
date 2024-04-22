package com.example.recyclerviewapp

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import kotlinx.coroutines.delay

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.getSimpleName()
        private const val COUNTDOWN_INTERVAL: Long = 1000
        public const val IMMEDIATELY : Long = 0L
        public const val TEN_SECONDS : Long = 10 * 1000L
        public const val THIRTY_SECONDS : Long = 30 * 1000L
        public const val ONE_MINUTE : Long = 1 * 60 * 1000L
        public const val FIVE_MINUTE : Long = 5 * 60 * 1000L
        public const val TEN_MINUTES : Long = 10 * 60 * 1000L
        public const val TWENTY_MINUTES : Long = 20 * 60 * 1000L
    }

    private val livePlay : MutableLiveData<Boolean> = MutableLiveData(true)
    private val liveModels : MutableLiveData<List<CustomModel>> = MutableLiveData<List<CustomModel>>()
    private val models : MutableList<CustomModel> = mutableListOf<CustomModel>()

    public constructor(application : Application) : super(application) {
        models.add(CustomModel(0L, "Timer 0", observeTimerCountDown(IMMEDIATELY)))
        models.add(CustomModel(1L,"Timer 1",observeTimerCountDown(TEN_SECONDS / COUNTDOWN_INTERVAL)))
        models.add(CustomModel(2L,"Timer 2",observeTimerCountDown(THIRTY_SECONDS / COUNTDOWN_INTERVAL)))
        models.add(CustomModel(3L,"Timer 3",observeTimerCountDown(ONE_MINUTE / COUNTDOWN_INTERVAL)))
        models.add(CustomModel(4L,"Timer 4",observeTimerCountDown(FIVE_MINUTE / COUNTDOWN_INTERVAL)))
        models.add(CustomModel(5L, "Timer 5",observeTimerCountDown(TEN_MINUTES / COUNTDOWN_INTERVAL)))
        models.add(CustomModel(6L,"Timer 6",observeTimerCountDown(TWENTY_MINUTES / COUNTDOWN_INTERVAL)))
        liveModels.setValue(models)
    }

    public fun observeModels() : LiveData<List<CustomModel>> {
        return liveModels
    }

    public fun addModel(name : String?, duration : String) {
        models.add(CustomModel(name, convertTimerCountDown(duration)))
        liveModels.setValue(models)
    }

    public fun isDurationValid(duration : String) : Boolean {
        return try {
            duration.toLong()
            true
        } catch (exception: NumberFormatException) {
            false
        }
    }

    private fun convertTimerCountDown(input : String) : String {
        Log.d(TAG,"convertTimerCountDown $input")
        val minutes = input.toLong() / 60
        val seconds = input.toLong() % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun observeTimerCountDown(input : Long) : String {
        val minutes = input / 60
        val seconds = input % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun convertDurationToLong(input : String) : Long {
        return try {
            val timer : Array<String> = input.split(":".toRegex(), limit = 2).toTypedArray()
            val minutes : Long = if (timer.isNotEmpty()) timer[0].toLong() * 60 else 0
            val seconds : Long = if (timer.size > 1) timer[1].toLong() else 0
            Log.d(TAG,"convertToLong $input, minutes $minutes, seconds $seconds")
            (minutes + seconds) * COUNTDOWN_INTERVAL
        } catch (exception: NumberFormatException) {
            Log.e(TAG," NumberFormatException $exception")
            IMMEDIATELY
        }
    }

    private fun startTimer() {
        Log.d(TAG, "startTimer")
        CoroutinesUtils.default(this@MainViewModel, work = {
            while (livePlay.getValue() == false) {
                delay(COUNTDOWN_INTERVAL);
                updateSerialTimer()
                //updateParallelTimers()
            }
        });
    }

    private suspend fun updateSerialTimer() {
        val left : Long = convertDurationToLong(models.get(0).duration) - COUNTDOWN_INTERVAL
        if (left <= IMMEDIATELY) {
            Log.d(TAG, "updateSerialTimer onFinish " + models.get(0).duration)
            models.get(0).duration = IMMEDIATELY.toString()
            models.removeAt(0)
            livePlay.postValue(true);
        } else if (livePlay.getValue() == false) {
            models.get(0).duration = observeTimerCountDown(left / COUNTDOWN_INTERVAL);
            Log.d(TAG, "updateSerialTimer onTick " + models.get(0).duration)
        }
        liveModels.postValue(models)
    }

    private suspend fun updateParallelTimers() {
        models.sortedBy { model : CustomModel -> convertDurationToLong(model.duration) }
        if (convertDurationToLong(models.get(0).duration) <= IMMEDIATELY) {
            models.removeAt(0)
            livePlay.postValue(true)
        }
        models.forEach { model : CustomModel ->
            val left : Long = convertDurationToLong(model.duration) - COUNTDOWN_INTERVAL
            if (left <= IMMEDIATELY) {
                Log.d(TAG, "updateParallelTimers onFinish " + model.duration)
                model.duration = observeTimerCountDown(IMMEDIATELY)
            } else if (livePlay.getValue() == false) {
                model.duration = observeTimerCountDown(left / COUNTDOWN_INTERVAL);
                Log.d(TAG, "updateParallelTimers onTick " + model.duration)
            }
        }
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            models.removeIf { model : CustomModel ->
                val left : Long = convertDurationToLong(model.duration) - COUNTDOWN_INTERVAL
                left <= IMMEDIATELY
            }
        } else {
            val filtered : List<CustomModel> = models.filter { model : CustomModel ->
                val left : Long = convertDurationToLong(model.duration) - COUNTDOWN_INTERVAL
                left <= IMMEDIATELY
            }
            filtered.map { model : CustomModel ->
                models.remove(model)
            }
        }
        */
        liveModels.postValue(models)
    }
    //region Play and Pause
    public fun toggleAnimatedVectorDrawable() {
        if (java.lang.Boolean.TRUE == livePlay.getValue()) {
            Log.d(TAG, "toggleAnimatedVectorDrawable true")
            livePlay.setValue(false)
            startTimer()
        } else {
            Log.d(TAG, "toggleAnimatedVectorDrawable false")
            //livePlay.setValue(true);
            //countDownTimer.cancel();
        }
    }

    public fun getAnimatedVectorDrawableCompat(isPlay : Boolean) : AnimatedVectorDrawableCompat? {
        val play = AnimatedVectorDrawableCompat.create(getApplication(), R.drawable.anim_play_to_pause)
        val pause = AnimatedVectorDrawableCompat.create(getApplication(), R.drawable.anim_pause_to_play)
        return if (isPlay == true) pause else play
    }

    public fun observeAnimatedVectorDrawable() : LiveData<Boolean> {
        return livePlay
    }
    //endregion
}