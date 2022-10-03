package com.khanapps.tic_tac_toe

class Player(var letter: Char,var winCondition: MutableList<MutableList<Int>>,var score : Int) {

    fun isWinner(position : Int) :Boolean{
        winCondition.forEach{ winCon ->
            winCon.remove(position)
            if(winCon.isEmpty())
                return true
        }
        return false
    }
}