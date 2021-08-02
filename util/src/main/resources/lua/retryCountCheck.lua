local isExists = redis.call("exists", KEYS[1])
if (isExists == 0) then
    redis.call("incr", KEYS[1]);
    redis.call("EXPIRE", KEYS[1], ARGV[1]);
    return true;
else
    local currentCount = redis.call("incr", KEYS[1]);
    local checkCount = tonumber(ARGV[2]);
    if tonumber(currentCount) > checkCount then
        return false;
    end;
    return true;
end;