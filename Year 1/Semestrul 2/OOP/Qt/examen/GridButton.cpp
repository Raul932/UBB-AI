#include "GridButton.h"

GridButton::GridButton(QWidget *parent) : QPushButton(parent) {
    connect(this, &QPushButton::clicked, this, &GridButton::emitPosition);
}

void GridButton::setPosition(int r, int c) {
    row = r;
    col = c;
}

int GridButton::getRow() const {
    return row;
}

int GridButton::getCol() const {
    return col;
}

void GridButton::emitPosition() {
    emit buttonClicked(row, col);
}
