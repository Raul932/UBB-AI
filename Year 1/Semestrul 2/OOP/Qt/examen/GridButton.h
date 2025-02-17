#ifndef GRIDBUTTON_H
#define GRIDBUTTON_H

#include <QPushButton>

class GridButton : public QPushButton {
    Q_OBJECT

public:
    GridButton(QWidget *parent = nullptr);
    void setPosition(int row, int col);
    int getRow() const;
    int getCol() const;

private:
    int row, col;

signals:
    void buttonClicked(int row, int col);

private slots:
    void emitPosition();
};

#endif // GRIDBUTTON_H
