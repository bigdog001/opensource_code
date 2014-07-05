local x = 3
--if
if x == 1 then
print(x)
	end

--if else
if x ==1 then
	print(1)
	elseif x ~=3 then
		print("x is not 1")
   else print("haha --- i am here")
	end

-- function without parameters
local function mylocalfunction()
	-- body
	print("mylocalfunction")
end

mylocalfunction()

-- function with parameters
local function function_param( p )

	-- body
	print(p)
end

function_param("i am the parameters")

--function with the single return value
local function function_return(x )
	-- body
	return x; -- the invokor should receive the value automaticaly
end 


print(function_return(11113))   


-- function with multiple return values
local function function_multipe_return_value( )
	-- body
	return 3,44,60
end

local  x1,x2,x3 = function_multipe_return_value()
print(x1)
print(x2)
print(x3)

-- the function with multiple inject parameters
function function_table( ... )
	-- body
	local xn_table = {...}
	for k,v in pairs(xn_table) do
		print(k.."-->>>"..v)
	end
end

function_table(22,33,"ffffffffff")