package com.es.snapgym;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by EYAL on 20/11/2018.
 */


public class SoundRhythm {

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
    private final LinkedList<Integer> fileInOrder;

    private boolean isPlaying = false;
    private MediaPlayer mp;

    private final TextView screenTextView;

    public SoundRhythm(LinkedList<Integer> times, int repeats, Context context, TextView tv){
        this.times = times;
        this.repeats = repeats;
        this.context = context;
        this.fileInOrder = createPlayList();
        this.screenTextView = tv;
    }

    public void run() {

        this.isPlaying = true;

        this.screenTextView.setVisibility(View.VISIBLE);

        int index = 0;
        startPlaying(index);

    }

    private LinkedList<Integer> createPlayList() {
        LinkedList<Integer> playList = new LinkedList<>();
        for (int set_index = 0; set_index < this.repeats; set_index++) {
            for (int time_index = 0; time_index < this.times.size(); time_index++) {
                playList.add(timeToAudio[this.times.get(time_index)]);
            }
        }
        return playList;
    }

    private void startPlaying(final int index) {

        if (index >= this.fileInOrder.size() || !this.isPlaying) {
            this.screenTextView.setVisibility(View.INVISIBLE);
            this.isPlaying = false;
            return;
        }

        mp = MediaPlayer.create(this.context, this.fileInOrder.get(index));

        // this function is called when the media play audio is finish
        // this function in not called when the media audio is stopped
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startPlaying(index + 1);
            }
        });
        mp.start();
    }

    public boolean isPlay() {
        this.screenTextView.setVisibility(View.INVISIBLE);
        return this.isPlaying;
    }

    public void stopPlay(){
        this.isPlaying = false;
        mp.stop();
    }

}
