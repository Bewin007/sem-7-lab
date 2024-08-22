print("URK21CS1128")
plaintext = input("Enter the plaintext: ")
a = int(input("Enter a value: "))
b = int(input("Enter b value: "))
ct = ""
for char in plaintext:
    if char.isalpha():
        base = ord('A') if char.isupper() else ord('a')
        ct += chr(((a*(ord(char)-base)+b)%26)+base)
    else:
        ct += char
print("After Encryption: ",ct)
pt = ""
inverse = pow(a,-1,26)
for char in ct:
    if char.isalpha():
        base = ord('A') if char.isupper() else ord('a')
        pt += chr(((inverse * (ord(char)-base-b))%26)+base)
    else:
        pt += char
print("After Decryption: ",pt)