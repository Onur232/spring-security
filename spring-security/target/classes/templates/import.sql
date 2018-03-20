--Insert role. table'a ilk giren role id'si 1 olur. Generatedtype'dan dolayı. bu yüzden user'ın en sonuncu hanesi 1.(role_id)
insert into role (name) values ('ROLE_USER');

--Insert two users (passwords are both 'password')
--password plain text olarak girilmiş. security vulnerability. google'da bcrypt generator arat ve şifre yerine encrypt 
--versiyonu yaz. dümdüz password yazmak yerine. aşağıda true'dan sonra gelen password'ün bcrypt hali.

insert into user (username, enabled,password,role_id) values ('user',true,'$2y$10$8ZhjC6gsWipJn4hflfjAUuI5c0LIw10YwtVQoFrUCGRHMmu/nJR8.',1);
insert into user (username, enabled,password,role_id) values ('user2',true,'$2y$10$8ZhjC6gsWipJn4hflfjAUuI5c0LIw10YwtVQoFrUCGRHMmu/nJR8.',1);

--Insert tasks
insert into task(complete,description,user_id) values (true, 'Code Task entity',1)
insert into task(complete,description,user_id) values (false, 'Discuss users and roles',1)
insert into task(complete,description,user_id) values (false, 'Enable Spring Security',2)
insert into task(complete,description,user_id) values (false, 'Test application',2)