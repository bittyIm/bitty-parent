### broker 节点管理

- 所有broker注册到 root;
- 所有broker 到 root 查询 其他路由节点缓存到本地 本地 路由容器;
- 路由节点可以被动失效
- root 可以使 broker 主动失效

### 用户管理

- 所有 user 注册到 root;
- 所有 broker 到 root 查询user 缓存到 本地 用户容器;
- root 可以使 user 主动失效