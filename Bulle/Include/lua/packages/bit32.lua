bit32.arshift = function(a, n)
    local sign = a < 0 and -1 or 1
    return bit32.rshift(sign * math.abs(a), n) * sign
end

bit32.band = function(a, b)
    local result = 0
    local bitval = 1
    for i = 0, 31 do
        if bitval > a or bitval > b then
            break
        end
        if a % (bitval * 2) >= bitval and b % (bitval * 2) >= bitval then
            result = result + bitval
        end
        bitval = bitval * 2
    end
    return result
end

bit32.bnot = function(a)
    return 4294967295 - a
end

bit32.bor = function(a, b)
    local result = 0
    local bitval = 1
    for i = 0, 31 do
        if bitval > a and bitval > b then
            break
        end
        if a % (bitval * 2) >= bitval or b % (bitval * 2) >= bitval then
            result = result + bitval
        end
        bitval = bitval * 2
    end
    return result
end

bit32.btest = function(...)
    for _, value in ipairs {...} do
        if value == 0 then
            return false
        end
    end
    return true
end

bit32.bxor = function(a, b)
    local result = 0
    local bitval = 1
    for i = 0, 31 do
        if bitval > a and bitval > b then
            break
        end
        if (a % (bitval * 2) >= bitval) ~= (b % (bitval * 2) >= bitval) then
            result = result + bitval
        end
        bitval = bitval * 2
    end
    return result
end

bit32.extract = function(n, field, width)
    return bit32.rshift(bit32.band(n, bit32.lshift(1, width) - 1), field)
end

bit32.lrotate = function(x, disp)
    disp = disp % 32
    return bit32.bor(bit32.lshift(x, disp), bit32.rshift(x, 32 - disp))
end

bit32.lshift = function(a, n)
    return a * (2 ^ n)
end

bit32.replace = function(x, v, field, width)
    local mask = bit32.lshift(bit32.lshift(1, width) - 1, field)
    return bit32.bor(bit32.band(x, bit32.bnot(mask)), bit32.lshift(bit32.band(v, mask), field))
end

bit32.rrotate = function(x, disp)
    disp = disp % 32
    return bit32.bor(bit32.rshift(x, disp), bit32.lshift(x, 32 - disp))
end

bit32.rshift = function(a, n)
    return math.floor(a / (2 ^ n))
end