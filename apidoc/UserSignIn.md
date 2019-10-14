## 1. 用户登录
### 1.1 功能描述
提供多种方式的用户登录
### 1.2 请求说明
> 请求方式: POST
  请求URL: [/sign-in](https://ecnuer996.cn/MeetHere/api/sign-in)
### 1.3 请求参数
#### 1.3.1 参数解释
参数名      |   参数说明
:----------:|:---------:
id   |  登录ID
method | 登录方式
credential | 登录凭证
#### 1.3.2 Method参数取值

method参数取值      |   登录方式   | 对应的登录ID | 对应的登录凭证
:----------:|:---------: | :--------------: | :--------:
nickname  |  用户名登录 | 用户名 | 密码
phone | 手机登录 | 手机号 | 短信验证码
email | 邮箱登录 | 邮箱地址 | 邮箱验证码
*注：由于没有邮箱服务和短信服务，验证码暂时以静态方式存储在数据库中
#### 1.3.3 参数示例
````json
{
    "id":"亚当",
    "method":"nickname",
    "credential":"password"
}
````

### 1.4 返回结果
#### 1.4.1 用户名登录
登陆成功
```json
{
  "code": 200,
  "data": {
    "id": 10000,
    "nickname": "亚当",
    "avatar": "https://ecnuer996.cn/images/user-avatars/10000.jpg",
    "phone": "12983570972",
    "email": "address@gmail.com"
  },
  "message": "登录成功"
}
```
用户不存在
````json
{
  "code": 500,
  "message": "用户不存在！"
}
````
密码错误
````json
{
  "code": 400,
  "message": "密码错误！"
}
````
### 1.5 返回参数
字段       |字段类型       |字段说明
:----------:|:---------:|:---------:
code     | int        | 错误码
message    | string       | 结果信息
data      | object       | 登陆成功返回<br>的用户信息

