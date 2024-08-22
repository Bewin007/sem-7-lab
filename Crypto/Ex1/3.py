#URK21CS1128
mystring = "Hello World"

list1 = []

print("Start: ",end='')
for i in range(len(mystring)):
    print(ord(mystring[i]),end=" ")
print()

for i in range(len(mystring)):
    charASCII = ord(mystring[i])
    charXOR = charASCII ^ 0
    list1.append(charXOR)
print("After XOR with 0: ",*list1)

for i in range(len(list1)):
    charXOR = list1[i]^127
    list1[i] = charXOR
print("After XOR with 127: ",*list1)