## 7. 搜索场馆
### 1.1 功能描述
用户输入想要查询的关键字，返回场馆名包含关键字的所有场馆
### 1.2 请求说明
> 请求方式: GET
  请求URL: [/venue](https://ecnuer996.cn/MeetHere/api/venue?name=[参数1])
### 1.3 请求参数
参数名      |   参数类型  | 参数说明
:----------:|:---------: | :------:
name   |  string         |  搜索关键字

### 1.4 返回结果
包含关键字的所有场馆信息
```ArrayList
[
    {
        "id":1,
        "name":"大学生活动中心",
        "address":"上海市普陀区中山北路3663号丽娃河畔",
        "introduction":"用于大学生日常的体育运动",
        "phone":"13946827394",
        "beginTime":25200000,
        "endTime":68400000
    },
    {
        "id":2,
        "name":"中学生活动中心",
        "address":"上海市普陀区中山北路3663号",
        "introduction":"提供给中学生需要的运动场所",
        "phone":"18927483925",
        "beginTime":27000000,
        "endTime":73800000
    }
]
```


