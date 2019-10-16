## 8. 搜索场地
### 1.1 功能描述
搜索一个场馆的所有场地
### 1.2 请求说明
> 请求方式: GET
  请求URL: [/venue_id](https://ecnuer996.cn/MeetHere/api/site?venue_id=[参数1])
### 1.3 请求参数
参数名      |   参数类型  | 参数说明
:----------:|:---------: | :------:
venue_id   |  int         |  场馆id

### 1.4 返回结果
返回场馆的所有场地信息
```ArrayList
[
    {
        "id":11,
        "name":"数学会议室",
        "venueId":3,
        "intruction":"进行数学学术会议",
        "image":"https://ecnuer996.cn/images/site-images/mathmeeting.jpg",
        "price":65.0
    },
    {
        "id":12,
        "name":"数学讨论室",
        "venueId":3,
        "intruction":"提供数学爱好者一个自由的讨论空间",
        "image":"https://ecnuer996.cn/images/site-images/mathenjoying.jpg",
        "price":20.0
    },
    {
        "id":13,
        "name":"数学研究室",
        "venueId":3,
        "intruction":"拥有相关专业文献，支持独立研究和团队协作",
        "image":"https://ecnuer996.cn/images/site-images/mathstudying.jpg",
        "price":40.0
    }
]
```


