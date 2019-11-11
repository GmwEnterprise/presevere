select a.id,substring_index(substring_index(a.name,',',b.help_topic_id+1),',',-1) name 
from test a join
mysql.help_topic b
on b.help_topic_id < (length(a.name) - length(replace(a.name,',',''))+1)
order by a.id;