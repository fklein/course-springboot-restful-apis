insert into "user"
    (id, email_address, first_name, last_name, role, ssn, user_name)
values
    (101, 'kreddy@stacksimplify.com', 'Kalyan', 'Reddy', 'admin', 'ssn101', 'kreddy'),
    (102, 'gwiser@stacksimplify.com', 'Greg', 'Wiser', 'admin', 'ssn102', 'gwiser'),
    (103, 'dmark@stacksimplify.com', 'David', 'Mark', 'admin', 'ssn103', 'dmark');

insert into "order"
    (id, description, user_id)
values
    (2001, 'Order 1/1', '101'),
    (2002, 'Order 1/2', '101'),
    (2003, 'Order 1/3', '101'),
    (2004, 'Order 2/1', '102'),
    (2005, 'Order 2/2', '102'),
    (2006, 'Order 3/1', '103');