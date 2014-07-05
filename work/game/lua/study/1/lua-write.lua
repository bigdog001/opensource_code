local f = assert(io.open("ok.txt","w"))

f:write("some content to the target file\n hello")
f:close()




local function function_write( filename ,content)
	local fw = assert(io.open(filename,"w"))
	fw:write(content);
    fw:close()
end 

function_write("haha.txt","this content come from the writer function....")