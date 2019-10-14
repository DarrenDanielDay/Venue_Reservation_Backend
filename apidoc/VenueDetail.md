## 1. 场馆详情页
### 1.1 功能描述
返回场馆详情页的信息
### 1.2 请求说明
> 请求方式: GET
  请求URL: [/venue-detail](https://ecnuer996.cn/MeetHere/api/venue-detail)
### 1.3 请求参数
参数名      |   参数说明
:----------:|:---------:
venue_id   |  所展示详情页的场馆的ID
### 1.4 返回结果
```json
{
  "images": [
    "https://ecnuer996.cn/images/venue-images/1-1.jpg",
    "https://ecnuer996.cn/images/venue-images/1-2.jpg",
    "https://ecnuer996.cn/images/venue-images/1-3.jpg"
  ],
  "address": "上海市普陀区中山北路3663号丽娃河畔",
  "phone": "13946827394",
  "name": "大学生活动中心",
  "sites": [
    {
      "id": 1,
      "name": "篮球场1",
      "venueId": 1,
      "intruction": "篮球场1的简介（位置说明）",
      "image": "https://ecnuer996.cn/images/site-images/1basketball1.jpg",
      "price": 60
    },
    {
      "id": 2,
      "name": "篮球场2",
      "venueId": 1,
      "intruction": "篮球场2的简介（位置说明）",
      "image": "https://ecnuer996.cn/images/site-images/1basketball2.jpg",
      "price": 60
    },
    {
      "id": 3,
      "name": "篮球场3",
      "venueId": 1,
      "intruction": "篮球场2的简介（位置说明）",
      "image": "https://ecnuer996.cn/images/site-images/1basketball2.jpg",
      "price": 60
    }
  ],
  "id": 1,
  "beginTime": "07:00",
  "endTime": "19:00",
  "introduction": "这里是场馆简介"
}
```
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
images     | array        | 场馆图片
address    | string       | 场馆地址
phone      | string       | 场馆联系电话
name       | string       | 场馆名称
sites      | array        | 场地信息
id         | int          | 场馆ID
beginTime  | string       | 最早可预约时间
endTime    | string       | 最迟可预约时间
introduction | string       | 场馆简介

