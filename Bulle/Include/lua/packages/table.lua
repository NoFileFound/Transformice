table.pack = function(...)
    return {...}
end

table.unpack = function(tbl, i, j)
    i = i or 1
    j = j or #tbl
    local result = {}
    for k = i, j do
        result[k - i + 1] = tbl[k]
    end
    return unpack(result)
end