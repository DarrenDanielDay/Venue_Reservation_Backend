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
    "id": 1,
    "site_id": 1,
    "user_id": 10000,
    "book_time": "2019-10-13 14:14:18",
    "date": "2019-10-20",
    "cost": 90,
    "begin_time": 0,
    "end_time": 3,
    "state": 1,
    "comment": null
  },
  "message": "查询成功"
}
```
用户不存在
````json
{
  "code": 250,
  "result": "illegal userId",
  "message": "你传了个假用户,拒绝"
}
````

### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
result      | object       | 查询成功返回的订单信息

