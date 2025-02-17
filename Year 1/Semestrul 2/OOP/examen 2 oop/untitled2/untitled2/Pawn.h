#pragma once
#include "Piece.h"

class Pawn : public Piece {
public:
    Pawn(int x, int y) : Piece(x, y) {}

    void move(ChessBoard& board, int x, int y) override;

private:
    bool isValidMove(int newX, int newY, const ChessBoard& board) const;
};
