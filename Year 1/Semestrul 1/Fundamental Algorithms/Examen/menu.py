from repo import PaintingRepository

repo=PaintingRepository()

repo.add_painting("Leonardo DiCaprio", "Mona Lisa", 0.99, 2.00)
repo.add_painting("Beyonce", "The Kiss", 1.99, 1000.00)
repo.add_painting("Delia", "The Last Supper", 0.00, 0.00)


while True:
    print("1. Add a new painting")
    print("2. Get the average")
    print("3. Update end price")
    print("4. Get all")
    print("0. Exit")
    option=int(input("Choose an option between (0-4):"))
    if option==1:
        name=input("Name of artist: ")
        title=input("Title: ")
        start=int(input("Starting price: "))
        end=int(input("Ending price: "))
        repo.add_painting(name, title, start, end)
    if option==2:
        name=input("Name: ")
        if len(name)==0:
            while len(name)==0:
                name=input("Please add a name: ")
        print(repo.get_average(name))
    if option==3:
        title=input("Title: ")
        new_price=int(input("New price: "))
        if repo.update_price(title, new_price):
            print("Price updated.")
    if option==4:
        repo.get_all()
    if option==0:
        break
