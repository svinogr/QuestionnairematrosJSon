package info.upump.questionnairematros;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by explo on 11.10.2017.
 */

public class SeniorAssistantFragment extends CaptainFragment{
    public static String TAG="ass";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CATEGORY = "старшие помощники капитана";

        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
