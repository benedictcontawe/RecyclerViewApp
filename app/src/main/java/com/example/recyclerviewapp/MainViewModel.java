package com.example.recyclerviewapp;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final MutableLiveData<Boolean> livePlay = new MutableLiveData<Boolean>(true);
    private final MutableLiveData<List<CustomModel>> liveModels = new MutableLiveData<List<CustomModel>>();
    private static final long COUNTDOWN_INTERVAL = 1000;
    public static final long
            IMMEDIATELY = 0L,
            THIRTY_SECONDS = 30 * 1000L,
            ONE_MINUTE = 1 * 60 * 1000L,
            FIVE_MINUTE = 5 * 60 * 1000L,
            TEN_MINUTES = 10 * 60 * 1000L,
            TWENTY_MINUTES = 20 * 60 * 1000L;
    private final List<CustomModel> models = new ArrayList<CustomModel>();

    public MainViewModel(Application application) {
        super(application);
        models.add(new CustomModel(0L, "Timer 0", observeTimerCountDown(IMMEDIATELY)));
        models.add(new CustomModel(1L, "Timer 1", observeTimerCountDown(THIRTY_SECONDS / COUNTDOWN_INTERVAL)));
        models.add(new CustomModel(2L, "Timer 2", observeTimerCountDown(ONE_MINUTE / COUNTDOWN_INTERVAL)));
        models.add(new CustomModel(3L, "Timer 3", observeTimerCountDown(FIVE_MINUTE / COUNTDOWN_INTERVAL)));
        models.add(new CustomModel(4L, "Timer 4", observeTimerCountDown(TEN_MINUTES / COUNTDOWN_INTERVAL)));
        models.add(new CustomModel(5L, "Timer 5", observeTimerCountDown(TWENTY_MINUTES / COUNTDOWN_INTERVAL)));
        liveModels.setValue(models);
    }

    public LiveData<List<CustomModel>> observeModels() {
        return liveModels;
    }

    public void addModel(String name, String duration) {
        models.add(new CustomModel(name, convertTimerCountDown(duration)));
        liveModels.setValue(models);
    }

    public boolean isDurationValid(String duration) {
        try {
            Long.parseLong(duration);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private String convertTimerCountDown(String input) {
        Log.d(TAG,"convertTimerCountDown " + input);
        long minutes = Long.parseLong(input) / 60;
        long seconds = Long.valueOf(input) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String observeTimerCountDown(Long input) {
        long minutes = input / 60;
        long seconds = input % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public long convertDurationToLong(String input) {
        try {
            final String[] timer = input.split(":", 2);
            final Long minutes = Long.parseLong(timer[0]) * 60;
            final Long seconds = Long.parseLong(timer[1]);
            Log.d(TAG,"convertToLong " + input + ", minutes " + minutes + ", seconds " + seconds);
            return (minutes + seconds) * COUNTDOWN_INTERVAL;
        } catch (NumberFormatException exception) {
            Log.e(TAG, " NumberFormatException " + exception);
            return IMMEDIATELY;
        }
    }

    private void startTimer() {
        Log.d(TAG,"startTimer");
        CountDownTimer countDownTimer = new CountDownTimer(convertDurationToLong(models.get(0).duration), COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                models.get(0).duration = observeTimerCountDown(millisUntilFinished / COUNTDOWN_INTERVAL);
                Log.d(TAG, "onTick " + models.get(0).duration);
                liveModels.setValue(models);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish " + models.get(0).duration);
                models.get(0).duration = String.valueOf(IMMEDIATELY);
                models.remove(0);
                liveModels.setValue(models);
                livePlay.setValue(true);
            }
        };
        countDownTimer.start();
    }
    //region Play and Pause
    public void toggleAnimatedVectorDrawable() {
        if (Boolean.TRUE.equals(livePlay.getValue())) {
            Log.d(TAG,"toggleAnimatedVectorDrawable true");
            livePlay.setValue(false);
            startTimer();
        }
        else {
            Log.d(TAG,"toggleAnimatedVectorDrawable false");
            //livePlay.setValue(true);
            //countDownTimer.cancel();
        }
    }

    public AnimatedVectorDrawableCompat getAnimatedVectorDrawableCompat(boolean isPlay) {
        final AnimatedVectorDrawableCompat play = AnimatedVectorDrawableCompat.create(getApplication(), R.drawable.anim_play_to_pause);
        final AnimatedVectorDrawableCompat pause = AnimatedVectorDrawableCompat.create(getApplication(), R.drawable.anim_pause_to_play);
        return isPlay == true ? pause : play;
    }

    public LiveData<Boolean> observeAnimatedVectorDrawable() {
        return livePlay;
    }
    //endregion
}