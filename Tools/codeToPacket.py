while True:
	d = int(input("-> "))
	c = d >> 8
	cc = d - (c << 8)
	print(c,cc)