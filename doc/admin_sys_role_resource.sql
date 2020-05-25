create table sys_role_resource
(
  id          bigint auto_increment comment '主键ID'
    primary key,
  role_id     int                                 null comment '角色ID',
  resource_id int                                 null comment '资源ID',
  create_time timestamp default CURRENT_TIMESTAMP null
)
  comment '角色资源表';

INSERT INTO admin.sys_role_resource (id, role_id, resource_id, create_time) VALUES (1, 1, 1, '2020-05-24 22:39:34');
INSERT INTO admin.sys_role_resource (id, role_id, resource_id, create_time) VALUES (2, 1, 2, '2020-05-25 17:55:23');
INSERT INTO admin.sys_role_resource (id, role_id, resource_id, create_time) VALUES (3, 1, 3, '2020-05-25 17:55:23');