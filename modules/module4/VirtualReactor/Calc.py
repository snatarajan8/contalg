a = [3.000,0.0,0.0,0.0]
b = [2.0000,0.0,0.0,0.0]
c = [1.5000,0.0,0.0,0.0]

t = 0.01

kab = 1
kc = 0.5

for i in range(1,4):
  k = t*((kc*c[i]) - (kab*a[i]*b[i]))
  a[i] = a[i-1] + k
  b[i] = b[i-1] + k
  c[i] = c[i-1] + (t*(-(kc*c[i]) + (kab*a[i]*b[i])))
  k = t*((kc*c[i]) - (kab*a[i]*b[i]))
  print(t*((kc*c[i]) - (kab*a[i]*b[i])))
  print("t = ", t)
  print("at = ", a[i])
  print("bt = ", b[i])
  print("ct = ", c[i])

print(a, "\n")
print(b, "\n")
print(c, "\n")
