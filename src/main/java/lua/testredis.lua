local redis = require "resty.redis";
local red = redis:new();

local ok, err = red:connect("172.16.0.42", 6379);-- 地址host，端口号port
ok, err = red:auth("qwer1234")-- password    -- redis设置的密码
if not ok then    -- 链接失败的时候
    ngx.log(ngx.DEBUG, "error:" .. err);
else
    local new_ip_blacklist, err = red:smembers(redis_key);-- 获取redis中redis_key的值
    if err then    -- 获取失败的时候
        ngx.log(ngx.DEBUG, "error:" .. err)
    else
        -- 获取成功！
    end
end

-- 写入数据
ok, err = red:set("test_ran", "123")
if not ok then
    ngx.say("set data error", err)
    return
end
ngx.say("set data success")

-- 读取数据
ok, err = red:get("test_ran", "123")
if not res then
    ngx.say("get data error ", err)
    return
end
if res == ngx.null then
    ngx.say("data is nil")
    return
end
ngx.say("test_ran", res)