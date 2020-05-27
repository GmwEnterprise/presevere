select b.* from wechat_user a
left join wechat_user_relation b
on a.user_id = b.from_user_id
and b.relation_type in (1, 2);