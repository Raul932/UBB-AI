#ifndef CHESS_EXCEPTION_H
#define CHESS_EXCEPTION_H

#include <stdexcept>
#include <string>

class ChessException : public std::runtime_error {
public:
    explicit ChessException(const std::string& msg) : std::runtime_error(msg) {}
};

#endif // CHESS_EXCEPTION_H
