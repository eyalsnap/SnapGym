package com.es.snapgym;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.LinkedList;

/**
 * Created by EYAL on 20/11/2018.
 */


public class SoundRhythm extends Thread {

    private static final int [] timeToAudio = {-1,
                                                R.raw.counting1,
                                                R.raw.counting2,
                                                R.raw.counting3,
                                                R.raw.counting4,
                                                R.raw.counting5,
                                                R.raw.counting6,
                                                R.raw.counting7,
                                                R.raw.counting8,
                                                R.raw.counting9,
                                                R.raw.counting10,
                                                R.raw.counting11,
                                                R.raw.counting12};

    private MediaPlayer ring;

    private final LinkedList<Integer> times;
    private final int repeats;
    private final Context context;
    private boolean dontStop = false;

    public SoundRhythm(LinkedList<Integer> times, int repeats, Context context){
        this.times = times;
        this.repeats = repeats;
        this.context = context;
    }

    @Override
    public void run() {

        dontStop = true;
        for (int set_index = 0; dontStop && set_index < this.repeats; set_index++) {
            for (int time_index = 0; dontStop && time_index < this.times.size(); time_index++) {
                ring = MediaPlayer.create(this.context, timeToAudio[this.times.get(time_index)]);
                ring.start();
                while (ring.isPlaying()) {
                    if (!dontStop) {
                        ring.stop();
                        return;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        dontStop = false;
    }


    public void stopSound(){
        dontStop = false;
    }

    public boolean isPlay(){
        return dontStop;
    }

}
