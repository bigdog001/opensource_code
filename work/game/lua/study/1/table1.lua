-- create an empty table
mytable = {}

--create a table with init value

mytable1={
2,
3,
4,
5
}
-- the index in lua come from 1 not 0
for i=1,#mytable1 do
	print(mytable1[i])
end



-- another kinds of table 
s = "ok"
mytable2 = {
	k = "xx"
}
print(mytable2["k"])
mytable2[s] = 100
print(mytable2[s])

print(mytable2.k) -- mytable2.k equals mytable2["k"]

--[[
 a.x  just means a["x"]
 a[x] get the value in a ,just using the value of x
]]

-- first way to iterate the table
for i=1,#mytable2 do
	print("the value is"..mytable2[i])
end

--second way to get the value in table
for k,v in pairs(mytable2) do
	print(k.."--->>"..v)
end