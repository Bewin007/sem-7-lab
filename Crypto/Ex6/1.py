from math import gcd

# Inputs
print("URK21CS1128")
p = int(input("Enter P value : "))
h = int(input("Enter H value : "))
Hmac = int(input("Enter Hmac value : "))

# Initialize variables
k, q, s, w, r = 0, 0, 0, 0, 0

# Find the largest prime divisor of p-1
for Q in range(p//2, 0, -1):
    if (p-1) % Q == 0:
        for j in range(2, Q//2 + 1):
            if Q % j == 0:
                break
        else:
            q = Q
    if q != 0:
        break

# Compute generator g
g = (h ** ((p - 1) // q)) % p
#print("g:", g)

x = 1  # x is typically a private key or similar value
print("x:",x)
# Find valid k and compute r and s
for k in range(2, q):
    if gcd(k, q) == 1:  # Ensure k is coprime with q
        r = (pow(g, k, p)) % q
        s = (pow(k, -1, q) * (Hmac + (r * x))) % q

        if gcd(s, q) == 1:  # Ensure s is coprime with q
            print("k:",k)
            print("r:",r)
            print("s:",s)
            w = pow(s, -1, q)
            print("w:",w)
            u1 = (Hmac * w) % q
            print("u1:",u1)
            u2 = (r * w) % q
            print("u2:",u2)
            y = pow(g, x, p)
            print("y:",y)
            v = (pow(g, u1, p) * pow(y, u2, p)) % p % q
            print("v:", v, "r:", r)
            if v==r:
                print("success: digital  signature is verified")
            break
