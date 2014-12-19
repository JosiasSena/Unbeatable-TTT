package handy.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HumanVsComputerPlayer extends ActionBarActivity {
    private AlertDialog.Builder mDialog;
    private Board board;
    private GameRules gameRules;
    private ComputerPlayer computerPlayer;
    private ArrayList<View> touchables;
    private static final HashMap<String, Integer> cellToMoveMap;

    // Various textviews to display
    private TextView human;
    private TextView humanCount;
    private TextView mTieCount;
    private TextView computer;
    private TextView computerCount;

    // Counters for the wins and ties
    private int humanCounter = 0;
    private int mTieCounter = 0;
    private int computerCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        setUpActionBar();

        board = new GameBoard();
        gameRules = new GameRules(board);
        computerPlayer = new ComputerPlayer(board, gameRules);
        touchables = findViewById(R.id.tableLayout).getTouchables();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            // This string gets the status that is being commented on
            mTieCounter = extra.getInt("tieCounter");
            computerCounter = extra.getInt("computerCounter");
        }

        init();
    }

    private void init() {
        // setup the textviews
        humanCount = (TextView) findViewById(R.id.humanCount);
        human = (TextView) findViewById(R.id.human);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        computerCount = (TextView) findViewById(R.id.conputerCount);
        computer = (TextView) findViewById(R.id.computer);

        // set the initial counter display values
        humanCount.setText(Integer.toString(humanCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        computerCount.setText(Integer.toString(computerCounter));

        human.setText("You: ");
        computer.setText("Computer: ");

        initmDialog();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Computer vs. Human");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the 'cellToMoveMap' HashMap
     */
    static {
        cellToMoveMap = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            /** These are the tags applied to the buttons in the XML X 2
             *  b/c we have to check backwards as well
             */
            cellToMoveMap.put("c" + i, i);
        }
    }

    /**
     * Run everytime a cell is clicked
     *
     * @param view
     * @throws Exception
     */
    public void runGame(View view) throws Exception {
        int move = setButtonText((Button) view);
        setHumanMove(move);
        setButtonStatus(false);
        if (!gameRules.isGameOver()) {
            // keep going until the game ends
            getComputerMove();
        } else {
            // This is impossible!!
            gameOver("You are the winner!");
            setButtonStatus(false);
        }
    }

    /**
     * Deactivate button after click
     *
     * @param state
     */
    void setButtonStatus(boolean state) {
        for (View touchable : touchables) {
            if (touchable instanceof Button) {
                touchable.setEnabled(state);
            }
        }
    }

    private int setButtonText(Button view) {
        view.setTextColor(Color.parseColor("#FF4444"));
        view.setText(R.string.marker_X);
        view.setClickable(false);
        return convertCellToInt((String) view.getTag());
    }

    /**
     * What to do on GameOver
     *
     * @param marker
     */
    private void gameOver(String marker) {
        if (gameRules.isDraw()) {
            mDialog.setTitle(R.string.gameOver);
            mDialog.setMessage(R.string.draw);
            mDialog.show();
            mTieCounter++;
            mTieCount.setText(Integer.toString(mTieCounter));
        } else {
            mDialog.setTitle(R.string.gameOver);
            mDialog.setMessage(marker);
            mDialog.show();
            computerCounter++;
            computerCount.setText(Integer.toString(computerCounter));
        }

    }

    void mapCells(char[] grid) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] == 'O') {
                String buttonID = "c" + i;
                Button button = (Button) findViewById(R.id.tableLayout).findViewWithTag(buttonID);
                button.setText(R.string.marker_O);
                button.setClickable(false);
            }
        }
    }

    int convertCellToInt(String cellID) {
        return cellToMoveMap.get(cellID);
    }

    void setHumanMove(int move) throws Exception {
        board.setCell('X', move);
    }

    void getComputerMove() {
        new ComputerMoveTask().execute();
    }

    /**
     * Get the next computer move
     */
    private class ComputerMoveTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                computerPlayer.selectMove();
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        protected void onPostExecute(Integer result) {
            mapCells(board.getGrid());
            if (gameRules.isGameOver()) {
                setButtonStatus(false);
                gameOver("The computer won.");
            } else {
                setButtonStatus(true);
            }
        }
    }

    private void initmDialog() {
        mDialog = new AlertDialog.Builder(this);
        mDialog.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), HumanVsComputerPlayer.class);
                intent.putExtra("computerCounter", computerCounter);
                intent.putExtra("tieCounter", mTieCounter);
                startActivity(intent);
            }
        });

        mDialog.setNegativeButton("Go to menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
    }
}
