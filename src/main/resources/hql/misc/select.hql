SELECT recipient_ext_id, collect_set(contributor_ext_id), collect_set(contributor_name), sum(amount)
FROM contributions_test WHERE cycle = 2012 GROUP BY recipient_ext_id;