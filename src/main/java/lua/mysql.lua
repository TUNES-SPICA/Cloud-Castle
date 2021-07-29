luasql = require "luasql.mysql"

--创建环境对象
env = luasql.mysql()

--连接数据库
conn = env:connect("测试库", "root", "123456", "127.0.0.1", 3306)

--设置数据库的编码格式
conn:execute "SET NAMES UTF8"

--执行数据库操作
--cur = conn:execute("select * from user")
cur = conn:execute("SELECT * FROM luas")

--for i = 1, 10 do
--    String ="insert luas value(id,name),(";
--    String = String.. i ..",\"wang\");"
--    print(String)
--    conn:execute(String)
--end

row = cur:fetch({}, "a")

--mysql> show columns from user;
--+-------+-------------+------+-----+---------+-------+
--| Field | Type        | Null | Key | Default | Extra |
--+-------+-------------+------+-----+---------+-------+
--| name  | varchar(20) | YES  |     | NULL    |       |
--| age   | int         | YES  |     | NULL    |       |
--+-------+-------------+------+-----+---------+-------+


--文件对象的创建
file = io.open("role.dat", "w+");

while row do
    --var = string.format("%s %s\n", row.age, row.name)
    var = string.format("%s %s\n", row.id, row.name)

    print(var)

    file:write(var)

    row = cur:fetch(row, "a")
end

