#Diffie-Hellman Key Exchange
print("URK21CS1128")
q= int(input("Enter prime value: "))
alpha = int(input("Enter primitive root alpha: "))
Xa = int(input("Enter Alice's secret key: "))
Xb = int(input("Enter Bob's secret key: "))
Ya = pow(alpha,Xa,q)
Yb = pow(alpha,Xb,q)
Ka = pow(Yb,Xa,q)
Kb = pow(Ya,Xb,q)
print(f"Alice's public key: {Ya}")
print(f"Bob's public key: {Yb}")
print(f"Alice's Shared secret key: {Ka}")
print(f"Bob's shared secret key: {Kb}")
if Ka == Kb:
    print("Key exchange was successfull")
else:
      print("Key exchange failed")