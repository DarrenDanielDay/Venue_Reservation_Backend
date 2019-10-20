## 5. 场地预约情况
### 1.1 功能描述
查询某场地某天的各个时段的预约情况
### 1.2 请求说明
> 请求方式: GET
  请求URL: [/site-time-list](https://ecnuer996.cn/MeetHere/api/site-time-list?site_id=1&book_date=2019-10-20)
### 1.3 请求参数
参数名      |   参数说明
:----------:|:---------:
site_id   |  场地ID
book_date | 预约日期

### 1.4 返回结果
```json
{
  "date": {
    "bookDate": "2019-10-20",
    "siteTimes": [
      {
        "bookable": false,
        "periodId": 0,
        "period": "7:00~7:30"
      },
      {
        "bookable": false,
        "periodId": 1,
        "period": "7:30~8:00"
      },
      {
        "bookable": false,
        "periodId": 2,
        "period": "8:00~8:30"
      },
      {
        "bookable": true,
        "periodId": 3,
        "period": "8:30~9:00"
      },
      {
        "bookable": true,
        "periodId": 4,
        "period": "9:00~9:30"
      },
      {
        "bookable": true,
        "periodId": 5,
        "period": "9:30~10:00"
      },
      {
        "bookable": true,
        "periodId": 6,
        "period": "10:00~10:30"
      },
      {
        "bookable": true,
        "periodId": 7,
        "period": "10:30~11:00"
      },
      {
        "bookable": true,
        "periodId": 8,
        "period": "11:00~11:30"
      },
      {
        "bookable": true,
        "periodId": 9,
        "period": "11:30~12:00"
      },
      {
        "bookable": true,
        "periodId": 10,
        "period": "12:00~12:30"
      },
      {
        "bookable": true,
        "periodId": 11,
        "period": "12:30~13:00"
      },
      {
        "bookable": true,
        "periodId": 12,
        "period": "13:00~13:30"
      },
      {
        "bookable": true,
        "periodId": 13,
        "period": "13:30~14:00"
      },
      {
        "bookable": true,
        "periodId": 14,
        "period": "14:00~14:30"
      },
      {
        "bookable": true,
        "periodId": 15,
        "period": "14:30~15:00"
      },
      {
        "bookable": true,
        "periodId": 16,
        "period": "15:00~15:30"
      },
      {
        "bookable": true,
        "periodId": 17,
        "period": "15:30~16:00"
      },
      {
        "bookable": true,
        "periodId": 18,
        "period": "16:00~16:30"
      },
      {
        "bookable": true,
        "periodId": 19,
        "period": "16:30~17:00"
      },
      {
        "bookable": true,
        "periodId": 20,
        "period": "17:00~17:30"
      },
      {
        "bookable": true,
        "periodId": 21,
        "period": "17:30~18:00"
      },
      {
        "bookable": true,
        "periodId": 22,
        "period": "18:00~18:30"
      },
      {
        "bookable": true,
        "periodId": 23,
        "period": "18:30~19:00"
      }
    ]
  },
  "code": 200,
  "message": "成功返回此场地预约时段信息"
}
```
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
data      | object       | 返回数据
data.siteTimes | array  | 预约时段<br>列表信息
data.bookDate | string  | 预约日期
siteTimes[i].period | string | 时间段
siteTimes[i].periodId | int | 时间段ID
siteTimes[i].bookable | boolean | 此时间段<br>是否可预约

