#URK21CS1128
def swap_numbers(a,b):
    a = a^b
    b = a^b
    a = a^b
    return a,b

num1 = int(input("Enter the first number: "))
num2 = int(input("Enter the second number: "))

swap_num1,swap_num2 = swap_numbers(num1,num2)
print(f"Original numbers: {num1},{num2}")
print(f"Swapped numbers: {swap_num1},{swap_num2}")