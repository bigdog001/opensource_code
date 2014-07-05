print("hello world")
function myfun(... )
	-- body
   -- this var vv should be the global var ,we can access it out of the function
	vv= 21
	print("ddddd,comes from the lua myfunction----")
	print()
end

myfun()
print(vv)

mytable = {
	12,13,14
}

for k,v in pairs(mytable) do
	print(k.."-->"..v)
end

print(mytable[3])
print(mytable)

--local var
b= 10       --b should be the global var 
local x = 1 -- if another lua script file import the file 
            -- they can not  access the var x,only var b