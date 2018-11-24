package com.es.snapgym;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
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

    private final RhythmAbstractClass rhythm;
    private final int repeats;
    private final Context context;
    private final LinkedList<Integer> fileInOrder;

    private boolean isPlaying = false;
    private MediaPlayer mp;

    private final TextView screenTextView;
    private final Button soundButton;

    public SoundRhythm(RhythmAbstractClass rhythm, int repeats, Context context, TextView tv, Button button){
        this.rhythm = rhythm;
        this.repeats = repeats;
        this.context = context;
        this.fileInOrder = createPlayList();
        this.screenTextView = tv;
        this.soundButton = button;
    }

    public void run() {

        this.isPlaying = true;

        this.screenTextView.setVisibility(View.VISIBLE);
        this.soundButton.setText("STOP");

        int index = 0;
        startPlaying(index);

    }

    private LinkedList<Integer> createPlayList() {
        LinkedList<Integer> playList = new LinkedList<>();

        if ("rhythm".equals(this.rhythm.getRhythmString()))
            return playList;

        playList.add(timeToAudio[this.rhythm.getPreparingTime()]);
        for (int set_index = 0; set_index < this.repeats; set_index++) {
            for (int time_index = 0; time_index < this.rhythm.getTimesLst().size(); time_index++) {
                playList.add(timeToAudio[this.rhythm.getTimesLst().get(time_index)]);
            }
        }
        return playList;
    }

    private void startPlaying(final int index) {

        if (index >= this.fileInOrder.size() || !this.isPlaying) {
            this.screenTextView.setVisibility(View.INVISIBLE);
            this.soundButton.setText("START");
            this.isPlaying = false;
            return;
        }

        mp = MediaPlayer.create(this.context, this.fileInOrder.get(index));
        if (index == 0)
            this.screenTextView.setText("GET READY");
        else
            this.screenTextView.setText("" + (int)Math.floor((index - 1) / this.rhythm.getTimesLst().size()));


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
        this.soundButton.setText("START");
        this.screenTextView.setVisibility(View.INVISIBLE);
        return this.isPlaying;
    }

    public void stopPlay(){
        this.isPlaying = false;
        mp.stop();
    }

}
