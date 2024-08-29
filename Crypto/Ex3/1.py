print("URK21CS1128")
def rail_fence_cipher(text,key):
    rails = [[] for _ in range(key)]
    rail = 0
    direction = 1
    for char in text:
        rails[rail].append(char)
        rail += direction
        if rail == 0 or rail == key-1:
            direction *= -1
    return ''.join([''.join(rail) for rail in rails])

def rail_fence_decipher(cipher_text,key):
    rails = [[] for _ in range(key)]
    rail = 0
    direction  = 1
    for _ in cipher_text:
        rails[rail].append('*')
        rail += direction
        if rail == 0 or rail == key-1:
            direction *= -1
    index = 0
    for i in range(key):
        for j in range(len(rails[i])):
            rails[i][j] = cipher_text[index]
            index += 1
    plain_text = []
    rail = 0
    direction = 1
    for _ in cipher_text:
        plain_text.append(rails[rail].pop(0))
        rail += direction
        if rail == 0 or rail == key - 1:
            direction *= -1
    return ''.join(plain_text)

input_string = input("Enter the string: ")
key = int(input("Enter the key(no of rails): "))
cipher_text = rail_fence_cipher(input_string,key)
print("Cipher text after applying rail fence: ")
print(cipher_text)
