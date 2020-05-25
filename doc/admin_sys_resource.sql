create table sys_resource
(
  id          bigint auto_increment comment '主键ID'
    primary key,
  name        varchar(100)                        null comment '资源名称',
  value       varchar(100)                        null comment '值',
  url         varchar(200)                        null comment '路径',
  type        int       default 0                 null comment '类型：0-菜单，1-页面，2-按钮',
  sort        int                                 null comment '排序',
  parent_id   int                                 null comment '父借点名称',
  remark      varchar(200)                        null comment '备注',
  creat_time  timestamp default CURRENT_TIMESTAMP null,
  update_time timestamp default CURRENT_TIMESTAMP null
)
  comment '资源表';

INSERT INTO admin.sys_resource (id, name, value, url, type, sort, parent_id, remark, creat_time, update_time) VALUES (1, '商户管理', 'table', '/index', 0, 1, null, '测试', '2020-05-24 22:32:24', '2020-05-24 22:32:24');
INSERT INTO admin.sys_resource (id, name, value, url, type, sort, parent_id, remark, creat_time, update_time) VALUES (2, '添加商品', 'addproduct', '/fus/products/addproducts/add-products', 1, 1, 1, null, '2020-05-25 17:53:39', '2020-05-25 17:53:39');
INSERT INTO admin.sys_resource (id, name, value, url, type, sort, parent_id, remark, creat_time, update_time) VALUES (3, '添加服务类别', 'addServerType', '/fus/storemanagement/serverType/addServerType', 1, 2, 1, null, '2020-05-25 17:53:39', '2020-05-25 17:53:39');