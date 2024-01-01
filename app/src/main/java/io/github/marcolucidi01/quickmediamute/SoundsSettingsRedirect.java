// see license file for copyright and license details
package io.github.marcolucidi01.quickmediamute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class SoundsSettingsRedirect extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String[] settings = new String[] {
            Settings.ACTION_SOUND_SETTINGS,
            "com.android.settings.SOUND_SETTINGS", // saw it in the source code of another app, probably legacy
            Settings.ACTION_SETTINGS,
        };
        for (String s : settings) {
            Intent intent = new Intent(s);
            // safeguard since in some cases, a matching Activity may not exist
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                break;
            }
        }

        finish();
    }
}
