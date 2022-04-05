/*
MovePool dipake buat nyimpen objek-objek move monstersaku.
Selain itu, class ini juga bisa dipakai sebagai atributnya Monster karena dia bisa punya beberapa moves.
*/

package com.monstersaku;
import java.util.ArrayList;
import java.util.List;
//import java.util.Arrays;

public class MovePool {
    private List<Move> listMove;

    public MovePool(){
        List<Move> listMove = new ArrayList<Move>();
        this.listMove = listMove;
    }

    public List<Move> getListMove(){
        return this.listMove;
    }

    public void addMove(Move m){
        (this.listMove).add(m);
    }

    public void printMovePool(){
        for (Move mo : listMove){
            System.out.printf("%d %s%n", mo.getMoveID(), mo.getMoveName());
        }
    }

    public void printMove(){
        int i = 1;
        for (Move m : listMove){
            System.out.printf("%d %s%n", i, m.getMoveName());
            i++;
        }
    }
}