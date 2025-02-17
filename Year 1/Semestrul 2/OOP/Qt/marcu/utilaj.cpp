#include "Utilaj.h"

Utilaj::Utilaj(int id, const QString &denumire, const QString &tip, int cilindrii)
    : id(id), denumire(denumire), tip(tip), cilindrii(cilindrii) {}

int Utilaj::getId() const { return id; }
QString Utilaj::getDenumire() const { return denumire; }
QString Utilaj::getTip() const { return tip; }
int Utilaj::getCilindrii() const { return cilindrii; }
