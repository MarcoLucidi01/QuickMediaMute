// see license file for copyright and license details
package io.github.marcolucidi01.quickmediamute;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class MediaMuteToggleActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_TOGGLE_MUTE, 0);

        finish();
    }
}
