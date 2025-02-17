def sort_repo(instance, criteria):
    '''
    Description:Sorts based on a criteria
    '''
    return sorted(instance, key=lambda x: criteria(x))

def filter_repo(instance, criteria):
    '''
    Description:Filters based on criteria
    '''
    return [x for x in instance if criteria(x)]

def create_combinations(v, k):
    '''
    Description:Creates combinations from backtracking
    '''
    c = []
    backtracking(v, k, c, [], 0)
    return c

def backtracking(v, k, c, com, i):
    '''
    Description:Backtracks based on your needs
    '''
    if(len(com) == k):
        c.append(com.copy())
        return 0
    for j in range(i, len(v)):
        com.append(v[j])
        backtracking(v, k, c, com, j + 1)
        com.pop()