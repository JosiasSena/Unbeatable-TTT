package handy.tictactoe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import handy.tictactoe.R;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.inject(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @OnClick(R.id.one_player)
    public void onePlayer(Button button){
        Intent intent = new Intent(MainMenu.this, HumanVsComputer.class);
        startActivity(intent);
    }

    @OnClick(R.id.two_player)
    public void twoPlayers(Button button){
        Intent intent = new Intent(MainMenu.this, HumanVsHuman.class);
        startActivity(intent);
    }

    @OnClick(R.id.exit_game)
    public void exitGame(Button button){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
