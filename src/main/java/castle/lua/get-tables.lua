print("                          ___                              ___");
print("                        |  |:|                           |  |::\\");
print("                        |  |:|                           |  |::\\");
print("                        |  |:|                           |  |:|:\\");
print("                      __|__|:|                         __|__|:|\\:\\");
print("                     /__/::::\\\\____                   /__/::::| \\:\\");
print("                        ~\\\\~~\\\\::::/                   \\  \\:\\~~\\__\\/");
print("                         |~~|:|~~                       \\  \\:\\");
print("                         |  |:|                          \\  \\:\\");
print("                         |  |:|                           \\  \\:\\");
print("                         |__|/                             \\__\\/");
print("星麦云商©");
print("author -> ran");
--print("create -> 2021-07-14 17:00:00");
print("create -> " .. os.date("%c"));
print("\n\n\n\n\n\n");

----------------------------------------------------------------------------------------
-- 导出数据库结构（处理绫致文档需求）
--
-- @apiNode 获取当前库全部表名，遍历获取结构。
-- @author ran
-- @param 设置连接参数  ( databases , user , pass , ip , port)
-- @process 建立连接 -> 输出tables -> 输出 or 返回
----------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------
-- @param
----------------------------------------------------------------------------------------
--> 库名
--var_mysql_database = "测试库"
--var_mysql_ip = "127.0.0.1";
--var_mysql_port = 3306;
--var_mysql_user_name = "root";
--var_mysql_user_pass = "123456";

var_mysql_database = "test_cloud_shop"
var_mysql_ip = "172.16.0.42";
var_mysql_port = 3306;
var_mysql_user_name = "root";
var_mysql_user_pass = "test123456";

--> 表集合
var_list_tables = nil;
--> 当前表名
var_string_thisTable = nil;

--> 表版本信息（表引擎，表版本，表注释）
var_map_table_version = nil;
--> 表字段信息（字段类型，是否非空，主键，默认值，字段注释）
var_map_print_table_field = nil;
--> 表索引信息（索引种类，索引类型，字段名称）
var_map_print_table_index = nil;


--文件对象的创建
--file = io.open("lz_database.dat", "w+");
----------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------
-- @建立连接
----------------------------------------------------------------------------------------
-- mysql
--> 引入luasql包，使用前通过luarocks安装luasql包
luasql = require("luasql.mysql");

---> 创建连接对象
env = luasql.mysql();

---> 连接数据库
---> @param ( databases , user , pass , ip , port)
conn = env:connect(var_mysql_database, var_mysql_user_name, var_mysql_user_pass, var_mysql_ip, var_mysql_port);

---> 设置编码格式
conn:execute("SET NAMES UTF8");
----------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------
-- @out tables
-- 输出表信息，show tables获取全部表，for循环遍历获取表版本、字段、索引信息
----------------------------------------------------------------------------------------
var_list_tables = conn:execute("SHOW TABLES");
var_list_temp = var_list_tables:fetch({}, "a");
while var_list_temp do
    var_string_thisTable = string.format("%s", var_list_temp["Tables_in_" .. var_mysql_database]);
    print("### " .. var_string_thisTable)

    -- 表 - 版本信息
    var_map_table_version = conn:execute("SHOW TABLE STATUS LIKE '" .. var_string_thisTable .. "'");
    var_list_temp_tableVersion = var_map_table_version:fetch({}, "a");
    print("##### 引擎 | 版本 | 备注")
    while var_list_temp_tableVersion do
        var_string_tableVersion = string.format("%s | %s | %s", var_list_temp_tableVersion.Engine, var_list_temp_tableVersion.Version, var_list_temp_tableVersion.Comment);
        print(var_string_tableVersion);
        var_list_temp_tableVersion = var_map_table_version:fetch(var_list_temp_tableVersion, "a")
    end

    -- 表 - 字段信息
    var_map_print_table_field = conn:execute("SHOW FULL COLUMNS FROM `" .. var_string_thisTable .. "`");
    var_list_temp_tableField = var_map_print_table_field:fetch({}, "a");
    print("##### 字段 | 类型 |是否可以为空 | 备注")
    while var_list_temp_tableField do
        var_string_tableField = string.format("%s | %s | %s | %s", var_list_temp_tableField.Field, var_list_temp_tableField.Type, var_list_temp_tableField.Null, var_list_temp_tableField.Comment);
        print(var_string_tableField);
        var_list_temp_tableField = var_map_print_table_field:fetch(var_list_temp_tableField, "a")
    end

    -- 表 - 索引信息
    var_map_print_table_index = conn:execute("SHOW INDEX FROM `" .. var_string_thisTable .. "`");
    var_list_temp_tableIndex = var_map_print_table_index:fetch({}, "a");
    print("##### 索引种类 | 索引类型 | 字段名称")
    while var_list_temp_tableIndex do
        if var_list_temp_tableIndex ~= null then
            var_string_tableIndex = string.format("%s | %s | %s", var_list_temp_tableIndex.Key_name, var_list_temp_tableIndex.Index_type, var_list_temp_tableIndex.Column_name);
            print(var_string_tableIndex);
            var_list_temp_tableIndex = var_map_print_table_index:fetch(var_list_temp_tableIndex, "a")
        end
    end

    --file:write(var_string_thisTable)

    var_list_temp = var_list_tables:fetch(var_list_temp, "a")
end
----------------------------------------------------------------------------------------


----------------------------------------------------------------------------------------
-- @end
----------------------------------------------------------------------------------------
-- mysql
---> 连接关闭
conn:execute("exit");
print(".....关闭连接")
----------------------------------------------------------------------------------------
