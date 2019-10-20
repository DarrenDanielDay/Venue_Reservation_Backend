## 2. 用户注册
### 1.1 功能描述
用户注册功能，同时检验注册信息的有效性（是否已被注册过）
### 1.2 请求说明
> 请求方式: POST
  请求URL: [/sign-up](https://ecnuer996.cn/MeetHere/api/sign-up)
### 1.3 请求参数
参数名      |   参数说明
:----------:|:---------:
nickname   |  用户名
password | 登录密码
email | 邮箱地址
phone | 用户电话

### 1.4 返回结果
#### 1.4.1 用户名登录
注册成功
```json
{
  "code": 200,
  "data": {
    "id": null,
    "nickname": "注册用户2",
    "avatar": "https://ecnuer996.cn/images/user-avatars/default.jpg",
    "phone": "12029837483",
    "email": "user@163.com"
  },
  "message": "注册成功！"
}
```
用户名已被注册（类似有电话，邮箱如已被注册，注册失败，返回对应提示信息）
````json
{
  "code": 400,
  "message": "用户名已被使用！"
}
````
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
data      | object       | 注册成功返回<br>的用户信息

