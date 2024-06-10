package com.example.tictactoe_game;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    boolean playerOneActive;
    TextView playerOneScore, playerTwoScore, playerStatus;
    Button[] buttons = new Button[9];
    Button reset, playAgain;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int rounds;
    private int playerOneScoreCount, playerTwoScoreCount;
    private boolean gameOver; // Variable to track if the game is over



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            playerOneScore = findViewById(R.id.player_1_score);
            playerTwoScore = findViewById(R.id.player_2_score);
            playerStatus = findViewById(R.id.text_status);
            reset = findViewById(R.id.btn_reset);
            playAgain = findViewById(R.id.btn_playagain);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = findViewById(getResources().getIdentifier("button_" + (i + 1), "id", getPackageName()));
                buttons[i].setOnClickListener(this);
            }

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAgain();
                    playerOneScoreCount = 0;
                    playerTwoScoreCount = 0;
                    updatePlayerScore();
                }
            });

            playAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAgain();
                }
            });

            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            playerOneActive = true;
            rounds = 0;
            gameOver = false;
        }

        @Override
        public void onClick(View v) {
            if (gameOver || !((Button) v).getText().toString().equals("")) { // Check if game over or cell is already marked
                return;
            }

            String buttonId = v.getResources().getResourceEntryName(v.getId());
            int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1)) - 1;

            if (playerOneActive) {
                ((Button) v).setText("X");
                ((Button) v).setTextColor(Color.parseColor("#000000"));
                gameState[gameStatePointer] = 0;
            } else {
                ((Button) v).setText("0");
                ((Button) v).setTextColor(Color.parseColor("#ffffff"));
                gameState[gameStatePointer] = 1;
            }
            rounds++;

            if (checkWinner()) {
                gameOver = true;
                if (playerOneActive) {
                    playerOneScoreCount++;
                    updatePlayerScore();
                    playerStatus.setText("Player 1 has won");
                } else {
                    playerTwoScoreCount++;
                    updatePlayerScore();
                    playerStatus.setText("Player 2 has won");
                }
            } else if (rounds == 9) {
                gameOver = true;
                playerStatus.setText("No winner");
            } else {
                playerOneActive = !playerOneActive;
            }
        }

        private boolean checkWinner() {
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    return true;
                }
            }
            return false;
        }

        private void playAgain() {
            rounds = 0;
            playerOneActive = true;
            gameOver = false; // Reset gameOver to false
            for (int i = 0; i < buttons.length; i++) {
                gameState[i] = 2;
                buttons[i].setText("");
            }
            playerStatus.setText("Status");
        }

        private void updatePlayerScore() {
            playerOneScore.setText(String.valueOf(playerOneScoreCount));
            playerTwoScore.setText(String.valueOf(playerTwoScoreCount));
        }
    }


//

