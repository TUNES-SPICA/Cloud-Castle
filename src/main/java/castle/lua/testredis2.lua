
local redis = require("redis")
local client = redis.connect('172.16.0.42',6379)
--[[redis.add_commands("auth","qwer1234")]]
client:auth('qwer1234')
client:set('testKey',1011)
local a = client:get('testKey')
print(a)
client:del("testKey")
a = client:get('testKey')
print(a)