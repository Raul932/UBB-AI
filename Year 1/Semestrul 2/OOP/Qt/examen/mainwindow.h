#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPushButton>
#include <QSlider>
#include <QGridLayout>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QWidget>
#include <QRadioButton>
#include <QLabel>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void generateGrid();
    void buttonClicked();

private:
    int width;
    int height;
    QSlider *widthSlider;
    QSlider *heightSlider;
    QSlider *radiusSlider;
    QPushButton *generateButton;
    QWidget *gridWidget;
    QGridLayout *gridLayout;

    QRadioButton *plusButton;
    QRadioButton *minusButton;

    QString calculateColor(int, int);
    void connectGridButtons();
};
#endif // MAINWINDOW_H
