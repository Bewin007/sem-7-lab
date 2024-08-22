print("URK21CS1128")
plaintxt = input("Enter the plain text: ")
key = int(input("Enter the key: "))
ct = ""
for i in plaintxt:
    if i.islower():
        ct += chr((ord(i)-97+key)%26+97)
    elif i.isupper():
        ct += chr((ord(i)-65+key)%26+65)
    else:
        ct += " "
print("Encrypted Text: ",ct)