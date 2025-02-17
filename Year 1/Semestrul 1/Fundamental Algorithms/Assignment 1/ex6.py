def age_calculator():
    '''
    Description: Calculates the age of a person
    Input: Current date: Integers in the dd.mm.yyyy format
           Birthday: Integers in the dd.mm.yyyy format
    Preconditions: Birth year<=Current year
                   dd<=31
                   mm<=12
                   birth year, current year>0
    Output: The age of the person
    '''
    current_day, current_month, current_year=(int(x) for x in input("Current day: ").split("."))
    birth_day, birth_month, birth_year=(int(x) for x in input("Birthday: ").split("."))

    if current_month>birth_month:
        print(f"The person is {current_year-birth_year} years old")
    elif current_month==birth_month:
        if current_day>=birth_day:
            print(f"The person is {current_year-birth_year} years old")
        else:
            print(f"The person is {current_year-birth_year-1} years old")
    else:
        print(f"The person is {current_year-birth_year-1} years old")

age_calculator()