-- lua 循环

int = 0;

-- for循环
for i=10,1,-1 do
    print(i)
end

-- while循环
while (true) do
    if (int==10) then
        break
    end
    int = int + 1
    print(int);
end