## 1. 场馆列表
### 1.1 功能描述
返回场馆的列表信息
### 1.2 请求说明
> 请求方式: GET
  请求URL: [/all-venues](https://ecnuer996.cn/MeetHere/api/all-venues)
### 1.3 请求参数
无请求参数
### 1.4 返回结果
```json
{
  "venues": [
    {
      "id": 1,
      "name": "大学生活动中心",
      "address": "上海市普陀区中山北路3663号丽娃河畔",
      "beginTime": "07:00",
      "endTime": "19:00",
      "cover": "https://ecnuer996.cn/images/venue-images/1-1.jpg"
    },
    {
      "id": 2,
      "name": "中学生活动中心",
      "address": "上海市普陀区中山北路3663号",
      "beginTime": "07:30",
      "endTime": "20:30",
      "cover": "https://ecnuer996.cn/images/venue-images/2-1.jpg"
    }
  ]
}
```
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
venues     | array        | 场馆列表
id         | int          | 场馆ID
name       | string       | 场馆名称
address    | string       | 场馆地址
beginTime  | string       | 最早可预约时间
endTime    | string       | 最迟可预约时间
cover      | string       | 场馆封面图片

