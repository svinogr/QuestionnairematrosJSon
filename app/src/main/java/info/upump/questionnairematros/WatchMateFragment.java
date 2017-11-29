package info.upump.questionnairematros;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by explo on 11.10.2017.
 */

public class WatchMateFragment extends  CaptainFragment{
    public static String TAG="watch";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CATEGORY = "вахтенные помощники капитана";
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
