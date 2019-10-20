## 6. 预定场地
### 1.1 功能描述
提交预定场地的请求，生成一个预定订单
### 1.2 请求说明
> 请求方式: POST
  请求URL: [/reserve](https://ecnuer996.cn/MeetHere/api/reserve)
### 1.3 请求参数
参数名      |   参数类型  | 参数说明
:----------:|:---------: | :------:
siteId   |  int         |  场地ID
userId | int           |  用户ID
bookDate | string      | 预约日期
beginPeriod | int       | 开始时段ID
endPeriod  |  int       | 结束时段ID
POST传输数据示例
````json
{
  "siteId":1,
  "userId":10000,
  "bookDate":"2019-10-25",
  "beginPeriod":4,
  "endPeriod":7
}
````
### 1.4 返回结果
预定成功
```json
{
  "code": "200",
  "data": {
    "reservationDetail": {
      "siteName": "篮球场1",
      "siteImage": "https://ecnuer996.cn/images/site-images/1basketball1.jpg",
      "venueName": "大学生活动中心",
      "bookTime": "2019-10-14 21:20:24",
      "reserveDate": "2019-10-25",
      "cost": 120.0,
      "beginTime": "9:00",
      "endTime": "11:00",
      "state": "未开始"
    }
  },
  "message": "预定成功！"
}
```
预定失败
````json
{
  "code": 500,
  "message": "您所选的时段已被预约，请重新选择预约时间！"
}
````
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
data      | object       | 返回数据
data.reservationDetail | object  | 预约详情
reservationDetail.siteName | string  | 预约场地
reservationDetail.siteImage | string | 场地图片URL
reservationDetail.venueName | string | 场地的场馆
reservationDetail.bookTime | string | 操作时间
reservationDetail.reserveDate | string | 预约日期
reservationDetail.cost |  float  | 费用
reservationDetail.beginTime | string | 开始时间
reservationDetail.endTime | string | 结束时间
reservationDetail.state | string | 预约状态

