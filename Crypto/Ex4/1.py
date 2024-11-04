def string_to_binary(text):
    return ''.join(format(ord(char), '08b') for char in text)
def binary_to_string(binary):
    chars = [binary[i:i+8] for i in range(0, len(binary), 8)]
    return ''.join(chr(int(char, 2)) for char in chars)
def permute(k, arr, n):
    return [k[i - 1] for i in arr]
def shift_left(k, shifts):
    return k[shifts:] + k[:shifts]
def xor(a, b):
    return [str(int(x) ^ int(y)) for x, y in zip(a, b)]
def sbox_output(bits, sbox):
    row = int(bits[0] + bits[3], 2)
    col = int(bits[1] + bits[2], 2)
    return format(sbox[row][col], '02b')
def encrypt_decrypt(plain_text, key, encrypt=True):
    ip = [2, 6, 3, 1, 4, 8, 5, 7]
    ip_inv = [4, 1, 3, 5, 7, 2, 8, 6]
    ep = [4, 1, 2, 3, 2, 3, 4, 1]
    p4 = [2, 4, 3, 1]
    s0 = [[1, 0, 3, 2], [3, 2, 1, 0], [0, 2, 1, 3], [3, 1, 3, 2]]
    s1 = [[0, 1, 2, 3], [2, 0, 1, 3], [3, 0, 1, 0], [2, 1, 0, 3]]
    p10 = [3, 5, 2, 7, 4, 10, 1, 9, 8, 6]
    p8 = [6, 3, 7, 4, 8, 5, 10, 9]
    key_p10 = permute(key, p10, 10)
    print(f"After P10 permutation: {''.join(key_p10)}")
    left_half, right_half = key_p10[:5], key_p10[5:]
    left_half = shift_left(left_half, 1)
    right_half = shift_left(right_half, 1)
    shifted_key_1 = left_half + right_half
    print(f"Shifted halves (1st): {''.join(left_half)} | {''.join(right_half)}")

    key_1 = permute(shifted_key_1, p8, 8)
    print(f"Key-1: {''.join(key_1)}")
    left_half = shift_left(left_half, 2)
    right_half = shift_left(right_half, 2)
    shifted_key_2 = left_half + right_half
    print(f"Shifted halves (2nd): {''.join(left_half)} | {''.join(right_half)}")
    key_2 = permute(shifted_key_2, p8, 8)
    print(f"Key-2: {''.join(key_2)}")
    k1, k2 = (key_1, key_2) if encrypt else (key_2, key_1)
    pt_ip = permute(plain_text, ip, 8)
    print(f"After initial permutation (IP): {''.join(pt_ip)}")
    left_half, right_half = pt_ip[:4], pt_ip[4:]
    print(f"Initial L = {''.join(left_half)}, R = {''.join(right_half)}")

    def fk(l, r, key):
        r_ep = permute(r, ep, 8)
        print(f"After expansion/permutation (EP): {''.join(r_ep)}")
        xor_result = xor(r_ep, key)
        print(f"After XOR with key: {''.join(xor_result)}")
        left_sbox_in, right_sbox_in = xor_result[:4], xor_result[4:]
        sbox_out = sbox_output(left_sbox_in, s0) + sbox_output(right_sbox_in, s1)
        print(f"S-box output: {sbox_out}")
        p4_result = permute(sbox_out, p4, 4)
        print(f"After P4 permutation: {''.join(p4_result)}")
        l_xor_p4 = xor(l, p4_result)
        print(f"After XOR with left: {''.join(l_xor_p4)}")
        return l_xor_p4, r
    left_half, right_half = fk(left_half, right_half, k1)
    left_half, right_half = right_half, left_half
    print(f"After swap: L = {''.join(left_half)}, R = {''.join(right_half)}")
    left_half, right_half = fk(left_half, right_half, k2)
    combined = left_half + right_half
    print(f"Before inverse IP: {''.join(combined)}")
    cipher_text = permute(combined, ip_inv, 8)
    print(f"After inverse permutation (IP-1): {''.join(cipher_text)}")
    return ''.join(cipher_text)
print('Perform the logic behind simplified DES')
plaintext = input("Enter a plaintext (word): ")
key = input("Enter a 10-bit key: ")
plaintext_binary = string_to_binary(plaintext)
cipher_binary = ''
for i in range(0, len(plaintext_binary), 8):
    block = plaintext_binary[i:i+8]
    print(f"\n--- Encrypting block {block} ---")
    cipher_binary += encrypt_decrypt(block, key, encrypt=True)
decrypted_binary = ''
for i in range(0, len(cipher_binary), 8):
    block = cipher_binary[i:i+8]
    print(f"\n--- Decrypting block {block} ---")
    decrypted_binary += encrypt_decrypt(block, key, encrypt=False)
decrypted_text = binary_to_string(decrypted_binary)
print(f"Cipher Binary: {cipher_binary}")
print(f"Decrypted Text: {decrypted_text}")