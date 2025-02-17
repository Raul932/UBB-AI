#pragma once
#include "Piece.h"

class Rook : public Piece {
public:
    Rook(int x, int y) : Piece(x, y) {}

    void move(ChessBoard& board, int x, int y) override;

private:
    bool isValidMove(int newX, int newY, const ChessBoard& board) const;
};
