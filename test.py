file = open("out.txt", "r")

lines = [ line.strip().split(" ") for line in file ]

nums = [ [ float(n) for n in arr ] for arr in lines ]

mini = 9999
maxi = -9999

for arr in nums:
	for n in arr:
		mini = min(mini, n)
		maxi = max(maxi, n)
		
print(mini, maxi)