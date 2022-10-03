package com.khanapps.tic_tac_toe

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameBoard: List<TicBlock>
    private var alternator = true
    private lateinit var start: Button
    private lateinit var reset: Button
    private lateinit var playerXscore: TextView
    private lateinit var playerOscore: TextView
    private lateinit var timer: TextView
    private lateinit var player_x: Player
    private lateinit var player_o: Player
    private var tieCheck = 0
    lateinit var clock : CountDownTimer
    var gameOver = false
    var checked = mutableListOf<Int>(1,2,3,4,5,6,7,8,9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer = findViewById(R.id.timer)
        start = findViewById<Button>(R.id.start_button)
        reset = findViewById<Button>(R.id.reset_button)
        playerXscore = findViewById(R.id.player_x_score)
        playerOscore = findViewById(R.id.player_o_score)
        reset.isEnabled = false

        clock = object : CountDownTimer(4000,1000){
            override fun onTick(p0: Long) {
                timer.text = (p0/1000).toString()
            }

            override fun onFinish() {
                timesUp()
            }
        }

        player_x = initiatePlayers('x', 0)
        player_o = initiatePlayers('o', 0)

        val x = resources.getDrawable(R.drawable.xbox)
        val o = resources.getDrawable(R.drawable.obox)
        val box = resources.getDrawable(R.drawable.box)

        gameBoard = listOf(
            TicBlock(findViewById(R.id.block1), 1),
            TicBlock(findViewById(R.id.block2), 2),
            TicBlock(findViewById(R.id.block3), 3),
            TicBlock(findViewById(R.id.block4), 4),
            TicBlock(findViewById(R.id.block5), 5),
            TicBlock(findViewById(R.id.block6), 6),
            TicBlock(findViewById(R.id.block7), 7),
            TicBlock(findViewById(R.id.block8), 8),
            TicBlock(findViewById(R.id.block9), 9)
        )
    //Click listener to start the Game. Disabled once clicked since "Restart" takes over to run the game after
        start.setOnClickListener {
            startGame(gameBoard, x, o)
            Toast.makeText(this, "Game Started!", Toast.LENGTH_SHORT).show()
            start.isEnabled = false
            reset.isEnabled = true
            timer.visibility = View.VISIBLE
        }

    //Reset button, essentially used to reset the game board
        reset.setOnClickListener {
            resetGame(gameBoard, box)
            Toast.makeText(this, "Game Restarted!", Toast.LENGTH_SHORT).show()
        }
    }

    //Method used in conjunction with start button to start the game and build board
    private fun startGame(board: List<TicBlock>, xturn: Drawable, oturn: Drawable) {
        board.forEach { block ->
            block.image.setOnClickListener {
                setImage(block.image, alternator, xturn, oturn, block.place)
                alternator = !alternator
                clock.cancel()
                clock.start()
            }
        }
        timer.visibility = View.VISIBLE
    }

    //Function to set proper player image (x or o) to the block pressed. And checks if the user wins
    private fun setImage(
        image: ImageView,
        turn: Boolean,
        xturn: Drawable,
        oturn: Drawable,
        position: Int
    ) {
        if (turn) {
            image.setImageDrawable(xturn)
            if (player_x.isWinner(position)) {
                gameOver = true
                timer.visibility = View.INVISIBLE
                Log.d("Game FINISHED", "Winner is X")
                Toast.makeText(this, "Player X wins!", Toast.LENGTH_SHORT).show()
                player_x.score++
                playerXscore.text = player_x.score.toString()
                gameFinished(gameBoard, player_x.score, player_o.score)

            }
        } else {
            image.setImageDrawable(oturn)
            if (player_o.isWinner(position)) {
                gameOver = true
                timer.visibility = View.INVISIBLE
                Log.d("Game FINISHED", "Winner is O")
                Toast.makeText(this, "Player O wins!", Toast.LENGTH_SHORT).show()
                player_o.score++
                playerOscore.text = player_o.score.toString()
                gameFinished(gameBoard, player_x.score, player_o.score)
            }
        }
        tieCheck++
        if (tieCheck == 9) {
            gameOver = true
            timer.visibility = View.INVISIBLE
            Toast.makeText(this, "It is a Draw,reset the game!", Toast.LENGTH_SHORT).show()
        }
        image.isClickable = false

        checked.remove(position)
    }

    private fun resetGame(board: List<TicBlock>, box: Drawable) {
        checked = mutableListOf<Int>(1,2,3,4,5,6,7,8,9)
        gameOver = false
        clock.cancel()
        timer.text = "0"
        timer.visibility = View.VISIBLE
        tieCheck = 0
        gameFinished(gameBoard, player_x.score, player_o.score)
        board.forEach {
            it.image.setImageDrawable(box)
            it.image.isClickable = true
        }
        alternator = true
    }

    //Builder to initiate player objects with the new win condition lists
    private fun initiatePlayers(letter: Char, score: Int): Player {
        return Player(
            letter, mutableListOf<MutableList<Int>>(
                mutableListOf(1, 2, 3),
                mutableListOf(4, 5, 6),
                mutableListOf(7, 8, 9),
                mutableListOf(1, 4, 7),
                mutableListOf(2, 5, 8),
                mutableListOf(3, 6, 9),
                mutableListOf(1, 5, 9),
                mutableListOf(3, 5, 7)
            ), score
        )
    }

    //Game is finished, reset the players but keep the score
    private fun gameFinished(board: List<TicBlock>, scoreX: Int, scoreO: Int) {
        board.forEach { block ->
            block.image.isClickable = false
        }
        tieCheck = 0
        player_x = initiatePlayers('x', scoreX)
        player_o = initiatePlayers('o', scoreO)
    }

    private fun timesUp() {
        if (!gameOver) {
            val chosen = checked.random()
            checked.remove(chosen)
            gameBoard[chosen - 1].image.performClick()
        }
    }

}