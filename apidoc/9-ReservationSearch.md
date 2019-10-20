## 9. 订单查询
### 1.1 功能描述
查询一个用户的所有历史订单
### 1.2 请求说明
> 请求方式: POST
  请求URL: [/orders](https://ecnuer996.cn/MeetHere/api/orders)
### 1.3 请求参数
#### 1.3.1 参数解释
参数名      |   参数说明
:----------:|:---------:
id   |  用户id

### 1.4 返回结果
查询成功
```json
{
  "code": 200,
  "result": {
    "siteName":"篮球场1",
    "siteImage":"https://ecnuer996.cn/images/site-images/1basketball1.jpg",
    "venueName":"大学生活动中心",
    "bookTime":"2019-10-20 13:58:05",
    "reserveDate":"2019-10-25",
    "cost":120,
    "beginTime":"18",
    "endTime":"23",
    "state":"未开始"
  },
  "message": "查询成功"
}
```
用户不存在
````json
{
  "code": 250,
  "message": "你传了个假用户,拒绝"
}
````

### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
result      | object       | 查询成功返回的订单信息

