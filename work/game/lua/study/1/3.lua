-- and is a little  bit look like  ? operation in java 1==3? "value true": "value of false"
print(false and 5)
print(2 and 3)

--or 
print(2 or 7)
print(nil or 8)

--not
print(not nil)
print(not 0 )

-- while
m_table ={1,2,3,4,5}
local index = 1
while m_table[index] do
   print(m_table[index])
   index = index+1
end


--repeat just like do-while in java

local  snumber = 71
repeat
	snumber = snumber+1
    print(snumber)

	until snumber ==74




-- for 
for i=1,10 do
	print(i)
end

-- in for loop, # means get the var`s length.like s.size() in java
-- the third parameter in for means the step value
for i=1,#m_table,2 do
	print(m_table[i])
end

for i=1,#m_table do
	print(m_table[i])
end