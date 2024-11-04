#El Gammal’s encryption and decryption
def mod_inverse(a,q):
    for i in range(1,q):
        if (a*i)%q == 1:
            return i
    return None
def primitive_root(alpha,q):
    required_set = set(num for num in range(1,q))
    actual_set = set(pow(alpha, powers,q) for powers in range(1,q))
    return required_set == actual_set
def elgamal_encryption(q,alpha):
    Xa = int(input("Enter Alice's secret Key: "))
    M = int(input("Enter the message: "))
    k = int(input("Enter Bob's random int: "))
    Ya = pow(alpha,Xa,q)
    c1 = pow(alpha,k,q)
    K = pow(Ya,k,q)
    c2 = (M*K)%q
    print(f"Encrypted Message: c1={c1}, c2={c2}")
    K_decrypt = pow(c1,Xa,q)
    k_inv = mod_inverse(K_decrypt,q)
    decrypted_mesg = (c2*k_inv)%q
    print(f"Decrypted message: {decrypted_mesg}")
q = int(input("Enter prime number: "))
alpha = int(input("Enter primitive root: "))

if not primitive_root(alpha,q):
    print(f"{alpha} is not a primitive root of {q}")
else:
    elgamal_encryption(q,alpha)