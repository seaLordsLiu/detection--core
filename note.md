## 设计思路


1. TroubleManage 接收错误信息
2. 通过 TroubleContentGenerator 构造 TroubleContent 核心内容
3. 委托 TroubleDelegate 进行处理


### 0.1.0
优化信息
1. ~~异常描述信息存在问题~~
   - 不直观
   - 内容有时候很多无效信息

2. 去处项目中的 `lombok` 依赖
   - `lombok` 会导致 `idea` 出现源码不匹配的错误提示
   - 子项目也会被引用 `lombok`
