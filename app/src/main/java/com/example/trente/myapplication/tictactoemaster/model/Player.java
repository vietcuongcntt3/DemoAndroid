/**
 *  lop dung chung cho computer player va human player
 */
package com.example.trente.myapplication.tictactoemaster.model;

public interface Player {
	public Point movePoint(int player);

	int getPlayerFlag();

	void setPlayerFlag(int playerFlag);

	BoardState getBoardState();
}
