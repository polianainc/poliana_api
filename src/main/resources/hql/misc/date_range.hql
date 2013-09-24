SELECT recipient_ext_id, date, collect_set(transaction_id)
FROM test_table WHERE
UNIX_TIMESTAMP(CONCAT(date, ' 00:00:00')) >= UNIX_TIMESTAMP(CONCAT('2012-03-04', ' 00:00:00')) - 2629743 AND
UNIX_TIMESTAMP(CONCAT(date, ' 00:00:00')) <= UNIX_TIMESTAMP(CONCAT('2012-03-04', ' 00:00:00')) + 2629743
GROUP BY recipient_ext_id, date;
