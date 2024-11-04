#rsa encryption and decryption
print("URK21CS1128")
import math
def gcd(a,b):
    while b != 0:
        a,b = b,a % b
    return a
def mod_inverse(e,phi):
    d = 0
    x1,x2,x3 = 1,0,phi
    y1,y2,y3 = 0,1,e
    while y3 != 0:
        q = x3 // y3
        x1,x2,x3,y1,y2,y3 = (y1,y2,y3,x1-q*y1,x2-q*y2,x3-q*y3)
    if x2 < 0:
        x2 += phi
    return x2
def encrypt(public_key,plaintext):
    e,n = public_key
    cipher = [(ord(char) ** e) % n for char in plaintext]
    return cipher
def decrypt(private_key,ciphertext):
    d,n = private_key
    plaintext = [chr((char ** d) % n) for char in ciphertext]
    return ''.join(plaintext)
def rsa_encrypt_decrypt():
    p = int(input("Enter prime number p: "))
    q = int(input("Enter prime number q: "))
    n = p*q
    phi = (p-1)*(q-1)
    e = 2
    while e<phi:
        if gcd(e,phi)==1:
            break
        e += 1
    d = mod_inverse(e,phi)
    public_key = (e,n)
    private_key = (d,n)
    msg = input("Enter message to encrypt: ")
    encrypted_msg = encrypt(public_key,msg)
    print("Encrypted message:",encrypted_msg)
    decrypted_msg = decrypt(private_key,encrypted_msg)
    print("Decrypted message: ",decrypted_msg)
rsa_encrypt_decrypt()