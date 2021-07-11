-- lua 打印
-- 输出
print("===== lua 输出")
print("hello world");


-- lua 类型
-- int 类型，布尔类型 字符串类型
print("===== lua 类型")
int = 4;
boolean1 = true;
boolean2 = false;
str1 = "字符串"
print(int);
print(boolean1);
print(boolean2);
print(str1);

-- lua 变量
-- Lua 变量有三种类型：全局变量、局部变量、表中的域。
print("===== lua 变量")
lubStaticVar = 5
local luaPartVar = 10;
print(luaPartVar + lubStaticVar);