package handy.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        Button one_player = (Button) findViewById(R.id.one_player);
        one_player.setOnClickListener(new OnePlayerOnclickListener());

        Button two_player = (Button) findViewById(R.id.two_player);
        two_player.setOnClickListener(new TwoPlayerOnclickListener());

        Button exit_game = (Button) findViewById(R.id.exit_game);
        exit_game.setOnClickListener(new ExitGameOnclickListener());
    }

    private class OnePlayerOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, HumanVsComputerPlayer.class);
            startActivity(intent);
        }
    }

    private class TwoPlayerOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainMenu.this, HumanVsHuman.class);
            startActivity(intent);
        }
    }

    private class ExitGameOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
