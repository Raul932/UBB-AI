#pragma once

class ChessBoard {
public:
    ChessBoard(int w, int h) : width(w), height(h) {}

    int getWidth() const { return width; }

    int getHeight() const { return height; }

private:
    int width;
    int height;
};
