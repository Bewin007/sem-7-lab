print("URK21CS1128")
pt = input("Plain text: ").replace(" ","")
key = sorted(list(enumerate(list(input("key: ")))),key=lambda x:ord(x[1]))
m = []
row = 0
index = 0
while index != len(pt):
    m.append([""]*len(key))
    for col in range(len(key)):
        m[row][col] = pt[index]
        index += 1
        if index == len(pt):
            break
    row+=1
print("Encrypted text:",end=" ")
for col,value in key:
    for row in range(len(m)):
        print(m[row][col],end="")