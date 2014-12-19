package handy.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class HumanVsHuman extends ActionBarActivity {
    private AlertDialog.Builder mDialog;
    private Board board;
    private GameRules gameRules;
    private ArrayList<View> touchables;

    private int turnCount = 0;

    // Various textviews to display
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    // Counters for the wins and ties
    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        setUpActionBar();

        board = new GameBoard();
        gameRules = new GameRules(board);
        touchables = findViewById(R.id.tableLayout).getTouchables();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            // This string gets the status that is being commented on
            mPlayerOneCounter = extra.getInt("playerOneCounter");
            mPlayerTwoCounter = extra.getInt("playerTwoCounter");
            mTieCounter = extra.getInt("tieCounter");
        }

        init();
    }

    private void init(){
        // setup the textviews
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.conputerCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.computer);

        // set the initial counter display values
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        mPlayerOneText.setText("Player One: ");
        mPlayerTwoText.setText("Player Two: ");

        initmDialog();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Human vs. Human");
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

    private static final HashMap<String, Integer> cellToMoveMap;
    static {
        cellToMoveMap = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            cellToMoveMap.put("c" + i, i);
        }
    }

    public void runGame(View view) throws Exception {
        char marker = getCurrentPlayer();
        setButtonStatus(false);
        setButtonText((Button) view, marker);
        int move = getButtonIndex((Button) view);
        setHumanMove(move, marker);
        setButtonStatus(true);
        if (gameRules.isGameOver()) {
            setButtonStatus(false);
            gameOver(marker);
        }
        turnCount ++;
    }

    char getCurrentPlayer() {
        if (turnCount % 2 == 0) {
            return 'X';
        } else {
            return 'O';
        }
    }

    void setButtonStatus(boolean state) {
        for (View touchable : touchables) {
            if (touchable instanceof Button) {
                touchable.setEnabled(state);
            }
        }
    }

    private void setButtonText(Button view, char marker) {
        if (marker == 'X') {
            view.setTextColor(Color.parseColor("#FF4444"));
            view.setText(R.string.marker_X);
        } else if (marker == 'O') {
            view.setText(R.string.marker_O);
        }
    }

    private int getButtonIndex(Button view) {
        view.setClickable(false);
        return convertCellToInt((String) view.getTag());
    }

    void setHumanMove(int move, char marker) throws Exception {
        board.setCell(marker, move);
    }

    int convertCellToInt(String cellID) {
        return cellToMoveMap.get(cellID);
    }

    private void gameOver(char marker) {
        if (gameRules.isDraw()) {
            mDialog.setTitle(R.string.gameOver);
            mDialog.setMessage(R.string.draw);
            mDialog.show();
            mTieCounter++;
            mTieCount.setText(Integer.toString(mTieCounter));
        } else {
            mDialog.setTitle(R.string.gameOver);

            if (marker == 'X'){
                mDialog.setMessage("Player one won!");
                mPlayerOneCounter++;
                mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
            }else{
                mDialog.setMessage("Player two won!");
                mPlayerTwoCounter++;
                mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
            }

            mDialog.show();
        }
    }

    private void initmDialog(){
        mDialog = new AlertDialog.Builder(this);
        mDialog.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), HumanVsHuman.class);
                intent.putExtra("playerOneCounter", mPlayerOneCounter);
                intent.putExtra("playerTwoCounter", mPlayerTwoCounter);
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