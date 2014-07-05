--open the file and read all lines from the file
local file = assert(io.open("namelist.txt","r"))
local string = file:read("*all")
file:close();
print(string)




-- function to read one file
local function function_name( filename)
	local file_ = assert(io.open(filename,"r"))
	loca content = file_:read("*all")
end 