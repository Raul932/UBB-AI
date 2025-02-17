#ifndef UTILAJ_H
#define UTILAJ_H

#include <QString>

class Utilaj {
public:
    Utilaj(int id, const QString &denumire, const QString &tip, int cilindrii);

    int getId() const;
    QString getDenumire() const;
    QString getTip() const;
    int getCilindrii() const;

private:
    int id;
    QString denumire;
    QString tip;
    int cilindrii;
};

#endif // UTILAJ_H
