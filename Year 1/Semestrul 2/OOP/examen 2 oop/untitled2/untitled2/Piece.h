#pragma once
#include "ChessBoard.h"

class Piece {
public:
    Piece(int x, int y) : posX(x), posY(y) {}

    virtual void move(ChessBoard& board, int x, int y) = 0;

    int getX() const { return posX; }

    int getY() const { return posY; }

protected:
    int posX; // x-coordinate of the piece
    int posY; // y-coordinate of the piece
};
