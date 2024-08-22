#URK21CS1128

a = int(input("Enter a number: "))
a_bin = bin(a)

if int(a_bin[-1]) == 0:
    print("The number is even")
else:
    print("The number is odd")