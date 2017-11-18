create schema if not exists db_newj2ee default character set utf8 collate utf8_general_ci;
use db_newj2ee;

create table if not exists t_user(
  username varchar(20) not null,
  password varchar(32) not null,
  email text not null,
  role enum('a','u') not null default 'u',
  realname text not null,
  company text not null,
  primary key (username));

create table if not exists t_meeting(
  m_id int not null auto_increment,
  m_title text not null,
  m_address text not null,
  m_datetime datetime not null,
  m_fee double not null,
  m_deadline date not null,
  unique index m_datetime_unique (m_datetime asc),
  primary key (m_id));

create table if not exists t_announce(
  ann_id int not null auto_increment,
  ann_title text not null,
  ann_text text not null,
  m_id int not null,
  primary key (ann_id),
    foreign key (m_id)
    references t_meeting (m_id)
    on delete cascade
    on update no action);

create table if not exists t_document(
  username varchar(20) not null,
  m_id int not null,
  doc_filename text not null,
  doc_status enum('w','p','f') not null default 'w',
  primary key (username, m_id),
    foreign key (username)
    references t_user (username)
    on delete cascade
    on update no action,
    foreign key (m_id)
    references t_meeting (m_id)
    on delete cascade
    on update no action);

create table if not exists t_meeting_status(
  username varchar(20) not null,
  m_id int not null,
  payway enum('o','p') not null default 'p',
  primary key (username, m_id),
    foreign key (username)
    references t_user (username)
    on delete cascade
    on update no action,
    foreign key (m_id)
    references t_meeting (m_id)
    on delete cascade
    on update no action);

create or replace view v_meeting_count as
select m_id,count(*) as m_count
from t_meeting_status
group by m_id;

create or replace view v_meeting as
select t_meeting.m_id,m_title,m_address,m_datetime,m_fee,m_deadline,
(case when m_count is null then 0 else m_count end) as m_attend
from t_meeting left join v_meeting_count
on t_meeting.m_id=v_meeting_count.m_id;

create or replace view v_mailsending as
select t_user.username,t_meeting.m_id,email,m_title
from t_document,t_meeting,t_user
where t_user.username=t_document.username and t_meeting.m_id=t_document.m_id;

--默认的管理员密码为adminadmin
insert into t_user
values('admin','f6fdffe48c908deb0f4c3bd36c032e72','358930328@qq.com',
		'a','管理员','Windows小游戏研究学会');