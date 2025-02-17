#include "mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    resize(1920, 1080);
    widthSlider = new QSlider(Qt::Horizontal, this);
    widthSlider->setRange(0, 20);
    widthSlider->setFixedWidth(250);

    heightSlider = new QSlider(Qt::Horizontal, this);
    heightSlider->setRange(0, 20);
    heightSlider->setFixedWidth(250);

    generateButton = new QPushButton("Generate Grid", this);
    generateButton->setFixedWidth(150);

    QVBoxLayout *controlLayout = new QVBoxLayout;
    controlLayout->addWidget(widthSlider);
    controlLayout->addWidget(heightSlider);
    controlLayout->addWidget(generateButton);

    radiusSlider = new QSlider(Qt::Horizontal, this);
    radiusSlider->setRange(0, 5);
    radiusSlider->setFixedWidth(150);

    plusButton = new QRadioButton("Increase", this);
    minusButton = new QRadioButton("Decrease", this);

    QVBoxLayout *radiusLayout = new QVBoxLayout;
    radiusLayout->addWidget(radiusSlider);
    radiusLayout->addWidget(plusButton);
    radiusLayout->addWidget(minusButton);

    QHBoxLayout *topLevelLayout = new QHBoxLayout;
    topLevelLayout->addLayout(controlLayout);
    topLevelLayout->addStretch();
    topLevelLayout->addLayout(radiusLayout);

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addLayout(topLevelLayout);

    gridWidget = new QWidget;
    gridLayout = new QGridLayout;
    gridWidget->setLayout(gridLayout);

    mainLayout->addWidget(gridWidget);

    QWidget *centralWidget = new QWidget(this);
    centralWidget->setLayout(mainLayout);
    setCentralWidget(centralWidget);

    connect(generateButton, &QPushButton::clicked, this, &MainWindow::generateGrid);

}

MainWindow::~MainWindow()
{
}

void MainWindow::generateGrid() {

    while (QLayoutItem *item = gridLayout->takeAt(0)) {
        if (QWidget* widget = item->widget()) widget->deleteLater();
        delete item;
    }

    int width = widthSlider->value();
    int height = heightSlider->value();

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            QPushButton *button = new QPushButton(gridWidget);
            button->setMinimumSize(40, 40);
            QString style = calculateColor(i, height);
            button->setStyleSheet(style);
            button->setObjectName(QString("button_%1_%2").arg(i).arg(j));
            connect(button, &QPushButton::clicked, this, &MainWindow::buttonClicked);
            gridLayout->addWidget(button, i, j);
        }
    }

    gridWidget->adjustSize();
}

void MainWindow::buttonClicked() {
    QPushButton *button = qobject_cast<QPushButton*>(sender());
    if (!button) return;

    QStringList parts = button->objectName().split('_');
    if (parts.size() < 3) return;
    int row = parts.at(1).toInt();
    int col = parts.at(2).toInt();

    int radius = radiusSlider->value();

    int width = widthSlider->value();
    int height = heightSlider->value();
    QString color = plusButton->isChecked() ? "green" : "blue";

    for (int i = row - radius; i <= row + radius; i++) {
        for (int j = col - radius; j <= col + radius; j++) {
            if (i >= 0 && i < height && j >= 0 && j < width) {
                QPushButton *nearButton = qobject_cast<QPushButton*>(gridLayout->itemAtPosition(i, j)->widget());
                if (nearButton) {
                    nearButton->setStyleSheet("background-color: " + color);
                }
            }
        }
    }
}

QString MainWindow::calculateColor(int i, int height) {
    double ratio = 1.0 - (double)i / (height - 1);
    QColor color;
    if (ratio <= 0.2) {
        color.setRgb(0, 0, 128 + static_cast<int>(127 * ratio / 0.2));
    } else if (ratio <= 0.4) {
        color.setRgb(0, 128 + static_cast<int>(127 * (ratio - 0.2) / 0.2), 255);
    } else if (ratio <= 0.6) {
        color.setRgb(0, 255, 0);
    } else if (ratio <= 0.8) {
        color.setRgb(0, 160 - static_cast<int>(60 * (ratio - 0.6) / 0.2), 0);
    } else if (ratio <= 0.9) {
        color.setRgb(101, 67, 33);
    } else {
        color.setRgb(255, 255, 255);
    }
    return QString("background-color: %1;").arg(color.name());
}




