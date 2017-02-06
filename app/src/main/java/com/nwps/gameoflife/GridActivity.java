package com.nwps.gameoflife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class GridActivity extends AppCompatActivity {
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setMode(GridView.RUNNING);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gridView.setMode(GridView.PAUSE);
    }
}
