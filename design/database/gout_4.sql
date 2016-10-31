/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/6/2 星期四 20:36:31                        */
/*==============================================================*/


drop index Index_1 on auth;

drop table if exists auth;

drop index Index_1 on buddy;

drop table if exists buddy;

drop index Index_1 on message;

drop table if exists message;

drop index Index_1 on monthyassay;

drop table if exists monthyassay;

drop index Index_1 on patientdetail;

drop table if exists patientdetail;

drop index Index_1 on reminder;

drop table if exists reminder;

drop index Index_3 on user;

drop index Index_2 on user;

drop index Index_1 on user;

drop table if exists user;

drop index Index_1 on usertype;

drop table if exists usertype;

drop index Index_1 on usertype_auth;

drop table if exists usertype_auth;

drop index Index_1 on weekhabit;

drop table if exists weekhabit;

/*==============================================================*/
/* Table: auth                                                  */
/*==============================================================*/
create table auth
(
   id                   int not null auto_increment,
   apiname              varchar(20) not null,
   url                  varchar(100) not null,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on auth
(
   id
);

/*==============================================================*/
/* Table: buddy                                                 */
/*==============================================================*/
create table buddy
(
   relationshipid       int not null auto_increment,
   userid               int,
   userid2              int not null,
   relationshiptype     tinyint,
   re_createtime        timestamp,
   lastcontacttime      timestamp,
   closelevel           TinyInt,
   primary key (relationshipid)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on buddy
(
   userid,
   userid2
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   msgid                integer(20) not null auto_increment,
   rcvuserid            int,
   sduserid             int,
   datetime             timestamp,
   isrcv                tinyint default 0 comment '是否已经接收',
   primary key (msgid)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on message
(
   msgid
);

/*==============================================================*/
/* Table: monthyassay                                           */
/*==============================================================*/
create table monthyassay
(
   assayid              integer(20) not null auto_increment,
   userid               int,
   assay_docid          int,
   diseasecourse        int not null,
   isjointpain          bool not null comment '是否关节痛',
   painpart             varchar(100) comment '关节痛部位',
   isjointswelling      bool not null comment '是否关节肿胀',
   swellingpart         varchar(100),
   isdietchange         bool not null,
   isexercise           bool not null,
   esr                  numeric(10,3) comment '血沉（mm/h）',
   crp                  numeric(10,3) comment 'C反应蛋白（mg/dL）',
   ua                   numeric(10,3) comment '尿酸（umol/L）',
   ganyousanzhi         numeric(10,3) comment '甘油三酯（mmol/L）',
   totalcholesterol     numeric(10,3) comment '总胆固醇（mmol/L）',
   tmdasajzym           numeric(10,3) comment '天门冬氨酸氨基转移酶（U/L）',
   basajzym             numeric(10,3) comment '丙氨酸氨基转移酶（U/L）',
   cr                   numeric(10,3) comment '肌酐（umol/L）',
   cbc                  varchar(40) comment '血常规',
   havetophus           tinyint not null default 0 comment '有无痛风结石',
   b_modeus             tinyint not null default 0 comment '肾脏B超有无泌尿系结石或结晶',
   havehypertension     tinyint not null default 0,
   havediabetes         tinyint not null default 0,
   haveheartdisease     tinyint not null default 0,
   havehlp              tinyint not null default 0,
   haveotherdisease     tinyint not null default 0,
   hypertensionmedicine varchar(200),
   diabetesmedicine     varchar(200),
   heartdiseasemedicine varchar(200),
   hlpmedicine          varchar(200),
   otherdiseasemedicine varchar(500),
   gcsdosage            numeric(10,2) default -1 comment '糖皮质激素剂量',
   colcdosage           numeric(10,2) default -1 comment '秋水仙碱剂量',
   allopurinoldosage    numeric(10,2) default -1 comment '别嘌醇剂量',
   benzbromaronedosage  numeric(10,2) default -1 comment '苯溴马隆剂量',
   nsaiddosage          numeric(10,2) default -1 comment '非甾体抗炎药剂量',
   febuxostatdosage     numeric(10,2) default -1 comment '非布司他剂量',
   createtime           timestamp,
   modifytime           timestamp,
   primary key (assayid)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on monthyassay
(
   assayid,
   userid,
   assay_docid
);

/*==============================================================*/
/* Table: patientdetail                                         */
/*==============================================================*/
create table patientdetail
(
   id                   int not null auto_increment,
   patientid            int,
   docterid             int,
   realname             varchar(50),
   gender               tinyint default 0 comment '性别 1男 2女 0 未定义',
   idcardnumber         varchar(20),
   height               numeric(5,2),
   weight               numeric(5,2),
   age                  tinyint,
   nation               varchar(10),
   nativeplace          varchar(30),
   job                  varchar(30),
   phonenumber          varchar(15),
   email                varchar(50),
   firstvisitdate       datetime,
   isrelativegout       tinyint default false,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on patientdetail
(
   patientid,
   realname,
   docterid
);

/*==============================================================*/
/* Table: reminder                                              */
/*==============================================================*/
create table reminder
(
   reminderid           int not null,
   userid               int,
   remindertime         timestamp not null,
   primary key (reminderid)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on reminder
(
   userid,
   remindertime
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   userid               int not null auto_increment,
   usertypeid           int not null default -1,
   username             varchar(32) not null comment '登录账号',
   token                varchar(32) not null comment '令牌',
   registerdate         timestamp,
   password             varchar(32) not null default '666666799' comment '密码',
   primary key (userid)
);

alter table user comment '用户基本信息表';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create unique index Index_1 on user
(
   username,
   password
);

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create unique index Index_2 on user
(
   userid,
   token
);

/*==============================================================*/
/* Index: Index_3                                               */
/*==============================================================*/
create unique index Index_3 on user
(
   userid,
   usertypeid
);

/*==============================================================*/
/* Table: usertype                                              */
/*==============================================================*/
create table usertype
(
   id                   int not null,
   usertypename         varchar(20),
   primary key (id)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on usertype
(
   id
);

/*==============================================================*/
/* Table: usertype_auth                                         */
/*==============================================================*/
create table usertype_auth
(
   id                   integer(20) not null,
   usertypeid           int not null,
   authid               int not null,
   primary key (id)
);

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on usertype_auth
(
   id
);

/*==============================================================*/
/* Table: weekhabit                                             */
/*==============================================================*/
create table weekhabit
(
   habitid              integer(30) not null auto_increment,
   userid               int,
   createtime           timestamp default CURRENT_TIMESTAMP,
   modifytime           timestamp,
   staplefood           tinyint not null comment '1=米饭,2=面,3=粥,4=面包,5=方便食品,6=其他',
   staplefoodamount     tinyint,
   taste                tinyint comment '1=不辣，2=微辣，3=中辣，4=极辣',
   dietarypreference    tinyint comment '1=海鲜，2=红肉，3=豆类，4=甜食，5=蔬菜，6=其它',
   drinktype            tinyint comment '1=啤酒，2=白酒，3=红酒，4=不饮',
   fishpd               tinyint comment '1=0，2=0-50g，3=50-100g，4=100-150g，5=150-200g，6=200-250g，7=250g以上',
   seafoodpd            tinyint,
   beefpd               tinyint,
   porkpd               tinyint,
   poultrypd            tinyint,
   visceralpd           tinyint,
   vegetablepd          tinyint,
   beanpd               tinyint,
   eggpd                tinyint,
   nutpd                tinyint,
   fruitpd              tinyint,
   saltpd               tinyint,
   beerpd               tinyint comment '1=0，2=0-500ml，3=500-1000ml，4=1000ml以上',
   milkpd               tinyint,
   liquorpd             tinyint,
   wirepd               tinyint,
   teatype              tinyint comment '1=无，2=花茶，3=绿茶，4=红茶，5=乌龙茶，6=普洱茶，7=咖啡，8=含糖饮料，9=苏打水',
   teapd                tinyint,
   primary key (habitid)
);

alter table weekhabit comment '每周生活习惯表 
每一个量都是每日的平均记录';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on weekhabit
(
   habitid,
   userid
);

alter table buddy add constraint FK_Reference_11 foreign key (userid)
      references user (userid) on delete restrict on update restrict;

alter table message add constraint FK_msg_rcv_user foreign key (sduserid)
      references user (userid) on delete restrict on update restrict;

alter table message add constraint FK_msg_send_user foreign key (rcvuserid)
      references user (userid) on delete restrict on update restrict;

alter table monthyassay add constraint FK_monthassay_user foreign key (userid)
      references user (userid) on delete restrict on update restrict;

alter table patientdetail add constraint FK_patientdetail_user foreign key (docterid)
      references user (userid) on delete cascade on update cascade;

alter table patientdetail add constraint FK_userdetail_user foreign key (patientid)
      references user (userid) on delete cascade on update cascade;

alter table reminder add constraint FK_remind_user foreign key (userid)
      references user (userid) on delete cascade on update cascade;

alter table user add constraint FK_user_usertype foreign key (usertypeid)
      references usertype (id) on delete cascade on update cascade;

alter table usertype_auth add constraint FK_Reference_10 foreign key (authid)
      references auth (id) on delete cascade on update cascade;

alter table usertype_auth add constraint FK_auth_usertype foreign key (usertypeid)
      references usertype (id) on delete restrict on update restrict;

alter table weekhabit add constraint FK_weekhabit_user foreign key (userid)
      references user (userid) on delete restrict on update restrict;


delimiter #
create trigger trigger_user after insert
on user for each row
begin
    if new.usertypeid=10 then
        insert patientdetail(patientid)
        values (new.userid);
    end if;
end;#
delimiter ;

