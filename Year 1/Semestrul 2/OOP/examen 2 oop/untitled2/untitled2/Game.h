#pragma once
#include "Piece.h"
#include <vector>
#include <cassert>

class Game {
public:
    Game(int boardWidth, int boardHeight) : board(boardWidth, boardHeight) {}

    void addPiece(Piece* piece) {
        pieces.push_back(piece);
    }

    void movePiece(Piece* piece, int x, int y) {
        piece->move(board, x, y);

        for (auto it = pieces.begin(); it != pieces.end(); ++it) {
            if (*it != piece && (*it)->getX() == x && (*it)->getY() == y) {
                delete *it;
                pieces.erase(it);
                break;
            }
        }
    }

    const ChessBoard& getBoard() const { return board; }

private:
    ChessBoard board;
    std::vector<Piece*> pieces;
};
