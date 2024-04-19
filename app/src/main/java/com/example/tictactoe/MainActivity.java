package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    //this is a 2d array for our buttons
    //we will be using this to check win conditions for player 1 and 2

    private boolean player1turn = true;
    //true = players 1 turn / false = players 2 turn

    private int roundcount;
    //this int will indicate a draw for us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //using this loop to find the and store all button into a 2d array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.RESET);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }


    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        String buttonText = clickedButton.getText().toString();
        if (!buttonText.isEmpty() && !buttonText.equals("-")) {
            return; // Exit if the button is already filled with "X" or "O"
        }

        if (player1turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundcount++;

        if (Winner()) {
            if (player1turn) {
                player1();
            } else {
                player2();
            }
        } else if (roundcount == 9) {
            draw();
        } else {
            player1turn = !player1turn;
        }

    }
    //this method will help us find the matching string to determine the winner
    private boolean Winner() {
        String[][] field = new String[3][3];

        // Populate field array with button text
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Get the text of each button
                String buttonText = buttons[i][j].getText().toString();
                field[i][j] = buttonText;
            }
        }

        // Check rows for a match
        for (int i = 0; i < 3; i++) {
            if (!field[i][0].equals("-") && field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])) {
                return true; // Row match found
            }
        }

        // Check columns for a match
        for (int i = 0; i < 3; i++) {
            if (!field[0][i].equals("-") && field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])) {
                return true; // Column match found
            }
        }

        // Check diagonals for a match
        if (!field[0][0].equals("-") && field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])) {
            return true; // Main diagonal match found
        }

        if (!field[0][2].equals("-") && field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])) {
            return true; // Anti-diagonal match found
        }

        return false; // No match found
    }




    private void player1(){
        Toast.makeText(this, "X Win!!", Toast.LENGTH_SHORT).show();

        resetBoard();
    }

    private void player2(){
        Toast.makeText(this, "O Win!!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
            }
        }

        roundcount = 0;
        player1turn = true;
    }
}