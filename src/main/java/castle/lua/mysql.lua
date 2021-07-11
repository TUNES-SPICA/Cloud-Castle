luasql = require "luasql.mysql"
env = luasql.mysql(); --创建环境对象
conn = env:connect("test", "root", "123456", "localhost", 3306);
conn:execute"SET NAMES UTF8"
print(env, conn);
--
status, errorString = conn:execute([[CREATE TABLE sample2 (id INTEGER, name TEXT)]]);
print(status, errorString);
--
--status, errorString = conn:execute([[INSERT INTO sample2 values('12', 'tony')]]);
--print(status, errorString);
--
--cursor, errorString = conn:execute("select * from sample2");
--print(cursor, errorString);
--
--print(cursor:numrows());
--
--row = cursor:fetch({}, "a");
--while row do
--   print(string.format("Id: %s, Name: %s", row.id, row.name));
--      row = cursor:fetch(row, "a");
--      end
--
--      cursor:close();
--      conn:close();
--      env:close();
