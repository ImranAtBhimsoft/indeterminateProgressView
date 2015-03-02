package uk.macrotechnologies.indeterminateProgress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.macrotechnologies.library.IndeterminateProgressView;

public class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IndeterminateProgressView progressIndicator=(IndeterminateProgressView)view.findViewById(R.id.progressIndicator);
        progressIndicator.setColorScheme(Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED);
    }
}