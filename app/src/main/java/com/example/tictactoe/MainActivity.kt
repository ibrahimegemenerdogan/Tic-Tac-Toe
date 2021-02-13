package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val boardCells = Array (3) { arrayOfNulls<ImageView>(3)}

    var board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadBoard()

        button_reset.setOnClickListener {
            board = Board()

            mapBoard()

        }

    }

    private fun mapBoard(){
        for (i in board.board.indices){
            for(j in board.board.indices){
                when (board.board[i][j]){
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.circle)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER->{
                        boardCells[i][j]?.setImageResource(R.drawable.cross)
                        boardCells[i][j]?.isEnabled = false
                    }
                    else ->{
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true

                    }
                }
            }
        }

    }

    private fun LoadBoard () {
        for (i in boardCells.indices){
            for (j in boardCells.indices){
                boardCells[i][j] = ImageView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 350
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.setBackgroundResource(R.drawable.background_main)
                boardCells[i][j]?.setOnClickListener(CellClickListener(i,j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener (
        private val i:Int,
        private val j:Int) : View.OnClickListener{

        override fun onClick(p0: View){
            val cell = Cell(i,j)
            board.placeMOve(cell,Board.PLAYER)
            if(board.availenleCells.isNotEmpty()) {
                val cCell = board.availenleCells[Random.nextInt(0, board.availenleCells.size)]
                board.placeMOve(cCell, Board.COMPUTER)
            }
            mapBoard()
        }
    }
}