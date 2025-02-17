def date_calculator(year, days):
    '''
    Description: Prints the exact date from a year with the number of days and year given
    Input: year,days- integers
    Preconditions: 0<days<=366 if we have a leap year, or 0<days<=365 if we don't
                   year>0
    Output: The exact day in the dd.mm.yy format in a year with the given number of days 
    '''
    month=1
    if year%4==0 and (year%100!=0 or year%400==0):
        while (days>31 and (month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12)) or (days>30 and (month==4 or month==6 or month==9 or month==11)) or (days>29 and month==2):
            if month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12:
                days-=31
                month+=1
            elif month==4 or month==6 or month==9 or month==11:
                days-=30
                month+=1
            else:
                days-=29
                month+=1
    else:
        while (days>31 and (month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12)) or (days>30 and (month==4 or month==6 or month==9 or month==11)) or (days>28 and month==2):
            if month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12:
                days-=31
                month+=1
            elif month==4 or month==6 or month==9 or month==11:
                days-=30
                month+=1
            else:
                days-=28
                month+=1
    if month<10:
        print(f"{days}.0{month}.{year}")
    else:
        print(f"{days}.{month}.{year}")
year=int(input())
days=int(input())
date_calculator(year,days)