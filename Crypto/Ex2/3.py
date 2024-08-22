print('URK21CS1128')
plaintext = input("Enter the plain text: ")
key = input("Enter the key: ")
key = list(key)
if len(plaintext) == len(key):
    key = "".join(key)
else:
    for i in range(len(plaintext) - len(key)):
        key.append(key[i % len(key)])
    key = "".join(key)
ct = ""
for i in range(len(plaintext)):
    if plaintext[i].isalpha():
        base = ord('A') if plaintext[i].isupper() else ord('a')
        ct += chr((ord(plaintext[i])- base + ord(key[i])-ord('A')) %26 +base)
    else:
        ct += plaintext[i]
print("After Encryption: ",ct)
pt = ""
for i in range(len(ct)):
    if ct[i].isalpha():
        base =  ord('A') if ct[i].isupper() else ord('a')
        pt += chr((ord(ct[i]) - ord(key[i])+26)%26+base)
    else:
        pt += ct[i]
print("After Decryption: ",pt)