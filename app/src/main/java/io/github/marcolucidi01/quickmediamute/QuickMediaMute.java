// see license file for copyright and license details
package io.github.marcolucidi01.quickmediamute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class QuickMediaMute extends TileService
{
    private static final String PKG = QuickMediaMute.class.getPackage().getName();
    private static final Icon IC_MEDIA_AUDIO_ON = Icon.createWithResource(PKG, R.drawable.ic_media_audio_on_24);
    private static final Icon IC_MEDIA_AUDIO_OFF = Icon.createWithResource(PKG, R.drawable.ic_media_audio_off_24);

    private final VolumeChangedReceiver volumeChangedReceiver = new VolumeChangedReceiver(this);
    // `android.media.VOLUME_CHANGED_ACTION` is an internal "undocumented" broadcast intent, so it
    // could break at any time, but works for me (for now)
    private final IntentFilter volumeChangedActionFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");

    @Override
    public void onTileAdded()
    {
        updateTile();
    }

    @Override
    public void onStartListening()
    {
        updateTile();
        // trigger a tile update if the volume changes *while* the quick settings menu is open
        registerReceiver(volumeChangedReceiver, volumeChangedActionFilter);
    }

    @Override
    public void onStopListening()
    {
        unregisterReceiver(volumeChangedReceiver);
    }

    @Override
    public void onClick()
    {
        AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_TOGGLE_MUTE, AudioManager.FLAG_SHOW_UI);
        updateTile();
    }

    public void updateTile()
    {
        AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int state = audio.isStreamMute(AudioManager.STREAM_MUSIC) || audio.getStreamVolume(AudioManager.STREAM_MUSIC) <= 0
                  ? Tile.STATE_INACTIVE
                  : Tile.STATE_ACTIVE;

        Tile tile = getQsTile();
        if (tile.getState() == state)
            return;

        tile.setState(state);
        if (state == Tile.STATE_ACTIVE) {
            tile.setIcon(IC_MEDIA_AUDIO_ON);
            tile.setLabel(getString(R.string.label_media_audio_on));
        } else {
            tile.setIcon(IC_MEDIA_AUDIO_OFF);
            tile.setLabel(getString(R.string.label_media_audio_off));
        }

        tile.updateTile();
    }

    private static class VolumeChangedReceiver extends BroadcastReceiver
    {
        private final QuickMediaMute quickMediaMute;

        public VolumeChangedReceiver(QuickMediaMute quickMediaMute)
        {
            this.quickMediaMute = quickMediaMute;
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            quickMediaMute.updateTile();
        }
    }
}
