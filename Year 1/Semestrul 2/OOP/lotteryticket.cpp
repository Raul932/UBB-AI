#include <iostream>
#include <ctime>
#include <cstdlib>
#include <array>
#include <algorithm>

class Date {
private:
    int year;
    int month;
    int day;

public:
    Date(int y, int m, int d) : year(y), month(m), day(d) {}

    void setYear(int y) {
        year = y;
    }

    void setMonth(int m) {
        if (m >= 1 && m <= 12)
            month = m;
        else
            std::cerr << "Invalid month!" << std::endl;
    }

    void setDay(int d) {
        if (d >= 1 && d <= 31)
            day = d;
        else
            std::cerr << "Invalid day!" << std::endl;
    }

    int getYear() const {
        return year;
    }

    int getMonth() const {
        return month;
    }

    int getDay() const {
        return day;
    }

    // Calculate the difference in days between two dates
    int differenceInDays(const Date& other) const {
        const int daysPerMonth[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int days = day - other.getDay();
        days += (month - other.getMonth()) * daysPerMonth[other.getMonth()];
        days += (year - other.getYear()) * 365;

        return days;
    }
};

class LotteryTicket {
private:
    static std::array<int, 6> extractedNumbers;
    static Date extractionDate;

public:
    // Constructor
    LotteryTicket() {}

    // Setter for extractedNumbers
    static void setExtractedNumbers(const std::array<int, 6>& nums) {
        extractedNumbers = nums;
    }

    // Setter for extractionDate
    static void setExtractionDate(const Date& date) {
        extractionDate = date;
    }

    // Reset extractedNumbers to zeros
    static void resetExtractedNumbers() {
        extractedNumbers.fill(0);
    }

    // Getters
    const std::array<int, 6>& getExtractedNumbers() const {
        return extractedNumbers;
    }

    static const Date& getExtractionDate() {
        return extractionDate;
    }

    // Display ticket information
    void displayTicket() const {
        std::cout << "Extracted Numbers: ";
        for (int num : extractedNumbers) {
            std::cout << num << " ";
        }
        std::cout << std::endl;
        std::cout << "Extraction Date: " << extractionDate.getYear() << "-" << extractionDate.getMonth() << "-" << extractionDate.getDay() << std::endl;
    }

    // Function to calculate the count of matching numbers between the ticket and extracted numbers
    int calculateMatchCount() const {
        if (std::all_of(extractedNumbers.begin(), extractedNumbers.end(), [](int i){ return i == 0; })) {
            return -1; // Extraction numbers are all 0, indicating no valid extraction
        }

        // Get the current date
        std::time_t currentTime = std::time(0);
        std::tm* currentDate = std::localtime(&currentTime);
        Date currentDateObj(currentDate->tm_year + 1900, currentDate->tm_mon + 1, currentDate->tm_mday);

        // Check if the ticket is eligible for the current extraction
        int daysDifference = currentDateObj.differenceInDays(extractionDate);
        if (daysDifference < -7 || daysDifference > 0) {
            return -1; // Ticket is not eligible for the current extraction
        }

        // Calculate the count of matching numbers
        int matchCount = 0;
        for (int num : extractedNumbers) {
            if (std::find(extractedNumbers.begin(), extractedNumbers.end(), num) != extractedNumbers.end()) {
                matchCount++;
            }
        }

        return matchCount;
    }
};

// Initialize static member variables
std::array<int, 6> LotteryTicket::extractedNumbers;
Date LotteryTicket::extractionDate(1970, 1, 1);

int main() {
    // Seed the random number generator
    std::srand(static_cast<unsigned int>(std::time(0)));

    // Set the extracted numbers
    std::array<int, 6> extractedNumbers = {7, 11, 28, 33, 44, 49};
    LotteryTicket::setExtractedNumbers(extractedNumbers);

    // Create a LotteryTicket object stored on the stack
    LotteryTicket ticket1;

    // Create a LotteryTicket object stored on the heap
    LotteryTicket* ticket2 = new LotteryTicket();

    // Create an array to store 100 LotteryTickets
    LotteryTicket tickets[10000];

    // Add the first two objects to the array
    tickets[0] = ticket1;
    tickets[1] = *ticket2;

    // Populate the rest of the array with default-constructed LotteryTickets
    for (int i = 2; i < 10000; ++i) {
        tickets[i] = LotteryTicket();
    }

    // Count the number of tickets guessing 4, 5, and 6 numbers
    int count4Numbers = 0, count5Numbers = 0, count6Numbers = 0;
    for (const auto& ticket : tickets) {
        int matchCount = ticket.calculateMatchCount();
        if (matchCount == 4) {
            count4Numbers++;
        } else if (matchCount == 5) {
            count5Numbers++;
        } else if (matchCount == 6) {
            count6Numbers++;
        }
    }

    // Display the counts
    std::cout << "Tickets Guessing 4 Numbers: " << count4Numbers << std::endl;
    std::cout << "Tickets Guessing 5 Numbers: " << count5Numbers << std::endl;
    std::cout << "Tickets Guessing 6 Numbers: " << count6Numbers << std::endl;

    // Clean up the dynamically allocated object
    delete ticket2;

    return 0;
}
