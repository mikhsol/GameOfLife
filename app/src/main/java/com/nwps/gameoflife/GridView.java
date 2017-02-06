package com.nwps.gameoflife;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

class GridView extends View {
    public static final String KEY_PREF_ROWS = "pref_rows";
    public static final String KEY_PREF_COLS = "pref_cols";
    public static final String KEY_PREF_DENS = "pref_dens";

    public static final int PAUSE = 0;
    public static final int RUNNING = 1;

    public static int ROWS;
    public static int COLS;

    private Game game;

    private long delay = 250;

    private RefreshHandler redrawHandler = new RefreshHandler();

    private class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            GridView.this.update();
            GridView.this.invalidate();
        }

        void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ROWS = Integer.parseInt(sharedPreferences.getString(KEY_PREF_ROWS, "100"));
        COLS = Integer.parseInt(sharedPreferences.getString(KEY_PREF_COLS, "100"));
        double dens = Double.parseDouble(sharedPreferences.getString(KEY_PREF_DENS, "30"));

        game = new Game(ROWS, COLS);
        game.init();
        try {
            game.fillGridRandomly(dens/100);
        } catch (WrongGridDensityException e) {
            e.printStackTrace();
        }
        initGridView();
    }

    public void setMode(int mode) {
        if (mode == RUNNING) {
            update();
            return;
        }
        if (mode == PAUSE) {
            // TODO: implement.
        }
    }

    private void update() {
        game.createNextGeneration();
        redrawHandler.sleep(delay);
    }

    private void initGridView() {
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(ContextCompat.getColor(getContext(), R.color.background));

        Paint cell = new Paint();
        background.setColor(ContextCompat.getColor(getContext(), R.color.cell));

        int width = getWidth();
        int height = getHeight();
        canvas.drawRect(0, 0, width, height, background);

        int[][] grid = game.getGrid();
        int cell_size = (width > height) ? height / COLS : width / COLS;
        if (cell_size < 2) cell_size = 2;

        for (int h = 0; h < ROWS; h++) {
            for (int w = 0; w < COLS; w++) {
                if (grid[h][w] != 0) {
                    canvas.drawRect(w*cell_size,
                            h*cell_size,
                            w*cell_size + cell_size - 1,
                            h*cell_size + cell_size - 1,
                            cell);
                }
            }
        }
    }

}
